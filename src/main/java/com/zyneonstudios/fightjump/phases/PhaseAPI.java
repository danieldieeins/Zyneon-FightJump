package com.zyneonstudios.fightjump.phases;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.Countdown;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.manager.ItemManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.deathmatch.DeathmatchAPI;
import com.zyneonstudios.fightjump.phases.jumping.JumpAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PhaseAPI {

    public static void setPhase(Phase phase) {
        FightJump.getInstance().phase = phase;
        if (phase.getType().equals(PhaseType.LOBBY) || phase.getType().equals(PhaseType.LOBBY_FULL)) {
            Bukkit.getWorlds().get(0).setTime(18000);
            FightJump.getInstance().setMaxPlayers(12);
        } else if (phase.getType().equals(PhaseType.END)) {
            for (Player all:Bukkit.getOnlinePlayers()) {
                all.setGameMode(GameMode.ADVENTURE);
                all.getInventory().clear();
                all.getInventory().setItem(8, ItemManager.backToLobby());
                all.teleport(FightJump.getLobby());
                all.setMaxHealth(20);
                all.setHealth(20);
                all.setFoodLevel(20);
            }
            FightJump.getInstance().isStarted = false;
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.teleport(FightJump.getLobby());
                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                all.setMaxHealth(20);
                all.setHealth(20);
                all.setFoodLevel(20);
            }
            new Countdown(30, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    for(Player all:Bukkit.getWorld("arena").getPlayers()) {
                        all.teleport(FightJump.getLobby());
                        all.setMaxHealth(20);
                        all.setHealth(20);
                        all.setFoodLevel(20);
                        all.getInventory().setItem(8,ItemManager.backToLobby());
                        all.setGameMode(GameMode.ADVENTURE);
                    }
                    for(Player all:Bukkit.getOnlinePlayers()) {
                        if(all.getInventory().getItem(8)==null) {
                            all.getInventory().setItem(8,ItemManager.backToLobby());
                        }
                        if(all.getWorld().getName().equals("arena")) {
                            all.teleport(FightJump.getLobby());
                            all.setMaxHealth(20);
                            all.setHealth(20);
                            all.setFoodLevel(20);
                            all.getInventory().setItem(8,ItemManager.backToLobby());
                            all.setGameMode(GameMode.ADVENTURE);
                        }
                        for(Player all2:Bukkit.getOnlinePlayers()) {
                            all2.showPlayer(FightJump.getInstance(),all);
                        }
                    }
                    if (i == 30) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 25) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 20) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 15) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 10) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i <= 5) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Strings.prefix() + "§7Der Spielserver stoppt in §e" + i + " Sekunden§8!");
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                        if (i <= 1) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                User u = Zyneon.getAPI().getOnlineUser(all.getUniqueId());
                                u.switchServer("Lobby-1");
                                all.kickPlayer(Strings.prefix() + "§7Du wirst zur Lobby zurückgesendet§8!");
                            }
                            if (i == 0) {
                                Bukkit.getServer().shutdown();
                            }
                        }
                    }
                }
            }.start();
        } else if (phase.getType() == PhaseType.JUMPING) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                FightJumpAPI.setSQL(all.getUniqueId(),"games", FightJumpAPI.getSQL(all.getUniqueId(),"games")+1);
            }
            new Countdown(910, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
                        return;
                    }
                    if (JumpAPI.isJumpingFinishing) {
                        return;
                    }
                    if (JumpAPI.isJumpingFinished) {
                        return;
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
                            JumpUser u = FightJump.getInstance().getJumpUser(p);
                            if(u.isSpectator()) {
                                u.getUser().getPlayer().setGameMode(GameMode.SPECTATOR);
                            }
                            if (u.getJumpModule() != null) {
                                p.sendActionBar("§aModul " + (u.getModuleInt() + 1) + "§8 | §a" + u.getJumpModule().getName() + "§7 (" + u.getJumpModule().getDifficulty() + ")" + "§8 | §a" + i / 60 + ":" + i % 60);
                            } else {
                                if(u.getModuleInt()>8) {
                                    p.sendActionBar("§aDu hast alle Module abgeschlossen!");
                                }
                            }
                        }
                    }
                    if (i == 30) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Sprungphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 25) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 20) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 15) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i <= 10) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Sprungphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                        if (i == 0) {
                            setPhase(new Phase(PhaseType.SHOP));
                            startShopping();
                            JumpAPI.isJumpingFinished = true;
                            JumpAPI.isJumpingFinishing = true;
                        }
                    }
                }
            }.start();
        } else if (phase.getType() == PhaseType.SHOP) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.getInventory().clear();
                all.setHealth(all.getMaxHealth());
                all.setFoodLevel(20);
            }
            for(JumpUser all : FightJump.getInstance().playingUsers) {
                for(JumpUser all2 : FightJump.getInstance().playingUsers) {
                    all2.getUser().getPlayer().showPlayer(FightJump.getInstance(),all.getUser().getPlayer());
                }
            }
            new Countdown(120, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
                        return;
                    }
                    int ready = 0;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        JumpUser u = FightJump.getInstance().getJumpUser(all);
                        if (u.isReady()) {
                            ready++;
                        }
                    }
                    if (ready == FightJump.getInstance().playingUsers.size()) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.setExp(0);
                            all.setLevel(0);
                            all.teleport(DeathmatchAPI.getArenaLocation());
                            all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        }
                        return;
                    }
                    if (i == 120) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                            all.sendMessage(Strings.prefix()+"§7Du kannst mit §e/ready§7 dafür voten§8,§7 dass die Shophase frühzeitig beendet wird§8.");
                            all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        }
                    } else if (i == 60) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 30) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 25) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 20) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 15) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i <= 10) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Die Einkaufsphase endet in §e" + i + " Sekunden§8!");
                        if (i > 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                            }
                        } else if (i == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.setExp(0);
                                all.setLevel(0);
                                all.teleport(DeathmatchAPI.getArenaLocation());
                                all.playSound(all.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                all.playSound(all.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            }
                        }
                    }
                }
            }.start();
        } else if (phase.getType().equals(PhaseType.DEATHMATCH)) {
            for (JumpUser all : FightJump.getInstance().playingUsers) {
                all.getUser().getPlayer().setGameMode(GameMode.SURVIVAL);
            }
            new Countdown(1800, FightJump.getInstance()) {
                @Override
                public void count(int i) {
                    if (!FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
                        return;
                    }
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendActionBar("§c" + i / 60 + ":" + i % 60);
                    }
                    if (i == 30) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Das Deathmatch endet in §e" + i + " Sekunden§8!");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 25) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Das Deathmatch endet in §e" + i + " Sekunden§8!");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 20) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Das Deathmatch endet in §e" + i + " Sekunden§8!");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i == 15) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Das Deathmatch endet in §e" + i + " Sekunden§8!");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                    } else if (i <= 10) {
                        Bukkit.broadcastMessage(Strings.prefix() + "§7Das Deathmatch endet in §e" + i + " Sekunden§8!");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        }
                        if (i == 0) {
                            boolean two = false;
                            int w = 0;
                            JumpUser user = null;
                            for(JumpUser u: FightJump.getInstance().playingUsers) {
                                if(u.getKills()>w) {
                                    w = u.getKills();
                                    user = u;
                                }
                            }
                            for(JumpUser u: FightJump.getInstance().playingUsers) {
                                if(u.getUser().getPlayer()!=user.getUser().getPlayer()) {
                                    if (u.getKills() == w) {
                                        two = true;
                                    }
                                }
                            }
                            if(!two) {
                                if(user!=null) {
                                    user.setWinner(true);
                                    return;
                                }
                            }
                            for(Player all:Bukkit.getOnlinePlayers()) {
                                all.sendTitle("§aUnentschieden","§7Niemand hat gewonnen§8!");
                            }
                            setPhase(new Phase(PhaseType.END));
                        }
                    }
                }
            }.start();
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.setHealth(all.getMaxHealth());
                all.setFoodLevel(20);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            FightJumpAPI.renewScoreboard(all);
        }
    }

    public static void startShopping() {
        if(!FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
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
        }
    }
}