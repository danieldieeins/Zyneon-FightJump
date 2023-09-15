package com.zyneonstudios.fightjump.utils;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.Bukkit.getServer;

public class Broadcaster {

    static ArrayList<String> Messages = new ArrayList<>();

    private static void saveDefaultConfig() {
        FightJump.getInstance().getFightConfig().checkEntry("Core.Settings.Broadcasts.Enable",false);
        FightJump.getInstance().getFightConfig().checkEntry("Core.Settings.Broadcasts.SecondInterval",10);
        FightJump.getInstance().getFightConfig().checkEntry("Core.Strings.Broadcasts",Messages);
        Messages = (ArrayList<String>) FightJump.getInstance().getFightConfig().getCFG().getList("Core.Strings.Broadcasts");
        FightJump.getInstance().getFightConfig().checkEntry("Core.Actionbar.Message","test");
        FightJump.getInstance().getFightConfig().saveConfig();
        FightJump.getInstance().getFightConfig().reloadConfig();
    }

    public static void send() {
        saveDefaultConfig();

        sendScoreboard(getServer().getScheduler());
        autoRenew(getServer().getScheduler());
        if(FightJump.getInstance().getFightConfig().getCFG().getBoolean("Core.Settings.Broadcasts.Enable")) {
            startBroadcastTimer(getServer().getScheduler());
        }
    }

    private static void sendActionbar(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(FightJump.getInstance(), () -> {
            sendActionbar(scheduler);
        },10);
    }

    private static void startBroadcastTimer(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(FightJump.getInstance(), () -> {
            Integer size = Messages.size();
            Integer random = ThreadLocalRandom.current().nextInt(0,size);
            Bukkit.broadcastMessage(Strings.prefix()+Messages.get(random).replace("&","§"));
            startBroadcastTimer(scheduler);
        }, FightJump.getInstance().getFightConfig().getCFG().getLong("Core.Settings.Broadcasts.SecondInterval")*20);
    }

    private static void sendScoreboard(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(FightJump.getInstance(), () -> {
            for(Player all : Bukkit.getOnlinePlayers()) {
                JumpUser u = FightJump.getInstance().getJumpUser(all);
                FightJumpAPI.setScoreboard(all);
                if(u.isSpectator()) {
                    if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.END)) {
                        for (Player all2 : Bukkit.getOnlinePlayers()) {
                            all2.hidePlayer(FightJump.getInstance(), all);
                        }
                        all.setGameMode(GameMode.SPECTATOR);
                    }
                }
            }
            if(FightJumpAPI.animatedState == 10) {
                FightJumpAPI.animatedState = 0;
            } else if(FightJumpAPI.animatedState == 0) {
                FightJumpAPI.animatedState = 1;
            } else if(FightJumpAPI.animatedState == 1) {
                FightJumpAPI.animatedState = 2;
            } else if(FightJumpAPI.animatedState == 2) {
                FightJumpAPI.animatedState = 3;
            } else if(FightJumpAPI.animatedState == 3) {
                FightJumpAPI.animatedState = 4;
            } else if(FightJumpAPI.animatedState == 4) {
                FightJumpAPI.animatedState = 5;
            } else if(FightJumpAPI.animatedState == 5) {
                FightJumpAPI.animatedState = 6;
            } else if(FightJumpAPI.animatedState == 6) {
                FightJumpAPI.animatedState = 7;
            } else if(FightJumpAPI.animatedState == 7) {
                FightJumpAPI.animatedState = 8;
            } else if(FightJumpAPI.animatedState == 8) {
                FightJumpAPI.animatedState = 9;
            } else if(FightJumpAPI.animatedState == 9) {
                FightJumpAPI.animatedState = 10;
            }
            for(Player all:Bukkit.getOnlinePlayers()) {
                FightJumpAPI.setPrefix(all);
            }
            sendScoreboard(scheduler);
        },15);
    }

    private static void autoRenew(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(FightJump.getInstance(), () -> {
            if(!FightJumpAPI.date.equals(Zyneon.getAPI().getTime())) {
                FightJumpAPI.date = Zyneon.getAPI().getTime();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    FightJumpAPI.renewScoreboard(all);
                }
            }
            autoRenew(scheduler);
        },15*20);
    }
}