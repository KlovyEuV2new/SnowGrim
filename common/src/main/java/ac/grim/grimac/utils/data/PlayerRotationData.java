package ac.grim.grimac.utils.data;

import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.TimeMilistUtil;
import ac.grim.grimac.utils.math.GrimMath;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;

@Getter@Setter
public class PlayerRotationData {
    public GrimPlayer player;
    private float yaw;
    private float pitch;
    private float lastYaw;
    private float lastPitch;
    private float deltaYaw;
    private float deltaPitch;
    private float lastDeltaYaw;
    private float lastDeltaPitch;
    private float yawAccel;
    private float pitchAccel;
    private float lastYawAccel;
    private float lastPitchAccel;
    private float rawMouseDeltaX;
    private float rawMouseDeltaY;
    @Getter
    private float fuckedPredictedPitch;
    @Getter
    private float fuckedPredictedYaw;
    private float lastFuckedPredictedPitch;
    private float lastFuckedPredictedYaw;
    private boolean invalidRate;
    private boolean invalidSensitivity;
    private boolean cinematic;
    private double finalSensitivity;
    private double mcpSensitivity;
    private final ArrayDeque<Integer> sensitivitySamples;
    private int sensitivity;
    private int lastRate;
    private int lastInvalidSensitivity;
    private TimeMilistUtil lastCinematic;
    private int mouseDeltaX;
    private int mouseDeltaY;
    @Getter
    private float lastJoltYaw;
    @Getter
    private float joltYaw;
    @Getter
    private float joltPitch;

    private float dy = 0.0f;
    private float dx = 0.0f;
    private float ddy = 0.0f;
    private float ddx = 0.0f;
    private float dddy = 0.0f;
    private float dddx = 0.0f;
    private final float lastDy = 0.0f;
    private final float lastDx = 0.0f;
    private final float lastDdy = 0.0f;
    private final float lastDdx = 0.0f;
    private float smoothness = 0.0f;
    private float consistency = 0.0f;
    private float smoothnessPitch;
    private float smoothnessYaw;

    public PlayerRotationData(GrimPlayer player) {
        this.player = player;
        this.sensitivitySamples = new ArrayDeque<Integer>();
        lastCinematic = new TimeMilistUtil(player);
    }
    public void handle(final float yaw, final float pitch) {
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastDeltaYaw = this.deltaYaw;
        this.lastDeltaPitch = this.deltaPitch;
        this.deltaYaw = Math.abs(yaw - this.lastYaw);
        this.deltaPitch = Math.abs(pitch - this.lastPitch);
        this.lastPitchAccel = this.pitchAccel;
        this.lastYawAccel = this.yawAccel;
        this.yawAccel = Math.abs(this.deltaYaw - this.lastDeltaYaw);
        this.pitchAccel = Math.abs(this.deltaPitch - this.lastDeltaPitch);
        final float f = (float)this.mcpSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 1.2f;
        this.rawMouseDeltaX = this.deltaYaw / gcd;
        this.rawMouseDeltaY = this.deltaPitch / gcd;
        this.mouseDeltaX = (int)(this.deltaYaw / gcd);
        this.mouseDeltaY = (int)(this.deltaPitch / gcd);
        final float expectedYaw = this.deltaYaw * 1.073742f + (float)(this.deltaYaw + 0.15);
        final float expectedPitch = this.deltaPitch * 1.073742f - (float)(this.deltaPitch - 0.15);
        final float pitchDiff = Math.abs(this.deltaPitch - expectedPitch);
        final float yawDiff = Math.abs(this.deltaYaw - expectedYaw);
        processCinematic(pitchDiff);
        this.lastFuckedPredictedPitch = this.fuckedPredictedPitch;
        this.lastFuckedPredictedYaw = this.fuckedPredictedYaw;
        this.fuckedPredictedPitch = Math.abs(this.deltaPitch - pitchDiff);
        this.fuckedPredictedYaw = Math.abs(this.deltaYaw - yawDiff);
        this.smoothnessYaw = 10.0f - Math.abs(yawAccel - lastYawAccel);
        this.smoothnessPitch = 5.0f - Math.abs(pitchAccel - lastPitchAccel);
        this.smoothness = 10.0f - Math.abs(yawAccel - lastYawAccel);
        this.consistency = 1.0f - Math.abs(1.0f - (deltaYaw / (deltaPitch + 0.0001f)));
        this.dx = Math.abs(yaw - this.lastYaw);
        this.dy = Math.abs(pitch - this.lastPitch);
        this.ddx = Math.abs(this.dx - this.lastDx);
        this.ddy = Math.abs(this.dy - this.lastDy);
        this.dddx = Math.abs(this.ddx - this.lastDdx);
        this.dddy = Math.abs(this.ddy - this.lastDdy);
        if (this.deltaPitch > 0.1 && this.deltaPitch < 25.0f) {
            this.processSensitivity();
        }
    }
    private void processCinematic(float yDiff) {
        final float differenceYaw = Math.abs(this.deltaYaw - this.lastDeltaYaw);
        final float differencePitch = Math.abs(this.deltaPitch - this.lastDeltaPitch);
        float mDY = mouseDeltaY;
        String  debug = "ydiff: " + yDiff + " mdy: " + mDY;
        if (mDY < 4 && mDY > 0.0 && yDiff <= 0.1 && finalSensitivity < 0) {
            lastCinematic.reset();
        }

        this.cinematic = false; // (now - this.lastCinematic < 8);
    }


    private void processSensitivity() {
        final float gcd = (float) GrimMath.getGcd(this.deltaPitch, this.lastDeltaPitch);
        final double sensitivityModifier = Math.cbrt(0.8333 * gcd);
        final double sensitivityStepTwo = 1.666 * sensitivityModifier - 0.3333;
        final double finalSensitivity = sensitivityStepTwo * 200.0;
        this.finalSensitivity = finalSensitivity;
        this.sensitivitySamples.add((int)finalSensitivity);
        if (this.sensitivitySamples.size() == 40) {
            this.sensitivity = GrimMath.getMode(this.sensitivitySamples);
            if (this.hasValidSensitivity()) {
                this.mcpSensitivity = SensValues.SENSITIVITY_MCP_VALUES.get(this.sensitivity);
            }
            this.sensitivitySamples.clear();
        }
    }

    public boolean hasValidSensitivity() {
        return this.sensitivity > 0 && this.sensitivity < 200;
    }

    public boolean hasValidSensitivityNormalaized() {
        return this.sensitivity > 0 && this.sensitivity < 269;
    }

    public boolean UsingCinematicCamera() {
        return lastCinematic != null && lastCinematic.hasNotPassed(12);
    }

    public boolean hasTooLowSensitivity() {
        return this.sensitivity >= 0 && this.sensitivity < 50;
    }

    public boolean hasTooZeroDelta() {
        return  this.deltaYaw == 0 && this.deltaPitch == 0;
    }

    public float getSmoothnessPitch() {
        return smoothnessPitch;
    }

    public float getSmoothnessYaw() {
        return smoothnessYaw;
    }
}
