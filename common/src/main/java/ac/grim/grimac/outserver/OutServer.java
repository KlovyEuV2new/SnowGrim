package ac.grim.grimac.outserver;

import ac.grim.grimac.api.config.ConfigManager;
import lombok.Getter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class OutServer {

    public String host;
    public int port;
    public ConnectionType type;
    public String token;

    @Getter
    private volatile boolean isValidToken = false;

    public enum ConnectionType {
        TCP, HTTP, HTTPS
    }

    private static final ExecutorService OUT_EXECUTOR =
            Executors.newFixedThreadPool(2, r -> {
                Thread t = new Thread(r, "OutServer-Async");
                t.setDaemon(true);
                return t;
            });

    public OutServer(String host, int port, ConnectionType type, String token) {
        this.host = host;
        this.port = port;
        this.type = type;
        this.token = token;

        CompletableFuture
                .delayedExecutor(3, TimeUnit.SECONDS, OUT_EXECUTOR)
                .execute(this::checkAndSetValid);
    }

    public void reloadSettings(ConfigManager config) {
        this.host = config.getStringElse("server.host", "127.0.0.1");
        this.port = config.getIntElse("server.port", 8080);

        try {
            this.type = ConnectionType.valueOf(
                    config.getStringElse("server.connection.type", "TCP")
            );
        } catch (Exception e) {
            this.type = ConnectionType.TCP;
        }

        this.token = config.getStringElse("server.token", "");

        CompletableFuture
                .delayedExecutor(1, TimeUnit.SECONDS, OUT_EXECUTOR)
                .execute(this::checkAndSetValid);
    }

    public CompletableFuture<List<String>> sendAsync(List<String> args, boolean isTokenCheck) {
        return CompletableFuture.supplyAsync(() -> {
            return switch (type) {
                case TCP -> sendTcp(args, isTokenCheck);
                case HTTP -> sendHttp(args, false, isTokenCheck);
                case HTTPS -> sendHttp(args, true, isTokenCheck);
            };
        }, OUT_EXECUTOR);
    }

    public CompletableFuture<List<String>> checkToken(String token) {
        return sendAsync(List.of(token), true);
    }

    public boolean isValid() {
        return isValidToken;
    }

    public void checkAndSetValid() {
        checkToken(token).thenAccept(list -> {
            if (list.isEmpty()) {
                isValidToken = false;
                return;
            }

            isValidToken = "valid:token".equals(list.get(0));
        });
    }

    private List<String> sendTcp(List<String> args, boolean isTokenCheck) {
        if (!isValidToken && !isTokenCheck) return List.of("invalid:token");
        if (args == null) return List.of("error:null_args");

        for (int attempt = 0; attempt < 3; attempt++) {
            try (Socket socket = new Socket()) {

                socket.connect(new InetSocketAddress(host, port), 3000);
                socket.setSoTimeout(5000);

                List<String> response = new ArrayList<>();

                try (PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

                    for (String arg : args) out.println(arg);
                    out.println("__END__");

                    String line;
                    while ((line = in.readLine()) != null) {
                        if ("__END__".equals(line)) break;
                        response.add(line);
                    }
                }

                return response;

            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }

        return List.of("error:connection_failed");
    }

    private List<String> sendHttp(List<String> args, boolean https, boolean isTokenCheck) {
        if (!isValidToken && !isTokenCheck) return List.of("invalid:token");

        List<String> response = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder();
            for (String arg : args) {
                query.append("c=")
                        .append(URLEncoder.encode(arg, StandardCharsets.UTF_8))
                        .append("&");
            }
            if (!query.isEmpty()) query.setLength(query.length() - 1);

            String protocol = https ? "https" : "http";
            URL url = new URL(protocol + "://" + host + ":" + port + "/check?" + query);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = in.readLine()) != null) {
                    response.add(line);
                }
            }

            con.disconnect();

        } catch (IOException ignored) {
        }

        return response;
    }

    public static void shutdown() {
        OUT_EXECUTOR.shutdownNow();
    }
}
