package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;

import java.util.HashSet;
import java.util.Set;

@CheckData(name = "SimulationB", setback = 1, description = "Velocity Repeat")
public class SimulationB extends Check implements PostPredictionCheck {

    private static final double MIN_XZ_DELTA = 0.08;
    private static final double MIN_Y_DELTA = 0.08;

    private Double lastX = null, lastY = null, lastZ = null;
    private Double lastDeltaX = null, lastDeltaY = null, lastDeltaZ = null;

    private int repeats = 0;
    private String deltaXReason = null, deltaYReason = null, deltaZReason = null;

    private static final Set<Double> ALLOWED_Y_DELTAS = new HashSet<>();
    private static final Set<Double> ALLOWED_XZ_DELTAS = new HashSet<>();

    private static final Set<Double> TRIMMED_Y = new HashSet<>();
    private static final Set<Double> TRIMMED_XZ = new HashSet<>();

    private static final Set<Double> BLACKLIST_Y = new HashSet<>();
    private static final Set<Double> BLACKLIST_XZ = new HashSet<>();

    private static final Set<Double> BLACKLIST_DIV_Y = new HashSet<>();
    private static final Set<Double> BLACKLIST_DIV_XZ = new HashSet<>();

    private boolean debug;
    private boolean enabled;

    static {
        ALLOWED_Y_DELTAS.add(0.04999999701976776);
        ALLOWED_Y_DELTAS.add(0.050000011920928955);

        ALLOWED_XZ_DELTAS.add(0.04999999701976776);
        ALLOWED_XZ_DELTAS.add(0.050000011920928955);

        ALLOWED_Y_DELTAS.add(0.1176000022888175);
        ALLOWED_Y_DELTAS.add(0.15000000596046448);
        ALLOWED_Y_DELTAS.add(0.1523351865055714);
        ALLOWED_Y_DELTAS.add(0.07840000152587834);

        ALLOWED_Y_DELTAS.add(0.03999999910593033);
        ALLOWED_Y_DELTAS.add(0.03999999999999915);
        ALLOWED_Y_DELTAS.add(0.039999999999995595);
        ALLOWED_Y_DELTAS.add(0.0400000000000027);

        ALLOWED_XZ_DELTAS.add(0.001011080264741171);
        ALLOWED_XZ_DELTAS.add(0.15000000596046448);
        ALLOWED_XZ_DELTAS.add(0.0016067271901443192);
        ALLOWED_XZ_DELTAS.add(0.001810771141185441);

        ALLOWED_XZ_DELTAS.add(8.0143895344E-5);
        ALLOWED_XZ_DELTAS.add(8.794294397462821E-5);
        ALLOWED_XZ_DELTAS.add(0.0001280150827517874);
        ALLOWED_XZ_DELTAS.add(0.15490000059546493);
        ALLOWED_XZ_DELTAS.add(0.0025580126639175121);
        ALLOWED_XZ_DELTAS.add(0.000118192207109880183);
        ALLOWED_XZ_DELTAS.add(9.53249511229198378E-4);
        ALLOWED_XZ_DELTAS.add(0.002079006644768011);
        ALLOWED_XZ_DELTAS.add(0.0028473061706479896);
        ALLOWED_XZ_DELTAS.add(0.15000000596046403);
        ALLOWED_XZ_DELTAS.add(0.0023880541581600134);
        ALLOWED_XZ_DELTAS.add(0.0023880541581604575);
        ALLOWED_XZ_DELTAS.add(0.15000000596046492);

        ALLOWED_XZ_DELTAS.add(7.775549726881326E-4);
        ALLOWED_XZ_DELTAS.add(0.002771342182771086);

        ALLOWED_Y_DELTAS.add(0.3400000110268664);
        ALLOWED_Y_DELTAS.add(0.29367097865790726);

        for (double d : ALLOWED_Y_DELTAS)
            TRIMMED_Y.add(removeLast2DigitsStatic(Math.abs(d)));

        for (double d : ALLOWED_XZ_DELTAS)
            TRIMMED_XZ.add(removeLast2DigitsStatic(Math.abs(d)));

        BLACKLIST_Y.add(removeLast2DigitsStatic(0.800000011920929));

        BLACKLIST_DIV_Y.add(0.000800000011);
        BLACKLIST_DIV_XZ.add(0.000800000011);
    }

    public SimulationB(GrimPlayer player) {
        super(player);
    }

    private static double removeLast2DigitsStatic(double value) {
        String s = Double.toString(value);
        if (!s.contains(".")) return value;
        int dot = s.indexOf('.');
        String intPart = s.substring(0, dot);
        String fracPart = s.substring(dot + 1);
        if (fracPart.length() < 6)
            return value;
        String newFrac = fracPart.substring(0, fracPart.length() - 3);
        return Double.parseDouble(intPart + "." + newFrac);
    }

    private double removeLast2Digits(double value) {
        return removeLast2DigitsStatic(value);
    }

    private int tickExempt;
    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (!enabled) return;
        if (player.disableGrim || player.onGround || player.lastOnGround) return;
        tickExempt++;
        if (!predictionComplete.isChecked() ||
                predictionComplete.getData().isTeleport() ||
                player.getSetbackTeleportUtil().blockOffsets ||
                player.packetStateData.lastPacketWasTeleport ||
                player.isClimbing || player.wasTouchingWater ||
                player.wasTouchingLava || player.inVehicle()) {
            tickExempt = 0;
            return;
        }

        double x = player.x;
        double y = player.y;
        double z = player.z;

        repeats = 0;
        deltaXReason = null;
        deltaYReason = null;
        deltaZReason = null;

