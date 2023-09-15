package com.zyneonstudios.fightjump.listeners;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.lobby.LobbyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
        Player p = e.getPlayer();
        JumpUser u = FightJump.getInstance().getJumpUser(p);
        FightJump.getInstance().playingUsers.remove(u);
        e.setQuitMessage(null);
        if(!u.isSpectator()) {
            Bukkit.broadcastMessage("§8« §c"+p.getName());
        }
        u.destroy();
        if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.END)) {
            if (!FightJump.getInstance().isStarted) {
                if (FightJump.getInstance().jumpUsers.size() < 2) {
                    LobbyAPI.endStartTimer();
                }
            } else {
                if (!(FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING) || FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY)) || FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY_FULL)) {
                    if (FightJump.getInstance().playingUsers.size() < 2) {
                        FightJump.getInstance().playingUsers.get(0).setWinner(true);
                    }
                } else {
                    if (FightJump.getInstance().playingUsers.size() < 2) {
                        FightJump.getInstance().playingUsers.get(0).setWinner(false);
                    }
                }
            }
        }
    }
}