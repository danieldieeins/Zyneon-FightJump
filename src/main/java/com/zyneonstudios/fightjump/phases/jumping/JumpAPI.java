package com.zyneonstudios.fightjump.phases.jumping;

import com.zyneonstudios.api.paper.utils.Countdown;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.module.Module;
import com.zyneonstudios.fightjump.objects.module.ModuleAPI;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.PhaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class JumpAPI {

    public static boolean isJumpingFinished = false;
    public static boolean isJumpingFinishing = false;

    public static void startJumping() {
        FightJump.getInstance().isStarted = true;
        if (FightJump.getInstance().getPhase().getType() != PhaseType.JUMPING) {
            FightJump.getInstance().freezedPlayers.addAll(Bukkit.getOnlinePlayers());
            PhaseAPI.setPhase(new Phase(PhaseType.JUMPING));
            new Countdown(10, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (i == 10) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.setLevel(0);
                            all.setExp(0);
                            all.setGameMode(GameMode.ADVENTURE);
                            all.getInventory().clear();
                            JumpUser jumpUser = FightJump.getInstance().getJumpUser(all);
                            Module jumpModule = ModuleAPI.getModule(all, ModuleAPI.getUsedModules().get(jumpUser.getModuleInt()).getName().replace("module", "").replace(".schem", ""), jumpUser.getModuleInt());
                            if (jumpModule != null) {
                                FightJump.getInstance().playingUsers.add(jumpUser);
                                jumpUser.setJumpModule(jumpModule);
                                jumpModule.pasteModule();
                                all.teleport(jumpModule.getStartLoc());
                                all.playSound(all.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                                all.playSound(all.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,100,100);
                            } else {
                                jumpUser.getUser().sendErrorMessage("§4Fehler§8: §cDas Modul konnte nicht gefunden werden§8!");
                            }
                        }
                    } else if(i<=5) {
                        if(i>0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendTitle("§a" + i + " Sekunden", "§7bis zum Start...");
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                                all.setGameMode(GameMode.ADVENTURE);
                                all.getInventory().clear();
                            }
                        } else if (i == 0) {
                            FightJump.getInstance().freezedPlayers.removeAll(Bukkit.getOnlinePlayers());
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendTitle("§aStart!","§7Viel Erfolg§8!");
                                all.getInventory().setItem(4, ItemManager.backToModule());
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public static void endJumping() {
        if (!isJumpingFinishing) {
            isJumpingFinishing = true;
            new Countdown(30, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
                        ArrayList<JumpUser> finishedUsers = new ArrayList<>();
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            JumpUser u = FightJump.getInstance().getJumpUser(all);
                            if (!u.isSpectator()) {
                                if (u.getPlayerInt() <= 11) {
                                    if (u.hasFinished()) {
                                        finishedUsers.add(u);
                                    }
                                }
                            }
                        }
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
                                JumpUser u = FightJump.getInstance().getJumpUser(p);
                                if (u.getJumpModule() != null) {
                                    p.sendActionBar("§aModul " + (u.getModuleInt() + 1) + "§8 | §a" + u.getJumpModule().getName() + "§7 (" + u.getJumpModule().getDifficulty() + ")" + "§8 | §a" + i / 60 + ":" + i % 60);
                                } else {
                                    if(u.getModuleInt()>8) {
                                        p.sendActionBar("§aDu hast alle Module abgeschlossen!");
                                    }
                                }
                            }
                        }
                        if (finishedUsers.size() == FightJump.getInstance().playingUsers.size()) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (!isJumpingFinished) {
                                    JumpUser u = FightJump.getInstance().getJumpUser(all);
                                    u.setJumpModule(null);
                                    all.sendMessage(Strings.prefix() + "§7Die Sprungphase ist vorbei§8!");
                                    all.sendTitle("Zeit abgelaufen!", "§7Die Sprungphase ist vorbei!");
                                    all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                                    all.playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
                                    isJumpingFinished = true;
                                    isJumpingFinishing = true;
                                    if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                        PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                    }
                                    all.teleport(FightJump.getLobby());
                                    all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                                }
                            }
                            for (Player all : Bukkit.getWorld("map").getPlayers()) {
                                if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                    PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                }
                                all.teleport(FightJump.getLobby());
                                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            }
                            return;
                        }
                        if (!isJumpingFinished) {
                            if (i == 30) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Strings.prefix() + "§7Die §eSpringzeit§7 läuft ab in§8: §a" + i + " Sekunden");
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 100);
                                }
                            } else if (i == 20) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Strings.prefix() + "§7Die §eSpringzeit§7 läuft ab in§8: §a" + i + " Sekunden");
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 100);
                                }
                            } else if (i == 15) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Strings.prefix() + "§7Die §eSpringzeit§7 läuft ab in§8: §a" + i + " Sekunden");
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 100);
                                }
                            } else if (i == 10) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Strings.prefix() + "§7Die §eSpringzeit§7 läuft ab in§8: §a" + i + " Sekunden");
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 100);
                                }
                            } else if (i < 6) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Strings.prefix() + "§7Die §eSpringzeit§7 läuft ab in§8: §a" + i + " Sekunden");
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 100);
                                    all.sendTitle("§c" + i, null);
                                }
                                if (i == 0) {
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        JumpUser u = FightJump.getInstance().getJumpUser(all);
                                        u.setJumpModule(null);
                                        all.sendMessage(Strings.prefix() + "§7Die Sprungphase ist vorbei§8!");
                                        all.sendTitle("Zeit abgelaufen!", "§7Die Sprungphase ist vorbei!");
                                        all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                        all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                                        all.playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
                                        if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                            PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                        }
                                        all.teleport(FightJump.getLobby());
                                        all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                        all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                                    }
                                    for (Player all : Bukkit.getWorld("map").getPlayers()) {
                                        if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                            PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                        }
                                        all.teleport(FightJump.getLobby());
                                        all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                        all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                                    }
                                    isJumpingFinished = true;
                                }
                            }
                        } else {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                JumpUser u = FightJump.getInstance().getJumpUser(all);
                                u.setJumpModule(null);
                                all.sendMessage(Strings.prefix() + "§7Die Sprungphase ist vorbei§8!");
                                all.sendTitle("Zeit abgelaufen!", "§7Die Sprungphase ist vorbei!");
                                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                                all.playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
                                isJumpingFinished = true;
                                isJumpingFinishing = true;
                                if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                    PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                }
                                all.teleport(FightJump.getLobby());
                                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            }
                            for (Player all : Bukkit.getWorld("map").getPlayers()) {
                                if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                                    PhaseAPI.setPhase(new Phase(PhaseType.SHOP));
                                }
                                all.teleport(FightJump.getLobby());
                                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public static int getPlayerScore(JumpUser user) {
        if(user.hasFinished()) {
            return 999;
        }
        int finalScore = 0;
        double coords = 50.0*user.getPlayerInt();
        finalScore = (int)user.getUser().getPlayer().getLocation().distance(new Location(Bukkit.getWorld("map"),0.5,75,coords+0.5));
        return finalScore;
    }

    public static ArrayList<String> getTopList() {
        ArrayList<String> list = new ArrayList<>();
        for(JumpUser u : FightJump.getInstance().playingUsers) {
            list.add(getPlayerScore(u)+"_"+u.getUser().getPlayer().getName());
        }
        list.sort(Collections.reverseOrder());
        return list;
    }
}