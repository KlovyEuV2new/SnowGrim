package ac.grim.grimac.checks.impl.misc.connection;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;

@CheckData(name = "ConnectionA", description = "Detects suspicious ping changes", experimental = true)
public class ConnectionA extends Check implements PacketCheck {
    private final SampleList<Double> delays = new SampleList<>(35);
    private double buffer;
    public ConnectionA(GrimPlayer player) {
        super(player);
    }
    public void onPacketReceive(PacketReceiveEvent event) {
        if (isTickPacket(event.getPacketType())) {
            if (player.packetStateData.lastPacketWasTeleport || System.currentTimeMillis() - player.joinTime < 5000 || player.inVehicle()) {
                return;
            }

            double delay = player.getTransactionPing();
            //String debug1 = "d: " + delay;
            //player.sendMessage(debug1);
            delays.add(delay);
            if (delays.isCollected()) {
                double avd = GrimMath.getAverage(delays);
              //  boolean isMoving = player.positionProcessor.isMoving();
                // String debug2 = "avg: " + avd;
                //player.sendMessage(debug2);
                delays.clear();
                if (avd > 300) {
                    if (++buffer > 4) {
                        flagAndAlert(String.format("avg=%.1f, delay=%s, buffer=%.1f", avd, delay, buffer));
                    if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetback();
                    }
                } else if (buffer > 0) {
                    buffer = buffer - 0.5;
                }
            }
        }
    }
}
