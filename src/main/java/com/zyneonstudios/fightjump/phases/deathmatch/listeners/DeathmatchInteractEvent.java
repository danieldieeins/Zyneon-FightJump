package com.zyneonstudios.fightjump.phases.deathmatch.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DeathmatchInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if(!FightJump.getInstance().getJumpUser(e.getPlayer()).hasBuild()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!FightJump.getInstance().getJumpUser(e.getPlayer()).hasBuild()) {
            e.setCancelled(true);
        }
    }
}