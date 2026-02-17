package ac.grim.grimac.api.npcs.armor;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.api.npcs.NpcManager;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.latency.CompensatedInventory;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.*;
import com.github.retrooper.packetevents.protocol.player.Equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


public class PlayerArmor {
    public static List<Equipment> copyRandomArmor(NpcManager.TrackedNpc npc) {
        GrimPlayer grimPlayer = npc.copying;
        return grimPlayer != null ? copyArmor(grimPlayer) : List.of();
    }
    public static NBTCompound toNBT(List<Equipment> equipments) {
        NBTCompound nbt = new NBTCompound();
        NBTList<@NotNull NBTCompound> armorList = new NBTList<>(NBTType.COMPOUND);

        for (Equipment equipment : equipments) {
            NBTCompound itemNBT = new NBTCompound();
            ItemStack item = equipment.getItem();

            itemNBT.setTag("id", new NBTString("minecraft:" + item.getType().getName().getKey()));
            itemNBT.setTag("Count", new NBTInt(item.getAmount()));

            NBTCompound tagCompound = new NBTCompound();
            if (item.getNBT() != null) {
                for (String key : item.getNBT().getTags().keySet()) {
                    tagCompound.setTag(key, item.getNBT().getTagOrNull(key));
                }
            }
            if (!tagCompound.getTags().isEmpty()) {
                itemNBT.setTag("tag", tagCompound);
            }

            armorList.addTag(itemNBT);
        }

        nbt.setTag("ArmorItems", armorList);
        return nbt;
    }
    public static GrimPlayer getRandomPlayer() {
        Collection<GrimPlayer> entries = GrimAPI.INSTANCE.getPlayerDataManager().getEntries();
        if (entries.isEmpty()) {
            return null;
        }
        int index = ThreadLocalRandom.current().nextInt(entries.size());
        var iterator = entries.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
    public static List<Equipment> copyArmor(@NotNull GrimPlayer player) {
        List<Equipment> equipments = new ArrayList<>();

        CompensatedInventory inventory = player.inventory;
        List<SlotItem> items = new ArrayList<>(Arrays.asList(
                new SlotItem(EquipmentSlot.HELMET, inventory.getHelmet()),
                new SlotItem(EquipmentSlot.CHEST_PLATE, inventory.getChestplate()),
                new SlotItem(EquipmentSlot.LEGGINGS, inventory.getLeggings()),
                new SlotItem(EquipmentSlot.BOOTS, inventory.getBoots()),
                new SlotItem(EquipmentSlot.MAIN_HAND, inventory.getItemInHand(InteractionHand.MAIN_HAND)),
                new SlotItem(EquipmentSlot.OFF_HAND, inventory.getItemInHand(InteractionHand.OFF_HAND))
        ));

        for (SlotItem item : items) {
            equipments.add(generateEquipment(item));
        }

        return equipments;
    }
    private static Equipment generateEquipment(SlotItem item) {
        return new Equipment(item.slot,item.item);
    }
    public static class SlotItem {
        @Getter
        private EquipmentSlot slot;
        @Getter
        private ItemStack item;
        public SlotItem(EquipmentSlot slot, ItemStack item) {
            this.slot = slot;
            this.item = item;
        }
    }
}
