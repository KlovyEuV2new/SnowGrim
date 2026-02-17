package ac.grim.grimac.checks.impl.autoclicker;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.lists.EvictingQueue;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AutoclickerB")
public class AutoclickerB extends Check implements PacketCheck {

    private double deltaDeviationThreshold;
    private double deviationThreshold;

    private EvictingQueue<Integer> samples;
    private int lastClickTick;
    private double deviationBuffer = 0;
    private double deltaDeviationBuffer = 0;
    private double lastDeviation;


    public AutoclickerB(@NotNull GrimPlayer player) {
        super(player);
    }


    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        boolean notAnimationPacket = event.getPacketType() != PacketType.Play.Client.ANIMATION;
        boolean breakingBlock = isExemptClick();//player.actionManager.isBreakingBlock();
        boolean notEnoughCPS = player.clickData.getCps() < 9;
        int delta = player.totalFlyingPacketsSent - lastClickTick;

        if (notAnimationPacket || breakingBlock || notEnoughCPS) {
            return;
        }

        lastClickTick = player.totalFlyingPacketsSent;
        samples.add(delta);

        if (samples.isCollected()) {
            check();
            // Clear previous samples due to affect and flags too much for one series.
            samples.clear();
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        deltaDeviationThreshold = config.getDoubleElse(getConfigName()+".delta-deviation-threshold", 0.01);
        deviationThreshold = config.getDoubleElse(getConfigName()+".deviation-threshold", 0.7);
        int sample_size = config.getIntElse(getConfigName()+".sample-size", 50);
        samples = new EvictingQueue<>(sample_size);
    }

    private void check() {
        double deviation = GrimMath.getStandardDeviation(samples);
        // We check delta average either, it can detect small cps difference e.t.c on long time.
        double deltaDeviation = Math.abs(lastDeviation - deviation);
        if (deltaDeviation < deltaDeviationThreshold) {
            deltaDeviationBuffer += (deltaDeviationThreshold - deltaDeviation) * 100;

            if (deltaDeviationBuffer > 2) {
                flagAndAlert("delta=" + deltaDeviation);
                deltaDeviationBuffer = 0;
            }
        } else {
            deltaDeviationBuffer -= Math.min(deltaDeviationBuffer, 0.3);
        }

        lastDeviation = deviation;

        // Deviation can`t be negative or zero.
        if (deviation < deviationThreshold) {
            deviationBuffer += (deviationThreshold - deviation) * 10;

            if (deviationBuffer > 10) {
                deviationBuffer = 0;
                flagAndAlert("dev=" + deviation);
            }
        } else {
            deviationBuffer -= Math.min(deviationBuffer, 0.5);
        }
    }
}
