package com.zyneonstudios.fightjump.phases.shop.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.InventoryManager;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.shop.ShopAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopInventoryEvent implements Listener {

    @EventHandler
    public void onShop(InventoryClickEvent e) {
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
            if(e.getCurrentItem()!=null) {
                if(e.getCurrentItem().getItemMeta()!=null) {
                    if(e.getWhoClicked() instanceof Player p) {
                        ItemStack item = e.getCurrentItem();
                        ItemMeta itemMeta = item.getItemMeta();
                        String itemName = itemMeta.getDisplayName();
                        if(itemName.equals(ItemManager.weaponShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.weaponShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.leatherShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.leatherShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.chainShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.chainShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.ironShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.ironShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.foodShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.foodShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.potionShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.potionShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.specialShop().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.openInventory(InventoryManager.specialShop(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else if(itemName.equals(ItemManager.placeholder().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK,100,100);
                        } else if(itemName.equals(ItemManager.closeInventory().getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            p.closeInventory();
                            p.playSound(p.getLocation(), Sound.BLOCK_CHEST_CLOSE,50,50);
                        } else if(ShopAPI.hasPrice(item)) {
                            e.setCancelled(true);
                            int price = ShopAPI.getPrice(item);
                            JumpUser u = FightJump.getInstance().getJumpUser(p);
                            if(u.getTokens()>=price) {
                                u.removeTokens(price);
                                if (item.getType().equals(Material.POPPY)) {
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                                    p.setMaxHealth(p.getMaxHealth()+2);
                                    p.setHealth(p.getMaxHealth());
                                    u.getUser().sendRawMessage("§c-"+price+" Tokens");
                                } else if (item.getType().equals(Material.SPLASH_POTION)) {
                                    if(hasInventorySpace(p)) {
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                                        if(itemName.contains("Heilung")) {
                                            if(itemName.contains("2")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give "+u.getUser().getName()+" minecraft:splash_potion{Potion:strong_healing} 2");
                                            } else {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give "+u.getUser().getName()+" minecraft:splash_potion{Potion:healing} 3");
                                            }
                                        } else {
                                            if(itemName.contains("2")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give "+u.getUser().getName()+" minecraft:splash_potion{Potion:strong_harming}");
                                            } else {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give "+u.getUser().getName()+" minecraft:splash_potion{Potion:harming}");
                                            }
                                        }
                                        u.getUser().sendRawMessage("§c-"+price+" Tokens");
                                    } else {
                                        p.closeInventory();
                                        u.getUser().sendErrorMessage("§cDu hast nicht genug Platz im Inventar§8!");
                                    }
                                } else {
                                    if(hasInventorySpace(p)) {
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                                        ItemStack give = new ItemStack(item.getType());
                                        give.setAmount(item.getAmount());
                                        p.getInventory().addItem(give);
                                        u.getUser().sendRawMessage("§c-"+price+" Tokens");
                                    } else {
                                        p.closeInventory();
                                        u.getUser().sendErrorMessage("§cDu hast nicht genug Platz im Inventar§8!");
                                    }
                                }
                            } else {
                                p.closeInventory();
                                u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean hasInventorySpace(Player player) {
        int count = 0;
        for (ItemStack i : player.getInventory()) {
            if (i == null) {
                count++;
            }
        }
        return count != 0;
    }
}