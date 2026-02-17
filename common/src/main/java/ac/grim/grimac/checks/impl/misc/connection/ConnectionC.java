package ac.grim.grimac.checks.impl.misc.connection;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.TimeMilistUtil;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;

@CheckData(name = "ConnectionC", description = "Detects suspicious decay batch changes", experimental = true,setback = 4)
public class ConnectionC extends Check implements PacketCheck {
    private int laggetTicking;
    private final TimeMilistUtil lastresponce;
    private final SampleList<Double> delaysavg = new SampleList<>(10);
    private final SampleList<Double> delaydev = new SampleList<>(10);
    public ConnectionC(GrimPlayer player) {
        super(player);
        lastresponce = new TimeMilistUtil(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.packetStateData.lastPacketWasTeleport || System.currentTimeMillis() - player.joinTime < 5000 || player.inVehicle()) {
            return;
        }

        if (isTickPacket(event.getPacketType()) && player.actionManager.hasAttackedSince(250)) {
            double delay = lastresponce.getPassed();
            int ticks = (int) player.hitticks;
            delaysavg.add(delay);
            delaydev.add(delay);
             if (delay > 130) {
                 if (++laggetTicking > 7) {
                     if (flagAndAlert(String.format("ticks=%s , delay=%.1f,", ticks, delay))) {
                         if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetbackDown();
                     }
                 }
             }
             else if (laggetTicking > 0) {
                 laggetTicking=0;
             }
            if (delaysavg.isCollected() && delaydev.isCollected()) {
                double avg = GrimMath.getAverage(delaysavg);
                double dev = GrimMath.getStandardDeviation(delaydev);
                if (dev > 200 && avg > 200) {
                    flagAndAlert(String.format("avg=%.1f, dev=%.1f, ticks=%s", avg, dev, ticks));
                }
                String info = "avg: " + avg + ", dev: " + dev;
             //  player.debug(info);
                delaysavg.clear();
                delaydev.clear();
            }
        }
        if (isTransaction(event.getPacketType()) && player.packetStateData.lastTransactionPacketWasValid) {
            lastresponce.reset();
        }
    }
}
