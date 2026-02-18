package ac.grim.grimac.checks.impl.aim.snap;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@CheckData(name = "AimSnapA", decay = 0.01, maxBuffer = 3.0)
public class AimSnapA extends Check implements RotationCheck, PacketCheck {

    private boolean enabled;
    private int sampleSize;
    private double accelerationMultiplier;
    private double slowdownMultiplier;
    private double closenessThreshold;
    private double minYaw;
    private double minPitch;
    private int rotationWindow;
    private int requiredSnaps;
    private double bufferDecay;
    private double bufferMaxIncrease;
    private double duplicateThreshold;
    private double maxBuffers;

    private final Deque<Double> lastYawDeltas = new ArrayDeque<>();
    private final Deque<Double> lastPitchDeltas = new ArrayDeque<>();
    private final Deque<Boolean> snapHistory = new ArrayDeque<>();
    private final Deque<SnapData> snapDataHistory = new ArrayDeque<>();

    private double buffer = 0;
    private double lastYaw = 0;
    private double lastPitch = 0;

    public AimSnapA(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);
        sampleSize = config.getIntElse(getConfigName() + ".sample-size", 3);
        accelerationMultiplier = config.getDoubleElse(getConfigName() + ".acceleration-multiplier", 7.5);
        slowdownMultiplier = config.getDoubleElse(getConfigName() + ".slowdown-multiplier", 0.25);
        closenessThreshold = config.getDoubleElse(getConfigName() + ".closeness-threshold", 0.5);
        minYaw = config.getDoubleElse(getConfigName() + ".min-yaw", 0.25);
        minPitch = config.getDoubleElse(getConfigName() + ".min-pitch", 0.25);
        rotationWindow = config.getIntElse(getConfigName() + ".rotation-window", 20);
        requiredSnaps = config.getIntElse(getConfigName() + ".required-snaps", 2);
        bufferDecay = config.getDoubleElse(getConfigName() + ".buffer-decay", 0.25);
        bufferMaxIncrease = config.getDoubleElse(getConfigName() + ".buffer-max-increase", 1.0);
        duplicateThreshold = config.getDoubleElse(getConfigName() + ".duplicate-threshold", 0.001);
        maxBuffers = config.getDoubleElse(getConfigName() + ".max-buffer", 1.0);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (!enabled) return;

        if (!player.actionManager.hasAttackedSince(5000) ||
                player.packetStateData.lastPacketWasTeleport ||
                player.vehicleData.wasVehicleSwitch ||
                player.isCinematicRotation()) {

            lastYawDeltas.clear();
            lastPitchDeltas.clear();
            snapHistory.clear();
            snapDataHistory.clear();
            buffer = 0;
            lastYaw = rotationUpdate.getTo().yaw();
            lastPitch = rotationUpdate.getTo().pitch();
            return;
        }

        double currentYaw = rotationUpdate.getTo().yaw();
        double currentPitch = rotationUpdate.getTo().pitch();

        double deltaYaw = Math.abs(wrapTo180(currentYaw - lastYaw));
        double deltaPitch = Math.abs(currentPitch - lastPitch);

        boolean isDuplicateYaw = deltaYaw < duplicateThreshold;
        boolean isDuplicatePitch = deltaPitch < duplicateThreshold;

        if (isDuplicateYaw && isDuplicatePitch) return;

        lastYaw = currentYaw;
        lastPitch = currentPitch;

        if (!isDuplicateYaw) {
            lastYawDeltas.add(deltaYaw);
            if (lastYawDeltas.size() > sampleSize) lastYawDeltas.removeFirst();
        }

        if (!isDuplicatePitch) {
            lastPitchDeltas.add(deltaPitch);
            if (lastPitchDeltas.size() > sampleSize) lastPitchDeltas.removeFirst();
        }

        boolean snapDetected = false;
        Result snapResult = null;
        String snapAxis = "";

        if (lastYawDeltas.size() == sampleSize) {
            boolean allAboveMin = lastYawDeltas.stream().allMatch(d -> d > minYaw);
            if (allAboveMin) {
                Result result = checkPattern(lastYawDeltas);
                if (result.isFlagged()) {
                    snapDetected = true;
                    snapResult = result;
                    snapAxis = "yaw";
                }
            }
        }

