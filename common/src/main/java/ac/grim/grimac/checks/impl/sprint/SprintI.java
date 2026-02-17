package ac.grim.grimac.checks.impl.sprint;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;


import static com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client.ENTITY_ACTION;
import static com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client.INTERACT_ENTITY;
import static com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity.InteractAction.ATTACK;

@CheckData(name = "SprintI", description = "Fast sprint-reset timing", setback = 0)
public class SprintI extends Check implements PacketCheck {

    public static class Buffer {
        public boolean enabled;
        public double threshold;
        public double increment;
        public double decrement;
        public double value;

        public Buffer(boolean enabled, double threshold, double increment, double decrement) {
            this.enabled = enabled;
            this.threshold = threshold;
            this.increment = increment;
            this.decrement = decrement;
        }

        public void add() {
            value += increment;
        }

        public void remove() {
            value = Math.max(0, value - decrement);
        }

        public void reset() {
            value = 0;
        }

        public boolean passed() {
            return value >= threshold;
        }
    }

    private boolean enabled;
    private Buffer buffer;

    private double diffMs;

    public SprintI(GrimPlayer player) {
        super(player);
    }

    private EntityActionInfo lastInfo = null;
    public record EntityActionInfo(long time, WrapperPlayClientEntityAction packet) {
    }

    private boolean isAttack = false;
    private boolean isStopSprint = false;
    private boolean isStartSprint = false;

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);

        boolean bufferEnabled = config.getBooleanElse(getConfigName() + ".buffer.enabled", true);
        double threshold = config.getDoubleElse(getConfigName() + ".buffer.threshold", 3.0);
        double increment = config.getDoubleElse(getConfigName() + ".buffer.increment", 1.0);
        double decrement = config.getDoubleElse(getConfigName() + ".buffer.decrement", 0.45);

        buffer = new Buffer(bufferEnabled, threshold, increment, decrement);
        diffMs = config.getDoubleElse(getConfigName() + ".diff", 5.0);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!enabled || player.disableGrim) return;

        if (isTransaction(event.getPacketType())) {
            isStartSprint = false;
            isStopSprint = false;
            isAttack = false;
        } else if (event.getPacketType().equals(ENTITY_ACTION)) {
            WrapperPlayClientEntityAction packet = new WrapperPlayClientEntityAction(event);
            if (packet.getAction().equals(WrapperPlayClientEntityAction.Action.STOP_SPRINTING)) {
                isStopSprint = true;
                if (isAttack) {
                    if (buffer.enabled) buffer.remove();
                    reward();
                }
            }
            else if (packet.getAction().equals(WrapperPlayClientEntityAction.Action.START_SPRINTING)) {
                isStartSprint = true;
                if (isStopSprint && isAttack) {
                    if (buffer.enabled) {
                        buffer.add();
                        if (buffer.passed()) {
                            flagAndAlert("suspicious packet sequence (SprintReset), buffer=" +
                                    String.format("%.2f", buffer.value)
                            );
                            buffer.reset();
                        }
                    } else {
                        flagAndAlert("suspicious packet sequence (SprintReset)");
                    }
                } else if (isAttack) {
                    if (buffer.enabled) buffer.remove();
                    reward();
                }
            }
        } else if (event.getPacketType().equals(INTERACT_ENTITY)) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (packet.getAction().equals(ATTACK)) isAttack = true;
        }


    }
}
