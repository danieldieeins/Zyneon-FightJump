package com.zyneonstudios.fightjump.listeners;

import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class PlayerCommandListener implements Listener {

    private static final Collection<String> blocked = new ArrayList<>();

    public static void initBlocked() {
        blocked.add("plugins");
        blocked.add("pl");
        blocked.add("ver");
        blocked.add("version");
        blocked.add("about");
        blocked.add("timings");
        blocked.add("?");
        blocked.add("help");
        blocked.add("gsit");
        blocked.add("paper");
        blocked.add("spigot");
        FightJumpAPI.commands.removeAll(blocked);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (e.getMessage().contains("/plugins")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/pl")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/ver")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/?")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/help")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/minecraft:?")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/minecraft:help")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/version")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/about")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/timings")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/gsit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/spigot")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if (e.getMessage().contains("/bukkit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        }
    }

    @EventHandler
    public void onPlayerTab(PlayerCommandSendEvent e) {
        if(e.getPlayer().hasPermission("zyneon.leading")) {
            e.getCommands().removeAll(blocked);
            e.getCommands().removeIf(command -> command.contains(":"));
            return;
        }
        e.getCommands().clear();
        e.getCommands().addAll(FightJumpAPI.commands);
    }
}