package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.data.PlayerRotationData;
import ac.grim.grimac.utils.data.SampleList;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimD",setback = -1)
public class AimD extends Check implements RotationCheck {
    private double count;

    private SampleList<Double> yawSamplesslinky = new SampleList<>(50), pitchSamplesslinky = new SampleList<>(50);



    public AimD(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(500L) && player.lastAttackedRealTick < 15) {
            PlayerRotationData rotationTracker = player.rotatationUpdateData;
            double yawDelta = rotationTracker.getDeltaYaw();
            double pitchDelta = rotationTracker.getDeltaPitch();

            yawSamplesslinky.add(yawDelta);
            pitchSamplesslinky.add(pitchDelta);

            if (pitchSamplesslinky.isCollected() && yawSamplesslinky.isCollected()) {

                int distinctYaw = (int) yawSamplesslinky.stream().distinct().count();
                int distinctPitch = (int) pitchSamplesslinky.stream().distinct().count();

                if (distinctYaw > 40 && distinctPitch <= distinctYaw / 2 && distinctPitch > 10) {
                    flagAndAlert("Y " + distinctYaw + " P " + distinctPitch);
                    pitchSamplesslinky.clear();
                    yawSamplesslinky.clear();
                }
            }
        }
    }
}
