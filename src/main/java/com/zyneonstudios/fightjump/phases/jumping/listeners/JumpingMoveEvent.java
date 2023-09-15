package com.zyneonstudios.fightjump.phases.jumping.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.module.Module;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.jumping.JumpAPI;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JumpingMoveEvent implements Listener {

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(FightJump.getInstance().freezedPlayers.contains(p)) {
            e.setCancelled(true);
            return;
        }
        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
            JumpAPI.getTopList();
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if (!u.isSpectator()) {
                if (u.getJumpModule() != null) {
                    if (u.getPlayerInt() <= 11) {
                        if (p.getLocation().getBlockY() < 66) {
                            Module jumpModule = u.getJumpModule();
                            jumpModule.setFails(jumpModule.getFails() + 1);
                            if(jumpModule.getFails()==3) {
                                jumpModule.pasteLiteModule();
                            }
                            u.setBack(0);
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
                        }
                    }
                }
            }
        }
    }
}