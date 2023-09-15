package com.zyneonstudios.fightjump.manager;

import com.zyneonstudios.fightjump.phases.shop.ShopAPI;
import com.zyneonstudios.fightjump.phases.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionType;

public class InventoryManager {

    public static void placeExitRow(Inventory inventory) {
        inventory.setItem(18,ItemManager.placeholder());
        inventory.setItem(19,ItemManager.placeholder());
        inventory.setItem(20,ItemManager.placeholder());
        inventory.setItem(21,ItemManager.placeholder());
        inventory.setItem(22,ItemManager.closeInventory());
        inventory.setItem(23,ItemManager.placeholder());
        inventory.setItem(24,ItemManager.placeholder());
        inventory.setItem(25,ItemManager.placeholder());
        inventory.setItem(26,ItemManager.placeholder());
    }

    private static void placeShopRow(Inventory inventory) {
        inventory.setItem(0,ItemManager.weaponShop());
        inventory.setItem(1,ItemManager.leatherShop());
        inventory.setItem(2,ItemManager.chainShop());
        inventory.setItem(3,ItemManager.ironShop());
        inventory.setItem(5,ItemManager.foodShop());
        inventory.setItem(6,ItemManager.potionShop());
        inventory.setItem(8,ItemManager.specialShop());
    }

    public static Inventory shopInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);
        return inv;
    }

    public static Inventory weaponShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(9,new ShopItem(Material.WOODEN_SWORD,200));
        inv.setItem(10,new ShopItem(Material.STONE_SWORD,300));
        inv.setItem(11,new ShopItem(Material.IRON_SWORD,400));

        inv.setItem(13,new ShopItem(Material.BOW,150));
        inv.setItem(14,new ShopItem(Material.ARROW,8));

        inv.setItem(16,new ShopItem(Material.FISHING_ROD,100));
        inv.setItem(17,new ShopItem(Material.TNT,80));

        return inv;
    }

    public static Inventory leatherShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(11,new ShopItem(Material.LEATHER_HELMET,50));
        inv.setItem(12,new ShopItem(Material.LEATHER_CHESTPLATE,100));

        inv.setItem(14,new ShopItem(Material.LEATHER_LEGGINGS,50));
        inv.setItem(15,new ShopItem(Material.LEATHER_BOOTS,50));

        return inv;
    }

    public static Inventory chainShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(11,new ShopItem(Material.CHAINMAIL_HELMET,100));
        inv.setItem(12,new ShopItem(Material.CHAINMAIL_CHESTPLATE,200));

        inv.setItem(14,new ShopItem(Material.CHAINMAIL_LEGGINGS,100));
        inv.setItem(15,new ShopItem(Material.CHAINMAIL_BOOTS,100));

        return inv;
    }

    public static Inventory ironShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(11,new ShopItem(Material.IRON_HELMET,200));
        inv.setItem(12,new ShopItem(Material.IRON_CHESTPLATE,300));

        inv.setItem(14,new ShopItem(Material.IRON_LEGGINGS,200));
        inv.setItem(15,new ShopItem(Material.IRON_BOOTS,200));

        return inv;
    }

    public static Inventory foodShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(11,new ShopItem(Material.APPLE,2));
        inv.setItem(12,new ShopItem(Material.COOKED_BEEF,4));

        inv.setItem(14,new ShopItem(Material.CAKE,8));
        inv.setItem(15,new ShopItem(Material.GOLDEN_APPLE,100));

        return inv;
    }

    public static Inventory potionShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(11, ShopAPI.getShopPotion(3,PotionType.INSTANT_HEAL,"Werfbarer Trank der Heilung",200));
        inv.setItem(12,ShopAPI.getShopPotion(2,PotionType.INSTANT_HEAL,"Werfbarer Trank der Heilung 2",200));

        inv.setItem(14,ShopAPI.getShopPotion(1,PotionType.INSTANT_DAMAGE,"Werfbarer Trank des Schadens",180));
        inv.setItem(15,ShopAPI.getShopPotion(1,PotionType.INSTANT_DAMAGE,"Werfbarer Trank des Schadens 2",250));

        return inv;
    }

    public static Inventory specialShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"§8FightJump-Shop");
        placeExitRow(inv);
        placeShopRow(inv);

        inv.setItem(13,new ShopItem(Material.POPPY,200,"Ein Extra Herz"));

        return inv;
    }
}