package com.zyneonstudios.fightjump.manager;

import com.zyneonstudios.api.utils.Strings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    public static ItemStack createGeneralItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DYE,ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack placeholder() {
        return createGeneralItem(Material.BLACK_STAINED_GLASS_PANE,"§0");
    }

    public static ItemStack closeInventory() {
        return createGeneralItem(Material.BARRIER,"§cSchließen");
    }

    public static ItemStack backToLobby() {
        return createGeneralItem(Material.SLIME_BALL, Strings.backToLobbyItem());
    }

    public static ItemStack backToModule() {
        return createGeneralItem(Material.RED_DYE, "§cZurück zum Checkpoint");
    }

    public static ItemStack weaponShop() {
        return createGeneralItem(Material.GOLDEN_SWORD, "§eWaffen");
    }

    public static ItemStack leatherShop() {
        return createGeneralItem(Material.LEATHER_CHESTPLATE, "§eLederrüstung");
    }

    public static ItemStack chainShop() {
        return createGeneralItem(Material.CHAINMAIL_CHESTPLATE, "§eKettenrüstung");
    }

    public static ItemStack ironShop() {
        return createGeneralItem(Material.IRON_CHESTPLATE, "§eEisenrüstung");
    }

    public static ItemStack foodShop() {
        return createGeneralItem(Material.CAKE, "§eNahrung");
    }

    public static ItemStack potionShop() {
        return createGeneralItem(Material.POTION, "§eTränke");
    }

    public static ItemStack specialShop() {
        return createGeneralItem(Material.EMERALD, "§eSpezial");
    }
}