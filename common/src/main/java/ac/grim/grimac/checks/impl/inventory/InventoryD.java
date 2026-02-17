/* Decompiler 102ms, total 611ms, lines 127 */
package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.InventoryCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import ac.grim.grimac.utils.data.VectorData;
import ac.grim.grimac.utils.data.VehicleData;
import ac.grim.grimac.utils.data.VectorData.MoveVectorData;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

import java.util.StringJoiner;

@CheckData(
        name = "InventoryD",
        setback = 1.0D,
        decay = 0.25D
)
public class InventoryD extends InventoryCheck {
    private int horseJumpVerbose;
    public boolean lastMoved = false;

    public InventoryD(GrimPlayer player) {
        super(player);
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            if ((!this.lastMoved || !this.player.getSetbackTeleportUtil().blockOffsets) && !this.player.packetStateData.lastPacketWasTeleport && !this.player.packetStateData.isSlowedByUsingItem() && System.currentTimeMillis() - this.player.lastBlockPlaceUseItem >= 50L) {
                if (this.player.hasInventoryOpen) {
                    boolean inVehicle = this.player.inVehicle();
                    boolean isJumping;
                    boolean isMoving;
                    if (inVehicle) {
                        VehicleData vehicle = this.player.vehicleData;
                        isJumping = vehicle.nextHorseJump > 0.0F && this.horseJumpVerbose++ >= 1;
                        isMoving = vehicle.nextVehicleForward != 0.0F || vehicle.nextVehicleHorizontal != 0.0F;
                    } else {
                        MoveVectorData move = this.findMovement(this.player.predictedVelocity);
                        isJumping = this.player.predictedVelocity.isJump();
                        isMoving = move != null && (move.x != 0 || move.z != 0);
                    }

                    if (!isMoving && !isJumping) {
                        this.reward();
                        return;
                    }

                    if (this.flag() && this.shouldModifyPackets()) {
                        event.setCancelled(true);
                        player.onPacketCancel();
                    }
                }

            }
        }
    }

    public void onPredictionComplete(PredictionComplete predictionComplete) {
        if (predictionComplete.isChecked() && !predictionComplete.getData().isTeleport() && !this.player.getSetbackTeleportUtil().blockOffsets && !this.player.packetStateData.lastPacketWasTeleport && !this.player.packetStateData.isSlowedByUsingItem() && System.currentTimeMillis() - this.player.lastBlockPlaceUseItem >= 50L) {
            if (this.player.hasInventoryOpen) {
                boolean inVehicle = this.player.inVehicle();
                boolean isJumping;
                boolean isMoving;
                if (inVehicle) {
                    VehicleData vehicle = this.player.vehicleData;
                    isJumping = vehicle.nextHorseJump > 0.0F && this.horseJumpVerbose++ >= 1;
                    isMoving = vehicle.nextVehicleForward != 0.0F || vehicle.nextVehicleHorizontal != 0.0F;
                } else {
                    MoveVectorData move = this.findMovement(this.player.predictedVelocity);
                    isJumping = this.player.predictedVelocity.isJump();
                    isMoving = move != null && (move.x != 0 || move.z != 0);
                }

                if (!isMoving && !isJumping) {
                    this.reward();
                    this.lastMoved = false;
                    return;
                }

                this.lastMoved = true;
                if (this.flag()) {
                    if (!this.isNoSetbackPermission()) {
                        this.shouldSetback();
                        if (this.getViolations() > this.getSetbackVL()) {
                            this.closeInventory();
                        }
                    }

                    StringJoiner joiner = new StringJoiner(" ");
                    if (isMoving) {
                        joiner.add("moving");
                    }

                    if (isJumping) {
                        joiner.add("jumping");
                    }

                    if (inVehicle) {
                        joiner.add("inVehicle");
                    }

                    this.alert(joiner.toString());
                }
            } else {
                this.horseJumpVerbose = 0;
            }

        }
    }

    private MoveVectorData findMovement(VectorData vectorData) {
        if (!(vectorData instanceof MoveVectorData)) {
            do {
                if (vectorData == null) {
                    return null;
                }

                vectorData = vectorData.lastVector;
            } while (!(vectorData instanceof MoveVectorData));

        }
        return (MoveVectorData)vectorData;
    }
}
