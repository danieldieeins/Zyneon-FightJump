package com.zyneonstudios.fightjump.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("zyneon.team")) {
            if(p.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("ENTITY-REMOVER")) {
                    e.getRightClicked().remove();
                }
            }
        }
    }
}