package ac.grim.grimac.checks.impl.badpackets;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "BadPacketsZ", description = "Bad packets from Z",maxBuffer = 14)
public class BadPacketsZ extends Check implements PacketCheck {
    private double buffer,TwoBuffer,Buffer3;

    public BadPacketsZ(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            WrapperPlayClientEntityAction action = new WrapperPlayClientEntityAction(event);
            if (player.hitticks > 20) return;
            switch (action.getAction()) {
                case START_SPRINTING -> {
                    long passedms = player.actionManager.getPassedAttackSince();
                    int passedTicks = player.totalFlyingPacketsSent;
                    int cps = (int) player.clickData.getCps();
                    if (cps > 5) return;
                    if (passedms > 45 && passedms < 59 && passedTicks < 5) {
                        ++TwoBuffer;
                        if (TwoBuffer >5) {
                            flagAndAlert("[2]ps: " + passedms + " pt: " + passedTicks + " cps: " + cps + " buff: " + TwoBuffer);
                        }
                    } else if (TwoBuffer > 0) {
                        TwoBuffer = TwoBuffer -0.5;
                    }
                    if (passedTicks > 4 && passedTicks < 7 && passedms >245 && passedms < 255) {
                        ++Buffer3;
                        if (Buffer3 > 7) {
                            flagAndAlert("[3]ps: " + passedms + " pt: " + passedTicks + " cps: " + cps + " buff: " + Buffer3);
                        }
                    }
                    else if (Buffer3 > 0) {
                        Buffer3 = Buffer3 -0.5;
                    }
                    if (passedms < 10 && passedTicks < 3) {
                        ++buffer;
                        if (buffer > getMaxBuffer()) {
                            flagAndAlert("ps: " + passedms + " pt: " + passedTicks + " cps: " + cps);
                        }
                    } else if (buffer > 0) {
                        buffer = buffer -0.5;
                    }
//                    String debug = "passedms=" + passedms + ", passedTicks=" + passedTicks;
//                    player.debug(debug);
//                    alert(debug);
                }
            }
        }
    }
}
