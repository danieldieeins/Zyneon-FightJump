package com.zyneonstudios.fightjump.phases.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ShopItem extends ItemStack {

    private int price;

    public ShopItem(Material material, int price) {
        super(material);
        this.price = price;
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e§l"+price+" Tokens");
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
    }

    public ShopItem(Material material, int price, String name) {
        super(material);
        this.price = price;
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e§l"+price+" Tokens");
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        setItemMeta(meta);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}