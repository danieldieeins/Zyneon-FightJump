package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReadyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(u.isSpectator()) {
                u.getUser().sendErrorMessage("§cAls Zuschauer kannst du nicht bereit sein§8.");
                return false;
            }
            if(FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                if(!u.isReady()) {
                    u.setReady(true);
                    Bukkit.broadcastMessage(Strings.prefix() + "§e" + u.getUser().getName() + "§7 ist bereit§8!");
                    for(Player all:Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                    }
                } else {
                    u.getUser().sendErrorMessage("§cDu kannst /ready nicht zurückziehen§8!");
                }
            } else {
                u.getUser().sendErrorMessage("§cDieser Befehl kann nur während der Einkaufsphase genutzt werden§8!");
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}