package ac.grim.grimac.checks.impl.aura;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.packetentity.PacketEntity;
import ac.grim.grimac.utils.lists.EvictingQueue;
import ac.grim.grimac.utils.math.GrimMath;
import ac.grim.grimac.utils.math.Vector3dm;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

import java.util.ArrayList;
import java.util.List;

@CheckData(name = "AuraB", description = "Suspicious accuracy of attack", experimental = true)
public class AuraB extends Check implements PacketCheck {
    private int maxAccuracy;
    private int sampleSize;
    private double minDistance;
    private boolean hit = false;
    private EvictingQueue<Boolean> hitList;
    private EvictingQueue<Vector3dm> positionList;
    private int lastAttack = 0;
    private int maxCombatDuration;
    private double minAverageTargetMovement;
    private PacketEntity lastTarget = null;

    public AuraB(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (!player.disableGrim && event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity action = new WrapperPlayClientInteractEntity(event);
            if (action.getAction() != WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                return;
            }
            PacketEntity target = player.compensatedEntities.entityMap.get(action.getEntityId());
            if (target == null || target.type != EntityTypes.PLAYER) {
                return;
            }
            if(target.getPossibleCollisionBoxes().distance(player.boundingBox) > minDistance) {
                hit = true;
            }
            if(target != lastTarget || (lastAttack - ((int) System.currentTimeMillis() / 1000)) > maxCombatDuration) {
                hitList.clear();
                positionList.clear();
            }
            lastTarget = target;
            lastAttack = (int) System.currentTimeMillis() / 1000;
            if (hitList.size() == sampleSize) {
                double accuracy = calculateTruePercentage(hitList);
                double averageTargetMovement = getAverageTargetMovement();
                if (accuracy >= maxAccuracy && averageTargetMovement >= minAverageTargetMovement) {
                    flagAndAlert("accuracy=" + accuracy);
                }
            }

            //Add this after the check bc the hit will be added after the arm animation packet and we dont want new positions and old hits
            //Adding the max or min doesn't matter but there is nothing like .getPosition
            positionList.add(target.getPossibleCollisionBoxes().max());


        } else if (!player.disableGrim && event.getPacketType() == PacketType.Play.Client.ANIMATION) {
            hitList.add(hit);
            hit = false; //set the current hit to false
        }


    }

    private double calculateTruePercentage(List<Boolean> booleanList) {
        int trueCount = 0;
        for (Boolean bool : booleanList) {
            if (bool) {
                trueCount++;
            }
        }
        return (double) trueCount / booleanList.size() * 100;
    }
    private double getAverageTargetMovement() {
        List<Double> movements = new ArrayList<>();
        Vector3dm lastPosition = null;
        for (Vector3dm position: positionList) {
            position.setY(0); //we want to ignore the Y movement
            if(lastPosition != null) {
                movements.add(position.distance(lastPosition));
            }
            lastPosition = position;
        }
        return GrimMath.getAverageDouble(movements);
    }

    public void onReload(ConfigManager config) {
        this.maxAccuracy = Math.min(100, config.getIntElse(this.getConfigName() + ".max-accuracy", 90));
        this.sampleSize =  config.getIntElse(this.getConfigName() + ".sample-size", 25);
        this.minDistance = config.getDoubleElse(this.getConfigName() + ".min-distance", 0.3);
        this.maxCombatDuration = config.getIntElse(this.getConfigName() + ".max-combat-duration", 10);
        this.minAverageTargetMovement = config.getDoubleElse(this.getConfigName() + ".min-average-target-movement", 0.3);
        hitList = new EvictingQueue<>(sampleSize);
        positionList = new EvictingQueue<>(sampleSize);
    }
}
