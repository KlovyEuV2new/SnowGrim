package ac.grim.grimac.checks.impl.airstuck;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

@CheckData(name = "AirStuckA")
public class AirStuckA extends Check implements PacketCheck {

    private int positions = 0;
    private long clock = 0L;
    private long lastTransTime = 0L;
    private short oldTransId = 0;

    public AirStuckA(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.disableGrim) return;
        if (isTransaction(event.getPacketType()) && !player.onGround) {
            long currentTime = System.currentTimeMillis();
            long diff = currentTime - this.lastTransTime;
            boolean exempt = player.inVehicle() || player.compensatedEntities.self.isDead ||
                    player.gamemode.equals(GameMode.SPECTATOR) ||
                    player.compensatedWorld.isNearHardEntity(player.boundingBox);

            if (diff > 2000L) {
                if (this.positions == 0 && (this.clock != 0L) && !exempt) {
                    flagAndAlert("player no Response Since " + diff + " ms");
                    player.getSetbackTeleportUtil().executeViolationSetbackDown();
                }
                this.positions = 0;
                this.clock = currentTime;
                this.lastTransTime = currentTime;
                this.oldTransId = (short) player.getLastTransactionSent();
            }
        }

        if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            this.positions++;
        }
        else if (event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE ||
                event.getPacketType() == PacketType.Play.Client.VEHICLE_MOVE) {
            this.positions++;
        }
    }
}
