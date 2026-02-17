package ac.grim.grimac.checks.server.combat;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.outserver.OutServer;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CheckData(name = "AutoTotem")
public class AutoTotem extends Check implements PacketCheck {
    public long lastClick = -1;
    public long lastClose = -1;
    public AutoTotem(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!event.isCancelled() && !player.disableGrim) {
            OutServer outServer = GrimAPI.INSTANCE.getOutServer();
            long now = System.nanoTime();
            if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
                if (lastClick > 0 && lastClose > 0) {
                    List<String> argsToServer = List.of(
                            outServer.token, // 1 - должен быть токен
                            getCheckName(), // 2 - название проверки
                            event.getPacketType().getName(), // 3+ аргументы
                            String.valueOf(lastClick),
                            String.valueOf(lastClose)
                    );
                    CompletableFuture<List<String>> output = outServer.sendAsync(argsToServer,false);

                    output.thenAccept(args -> {
                        if (args.size() == 2) {
                            String action = args.get(0);
                            String description = args.get(1);
                            switch (action) {
                                case "FLAG": {
                                    flagAndAlert(description);
                                    return;
                                }
                                case "ALERT": {
                                    alert(description);
                                    return;
                                }
                                case "SETBACK": {
                                    player.getSetbackTeleportUtil().teleportBack();
                                    return;
                                }
                            }
                        }
                    });
                }
                lastClick = now;
            } else if (event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {
                if (lastClick > 0 && lastClose > 0) {
                    List<String> argsToServer = List.of(
                            outServer.token, // 1 - должен быть токен
                            getCheckName(), // 2 - название проверки
                            event.getPacketType().getName(), // 3+ аргументы
                            String.valueOf(lastClick),
                            String.valueOf(lastClose)
                    );
                    CompletableFuture<List<String>> output = outServer.sendAsync(argsToServer,false);

                    output.thenAccept(args -> {
                        if (args.size() == 2) {
                            String action = args.get(0);
                            String description = args.get(1);
                            switch (action) {
                                case "FLAG": {
                                    flagAndAlert(description);
                                    return;
                                }
                                case "ALERT": {
                                    alert(description);
                                    return;
                                }
                                case "SETBACK": {
                                    player.getSetbackTeleportUtil().teleportBack();
                                    return;
                                }
                            }
                        }
                    });
                }
                lastClose = now;
            }
        }
    }
}
