package com.zyneonstudios.fightjump.phases.lobby;

import com.zyneonstudios.api.paper.utils.Countdown;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.phases.jumping.JumpAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyAPI {

    public static boolean isStarting = false;

    public static int TimerID;
    public static void startTimer() {
        if(!isStarting) {
            isStarting = true;
            TimerID = new Countdown(30, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (i == 30) {
                        Bukkit.getConsoleSender().sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 25) {
                        Bukkit.getConsoleSender().sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 20) {
                        Bukkit.getConsoleSender().sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 15) {
                        Bukkit.getConsoleSender().sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i <= 10) {
                        Bukkit.getConsoleSender().sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                        if (i > 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(Strings.prefix() + "§7Die Runde beginnt in §e" + i + " Sekunden§8!");
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                                all.sendTitle("Die Runde beginnt...", "§7...in §e" + i + " Sekunden§7!");
                            }
                        } else if (i == 0) {
                            JumpAPI.startJumping();
                        }
                    }
                }
            }.getTaskID();
        }
    }

    public static void endStartTimer() {
        if(isStarting) {
            isStarting = false;
            Bukkit.getScheduler().cancelTask(TimerID);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(Strings.prefix() + "§7Der Starttimer wurde abgebrochen§8, §7da nicht genug Spieler da sind§8!");
                all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
            }
        }
    }
}