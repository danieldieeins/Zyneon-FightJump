package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.paper.configuration.Config;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.getUser().sendErrorMessage("§4Fehler§8:§c /set [1-12]");
                } else {
                    if(args[0].equalsIgnoreCase("1")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("2")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("3")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("4")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("5")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("6")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("7")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("8")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("9")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("10")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("11")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else if(args[0].equalsIgnoreCase("12")) {
                        setLocation(new Config("plugins/FightJump/locations/"+args[0]+".yml"),p);
                    } else {
                        u.getUser().sendErrorMessage("§4Fehler§8:§c /set [1-12]");
                    }
                }
            } else {
                u.getUser().sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }

    private void setLocation(Config locfg,Player p) {
        locfg.set("Location.World",p.getWorld().getName());
        locfg.set("Location.X",p.getLocation().getX());
        locfg.set("Location.Y",p.getLocation().getY());
        locfg.set("Location.Z",p.getLocation().getZ());
        locfg.set("Location.y",p.getLocation().getYaw());
        locfg.set("Location.p",p.getLocation().getPitch());
        locfg.saveConfig();
        locfg.reloadConfig();
    }
}
