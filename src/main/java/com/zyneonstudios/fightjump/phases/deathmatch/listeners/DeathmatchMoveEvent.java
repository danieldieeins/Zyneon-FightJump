package com.zyneonstudios.fightjump.phases.deathmatch.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.PhaseAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class DeathmatchMoveEvent implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            Player p = e.getPlayer();
            if (p.getWorld().getName().toLowerCase().contains("arena")) {
                PhaseAPI.setPhase(new Phase(PhaseType.DEATHMATCH));
            }
        }
    }
}
