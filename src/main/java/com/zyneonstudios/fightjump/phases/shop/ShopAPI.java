package com.zyneonstudios.fightjump.phases.shop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class ShopAPI {

    public static void spawnVillager(Location location) {
        Villager villager = (Villager)location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setGravity(false);
    }

    public static ItemStack getShopPotion(int amount, PotionType type, String name, int price) {
        ItemStack stack = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta)stack.getItemMeta();
        meta.setBasePotionData(new PotionData(type));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e§l"+price+" Tokens");
        meta.setLore(lore);
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        stack.setAmount(amount);
        return stack;
    }

    public static boolean hasPrice(ItemStack itemStack) {
        if(itemStack.hasItemMeta()) {
            if(itemStack.getItemMeta().hasLore()) {
                if(itemStack.getItemMeta().getLore().get(0).contains("Tokens")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int getPrice(ItemStack itemStack) {
        if(hasPrice(itemStack)) {
            return Integer.parseInt(itemStack.getItemMeta().getLore().get(0).replace("§e§l", "").replace(" ", "").replace("Tokens", ""));
        } else {
            return 0;
        }
    }
}