package com.zyneonstudios.fightjump.phases.jumping.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.module.Module;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class JumpingInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getItem() != null) {
                    if (e.getItem().getItemMeta() != null) {
                        if (e.getItem().getItemMeta().getDisplayName().equals(ItemManager.backToModule().getItemMeta().getDisplayName())) {
                            JumpUser u = FightJump.getInstance().getJumpUser(e.getPlayer());
                            if (u.getJumpModule() != null) {
                                if (u.getJumpModule().getStartLoc() != null) {
                                    if (u.getBack() != 0) {
                                        Player p = e.getPlayer();
                                        Bukkit.getScheduler().cancelTask(u.backint);
                                        u.setBack(0);
                                        Module jumpModule = u.getJumpModule();
                                        jumpModule.setFails(jumpModule.getFails() + 1);
                                        if(jumpModule.getFails()==3) {
                                            jumpModule.pasteLiteModule();
                                        }
                                        Location respawnLoc = new Location(jumpModule.getStartLoc().getWorld(), jumpModule.getStartLoc().getBlockX() + 0.5, jumpModule.getStartLoc().getBlockY(), jumpModule.getStartLoc().getBlockZ() + 0.5, -90, 0);
                                        p.teleport(respawnLoc);
                                        p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 100, 100);
                                        if (jumpModule.getFails() == 1) {
                                            p.sendTitle("§c§l× §r§l× §r§l×", "§7Du bist einmal heruntergefallen§8!");
                                        } else if (jumpModule.getFails() == 2) {
                                            p.sendTitle("§c§l× §c§l× §r§l×", "§7Du bist zweimal heruntergefallen§8!");
                                        } else if (jumpModule.getFails() == 3) {
                                            p.sendTitle("§c§l× §c§l× §c§l×", "§7Du bist dreimal heruntergefallen§8!");
                                        } else {
                                            p.sendTitle("§c§l× §c§l× §c§l×", "§7Du bist " + jumpModule.getFails() + "x heruntergefallen§8!");
                                        }
                                    } else {
                                        u.setBack(1);
                                        u.getUser().sendMessage("§7Rechtsklicke erneut§8, um zum §eModulstart§7 zurückzukehren§8!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
            e.setCancelled(true);
        }
    }
}