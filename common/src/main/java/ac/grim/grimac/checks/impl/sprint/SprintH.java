package ac.grim.grimac.checks.impl.sprint;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

@CheckData(name = "SprintH", description = "invalid sprint prediction pattern", maxBuffer = 3)
public class SprintH extends Check implements PostPredictionCheck {
    private int ticksSienceAttack = Integer.MAX_VALUE, ticksSienceStopSprint = Integer.MAX_VALUE,
            ticksSienceStartSprint = Integer.MAX_VALUE;
    private double buffer;

    public SprintH(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onReload(ConfigManager config) {
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity action = new WrapperPlayClientInteractEntity(event);
            if (action.getAction() == WrapperPlayClientInteractEntity.InteractAction.ATTACK) ticksSienceAttack = 0;
        } else if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            WrapperPlayClientEntityAction action = new WrapperPlayClientEntityAction(event);
            if (action.getAction() == WrapperPlayClientEntityAction.Action.STOP_SPRINTING) ticksSienceStopSprint = 0;
            else if (action.getAction() == WrapperPlayClientEntityAction.Action.START_SPRINTING) ticksSienceStartSprint = 0;
        }
    }

    @Override
    public void onPredictionComplete(PredictionComplete p) {
        if (ticksSienceStartSprint == 1 && ticksSienceAttack == 1 && ticksSienceStopSprint == 2) {
            if (player.isSprinting && player.isSprintSimulation()) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert(String.format("invalid sprint prediction pattern. buffer=%.2f", buffer));
                }
            } else if (buffer > 0) buffer -= 0.25;
        } else if (ticksSienceAttack == 1 && buffer > 0) buffer -= 0.15;
        updateTicks();
    }

    public void updateTicks() {
        ticksSienceAttack++;
        ticksSienceStopSprint++;
        ticksSienceStartSprint++;
    }
}
