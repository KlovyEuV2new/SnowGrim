package ac.grim.grimac.api.npcs.checks.move;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.api.npcs.NpcManager;
import ac.grim.grimac.api.npcs.util.NpcUtil;
import ac.grim.grimac.api.npcs.util.PlayerUtil;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

public class NpcPacketListener extends Check implements PacketCheck {

    public boolean rotationValidate = true;
    public double buffer = 0;
    public double maxVL;

    private int validRotationTicks = 0;
    private int invalidRotationTicks = 0;
    private static final int REQUIRED_VALID_TICKS = 30;
    private static final int TOLERANCE_TICKS = 5;

    private final Deque<Float> yawSpeedHistory = new ArrayDeque<>();
    private final Deque<Float> pitchSpeedHistory = new ArrayDeque<>();
    private static final int HISTORY_SIZE = 35;

    private float lastYaw = 0;
    private float lastPitch = 0;
    private float lastYawSpeed = 0;
    private float lastPitchSpeed = 0;

    private static final float MAX_ACCELERATION_MULTIPLIER = 7.5f;
    private static final float MIN_ROTATION_CHANGE = 0.001f;
    private static final float MIN_SPEED_THRESHOLD = 0.5f;

    public NpcPacketListener(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onReload(ConfigManager config) {
        NpcManager.loadSettings(config);
        NpcManager.nameManager.reloadConfigNames();
        this.maxVL = config.getDoubleElse("npc.vl", 1);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        PacketTypeCommon type = event.getPacketType();

        if (type == PacketType.Play.Server.ENTITY_METADATA) {
            WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event);
            GrimPlayer gp = PlayerUtil.getPlayer(packet.getEntityId());
            if (gp != null) {
                gp.lastMetadata = packet.getEntityMetadata();
            }
        }
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        PacketTypeCommon type = event.getPacketType();

        if (type == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            WrapperPlayClientInteractEntity.InteractAction action = packet.getAction();

            if (action == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                if (!event.isCancelled() && rotationValidate) {
                    buffer++;
                    if (buffer > maxVL && NpcUtil.spawnNpc(player)) {
                        buffer = 0;
                        validRotationTicks = 0;
                        invalidRotationTicks = 0;
                    }
                } else if (!rotationValidate) {
                    buffer = 0;
                }
            }
        } else if (type == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION
                || type == PacketType.Play.Client.PLAYER_POSITION
                || type == PacketType.Play.Client.PLAYER_ROTATION) {

            if (type == PacketType.Play.Client.PLAYER_ROTATION
                    || type == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
                boolean currentTickValid = player.actionManager.hasAttackedSince(1000)
                        && !player.isCinematicRotation()
                        && checkSmoothAcceleration();

                if (currentTickValid) {
                    validRotationTicks++;
                    invalidRotationTicks = Math.max(0, invalidRotationTicks - 1);
                } else {
                    invalidRotationTicks++;
                    validRotationTicks = Math.max(0, validRotationTicks - 1);
                }

                if (validRotationTicks >= REQUIRED_VALID_TICKS) {
                    rotationValidate = true;
                } else if (invalidRotationTicks > TOLERANCE_TICKS) {
                    rotationValidate = false;
                }

                updateRotationHistory();
            }

            NpcManager.handleMovementTick(player);
        }
    }

    private boolean checkSmoothAcceleration() {
        float currentYawSpeed = Math.abs(player.yaw - lastYaw);
        float currentPitchSpeed = Math.abs(player.pitch - lastPitch);

        if (currentYawSpeed < MIN_ROTATION_CHANGE && currentPitchSpeed < MIN_ROTATION_CHANGE) {
            return true;
        }

        boolean yawValid = true;
        if (lastYawSpeed > MIN_SPEED_THRESHOLD && currentYawSpeed > MIN_SPEED_THRESHOLD) {
            float yawAcceleration = currentYawSpeed / lastYawSpeed;
            yawValid = yawAcceleration <= MAX_ACCELERATION_MULTIPLIER
                    && yawAcceleration >= (1.0f / MAX_ACCELERATION_MULTIPLIER);
        }

        boolean pitchValid = true;
        if (lastPitchSpeed > MIN_SPEED_THRESHOLD && currentPitchSpeed > MIN_SPEED_THRESHOLD) {
            float pitchAcceleration = currentPitchSpeed / lastPitchSpeed;
            pitchValid = pitchAcceleration <= MAX_ACCELERATION_MULTIPLIER
                    && pitchAcceleration >= (1.0f / MAX_ACCELERATION_MULTIPLIER);
        }

        boolean historyValid = checkSpeedHistoryConsistency(currentYawSpeed, currentPitchSpeed);

        return yawValid && pitchValid && historyValid;
    }

    private boolean checkSpeedHistoryConsistency(float currentYawSpeed, float currentPitchSpeed) {
        if (yawSpeedHistory.size() < 3) {
            return true;
        }

        float avgYawSpeed = 0;
        float avgPitchSpeed = 0;

        for (Float speed : yawSpeedHistory) {
            avgYawSpeed += speed;
        }
        for (Float speed : pitchSpeedHistory) {
            avgPitchSpeed += speed;
        }

        avgYawSpeed /= yawSpeedHistory.size();
        avgPitchSpeed /= pitchSpeedHistory.size();

        boolean yawConsistent = true;
        if (avgYawSpeed > MIN_SPEED_THRESHOLD && currentYawSpeed > MIN_SPEED_THRESHOLD) {
            float yawDeviation = currentYawSpeed / avgYawSpeed;
            yawConsistent = yawDeviation <= MAX_ACCELERATION_MULTIPLIER
                    && yawDeviation >= (1.0f / MAX_ACCELERATION_MULTIPLIER);
        }

        boolean pitchConsistent = true;
        if (avgPitchSpeed > MIN_SPEED_THRESHOLD && currentPitchSpeed > MIN_SPEED_THRESHOLD) {
            float pitchDeviation = currentPitchSpeed / avgPitchSpeed;
            pitchConsistent = pitchDeviation <= MAX_ACCELERATION_MULTIPLIER
                    && pitchDeviation >= (1.0f / MAX_ACCELERATION_MULTIPLIER);
        }

        return yawConsistent && pitchConsistent;
    }

    private void updateRotationHistory() {
        float currentYawSpeed = Math.abs(player.yaw - lastYaw);
        float currentPitchSpeed = Math.abs(player.pitch - lastPitch);

        yawSpeedHistory.addLast(currentYawSpeed);
        pitchSpeedHistory.addLast(currentPitchSpeed);

        if (yawSpeedHistory.size() > HISTORY_SIZE) {
            yawSpeedHistory.removeFirst();
        }
        if (pitchSpeedHistory.size() > HISTORY_SIZE) {
            pitchSpeedHistory.removeFirst();
        }

        lastYawSpeed = currentYawSpeed;
        lastPitchSpeed = currentPitchSpeed;
    }
}
