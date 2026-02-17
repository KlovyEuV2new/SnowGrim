package ac.grim.grimac.predictionengine.predictions.state;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;

public class PredictionCheckJump {
    private static final double COLLIDE_BLOCK_VAL = 0.20000004768371582;
    private static final double WATER_JUMP_BASE = 0.03999999910593033;
    private static final double HONEY_JUMP_VAL = 0.20999999344348907;
    private static final double EPSILON = 1.0E-5;

    public static PredictionJumpResult check(GrimPlayer player) {
        if (!player.predictedVelocity.isJump()) return new PredictionJumpResult(Double.MIN_VALUE);

        double deltaY = player.deltaY();
        double jumpStrength = (double) player.compensatedEntities.self.getAttributeValue(Attributes.JUMP_STRENGTH);
        double lastDeltaY = player.lastDeltaY;

        double minOffset = Math.abs(deltaY - jumpStrength);
        minOffset = Math.min(minOffset, Math.abs(deltaY - COLLIDE_BLOCK_VAL));

        if (player.uncertaintyHandler.isSteppingOnHoney) {
            minOffset = Math.min(minOffset, Math.abs(deltaY - HONEY_JUMP_VAL));
        }

        if (player.uncertaintyHandler.isSteppingOnSlime) {
            double absLastY = Math.abs(lastDeltaY);
            double bounce = absLastY * 0.8;

            minOffset = Math.min(minOffset, Math.abs(deltaY - bounce));
            minOffset = Math.min(minOffset, Math.abs(deltaY - (jumpStrength + bounce)));
            minOffset = Math.min(minOffset, Math.abs(deltaY - (jumpStrength + bounce - 0.08)));
            minOffset = Math.min(minOffset, Math.abs(deltaY - (bounce - 0.08)));
            minOffset = Math.min(minOffset, Math.abs(deltaY - (bounce * 0.8)));
        }

        if (player.wasTouchingWater) {
            minOffset = Math.min(minOffset, Math.abs(deltaY - WATER_JUMP_BASE));
            minOffset = Math.min(minOffset, Math.abs(deltaY - jumpStrength));

            double fluidLogic1 = (lastDeltaY - 0.02) * 0.8 + 0.04;
            double fluidLogic2 = lastDeltaY * 0.8 + 0.04;
            double fluidLogic3 = (lastDeltaY + 0.04) * 0.8;

            minOffset = Math.min(minOffset, Math.abs(deltaY - fluidLogic1));
            minOffset = Math.min(minOffset, Math.abs(deltaY - fluidLogic2));
            minOffset = Math.min(minOffset, Math.abs(deltaY - fluidLogic3));

            if (player.uncertaintyHandler.isSteppingNearBubbleColumn) {
                minOffset = Math.min(minOffset, Math.abs(deltaY - 0.7));
                minOffset = Math.min(minOffset, Math.abs(deltaY - (lastDeltaY * 0.8 + 0.7)));
                minOffset = Math.min(minOffset, Math.abs(deltaY - (lastDeltaY + 0.7)));

                minOffset = Math.min(minOffset, Math.abs(deltaY - (-0.15)));
                minOffset = Math.min(minOffset, Math.abs(deltaY - (lastDeltaY * 0.8 - 0.15)));
            }
        }

        if (minOffset < EPSILON) minOffset = 0;

        return new PredictionJumpResult(minOffset);
    }

    public record PredictionJumpResult(double offset) {
    }
}
