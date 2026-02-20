package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import ac.grim.grimac.utils.collisions.datatypes.SimpleCollisionBox;
import ac.grim.grimac.utils.data.packetentity.PacketEntity;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "ShulkerOffset")
public class ShulkerOffset extends Check implements PostPredictionCheck {
    private int lastShulkerCollision = 100;
    public ShulkerOffset(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (player.isFlying || player.packetStateData.lastPacketWasTeleport || player.compensatedEntities.self.hasPotionEffect(PotionTypes.JUMP_BOOST)) return;
        lastShulkerCollision++;
        SimpleCollisionBox expandedBB = player.boundingBox.copy().expand(1);
        if (regularHardCollision(expandedBB)) {
            lastShulkerCollision = 0;
        }
        if (lastShulkerCollision < 30) {
            double maxmotion = player.compensatedEntities.self.getAttributeValue(Attributes.JUMP_STRENGTH);
            double motionY = player.actualMovement.getY();
            if (motionY > maxmotion) {
                flagAndAlert(String.format("y: %.2f", motionY));
            }
        }
    }
    private boolean regularHardCollision(SimpleCollisionBox expandedBB) {
        final PacketEntity riding = player.compensatedEntities.self.getRiding();
        for (PacketEntity entity : player.compensatedEntities.entityMap.values()) {
            if ((entity.type == EntityTypes.SHULKER && entity != riding
                    && entity.getPossibleCollisionBoxes().isIntersected(expandedBB))) {
                return true;
            }
        }

        return false;
    }
}
