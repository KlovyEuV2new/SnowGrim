package ac.grim.grimac.api.npcs.armor;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemType;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomArmorGenerator {

    private static final String[] HELMET_NAMES = {
            "leather_helmet", "golden_helmet", "chainmail_helmet",
            "iron_helmet", "diamond_helmet", "netherite_helmet"
    };
    private static final String[] CHESTPLATE_NAMES = {
            "leather_chestplate", "golden_chestplate", "chainmail_chestplate",
            "iron_chestplate", "diamond_chestplate", "netherite_chestplate"
    };
    private static final String[] LEGGINGS_NAMES = {
            "leather_leggings", "golden_leggings", "chainmail_leggings",
            "iron_leggings", "diamond_leggings", "netherite_leggings"
    };
    private static final String[] BOOTS_NAMES = {
            "leather_boots", "golden_boots", "chainmail_boots",
            "iron_boots", "diamond_boots", "netherite_boots"
    };

    public static void sendRandomArmor(GrimPlayer player, int entityId) {
        try {
            List<Equipment> equipmentList = new ArrayList<>();

            List<ItemType> helmets = getAvailableArmorForVersion(HELMET_NAMES);
            List<ItemType> chestplates = getAvailableArmorForVersion(CHESTPLATE_NAMES);
            List<ItemType> leggings = getAvailableArmorForVersion(LEGGINGS_NAMES);
            List<ItemType> boots = getAvailableArmorForVersion(BOOTS_NAMES);

            equipmentList.add(new Equipment(EquipmentSlot.HELMET, getRandomItem(helmets)));
            equipmentList.add(new Equipment(EquipmentSlot.CHEST_PLATE, getRandomItem(chestplates)));
            equipmentList.add(new Equipment(EquipmentSlot.LEGGINGS, getRandomItem(leggings)));
            equipmentList.add(new Equipment(EquipmentSlot.BOOTS, getRandomItem(boots)));

            WrapperPlayServerEntityEquipment packet = new WrapperPlayServerEntityEquipment(entityId, equipmentList);
            player.user.sendPacket(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<ItemType> getAvailableArmorForVersion(String[] materialNames) {
        List<ItemType> availableItems = new ArrayList<>();
        for (String name : materialNames) {
            ItemType type = ItemTypes.getByName(name);
            if (type != null) {
                if (type != ItemTypes.AIR) {
                    availableItems.add(type);
                }
            }
        }
        return availableItems;
    }

    private static ItemStack getRandomItem(List<ItemType> materials) {
        if (materials.isEmpty()) {
            return ItemStack.builder().type(ItemTypes.AIR).amount(1).build();
        }
        ItemType type = materials.get(ThreadLocalRandom.current().nextInt(materials.size()));
        return ItemStack.builder().type(type).amount(1).build();
    }
}
