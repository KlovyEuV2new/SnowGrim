package ac.grim.grimac.checks.impl.aura;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AuraD", description = "Detects elytra target", decay = 3, setback = 10,experimental = true)
public class AuraD extends Check implements PacketCheck {
    private long lastAttack,minAttackTime;
    private int minHits,Hits;
    public AuraD(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity interactEntity = new WrapperPlayClientInteractEntity(event);

            if (interactEntity.getAction() == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                if (player.clickData.getCps() > 2) return; //falses
                if (player.isGliding) {
                    double diff = System.currentTimeMillis() - lastAttack;
                    if (diff < minAttackTime) {
                        ++Hits;
                        if (Hits >  minHits) {
                            flagAndAlert("diff: " + diff + " Hits: " + Hits);
                        }
                    }
                    lastAttack = System.currentTimeMillis();
                }
            }
        }
        if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            if (!player.isGliding && player.onGround) {
                Hits = 0;
            }
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        this.minAttackTime = (long) config.getDoubleElse(getCheckName()+".minAttackTime", 120);
        this.minHits = config.getIntElse(getCheckName()+".minHits", 5);
    }
}
