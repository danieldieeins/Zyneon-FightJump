package com.zyneonstudios.fightjump.objects.module;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class ModuleAPI {

    private static ArrayList<File> usedModules = new ArrayList<>();

    public static Module getModule(Player player, String name, int moduleInt) {
        String dif;
        ModuleDifficulty diff;
        if(moduleInt<3) {
            dif = "easy";
            diff = ModuleDifficulty.EASY;
        } else if(moduleInt<6) {
            dif = "medium";
            diff = ModuleDifficulty.MEDIUM;
        } else if(moduleInt<9) {
            dif = "hard";
            diff = ModuleDifficulty.HARD;
        } else {
            dif = "extreme";
            diff = ModuleDifficulty.EXTREME;
        }
        try {
            JumpUser u = FightJump.getInstance().getJumpUser(player);
            File schematicFile = new File("plugins/FightJump/modules/"+dif+"/module" + name + ".schem");
            File liteFile = new File("plugins/FightJump/modules/"+dif+"/lite/module" + name + ".schem");
            Location startLoc = new Location(Bukkit.getWorld("map"), 0.5, 75, 0.5, -90, 0);
            Location endLoc = new Location(Bukkit.getWorld("map"), FightJump.getInstance().getModuleConfig().getCFG().getDouble("Modules."+name+".endLoc.X"), 75, 0, (float)-90, (float)0);
            if(u.getPlayerInt()>0) {
                double coords = 50.0*u.getPlayerInt();
                startLoc = new Location(Bukkit.getWorld("map"), 0.5, 75, coords+0.5, -90, 0);
                endLoc = new Location(Bukkit.getWorld("map"), FightJump.getInstance().getModuleConfig().getCFG().getDouble("Modules."+name+".endLoc.X"), 75, coords+0.5, (float)-90, (float)0);
            }
            return new Module(player, schematicFile, liteFile, startLoc, endLoc,diff);
        } catch (NullPointerException e) {
            Zyneon.getZyneonServer().sendErrorMessage("§cDas Module §4\"" + name + "\" §cwurde nicht gefunden§8.");
            return null;
        }
    }

    public static void getModules() {
        usedModules = new ArrayList<>();
        getRandomModules(getEasyModules(),3);
        getRandomModules(getMediumModules(),3);
        getRandomModules(getHardModules(),3);
        getRandomModules(getExtremeModules(),1);
    }

    public static ArrayList<File> getEasyModules() {
        ArrayList<File> easyModuleFiles = new ArrayList<>();
        File folder = new File("plugins/FightJump/modules/easy/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                easyModuleFiles.add(new File("plugins/FightJump/modules/easy/" + listOfFile.getName()));
            }
        }
        return easyModuleFiles;
    }

    public static ArrayList<File> getMediumModules() {
        ArrayList<File> mediumModuleFiles = new ArrayList<>();
        File folder = new File("plugins/FightJump/modules/medium/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                mediumModuleFiles.add(new File("plugins/FightJump/modules/medium/" + listOfFile.getName()));
            }
        }
        return mediumModuleFiles;
    }

    public static ArrayList<File> getHardModules() {
        ArrayList<File> hardModuleFiles = new ArrayList<>();
        File folder = new File("plugins/FightJump/modules/hard/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                hardModuleFiles.add(new File("plugins/FightJump/modules/hard/" + listOfFile.getName()));
            }
        }
        return hardModuleFiles;
    }

    public static ArrayList<File> getExtremeModules() {
        ArrayList<File> extremeModuleFiles = new ArrayList<>();
        File folder = new File("plugins/FightJump/modules/extreme/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                extremeModuleFiles.add(new File("plugins/FightJump/modules/extreme/" + listOfFile.getName()));
            }
        }
        return extremeModuleFiles;
    }

    public static void getRandomModules(ArrayList<File> type,int amount) {
        for (int i = 0; i < amount; ++i) {
            getRandomModule(type);
        }
    }

    public static void getRandomModule(ArrayList<File> type) {
        int index = (int) (Math.random() * type.size());
        if(!usedModules.contains(type.get(index))) {
            usedModules.add(type.get(index));
        } else {
            getRandomModule(type);
        }
    }

    public static ArrayList<File> getUsedModules() {
        return usedModules;
    }
}