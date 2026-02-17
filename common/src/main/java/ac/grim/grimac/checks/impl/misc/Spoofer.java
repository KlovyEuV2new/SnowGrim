package ac.grim.grimac.checks.impl.misc;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.packetentity.PacketEntity;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.netty.channel.ChannelHelper;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.score.FixedScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import net.kyori.adventure.text.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@CheckData(name = "Spoofer", description = "Hide meta for for nametags hacks.")
public class Spoofer extends Check implements PacketCheck {
    boolean enable;
    boolean healthHider;
    boolean xpHider;
    boolean oxygenHider;
    boolean absorptionHider;
    boolean onlyForPlayers;
    public boolean isStableversion =true;// PacketEvents.getAPI().getServerManager().getVersion().isOlderThanOrEquals(ServerVersion.V_1_9);

    public Spoofer(GrimPlayer player) {
        super(player);
    }

    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.SCOREBOARD_OBJECTIVE) {
            WrapperPlayServerScoreboardObjective wrapper = new WrapperPlayServerScoreboardObjective(event);
            if (wrapper.getScoreFormat() == null || !(wrapper.getScoreFormat() instanceof FixedScoreFormat)) {
                return;
            }
            FixedScoreFormat tempData = (FixedScoreFormat)wrapper.getScoreFormat();
            Component value = tempData.getValue();

        }
        if (!this.player.disableGrim && !this.player.noModifyPacketPermission && isStableversion && this.enable && event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
            WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(event);
            int entityId = wrapper.getEntityId();
            if (event.getUser().getEntityId() != entityId) {
                PacketEntity packetEntity = this.player.compensatedEntities.getEntity(entityId);
                if (packetEntity != null &&  packetEntity.isLivingEntity) {
                    List<EntityData<?>> entityMetaData = wrapper.getEntityMetadata();
                    boolean shouldPush = false;
                    Iterator iterator = entityMetaData.iterator();
                    while(true) {
                        while(iterator.hasNext()) {
                            EntityData data = (EntityData)iterator.next();
                            if (this.healthHider && data.getIndex() == MetadataIndex.HEALTH) {
                                float health = Float.parseFloat(String.valueOf(data.getValue()));
                                if (health > 0.0F) {
                                    float randomHealth =(float)(1 + new Random().nextFloat(1,1200));
                                    if (data.getValue() instanceof Float) {
                                        data.setValue(randomHealth);
                                        shouldPush = true;
                                    }


                                }
                            } else if (this.absorptionHider && data.getIndex() == MetadataIndex.ABSORPTION) {
                                this.setDynamicValue(data, 1000);
                                if (data.getValue() instanceof Float) {
                                    data.setValue(100.0F);
                                    shouldPush = true;
                                }
                            }
                        }

                        if (shouldPush) {
                            this.push(event, wrapper.getEntityId(), entityMetaData);
                        }

                        return;
                    }
                }
            }
        }

    }

    void push(PacketSendEvent event, int entityId, List<EntityData<?>> dataList) {
        event.setCancelled(true);
        WrapperPlayServerEntityMetadata metadata = new WrapperPlayServerEntityMetadata(entityId, dataList);
        ChannelHelper.runInEventLoop(this.player.user.getChannel(), () -> {
            this.player.user.sendPacketSilently(metadata);
        });
    }

    private void setDynamicValue(EntityData obj, int spoofValue) {
        Object value = obj.getValue();
        if (value instanceof Integer) {
            obj.setValue(spoofValue);
        } else if (value instanceof Short) {
            obj.setValue((short)spoofValue);
        } else if (value instanceof Byte) {
            obj.setValue((byte)spoofValue);
        } else if (value instanceof Long) {
            obj.setValue(spoofValue);
        } else if (value instanceof Float) {
            obj.setValue((float)spoofValue);
        } else if (value instanceof Double) {
            obj.setValue(spoofValue);
        }

    }

    @Override
    public void onReload(ConfigManager config) {
        this.onlyForPlayers = config.getBooleanElse("visual.metadata-hider.onlyForPlayers", true);
        this.healthHider = config.getBooleanElse("visual.metadata-hider.health", true);
        this.xpHider = config.getBooleanElse("visual.metadata-hider.xp", true);
        this.oxygenHider = config.getBooleanElse("visual.metadata-hider.oxygen", true);
        this.absorptionHider = config.getBooleanElse("visual.metadata-hider.absorption", true);
        this.enable = config.getBooleanElse("visual.metadata-hider.enabled", true);
    }
}
