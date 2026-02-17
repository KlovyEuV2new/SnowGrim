package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@CheckData(name = "AimJ",setback = -1)
public class AimJ extends Check implements RotationCheck {
    private final List<Double> pitchChanges = new ArrayList<>();
    private final List<Double> yawChanges = new ArrayList<>();
    private final List<Long> timestamps = new ArrayList<>();
    private final SampleList<Float> yawSamples = new SampleList<>(20);
    private final SampleList<Double> deviationAverage = new SampleList<>(5);
    private final SampleList<Float> yawSamples4 = new SampleList<>(20);
    private final SampleList<Double> samples2 = new SampleList<>(30);
    private double last;
    private double lastDeviation;

    public AimJ(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {



        if (player.actionManager.hasAttackedSince(80)) {
            float deltaYaw = player.rotatationUpdateData.getDeltaYaw();

            if (deltaYaw == 0.0 || deltaYaw > 20.0F) return;

            yawSamples.add(deltaYaw);

            if (yawSamples.isCollected()) {
                double deviation = GrimMath.getStandardDeviation(yawSamples);
                double average = GrimMath.getAverage(yawSamples);
                double difference = Math.abs(deviation - lastDeviation);

                if (average > 3.5 && difference < 0.025) {
                    flagAndAlert("(type=A) " + format(average) + " DIFF " + format(difference));
                }

                lastDeviation = deviation;
                yawSamples.clear();
            }

        }
        if (player.actionManager.hasAttackedSince(80) && player.lastAttackedRealTick < 15) {
            float deltaYaw = player.rotatationUpdateData.getDeltaYaw();

            if (deltaYaw == 0.0 || deltaYaw > 20.0F) return;

            yawSamples4.add(deltaYaw);

            if (yawSamples4.isCollected()) {
                double deviation = GrimMath.getStandardDeviation(yawSamples4);

                deviationAverage.add(deviation);

                if (deviationAverage.isCollected()) {
                    double avg = GrimMath.getAverage(deviationAverage);

                    if (avg < 12.5) {
                        flagAndAlert("(type=C) AVG " + format(avg));
                    }
                    deviationAverage.clear();
                }
                yawSamples4.clear();
            }
        }
        if (player.hitticks < 5) {
            double deviation = getDeviation();
            samples2.add(deviation);

            if (samples2.isCollected()) {
                double stDev = GrimMath.getStandardDeviation(samples2);
                double difference = Math.abs(stDev - last);
                double mean = calculateMean(samples2);

                boolean smooth = stDev < 0.5 && difference < 0.05 && mean < 0.01;
                if (smooth) {
                        flagAndAlert( "(type=G) S " + format(stDev) + " M " + format(mean));
                }
                last = stDev;
                samples2.clear();
            }
        }
    }







    private double getDeviation() {
        float currentYaw = player.rotatationUpdateData.getDeltaYaw();
        float currentPitch = player.rotatationUpdateData.getDeltaPitch();

        float previousYaw = player.rotatationUpdateData.getLastDeltaYaw(); // Get the previous yaw angle
        float previousPitch = player.rotatationUpdateData.getLastDeltaPitch(); // Get the previous pitch angle

        float yawDerivation = currentYaw - previousYaw;
        float pitchDerivation = currentPitch - previousPitch;

        // Clamp!
        double clampedYaw = clamp180(yawDerivation);
        double clampedPitch = clamp180(pitchDerivation);

        // Calculate symmetric deviation
        return (clampedYaw + clampedPitch) / 2;
    }

    private double calculateMean(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Input list is null or empty");
        }

        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }

        return sum / values.size();
    }

    public static double clamp180(double theta) {
        theta %= 360.0;
        if (theta >= 180.0) {
            theta -= 360.0;
        }
        if (theta < -180.0) {
            theta += 360.0;
        }
        return theta;
    }
}
