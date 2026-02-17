package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.data.RotationAnalysis;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimC",setback = -1,description = "Three rotation analyses in 1 check")
public class AimC extends Check implements RotationCheck {
    public AimC(@NotNull GrimPlayer player) {
        super(player);
    }
    private RotationAnalysis yawresult = new RotationAnalysis(20,1.30f,9);
    private RotationAnalysis pitchresult = new RotationAnalysis(20,1.30f,7);
    public SampleList Samples = new SampleList(50);

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport || System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle()|| player.rotatationUpdateData.hasTooZeroDelta()) {
            return;
        }

        if (player.actionManager.hasAttackedSince(50) && player.lastAttackedRealTick < 15) {
            double deltaYaw = player.rotatationUpdateData.getDeltaYaw();
            double deltaPitch = player.rotatationUpdateData.getDeltaPitch();

            if (deltaYaw < .5 || deltaPitch < .5) return;
            yawresult.process((float) deltaYaw);
            pitchresult.process((float) deltaPitch);
            if (yawresult.getResult() != null && pitchresult.getResult() != null) {
                RotationAnalysis.HeuristicsResult result = yawresult.getResult();
                RotationAnalysis.HeuristicsResult pitchResult = pitchresult.getResult();
               // player.sendMessage("yd: " + result.getDuplicates() + " pd: " + pitchResult.getDuplicates() + " yra: " + result.getAverage() + " pra: " + pitchResult.getAverage() + " ymin: " + result.getMin() + " ymax: " + result.getMax() + " pmin: " + pitchResult.getMin() + " pmax: " + pitchResult.getMax());
               if  (pitchResult.getDuplicates() == yawresult.getResult().getDuplicates() && pitchResult.getDuplicates() > 1) {
                   flagAndAlert("ypdup: " + ("yd: " + result.getDuplicates() + " pd: " + pitchResult.getDuplicates()));
               }
               if (pitchResult.getDuplicates() > 6 && yawresult.getResult().getDuplicates() < 5) {
                   flagAndAlert("hight pitch activity detected: " + ("yd: " + result.getDuplicates() + " pd: " + pitchResult.getDuplicates()));
               }

                yawresult.reset();

                pitchresult.reset();
            }




            final double YawAcel = player.rotatationUpdateData.getYawAccel();
            final double PitchAcel = player.rotatationUpdateData.getPitchAccel();
            double delta = Math.abs(YawAcel - PitchAcel);
            Samples.add(delta);
        //    player.sendMessage("da: " + delta + " dx: " + deltaYaw + " dp: " + deltaPitch);
            if (Samples.isCollected()) {
                int dublicates = GrimMath.getDuplicates(Samples);
                if (dublicates > 3) {
                    flagAndAlert("d: " + dublicates);
                }
              //  player.sendMessage("d: " + dublicates);




                Samples.clear();

//                    }
//                }
            }
        }
    }

}
