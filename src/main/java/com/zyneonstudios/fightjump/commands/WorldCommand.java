package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.getUser().sendErrorMessage("§4Fehler§8: §c/world [welt]");
                } else {
                    if(Bukkit.getWorld(args[0])!=null) {
                        p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
                    } else {
                        u.getUser().sendErrorMessage("§cDiese Welt existiert nicht§8!");
                    }
                }
            } else {
                u.getUser().sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.prefix());
        }
        return false;
    }
}
