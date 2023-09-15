package com.zyneonstudios.fightjump.listeners;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.PhaseAPI;
import com.zyneonstudios.fightjump.phases.lobby.LobbyAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setMaxHealth(20);
        p.setExp(0);
        JumpUser u = FightJump.getInstance().getJumpUser(p);
        u.setTokens(0);
        FightJumpAPI.renewScoreboard(p);
        for(Player all : Bukkit.getOnlinePlayers()) {
            //setRank(all);
        }
        e.setJoinMessage(null);
        if(u.isSpectator()) {
            p.setGameMode(GameMode.SPECTATOR);
            for(Player all:Bukkit.getOnlinePlayers()) {
                all.hidePlayer(FightJump.getInstance(),p);
            }
        } else {
            for(Player all:Bukkit.getOnlinePlayers()) {
                if(all.getUniqueId()!=p.getUniqueId()&&!all.getName().equals(p.getName())) {
                    all.sendMessage("§8» §a" + p.getName());
                }
            }
            Bukkit.getConsoleSender().sendMessage("§8» §a"+p.getName());
            if (FightJump.getInstance().getPhase().getType() == PhaseType.LOBBY|| FightJump.getInstance().getPhase().getType() == PhaseType.LOBBY_FULL) {
                p.teleport(FightJump.getLobby());
                p.setGameMode(GameMode.ADVENTURE);
                p.getInventory().clear();
                if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.END)) {
                    if (!FightJump.getInstance().isStarted) {
                        if (FightJump.getInstance().jumpUsers.size() >= 2) {
                            LobbyAPI.startTimer();
                        } else {
                            Bukkit.broadcastMessage(Strings.prefix()+"§7Es wird noch auf genügend Spieler gewartet§8...");
                            for(Player all:Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                            }
                        }
                    }
                    if (FightJump.getInstance().jumpUsers.size() == FightJump.getInstance().getMaxPlayers()) {
                        PhaseAPI.setPhase(new Phase(PhaseType.LOBBY_FULL));
                    }
                }
                p.getInventory().setItem(8, ItemManager.backToLobby());
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        JumpUser u = FightJump.getInstance().getJumpUser(p);
        if(FightJump.getInstance().getPhase().getType()==PhaseType.STARTUP) {
            e.setKickMessage("§cDas Spiel startet noch§8...");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(FightJump.getInstance().getPhase().getType()==PhaseType.JUMPING|| FightJump.getInstance().getPhase().getType()==PhaseType.SHOP|| FightJump.getInstance().getPhase().getType()==PhaseType.DEATHMATCH) {
            u.setSpectator(true);
            p.setGameMode(GameMode.SPECTATOR);
        } else if(FightJump.getInstance().getPhase().getType()==PhaseType.LOBBY_FULL) {
            e.setKickMessage("§cDer Gameserver ist voll§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(FightJump.getInstance().getPhase().getType()==PhaseType.END) {
            e.setKickMessage("§cDer Server startet neu§8...");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}