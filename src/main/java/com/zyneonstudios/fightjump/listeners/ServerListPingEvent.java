package com.zyneonstudios.fightjump.listeners;

import com.zyneonstudios.fightjump.FightJump;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ServerListPingEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(org.bukkit.event.server.ServerListPingEvent e) {
        e.setMotd(FightJump.getInstance().getPhase().getDisplay());
        e.setMaxPlayers(FightJump.getInstance().getMaxPlayers());
    }
}