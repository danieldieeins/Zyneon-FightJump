package com.zyneonstudios.fightjump.phases.shop.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.InventoryManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ShopInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
            if (e.getItem() != null) {
                if (e.getItem().getItemMeta() != null) {
                    if (e.getClickedBlock() != null) {
                        if (e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) {
                            e.setCancelled(true);
                            Player p = e.getPlayer();
                            JumpUser u = FightJump.getInstance().getJumpUser(p);
                            ItemStack item = e.getItem();
                            ItemMeta itemMeta = item.getItemMeta();
                            if (e.getItem().getType().toString().toLowerCase().contains("sword")) {
                                if (!itemMeta.hasCustomModelData()) {
                                    if (u.getTokens() >= 250) {
                                        p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                        item = enchantSword(item);
                                        itemMeta = item.getItemMeta();
                                        itemMeta.setCustomModelData(1);
                                        item.setItemMeta(itemMeta);
                                        e.getPlayer().getInventory().setItemInMainHand(item);
                                        u.removeTokens(250);
                                    } else {
                                        u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                    }
                                } else {
                                    if (itemMeta.getCustomModelData() < 5) {
                                        if (u.getTokens() >= 400) {
                                            p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                            item = enchantSword(item);
                                            itemMeta = item.getItemMeta();
                                            itemMeta.setCustomModelData(itemMeta.getCustomModelData() + 1);
                                            item.setItemMeta(itemMeta);
                                            e.getPlayer().getInventory().setItemInMainHand(item);
                                            u.removeTokens(400);
                                        } else {
                                            u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                        }
                                    } else {
                                        u.getUser().sendErrorMessage("§cDu hast das maximale Enchantmentlevel für dieses Item erreicht§8.");
                                    }
                                }
                            } else if (e.getItem().getType().toString().toLowerCase().contains("helmet") || e.getItem().getType().toString().toLowerCase().contains("chestplate") || e.getItem().getType().toString().toLowerCase().contains("leggings") || e.getItem().getType().toString().toLowerCase().contains("boots")) {
                                if (!itemMeta.hasCustomModelData()) {
                                    if (u.getTokens() >= 250) {
                                        p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                        item = enchantArmor(item);
                                        itemMeta = item.getItemMeta();
                                        itemMeta.setCustomModelData(1);
                                        item.setItemMeta(itemMeta);
                                        e.getPlayer().getInventory().setItemInMainHand(item);
                                        u.removeTokens(250);
                                    } else {
                                        u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                    }
                                } else {
                                    if (itemMeta.getCustomModelData() < 5) {
                                        if (u.getTokens() >= 400) {
                                            p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                            item = enchantArmor(item);
                                            itemMeta = item.getItemMeta();
                                            itemMeta.setCustomModelData(itemMeta.getCustomModelData() + 1);
                                            item.setItemMeta(itemMeta);
                                            e.getPlayer().getInventory().setItemInMainHand(item);
                                            u.removeTokens(400);
                                        } else {
                                            u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                        }
                                    } else {
                                        u.getUser().sendErrorMessage("§cDu hast das maximale Enchantmentlevel für dieses Item erreicht§8.");
                                    }
                                }
                            } else if (e.getItem().getType().toString().toLowerCase().contains("bow")) {
                                if (!itemMeta.hasCustomModelData()) {
                                    if (u.getTokens() >= 250) {
                                        p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                        item = enchantBow(item);
                                        itemMeta = item.getItemMeta();
                                        itemMeta.setCustomModelData(1);
                                        item.setItemMeta(itemMeta);
                                        e.getPlayer().getInventory().setItemInMainHand(item);
                                        u.removeTokens(250);
                                    } else {
                                        u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                    }
                                } else {
                                    if (itemMeta.getCustomModelData() < 5) {
                                        if (u.getTokens() >= 400) {
                                            p.playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,100,100);
                                            item = enchantBow(item);
                                            itemMeta = item.getItemMeta();
                                            itemMeta.setCustomModelData(itemMeta.getCustomModelData() + 1);
                                            item.setItemMeta(itemMeta);
                                            e.getPlayer().getInventory().setItemInMainHand(item);
                                            u.removeTokens(400);
                                        } else {
                                            u.getUser().sendErrorMessage("§cDazu hast du nicht genug Tokens§8!");
                                        }
                                    } else {
                                        u.getUser().sendErrorMessage("§cDu hast das maximale Enchantmentlevel für dieses Item erreicht§8.");
                                    }
                                }
                            } else {
                                u.getUser().sendErrorMessage("§cDieses Item kannst du nicht verzaubern§8!");
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
            if(e.getRightClicked() instanceof Villager) {
                Player p = e.getPlayer();
                p.openInventory(InventoryManager.shopInventory(p));
                p.playSound(p.getLocation(),Sound.BLOCK_CHEST_OPEN,50,50);
            }
        }
    }

    private ItemStack enchantSword(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
        if (!itemMeta.hasEnchant(Enchantment.DAMAGE_ALL)) {
            int r = ThreadLocalRandom.current().nextInt(1, 10);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL)+1;
            itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL,level,true);
        }
        if (!itemMeta.hasEnchant(Enchantment.KNOCKBACK)) {
            int r = ThreadLocalRandom.current().nextInt(1, 10);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.KNOCKBACK)+1;
            if(level!=3) {
                itemMeta.removeEnchant(Enchantment.KNOCKBACK);
                itemMeta.addEnchant(Enchantment.KNOCKBACK, level, true);
            }
        }
        if (!itemMeta.hasEnchant(Enchantment.FIRE_ASPECT)) {
            int r = ThreadLocalRandom.current().nextInt(1, 50);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.FIRE_ASPECT)+1;
            if(level!=2) {
                itemMeta.removeEnchant(Enchantment.FIRE_ASPECT);
                itemMeta.addEnchant(Enchantment.FIRE_ASPECT, level, true);
            }
        }
        if(itemMeta.getEnchants().equals(enchantments)) {
            return enchantSword(itemStack);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack enchantBow(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
        if (!itemMeta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
            int r = ThreadLocalRandom.current().nextInt(1, 10);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.ARROW_DAMAGE)+1;
            itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
            itemMeta.addEnchant(Enchantment.ARROW_DAMAGE,level,true);
        }
        if (!itemMeta.hasEnchant(Enchantment.ARROW_KNOCKBACK)) {
            int r = ThreadLocalRandom.current().nextInt(1, 10);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)+1;
            if(level!=2) {
                itemMeta.removeEnchant(Enchantment.ARROW_KNOCKBACK);
                itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, level, true);
            }
        }
        if (!itemMeta.hasEnchant(Enchantment.ARROW_FIRE)) {
            int r = ThreadLocalRandom.current().nextInt(1, 25);
            if (r == 1) {
                itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, false);
            }
        } else {
            int level = itemMeta.getEnchantLevel(Enchantment.ARROW_FIRE)+1;
            if(level!=3) {
                itemMeta.removeEnchant(Enchantment.ARROW_FIRE);
                itemMeta.addEnchant(Enchantment.ARROW_FIRE, level, true);
            }
        }
        if(itemMeta.getEnchants().equals(enchantments)) {
            return enchantBow(itemStack);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack enchantArmor(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchantments = itemMeta.getEnchants();
        if (!itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
            if (!itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                int r = ThreadLocalRandom.current().nextInt(1, 10);
                if (r == 1) {
                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                }
            } else {
                int level = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)+1;
                itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,level,true);
            }
        }
        if (!itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            if (!itemMeta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                int r = ThreadLocalRandom.current().nextInt(1, 10);
                if (r == 1) {
                    itemMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, false);
                }
            } else {
                int level = itemMeta.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)+1;
                itemMeta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
                itemMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,level,true);
            }
        }
        if (itemStack.getType().toString().toLowerCase().contains("boots")) {
            if (!itemMeta.hasEnchant(Enchantment.PROTECTION_FALL)) {
                int r = ThreadLocalRandom.current().nextInt(1, 25);
                if (r == 1) {
                    itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, false);
                }
            } else {
                int level = itemMeta.getEnchantLevel(Enchantment.PROTECTION_FALL)+1;
                itemMeta.removeEnchant(Enchantment.PROTECTION_FALL);
                itemMeta.addEnchant(Enchantment.PROTECTION_FALL,level,true);
            }
        }
        if(itemMeta.getEnchants().equals(enchantments)) {
            return enchantArmor(itemStack);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}