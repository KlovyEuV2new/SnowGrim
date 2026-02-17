package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;


/**
 * AUTHOR: M4tr0x
 */
@CheckData(name = "AimG",setback = -1,description =  "Detect Aura Rotation toggling")
public class AimG extends Check implements RotationCheck {
    private double lastX;

    public AimG(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(500L) && !player.packetStateData.lastPacketWasTeleport && !(player.compensatedEntities.self.getRiding() != null)) {
            double dx = rotationUpdate.getDeltaXRotABS();
            double acelx = Math.abs(dx - lastX);
            boolean motionxz = player.deltaXZ() > 0.06;
            if (Math.abs(dx) > 170.0f && lastX < 50 && acelx > 100 && motionxz) {

                String deltax = String.format("%.5f", dx);
                String ldeltax=   String.format("%.5f", lastX);
                String acelX = String.format("%.5f", acelx);
                    flagAndAlert("dx: " + deltax + " ldx: " + ldeltax + " ax: " + acelX) ;


            }
            lastX = dx;
        }
    }
}
