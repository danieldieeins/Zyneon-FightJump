package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.phases.shop.ShopAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VillagerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            JumpUser u = FightJump.getInstance().getJumpUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.getUser().sendMessage("§7Du hast einen §eShop§8-§eVillager§7 gespawnt§8.");
                    ShopAPI.spawnVillager(p.getLocation());
                } else {
                    u.getUser().sendMessage("§7Du hast den §eEntity§8-§eRemover§7 erhalten§8.");
                    p.getInventory().addItem(ItemManager.createGeneralItem(Material.STICK,"§cENTITY-REMOVER"));
                }
            } else {
                u.getUser().sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}