package ac.grim.grimac.checks.impl.client;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@CheckData(name = "ClientA", description = "invalid payload String")
public class ClientA extends Check implements PacketCheck {
    private List<String> blockedbrands = new ArrayList<>();
    public ClientA(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (isCheckPacket(event.getPacketType())) {
            String brand = player.getBrand().toLowerCase(Locale.ROOT);
            if (blockedbrands.contains(brand)) {
                flagAndAlert("type " + replaceBrand(brand));
            }
        }
    }

    private String replaceBrand(String brand) {
        if (brand.contains("/")) {
            return "client - expensive upgrade, monotone";
        }
        return "client - " + brand;
    }

    private boolean isCheckPacket(PacketTypeCommon packetType) {
        return packetType == PacketType.Play.Client.INTERACT_ENTITY || packetType == PacketType.Play.Client.ENTITY_ACTION || packetType == PacketType.Play.Client.CLICK_WINDOW;
    }

    public void onReload(ConfigManager config) {
        blockedbrands = config.getStringListElse(getConfigName() + ".blocked-brands",new ArrayList<>());
    }
}
