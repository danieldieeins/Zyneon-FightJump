package com.zyneonstudios.fightjump.phases.lobby.listeners;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY)|| FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY_FULL)|| FightJump.getInstance().getPhase().getType().equals(PhaseType.END)) {
            if(e.getAction().toString().toLowerCase().contains("right")) {
                if(e.getItem()!=null) {
                    if(e.getItem().getItemMeta()!=null) {
                        ItemStack item = e.getItem();
                        ItemMeta itemMeta = item.getItemMeta();
                        String itemName = itemMeta.getDisplayName();
                        if(itemName.equals(ItemManager.backToLobby().getItemMeta().getDisplayName())) {
                            Player p = e.getPlayer();
                            User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
                            u.switchServer("Lobby-1");
                            p.kickPlayer(Strings.prefix()+"§7Du wirst zur Lobby zurückgesendet§8!");
                        }
                    }
                }
            }
        }
    }
}