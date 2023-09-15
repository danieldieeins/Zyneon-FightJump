package com.zyneonstudios.fightjump.phases.deathmatch.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class DeathmatchTNTEvent implements Listener {

    @EventHandler
    public void onCreate(BlockPlaceEvent e) {
        if(e.getBlock().getType().equals(Material.TNT)) {
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        e.blockList().clear();
    }
}