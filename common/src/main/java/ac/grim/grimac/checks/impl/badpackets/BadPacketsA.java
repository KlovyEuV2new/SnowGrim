package ac.grim.grimac.checks.impl.badpackets;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientHeldItemChange;

@CheckData(name = "BadPacketsA", description = "Suspicious rapid slot switching pattern")
public class BadPacketsA extends Check implements PacketCheck {
    private ChangeData lastChange = null;
    private int lastSlot = -1;

    private long thresholdNanos = 1_000_000L;

    public BadPacketsA(final GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.HELD_ITEM_CHANGE) {
            final long now = System.nanoTime();
            final WrapperPlayClientHeldItemChange wrapper = new WrapperPlayClientHeldItemChange(event);
            final int newSlot = wrapper.getSlot();

            ChangeData currentChange = new ChangeData(lastSlot, newSlot, now);

            if (lastChange != null &&
                    lastChange.from == currentChange.to &&
                    lastSlot != -1) {

                final long diffNanos = currentChange.time - lastChange.time;

                if (diffNanos < thresholdNanos) {
                    final double diffMs = diffNanos / 1_000_000.0;
                    final String details = String.format(
                            "%d -> %d -> %d in %.3f ms",
                            lastChange.from,
                            lastChange.to,
                            newSlot,
                            diffMs
                    );

                    if (flagAndAlert(details) && shouldModifyPackets()) {
                        event.setCancelled(true);
                        player.onPacketCancel();
                    }
                }
            }

            lastChange = currentChange;
            lastSlot = newSlot;
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        double thresholdSeconds = config.getDoubleElse(getConfigName() + ".time", 1.0);
        thresholdNanos = (long) (thresholdSeconds * 1_000_000L);
    }

    private record ChangeData(int from, int to, long time) {
    }
}
