package ac.grim.grimac.checks.impl.misc.connection;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "ConnectionB", description = "Detects suspicious ping changes", experimental = true)
public class ConnectionB extends Check implements PacketCheck {
    private final SampleList<Double> delaysavg = new SampleList<>(35);
    private final SampleList<Double> delaydev = new SampleList<>(35);

    public ConnectionB(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.packetStateData.lastPacketWasTeleport || System.currentTimeMillis() - player.joinTime < 5000 || player.inVehicle()) {
            return;
        }

        if (isTickPacket(event.getPacketType()) && player.actionManager.hasAttackedSince(120)) {
            double delay = player.getTransactionPing();
            delaysavg.add(delay);
            delaydev.add(delay);
            if (delaysavg.isCollected() && delaydev.isCollected()) {
                double avg = GrimMath.getAverage(delaysavg);
                double dev = GrimMath.getStandardDeviation(delaydev);
                boolean isMoving = player.deltaXZ() > 0.01;
                if (avg > 300 && dev > 500 && isMoving) {
                    flagAndAlert(String.format("avg=%.1f, dev=%.1f, ticks=%s", avg, dev, player.actionManager.getLastAttack()));
                }
                delaysavg.clear();
                delaydev.clear();
            }
        }
    }
}
