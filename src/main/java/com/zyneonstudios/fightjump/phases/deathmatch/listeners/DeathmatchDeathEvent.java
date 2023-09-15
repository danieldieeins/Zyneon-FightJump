package com.zyneonstudios.fightjump.phases.deathmatch.listeners;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.deathmatch.DeathmatchAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathmatchDeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            Player p = e.getPlayer();
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(p.getKiller()!=null) {
                Player k = p.getKiller();
                JumpUser kU = FightJump.getInstance().getJumpUser(k);
                kU.setKills(kU.getKills()+1);
                k.playSound(k.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,100,100);
                kU.getUser().addCoins(5);
                if(k.hasPermission("zyneon.premium")) {
                    kU.getUser().addCoins(5);
                    kU.getUser().sendMessage("§7Für den Kill hast du 10 Coins bekommen§8.\n (5 Coins + 5 Coins Premiumboost)");
                } else {
                    kU.getUser().sendMessage("§7Für den Kill hast du 5 Coins bekommen§8.");
                }
                FightJumpAPI.setSQL(k.getUniqueId(),"kills", FightJumpAPI.getSQL(k.getUniqueId(),"kills")+1);
                Bukkit.broadcastMessage(Strings.prefix()+"§e"+p.getName()+"§7 wurde von §a"+p.getKiller().getName()+"§7 getötet§8!");
                p.setKiller(null);
            } else {
                Bukkit.broadcastMessage(Strings.prefix()+"§e"+p.getName()+"§7 ist gestorben§8!");
            }
            u.addDeath();
            FightJumpAPI.renewScoreboard(p);
            if(u.getDeaths()<=2) {
               e.setKeepInventory(true);
               e.setKeepLevel(true);
               e.getDrops().clear();
            } else {
                e.setKeepLevel(false);
                e.setKeepInventory(false);
            }
            if (FightJump.getInstance().playingUsers.size() < 2) {
                FightJump.getInstance().playingUsers.get(0).setWinner(true);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(DeathmatchAPI.getArenaLocation());
    }
}