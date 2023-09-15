package com.zyneonstudios.fightjump.objects.module;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Module {

    private ModuleDifficulty difficulty;
    private Clipboard clipboard;
    private File schematicFile;
    private File liteFile;
    private Location startLoc;
    private Location endLoc;
    private Player player;
    private String name;
    private int fails;

    public Module(Player player, File schematicFile, File liteFile, Location startLoc, Location endLoc, ModuleDifficulty difficulty) {
        this.difficulty = difficulty;
        this.schematicFile = schematicFile;
        this.liteFile = liteFile;
        this.name = schematicFile.getName().replace(".schem","");
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.player = player;
        ClipboardFormat format = ClipboardFormats.findByFile(this.schematicFile);
        try (ClipboardReader reader = format.getReader(new FileInputStream(this.schematicFile))) {
            clipboard = reader.read();
        } catch (FileNotFoundException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Datei nicht gefunden§8!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Es ist ein Fehler aufgetreten§8!");
            throw new RuntimeException(e);
        }
        fails = 0;
    }

    public void pasteModule() {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(Bukkit.getWorld("map")))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(startLoc.getBlockX(),startLoc.getBlockY(),startLoc.getBlockZ()))
                    .build();
            Operations.complete(operation);
        }
        player.setFlying(false);
        player.setAllowFlight(false);
    }

    public void pasteLiteModule() {
        ClipboardFormat format = ClipboardFormats.findByFile(this.liteFile);
        try (ClipboardReader reader = format.getReader(new FileInputStream(this.liteFile))) {
            clipboard = reader.read();
        } catch (FileNotFoundException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Datei nicht gefunden§8!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Es ist ein Fehler aufgetreten§8!");
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(Bukkit.getWorld("map")))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(startLoc.getBlockX(),startLoc.getBlockY(),startLoc.getBlockZ()))
                    .build();
            Operations.complete(operation);
        }
        player.setFlying(false);
        player.setAllowFlight(false);
    }

    public String getDifficulty() {
        return difficulty.toString();
    }

    public int getFails() {
        return fails;
    }

    public String getName() {
        return name.replace("module","");
    }

    public Location getStartLoc() {
        return startLoc;
    }

    public Location getEndLoc() {
        return endLoc;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    public void setStartLoc(Location startLoc) {
        this.startLoc = startLoc;
        this.endLoc = new Location(this.startLoc.getWorld(),this.startLoc.getBlockX()+this.endLoc.getBlockX(),75,this.endLoc.getBlockZ(),0,0);
    }

    public void finish() {
        JumpUser u = FightJump.getInstance().getJumpUser(player);
        u.addTokens(getRewardTokens());
        u.setModuleInt(u.getModuleInt()+1);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,100,100);
        int i = u.getModuleInt();
        u.getUser().addCoins(10);
        player.sendMessage(Strings.prefix()+"Du hast das Modul §e"+name+"§7 §8("+i+"/10)§7 geschafft§8!");
        if(player.hasPermission("zyneon.premium")) {
            u.getUser().addCoins(10);
            player.sendMessage("§a+"+getRewardTokens()+" Tokens §8(+20 Coins)");
        } else {
            player.sendMessage("§a+"+getRewardTokens()+" Tokens §8(+10 Coins)");
        }
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        Module newModule = ModuleAPI.getModule(player,ModuleAPI.getUsedModules().get(u.getModuleInt()).getName().replace("module","").replace(".schem",""), u.getModuleInt());
        u.setJumpModule(newModule);
        newModule.setStartLoc(this.endLoc);
        newModule.pasteModule();
        FightJumpAPI.setSQL(player.getUniqueId(),"modules", FightJumpAPI.getSQL(player.getUniqueId(),"modules")+1);
        destroyObject();
    }

    private int getRewardTokens() {
        if(difficulty==ModuleDifficulty.EASY) {
            if(fails>2) {
                return 100;
            }
            return 200;
        } else if(difficulty==ModuleDifficulty.MEDIUM) {
            if(fails>2) {
                return 150;
            }
            return 300;
        } else if(difficulty==ModuleDifficulty.HARD) {
            if(fails>2) {
                return 200;
            }
            return 400;
        } else if(difficulty==ModuleDifficulty.EXTREME) {
            if(fails>2) {
                return 250;
            }
            return 500;
        } else {
            return 0;
        }
    }

    public void destroyObject() {
        difficulty = null;
        clipboard = null;
        schematicFile = null;
        startLoc = null;
        endLoc = null;
        player = null;
        fails = -1;
        name = null;
        System.gc();
    }
}