package com.zyneonstudios.fightjump.manager;

import com.zyneonstudios.api.paper.events.ZyneonChatEvent;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatManager implements Listener {



    @EventHandler
    public void onChat(ZyneonChatEvent e) {
        Player p = e.getPlayer();
        JumpUser l = FightJump.getInstance().getJumpUser(p);
        String m = e.getMessage();
        e.setCancelled(true);
        if(p.hasPermission("zyneon.premium")) {
            m = m.replace("&&","%and%").replace("&","§").replace("%and%","&");
        }
        if(FightJump.getInstance().playingUsers.contains(l)) {
            Bukkit.broadcastMessage(e.getRank() + p.getName() + "§8 » §7" + m);
        } else {
            if(FightJump.getInstance().getPhase().getType().equals(PhaseType.END)||FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY)||FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY_FULL)) {
                Bukkit.broadcastMessage(e.getRank() + p.getName() + "§8 » §7" + m);
            } else {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    JumpUser u = FightJump.getInstance().getJumpUser(all);
                    if (u.isSpectator()) {
                        u.getUser().sendRawMessage("§8(Zuschauer) " + e.getRank() + p.getName() + "§8 » §7" + m);
                    }
                }
            }
        }
    }
}