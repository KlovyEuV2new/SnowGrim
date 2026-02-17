package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import org.jetbrains.annotations.NotNull;

@CheckData(
        name = "SimulationDouble",
        setback = 0.0D,
        decay = 0.02D,
        description = "Velocity Double Repeat"
)
public class SimulationC extends Check implements PacketCheck {
    private double maxSpeed = 0;
    private final double minX = 0.08D;
    private final double minY = 0.08D;
    private final double minZ = 0.08D;
    private int consecutiveViolations = 0;
    private static final int VIOLATION_THRESHOLD = 1;
    private boolean wasFlagged = false;
    private boolean highJumpHard = true;
    private boolean moveHard = true;
    private boolean tpb;
    private double thr = 5.0E-9D;

    public SimulationC(@NotNull GrimPlayer player) {
        super(player);
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        PacketTypeCommon packetType = event.getPacketType();
        boolean isExempt = this.player.isClimbing || this.player.wasClimbing || this.player.compensatedWorld.isNearHardEntity(this.player.boundingBox)
                || this.player.uncertaintyHandler.isStepMovement || this.player.uncertaintyHandler.wasStepMovement
                || this.player.wasTouchingWater || this.player.wasWasTouchingWater || this.player.packetStateData.lastPacketWasTeleport || this.player.onGround
                || this.player.getSetbackTeleportUtil().blockOffsets || this.player.wasGliding != this.player.isGliding || this.player.wasTouchingLava
                || this.player.wasWasTouchingLava || this.player.inVehicle();
        if (!this.player.disableGrim && !isExempt) {
            if (packetType.equals(PacketType.Play.Client.PLAYER_POSITION) || packetType.equals(PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION)) {
                if (this.consecutiveViolations > VIOLATION_THRESHOLD) {
                    this.consecutiveViolations = VIOLATION_THRESHOLD;
                } else if (this.consecutiveViolations < 0) {
                    this.consecutiveViolations = 0;
                }

                boolean flaggedThisTick = false;
                String var10001;
                double v;
                double delta;
                if (!this.player.packetStateData.isSlowedByUsingItem() && Math.abs(this.player.deltaX()) > minX
                        && Math.abs(this.player.lastDeltaX) > minX && this.isSmooth(Math.abs(this.player.lastDeltaX), Math.abs(this.player.deltaX()))) {
                    v = Math.abs(this.player.lastDeltaX) - Math.abs(this.player.deltaX());
                    delta = Math.abs(v - (double)Math.round(v));
                    flaggedThisTick = true;
                    if (this.moveHard || this.consecutiveViolations >= VIOLATION_THRESHOLD) {
                        var10001 = String.format("%.4f", Math.abs(this.player.deltaX()));
                        if (this.flagAndAlert("type=X, delta=" + var10001 + ", v=" + String.format("%.4f",delta) +", violations=" + this.consecutiveViolations) && this.shouldSetback() && this.tpb) {
                            this.giveOffsetLenienceNextTick(delta);
                            this.player.getSetbackTeleportUtil().teleportBack();
                        }
                    }
                }

                if (!this.player.isFlying && !this.player.compensatedEntities.self.hasPotionEffect(PotionTypes.LEVITATION) && !flaggedThisTick
                        && Math.abs(this.player.deltaY()) > minY && Math.abs(this.player.lastDeltaY) > minY
                        && this.isSmooth(Math.abs(this.player.lastDeltaY), Math.abs(this.player.deltaY()))) {
                    v = Math.abs(this.player.lastDeltaY) - Math.abs(this.player.deltaY());
                    delta = Math.abs(v - (double)Math.round(v));
                    flaggedThisTick = true;
                    if (this.highJumpHard || this.consecutiveViolations >= VIOLATION_THRESHOLD) {
                        var10001 = String.format("%.4f", Math.abs(this.player.deltaY()));
                        if (this.flagAndAlert("type=Y, delta=" + var10001 + ", v=" + String.format("%.4f",delta) +", violations=" + this.consecutiveViolations) && this.shouldSetback() && this.tpb) {
                            this.giveOffsetLenienceNextTick(delta);
                            this.player.getSetbackTeleportUtil().teleportBack();
                        }
                    }
                }

                if (!this.player.packetStateData.isSlowedByUsingItem() && !flaggedThisTick
                        && Math.abs(this.player.deltaZ()) > minZ && Math.abs(this.player.lastDeltaZ) > minZ && this.isSmooth(Math.abs(this.player.lastDeltaZ), Math.abs(this.player.deltaZ()))) {
                    v = Math.abs(this.player.lastDeltaZ) - Math.abs(this.player.deltaZ());
                    delta = Math.abs(v - (double)Math.round(v));
                    flaggedThisTick = true;
                    if (this.moveHard || this.consecutiveViolations >= VIOLATION_THRESHOLD) {
                        var10001 = String.format("%.4f", Math.abs(this.player.deltaZ()));
                        if (this.flagAndAlert("type=Z, delta=" + var10001 + ", v=" + String.format("%.4f",delta) +", violations=" + this.consecutiveViolations) && this.shouldSetback() && this.tpb) {
                            this.giveOffsetLenienceNextTick(delta);
                            this.player.getSetbackTeleportUtil().teleportBack();
                        }
                    }
                }

                if (!flaggedThisTick) {
                    if (this.consecutiveViolations >= VIOLATION_THRESHOLD && this.tpb) {
                        this.player.getSetbackTeleportUtil().teleportBack();
                    }

                    this.consecutiveViolations = 0;
                    this.removeOffsetLenience();
                    --this.consecutiveViolations;
                    this.reward();
                } else {
                    ++this.consecutiveViolations;
                }

                this.wasFlagged = flaggedThisTick;
            }

        }
    }

    public void onReload(ConfigManager config) {
        this.tpb = config.getBooleanElse(this.getConfigName() + ".setback", true);
        this.thr = config.getDoubleElse(getConfigName() + ".threshold", 5.0E-9D);
    }

    private void giveOffsetLenienceNextTick(double offset) {
        double minimizedOffset = Math.min(offset, 1.0D);
        this.player.uncertaintyHandler.lastHorizontalOffset = minimizedOffset;
        this.player.uncertaintyHandler.lastVerticalOffset = minimizedOffset;
    }

    private void removeOffsetLenience() {
        this.player.uncertaintyHandler.lastHorizontalOffset = 0.0D;
        this.player.uncertaintyHandler.lastVerticalOffset = 0.0D;
    }

    public boolean isSmooth(double from, double to) {
        return this.isInteger(from - to, thr);
    }

    public boolean isInteger(double v, double t) {
        return Math.abs(v - (double)Math.round(v)) < t;
    }
}
