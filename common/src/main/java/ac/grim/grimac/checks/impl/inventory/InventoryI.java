package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.InventoryCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.VectorData;
import ac.grim.grimac.utils.data.VehicleData;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CheckData(name = "InventoryI", setback = 0, description = "Scripted Clicks")
public class InventoryI extends InventoryCheck {

    private final ArrayList<WrapperPlayClientClickWindow.WindowClickType> white_types = new ArrayList<>(
            List.of(
                    WrapperPlayClientClickWindow.WindowClickType.PICKUP_ALL,
                    WrapperPlayClientClickWindow.WindowClickType.QUICK_CRAFT
            )
    );

    private final ArrayList<PacketTypeCommon> packet_types = new ArrayList<>(
            Arrays.asList(
                    PacketType.Play.Client.INTERACT_ENTITY,
                    PacketType.Play.Client.PLAYER_ROTATION,
                    PacketType.Play.Client.ENTITY_ACTION,
                    PacketType.Play.Client.HELD_ITEM_CHANGE,
                    PacketType.Play.Client.PLAYER_DIGGING,
                    PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT
            )
    );

    // Поля для хранения значений из конфигурации (конвертированных в наносекунды)
    private boolean enabled = true;
    private long clickThresholdNs = 15_000_000L;
    private long minDiffPacketsNs = 5_000_000_000L;
    private long similarityThresholdNs = 5_000_000L;
    private int violationThreshold = 3;
    private int minViolationThreshold = 2;
    private double minDiffThreshold = 0.15;

    private Long lastClickTime = null;
    private Long lastPacket = null;
    private long lastDiff = 0L;
    private int similarClicks = 0;

    public InventoryI(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!enabled || player.disableGrim) return;
        super.onPacketReceive(event);

        long now = System.nanoTime();
        PacketTypeCommon packetType = event.getPacketType();

        boolean inVehicle = this.player.inVehicle();
        boolean isJumping;
        boolean isMoving;
        if (inVehicle) {
            VehicleData vehicle = this.player.vehicleData;
            isJumping = vehicle.nextHorseJump > 0.0F;
            isMoving = vehicle.nextVehicleForward != 0.0F || vehicle.nextVehicleHorizontal != 0.0F;
        } else {
            VectorData.MoveVectorData move = this.findMovement(this.player.predictedVelocity);
            isJumping = this.player.predictedVelocity.isJump();
            isMoving = move != null && (move.x != 0 || move.z != 0);
        }

        if (packetType != PacketType.Play.Client.CLICK_WINDOW) {
            if (packet_types.contains(packetType)
                    || isMoving || isJumping) {
                lastPacket = now;
            }
            return;
        }

        WrapperPlayClientClickWindow packet = new WrapperPlayClientClickWindow(event);
        WrapperPlayClientClickWindow.WindowClickType clickType = packet.getWindowClickType();
        if (white_types.contains(clickType)) return;

        if (lastPacket == null) return;
        long diffPacket = now - lastPacket;
        if (diffPacket > minDiffPacketsNs || minDiffPacketsNs <= 0) return;

        if (lastClickTime != null) {
            long currentDiff = now - lastClickTime;
            long delta = Math.abs(currentDiff - lastDiff);
            String reason = "";

            if (clickThresholdNs > 0 && currentDiff <= clickThresholdNs) {
                reason = "diff";
                similarClicks++;
            } else if (similarityThresholdNs > 0 && delta <= similarityThresholdNs) {
                reason = "delta";
                similarClicks++;
            } else {
                similarClicks = 0;
                lastDiff = 0L;
            }

            int violationThresholds = violationThreshold;
            if (diffPacket < (minDiffPacketsNs * Math.min(1, minDiffThreshold))) {
                violationThresholds = minViolationThreshold;
            }

            if (similarClicks >= violationThresholds) {
                double diffMs = currentDiff / 1_000_000.0;
                double deltaMs = delta / 1_000_000.0;
                double packetMs = diffPacket / 1_000_000.0;

                String formatted;

                if (reason.equals("delta")) {
                    formatted = String.format(
                            "type=" + clickType.name() + ", delta=%.5fms, chain=%d, packet=%.5fms",
                            deltaMs, similarClicks, packetMs
                    );
                } else {
                    formatted = String.format(
                            "type=" + clickType.name() + ", diff=%.5fms, chain=%d, packet=%.5fms",
                            diffMs, similarClicks, packetMs
                    );
                }

                flagAndAlert(formatted);

                if (shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            } else reward();
        }

        if (lastClickTime != null) lastDiff = now - lastClickTime;
        lastClickTime = now;
    }

    private VectorData.MoveVectorData findMovement(VectorData vectorData) {
        if (!(vectorData instanceof VectorData.MoveVectorData)) {
            do {
                if (vectorData == null) {
                    return null;
                }
                vectorData = vectorData.lastVector;
            } while (!(vectorData instanceof VectorData.MoveVectorData));
        }
        return (VectorData.MoveVectorData) vectorData;
    }

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);
        clickThresholdNs = (long) (config.getDoubleElse(getConfigName() + ".click-threshold", 15.0) * 1_000_000.0);
        minDiffPacketsNs = (long) (config.getDoubleElse(getConfigName() + ".min-diff-packets", 5000.0) * 1_000_000.0);
        similarityThresholdNs = (long) (config.getDoubleElse(getConfigName() + ".similarity-threshold", 5.0) * 1_000_000.0);

        violationThreshold = config.getIntElse(getConfigName() + ".violation-threshold", 3);
        minViolationThreshold = config.getIntElse(getConfigName() + ".min-violation-threshold", 2);
        minDiffThreshold = config.getDoubleElse(getConfigName() + ".min-diff-threshold", 0.15);
    }
}
