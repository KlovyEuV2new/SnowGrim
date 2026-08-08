package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import ac.grim.grimac.utils.data.attribute.ValuedAttribute;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;

import java.util.Optional;

@CheckData(name = "NoWeb", description = "Was not slowed while in web", setback = 5)
public class NoWeb extends Check implements PostPredictionCheck {
    public boolean alternativeFix;
    public boolean flaggedLastTick = false, lastInWeb = false;
    double offsetToFlag;

    public NoWeb(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (!predictionComplete.isChecked()) return;

        if (player.isInWeb() && lastInWeb) {
            Optional<ValuedAttribute> att = player.compensatedEntities.self.getAttribute(Attributes.MOVEMENT_SPEED);
            double value = att.map(ValuedAttribute::get).orElse((double) 0.1f);
            if (player.isSprinting) value /= 1.3;
            double speed = player.deltaXZ() / value;

            double offset = 0.0;

            if (!player.lastOnGround && !player.onGround) {
                if (player.deltaY() > -0.015) {
                    offset = Math.abs(player.deltaY() - (-0.015));
                }
            } else if (player.deltaY() > 0.021) {
                offset = Math.abs(player.deltaY() - 0.021);
            }

            if (speed > 0.5f && !player.predictedVelocity.isJump()) {
                offset = Math.max(offset, Math.abs(speed - 0.5f));
            }

            boolean flagged = offset > offsetToFlag;
            boolean exempt = player.isFlying || player.isGliding || player.compensatedEntities.self.hasPotionEffect(PotionTypes.LEVITATION) || player.predictedVelocity.isKnockback();

            if (flagged && flaggedLastTick && !exempt) {
                flagAndAlertWithSetback(String.format("offset=%.6f", offset));
            }
            flaggedLastTick = alternativeFix || flagged;
        }
        lastInWeb = player.isInWeb();
    }

    @Override
    public void onReload(ConfigManager config) {
        offsetToFlag = config.getDoubleElse(getConfigName() + ".threshold", 0.001);
        alternativeFix = config.getBooleanElse(getConfigName() + ".mitigateTicks", false);
    }
}
