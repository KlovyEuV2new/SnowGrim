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

@CheckData(name = "AimK",maxBuffer = 3,description = "rotation pattern")
public class AimK extends Check implements RotationCheck {
    private List<Vec2> rotlist = new ArrayList<>();
    private double buffer;

    public AimK(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {
        if (player.hitticks < 20 && player.lastAttackedRealTick < 20) {
            rotlist.add(new Vec2(rotationUpdate.getTo().yaw() - rotationUpdate.getFrom().yaw(), rotationUpdate.getTo().pitch() - rotationUpdate.getFrom().pitch()));
            if (rotlist.size() > 10) check();
        }
    }

    private void check() {
        final List<Float> x = new ArrayList<>(), y = new ArrayList<>();
        for (Vec2 vec2 : this.rotlist) {
            x.add(vec2.x());
            y.add(vec2.y());
        }
        final int disX = GrimMath.getDistinct(x);
        if ((disX < 8 && Math.abs(GrimMath.getAverage(x)) > 2.5)) {
            if (++buffer > getMaxBuffer() && !player.packetStateData.lastPacketWasOnePointSeventeenDuplicate) {
                flagAndAlert(String.format("dist: %.7s", disX));
            }
        } else if (buffer > 0) buffer = buffer - 0.3;

        this.rotlist.clear();
    }
}
