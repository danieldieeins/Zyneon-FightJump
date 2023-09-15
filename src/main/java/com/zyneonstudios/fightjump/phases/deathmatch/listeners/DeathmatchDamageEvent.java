package com.zyneonstudios.fightjump.phases.deathmatch.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DeathmatchDamageEvent implements Listener {

    @EventHandler
    public void onDamage(org.bukkit.event.entity.EntityDamageEvent e) {
        if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            e.setCancelled(true);
        } else {
            if (e.getEntity() instanceof Player p) {
                if (FightJump.getInstance().getJumpUser(p).isSpectator()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}