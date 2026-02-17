package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.math.GrimMath;
import ac.grim.grimac.utils.math.LinearRegression;
import com.google.common.collect.Lists;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CheckData(name = "AimAssistL",experimental = true)
public class AimAssistL extends Check implements RotationCheck {
    private final List<Double> samplesYaw = Lists.newArrayList();
    private final List<Double> samplesPitch = Lists.newArrayList();
    private double lastdeltayaw;    private double lastdeltaPitch;
    private double buffer = 0.0;
    public AimAssistL(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.hitticks < 4) {
            double deltaYaw = Math.abs(rotationUpdate.getTo().yaw() - rotationUpdate.getFrom().yaw());
            double deltaPitch = Math.abs(rotationUpdate.getTo().pitch() - rotationUpdate.getFrom().pitch());
            double acelYaw = Math.abs(deltaYaw - lastdeltayaw);
            double acelPitch = Math.abs(deltaPitch - lastdeltaPitch);
            handle:
            {
                if (deltaYaw == 0.0 || deltaPitch == 0.0)
                    break handle;

                samplesYaw.add(acelYaw);
                samplesPitch.add(acelPitch);

                if (samplesYaw.size() + samplesPitch.size() == 60) {

                    val outliersYaw = GrimMath.getOutliers(samplesYaw);
                    val outliersPitch = GrimMath.getOutliers(samplesPitch);

                    Double[] regressionX = new Double[samplesYaw.size()];
                    Double[] regressionY = new Double[samplesPitch.size()];

                    regressionX = samplesYaw.toArray(regressionX);
                    regressionY = samplesPitch.toArray(regressionY);

                    final LinearRegression regression = new LinearRegression(regressionX, regressionY);

                    int fails = 0;

                    for (int i = 0; i < 30; i++) {
                        double tempX = regressionX[i];
                        double tempY = regressionY[i];

                        double predicted = regression.predict(tempX);
                        double subtracted = predicted - tempY;

                        fails = subtracted > 0.1 ? fails + 1 : fails;
                    }

                    double intercepts = regression.interceptStdErr();
                    double slope = regression.slopeStdErr();

                    int outliersX = outliersYaw.first().size() + outliersYaw.second().size();
                    int outliersY = outliersPitch.first().size() + outliersPitch.second().size();
                    if (intercepts < 0.3 && slope > 0.0 && fails > 15 && outliersX < 10 && outliersY > 9) {
                        // if (++buffer > 3) {
                        flagAndAlert(String.format("intercepts=%.3f, slope=%.3f, fails=%d, outliersX=%d, outliersY=%d", intercepts, slope, fails, outliersX, outliersY));
                        //}
                    } else buffer = 0.15;
                    samplesYaw.clear();
                    samplesPitch.clear();
                }


                lastdeltayaw = deltaYaw;
                lastdeltaPitch = deltaPitch;
            }
        }
    }
}
