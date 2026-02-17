package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.packetentity.PacketEntity;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimE",maxBuffer = 3,description = "invalid acelx factor")
public class AimE extends Check implements PacketCheck {
    private double buffer;

    public AimE(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity action = new WrapperPlayClientInteractEntity(event);
            if (action.getAction() != WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                return;
            }

            PacketEntity target = player.compensatedEntities.entityMap.get(action.getEntityId());
            if (target == null) return;
            if (target.type == null || target.type != EntityTypes.PLAYER || player.lastAttackedRealTick > 20) { //на спавне проще попадать и некоторые любят имитировать снапы которые этот чек и детектить
                return;
            }
            float dx = player.rotatationUpdateData.getDeltaYaw();
            float ldx = player.rotatationUpdateData.getLastDeltaYaw();

            float acelx = Math.abs(dx - ldx);
            double moveFactor = player.deltaXZ();
            boolean invalid = acelx > 43.0F && acelx < 180 && moveFactor > 0.15f;
            boolean exempt =  player.inVehicle() || player.packetStateData.lastPacketWasTeleport;
            if (invalid && !exempt) {
                if (buffer++ > getMaxBuffer()) {

                    final String info = String.format(
                            "axc= , b= [%.5f] %.1f",
                            acelx, buffer
                    );

                    flagAndAlert(info);
                }
            } else {
                buffer = Math.max(0.0D, buffer - 0.4);
                if (buffer == 0.0D) {
                    reward();
                }
            }
        }
    }
}
