package ac.grim.grimac.checks.impl.sprint;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.collisions.FluidUtil;
import ac.grim.grimac.utils.collisions.IceUtil;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

@CheckData(name = "SprintH", description = "Sprint spoof simulation reset", setback = 0)
public class SprintH extends Check implements PostPredictionCheck {

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
    private boolean shouldCheck;
    private int ticksSinceAttack;

    public SprintH(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);

        boolean bufferEnabled = config.getBooleanElse(getConfigName() + ".buffer.enabled", true);
        double threshold = config.getDoubleElse(getConfigName() + ".buffer.threshold", 3.0);
        double increment = config.getDoubleElse(getConfigName() + ".buffer.increment", 1.0);
        double decrement = config.getDoubleElse(getConfigName() + ".buffer.decrement", 0.35);

        buffer = new Buffer(bufferEnabled, threshold, increment, decrement);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!enabled) return;

        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (packet.getAction() != WrapperPlayClientInteractEntity.InteractAction.ATTACK) return;

//            player.sendMessage(
//                    "§7[SPRINT DEBUG] " +
//                            "attackTick=" + player.tick +
//                            " stopTick=" + player.stopSprintTick +
//                            " startTick=" + player.startSprintTick +
//                            " isSprint=" + player.isSprinting
//            );

            if (player.isSprinting || IceUtil.isOnIce(player) || FluidUtil.isInFluid(player) || player.inVehicle()
                    || player.stopSprintTick != (player.tick -1)) {
                if (buffer.enabled) buffer.remove();
                shouldCheck = false;
                return;
            }

            shouldCheck = true;
            ticksSinceAttack = 0;
            return;
        }

        if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION || event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            if (shouldCheck) {
                if (player.predictedVelocity.isKnockback() || player.predictedVelocity.isTrident()
                        || player.predictedVelocity.isExplosion()) {
                    shouldCheck = false;
                    return;
                }
                checkSprint();
                shouldCheck = false;
            }
        }
    }

    private void checkSprint() {
        boolean suspicious = player.isSprinting && player.isSprintSimulation()
                && player.stopSprintTick == (player.tick -2)
                && player.startSprintTick == (player.tick -1);
        if (suspicious) {
            if (buffer.enabled) {
                buffer.add();
                if (buffer.passed()) {
                    flagAndAlert("buffer=" + String.format("%.2f", buffer.value));
                    buffer.reset();
                }
            } else {
                flagAndAlert("suspicious sprint simulation");
            }
        } else {
            if (buffer.enabled) {
                buffer.remove();
            }
            reward();
        }
    }
}