        checkAxes(x, y, z);

        if (tickExempt > 3 && repeats > 0 && !player.compensatedWorld.isNearHardEntity(player.boundingBox)) {

            StringBuilder sb = new StringBuilder("directions=");

            if (deltaXReason != null) sb.append("x(").append(deltaXReason).append("),");
            if (deltaYReason != null) sb.append("y(").append(deltaYReason).append("),");
            if (deltaZReason != null) sb.append("z(").append(deltaZReason).append("),");

            String result = sb.toString();
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1);
            }

            if (flagAndAlert(result)) {
                if (shouldSetback()) {
                    player.getSetbackTeleportUtil().executeViolationSetbackDown();
                }
            }
        } else {
            reward();
        }
    }

    private void checkAxes(double x, double y, double z) {
        checkYAxis(y);
        checkXAxis(x);
        checkZAxis(z);
    }

    private boolean isInteger(double v) {
        return Math.abs(v - Math.round(v)) < 5e-9;
    }

    private void checkYAxis(double y) {
        if (lastY != null) {
            double deltaY = y - lastY;
            double abs = Math.abs(deltaY);
            double trimmed = removeLast2Digits(abs);

            if (abs > MIN_Y_DELTA) {
                double trimmedLast = lastDeltaY == null ? Double.NaN : removeLast2Digits(Math.abs(lastDeltaY));

                if (TRIMMED_Y.contains(trimmed)) {
                    lastDeltaY = deltaY;
                    lastY = y;
                    return;
                }

                if (BLACKLIST_Y.contains(trimmed)) {
                    repeats++;
                    deltaYReason = "BLACKLIST-HIT:" + trimmed;
                }

                for (double b : BLACKLIST_DIV_Y) {
                    if (b != 0) {
                        double div = abs / b;
                        if (isInteger(div)) {
                            repeats++;
                            deltaYReason = "BLACKLIST-DIV " + (debug ? String.valueOf(abs) : String.format("%.7f",abs)) + "/"
                                    + (debug ? String.valueOf(b) : String.format("%.6f",b)) + "=" + (debug ? String.valueOf(div) : String.format("%.5f",div));
                        }
                    }
                }

                if (lastDeltaY != null && trimmed == trimmedLast) {
                    repeats++;
                    deltaYReason = debug ? String.valueOf(deltaY) : String.format("%.6f",deltaY);
                }
                lastDeltaY = deltaY;
            }
        }
        lastY = y;
    }

    private void checkXAxis(double x) {
        if (lastX != null) {
            double deltaX = x - lastX;
            double abs = Math.abs(deltaX);
            double trimmed = removeLast2Digits(abs);

            if (abs > MIN_XZ_DELTA) {
                double trimmedLast = lastDeltaX == null ? Double.NaN : removeLast2Digits(Math.abs(lastDeltaX));

                if (TRIMMED_XZ.contains(trimmed)) {
                    lastDeltaX = deltaX;
                    lastX = x;
                    return;
                }

                if (BLACKLIST_XZ.contains(trimmed)) {
                    repeats++;
                    deltaXReason = "BLACKLIST-HIT:" + trimmed;
                }

                for (double b : BLACKLIST_DIV_XZ) {
                    if (b != 0) {
                        double div = abs / b;
                        if (isInteger(div)) {
                            repeats++;
                            deltaYReason = "BLACKLIST-DIV " + (debug ? String.valueOf(abs) : String.format("%.7f",abs)) + "/"
                                    + (debug ? String.valueOf(b) : String.format("%.6f",b)) + "=" + (debug ? String.valueOf(div) : String.format("%.5f",div));
                        }
                    }
                }

                if (lastDeltaX != null && trimmed == trimmedLast) {
                    repeats++;
                    deltaXReason = debug ? String.valueOf(deltaX) : String.format("%.6f",deltaX);
                }
                lastDeltaX = deltaX;
            }
        }
        lastX = x;
    }

    private void checkZAxis(double z) {
        if (lastZ != null) {
            double deltaZ = z - lastZ;
            double abs = Math.abs(deltaZ);
            double trimmed = removeLast2Digits(abs);

            if (abs > MIN_XZ_DELTA) {
                double trimmedLast = lastDeltaZ == null ? Double.NaN : removeLast2Digits(Math.abs(lastDeltaZ));

                if (TRIMMED_XZ.contains(trimmed)) {
                    lastDeltaZ = deltaZ;
                    lastZ = z;
                    return;
                }

                if (BLACKLIST_XZ.contains(trimmed)) {
                    repeats++;
                    deltaZReason = "BLACKLIST-HIT:" + trimmed;
                }

                for (double b : BLACKLIST_DIV_XZ) {
                    if (b != 0) {
                        double div = abs / b;
                        if (isInteger(div)) {
                            repeats++;
                            deltaYReason = "BLACKLIST-DIV " + (debug ? String.valueOf(abs) : String.format("%.7f",abs)) + "/"
                                    + (debug ? String.valueOf(b) : String.format("%.6f",b)) + "=" + (debug ? String.valueOf(div) : String.format("%.5f",div));
                        }
                    }
                }

                if (lastDeltaZ != null && trimmed == trimmedLast) {
                    repeats++;
                    deltaZReason = debug ? String.valueOf(deltaZ) : String.format("%.6f",deltaZ);
                }
                lastDeltaZ = deltaZ;
            }
        }
        lastZ = z;
    }

    @Override
    public void onReload(ConfigManager config) {
        debug = config.getBooleanElse(getConfigName() + ".debug", false);
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);
    }
}
