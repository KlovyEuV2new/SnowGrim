package ac.grim.grimac.utils.anticheat;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;


public class TimeMilistUtil {
    private GrimPlayer player;
    private long lastTime;
    public TimeMilistUtil(GrimPlayer player) {
        this.player = player;
        lastTime = System.currentTimeMillis();
    }
    public double getPassed() {
        return (System.currentTimeMillis() - lastTime);
    }
    public boolean hasNotPassed(int time) {
        return (getPassed() < time);
    }
    public void reset() {
        lastTime = System.currentTimeMillis();
    }
    public static boolean isTransaction(PacketTypeCommon packetType) {
        return packetType == PacketType.Play.Client.PONG || packetType == PacketType.Play.Client.WINDOW_CONFIRMATION;
    }
}