        if (!snapDetected && lastPitchDeltas.size() == sampleSize) {
            boolean allAboveMin = lastPitchDeltas.stream().allMatch(d -> d > minPitch);
            if (allAboveMin) {
                Result result = checkPattern(lastPitchDeltas);
                if (result.isFlagged()) {
                    snapDetected = true;
                    snapResult = result;
                    snapAxis = "pitch";
                }
            }
        }

        if (snapDetected && snapResult != null) {
            double increase = Math.min((snapResult.accelerationRatio / accelerationMultiplier) * bufferMaxIncrease, bufferMaxIncrease);
            buffer = Math.min(buffer + increase, getMaxBuffer());
            snapDataHistory.add(new SnapData(snapAxis, snapResult));
        } else {
            buffer = Math.max(0, buffer - bufferDecay);
        }

        snapHistory.add(snapDetected);
        if (snapHistory.size() > rotationWindow) snapHistory.removeFirst();
        if (snapDataHistory.size() > requiredSnaps * 2) snapDataHistory.removeFirst();

        if (snapHistory.size() == rotationWindow) {
            long snapCount = snapHistory.stream().filter(b -> b).count();
            if (snapCount >= requiredSnaps && buffer >= maxBuffers) {
                String avgStats = calculateAverageStats();
                flagAndAlert(String.format("snaps=%d/%d window=%d | buffer=%.2f | %s",
                        snapCount, requiredSnaps, rotationWindow, buffer, avgStats));
                buffer = 0;
            } else {
                reward();
            }
        }
    }

    private Result checkPattern(Deque<Double> deltas) {
        Double[] arr = deltas.toArray(new Double[0]);
        double d1 = arr[0], d2 = arr[1], d3 = arr[2];
        Result result = new Result(d1, d2, d3, false);
        if (d1 <= 0 || d2 <= 0) return result;

        result.accelerationRatio = d2 / d1;
        result.slowdownRatio = d3 / d2;
        result.closenessRatio = Math.abs(d3 - d1) / d1;

        if (result.accelerationRatio > accelerationMultiplier &&
                result.slowdownRatio < slowdownMultiplier &&
                result.closenessRatio < closenessThreshold) {
            result.setFlagged(true);
        }

        return result;
    }

    private String calculateAverageStats() {
        if (snapDataHistory.isEmpty()) return "no data";

        double avgD1 = 0, avgD2 = 0, avgD3 = 0;
        double avgAcc = 0, avgSlow = 0, avgClose = 0;
        int yawCount = 0, pitchCount = 0;

        for (SnapData data : snapDataHistory) {
            avgD1 += data.result.d1;
            avgD2 += data.result.d2;
            avgD3 += data.result.d3;
            avgAcc += data.result.accelerationRatio;
            avgSlow += data.result.slowdownRatio;
            avgClose += data.result.closenessRatio;

            if ("yaw".equals(data.axis)) yawCount++;
            else pitchCount++;
        }

        int size = snapDataHistory.size();
        avgD1 /= size; avgD2 /= size; avgD3 /= size;
        avgAcc /= size; avgSlow /= size; avgClose /= size;

        return String.format("avg: d1=%.3f d2=%.3f d3=%.3f | acc=%.2f slow=%.2f close=%.2f | yaw=%d pitch=%d",
                avgD1, avgD2, avgD3, avgAcc, avgSlow, avgClose, yawCount, pitchCount);
    }

    private static class SnapData {
        String axis;
        Result result;
        public SnapData(String axis, Result result) { this.axis = axis; this.result = result; }
    }

    private static class Result {
        double d1, d2, d3;
        double accelerationRatio;
        double slowdownRatio;
        double closenessRatio;
        @Setter @Getter private boolean flagged;
        public Result(double d1, double d2, double d3, boolean flagged) {
            this.d1 = d1; this.d2 = d2; this.d3 = d3; this.flagged = flagged;
        }
    }

    private double wrapTo180(double value) {
        value %= 360.0;
        if (value >= 180.0) value -= 360.0;
        if (value < -180.0) value += 360.0;
        return value;
    }
}
