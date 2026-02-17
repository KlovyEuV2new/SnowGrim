package ac.grim.grimac.checks.impl.autoclicker;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;

@CheckData(name = "AutoclickerA")
public class AutoClickerA extends Check implements PacketCheck {

    private final Deque<Long> samples;
    private long lastSwing;
    private double limitcps;

    public AutoClickerA(@NotNull GrimPlayer player) {
        super(player);
        this.samples = Lists.newLinkedList();
        this.lastSwing = 0L;
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ANIMATION && !isExemptClick()) {
            final long now = System.currentTimeMillis();
            final long delay = now - this.lastSwing;
            this.samples.add(delay);
            if (this.samples.size() == 20) {
                final double cps = GrimMath.getCps(this.samples);
                if (cps > limitcps && cps > 5.0) {
                    flagAndAlert("cps=" + cps);
                }
                this.samples.clear();
            }
            this.lastSwing = now;
        }
    }


    @Override
    public void onReload(ConfigManager config) {
        limitcps = config.getDoubleElse(getConfigName() + ".max-cps", 450);
    }


}
