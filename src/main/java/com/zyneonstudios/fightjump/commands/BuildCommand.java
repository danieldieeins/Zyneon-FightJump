package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(p.hasPermission("zyneon.team")) {
                u.setBuild(!u.hasBuild());
                u.getUser().sendMessage("§7Dein §eBuild§8-§eMode§7 steht nun auf§8: §a"+u.hasBuild());
            } else {
                u.getUser().sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}