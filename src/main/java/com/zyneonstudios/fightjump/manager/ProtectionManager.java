package com.zyneonstudios.fightjump.manager;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ProtectionManager implements Listener {

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            if (e.getEntity() instanceof Player p) {
                p.setFoodLevel(20);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent e) {
        JumpUser u = FightJump.getInstance().getJumpUser(e.getPlayer());
        if(!u.hasBuild()) {
            if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
                if (e.getAction().toString().toLowerCase().contains("block")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent e) {
        JumpUser u = FightJump.getInstance().getJumpUser(e.getPlayer());
        if(!u.hasBuild()) {
            if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        JumpUser u = FightJump.getInstance().getJumpUser(e.getPlayer());
        if (!u.hasBuild()) {
            if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
                e.setCancelled(true);
            }
        }
    }
}