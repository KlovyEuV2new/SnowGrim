package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.math.GrimMath;
import ac.grim.grimac.utils.math.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@CheckData(name = "AimL",maxBuffer = 4,description = "snap rotation like detect")
public class AimL extends Check implements RotationCheck {
    private List<Vec2> rotlist = new ArrayList<>();
    private double buffer;

    public AimL(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.hitticks < 20 && player.lastAttackedRealTick < 15) {
            rotlist.add(new Vec2(rotationUpdate.getTo().yaw() - rotationUpdate.getFrom().yaw(), rotationUpdate.getTo().pitch() - rotationUpdate.getFrom().pitch()));
            if (rotlist.size() > 25) check();
        }
    }

    private void check() {
        if (true) {
            List<Float> x = new ArrayList<>(), y = new ArrayList<>();
            for (Vec2 vec2 : this.rotlist) {
                x.add(vec2.x());
                y.add(vec2.y());
            }

            final double kTest = GrimMath.KsgoTest(GrimMath.getJiffDelta(x, 6), Function.identity());
            {
                if (kTest > 7 && Math.abs(GrimMath.getAverage(x, true)) < 13) {
                    if (kTest > 90) {
                        if (++buffer > getMaxBuffer()) {
                            flagAndAlert(" [" + kTest + "], [" + player.rotatationUpdateData.getDeltaYaw() + "], " + 0.0f);
                        }
                    }
                    else if (buffer > 0) {
                        buffer = buffer - 0.5;
                    }
                } else {
                }
            }

            this.rotlist.clear();
        }
    }
}
