package com.zyneonstudios.fightjump.phases.jumping.listeners;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.module.Module;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.jumping.JumpAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ModuleFinishEvent implements Listener {

    @EventHandler
    public void onFinish(PlayerInteractEvent e) {
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
            if (!JumpAPI.isJumpingFinished) {
                if (e.getAction().equals(Action.PHYSICAL)) {
                    if (e.getClickedBlock() != null) {
                        if (e.getClickedBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                            Block block = Bukkit.getWorld("map").getBlockAt(e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ());
                            Player p = e.getPlayer();
                            JumpUser u = FightJump.getInstance().getJumpUser(p);
                            if (u.getJumpModule() != null) {
                                Module jumpModule = u.getJumpModule();
                                Location loc = new Location(jumpModule.getEndLoc().getWorld(), jumpModule.getEndLoc().getBlockX(), jumpModule.getEndLoc().getBlockY(), jumpModule.getEndLoc().getBlockZ(), 0, 0);
                                if (e.getClickedBlock().getLocation().equals(loc)) {
                                    block.setType(Material.AIR);
                                    Bukkit.getWorld("map").getBlockAt(e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ()).setType(Material.AIR);
                                    try {
                                        Bukkit.getWorld("map").getBlockAt(jumpModule.getEndLoc()).setType(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                                        Bukkit.getWorld("map").getBlockAt(jumpModule.getEndLoc()).setType(Material.AIR);
                                        jumpModule.finish();
                                        e.getClickedBlock().setType(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                                    } catch (IndexOutOfBoundsException ex) {
                                        FightJumpAPI.setSQL(p.getUniqueId(),"modules", FightJumpAPI.getSQL(p.getUniqueId(),"modules")+1);
                                        if (!u.hasFinished()) {
                                            u.setHasFinished(true);
                                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                all.sendMessage(Strings.prefix() + "§e" + p.getName() + "§7 hat alle Module geschafft§8!");
                                                all.sendTitle(p.getName(), "§7hat alle Module geschafft!");
                                                all.playSound(all.getLocation(), Sound.ENTITY_WITHER_DEATH, 25, 50);
                                            }
                                            for (JumpUser all: FightJump.getInstance().playingUsers) {
                                                all.getUser().getPlayer().hidePlayer(FightJump.getInstance(),p);
                                            }
                                            u.setJumpModule(null);
                                            p.setAllowFlight(true);
                                            p.setFlying(true);
                                            JumpAPI.endJumping();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}