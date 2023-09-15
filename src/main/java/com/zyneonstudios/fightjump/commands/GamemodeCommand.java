package com.zyneonstudios.fightjump.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.ZyneonAPI;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.api.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("GameMode")) {
            if(s instanceof Player p) {
                User u = Zyneon.getUser(p);
                if(p.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        u.sendErrorMessage("§4Fehler: §c/gamemode [Spielmodus] §7(Spieler)");
                    } else {
                        if(args[0].equalsIgnoreCase("0")||args[0].equalsIgnoreCase("survival")) {
                            if(args.length == 1) {
                                u.getPlayer().setGameMode(GameMode.SURVIVAL);
                                u.sendMessage("§7Du bist nun im §eSurvival§8-§eModus§8!");
                            } else {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    User t = Zyneon.getUser(Bukkit.getPlayer(args[1]));
                                    t.getPlayer().setGameMode(GameMode.SURVIVAL);
                                    t.sendMessage("§7Du bist nun im §eSurvival§8-§eModus§8!");
                                    u.sendMessage("§7Du hast §a"+t.getPlayer().getName()+"§7 in den §eSurvival§8-§eModus§7 gesetzt§8!");
                                } else {
                                    u.sendErrorMessage(Strings.playerNotFound(args[1]));
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("1")||args[0].equalsIgnoreCase("creative")) {
                            if(args.length == 1) {
                                u.getPlayer().setGameMode(GameMode.CREATIVE);
                                u.sendMessage("§7Du bist nun im §eCreative§8-§eModus§8!");
                            } else {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    User t = Zyneon.getUser(Bukkit.getPlayer(args[1]));
                                    t.getPlayer().setGameMode(GameMode.CREATIVE);
                                    t.sendMessage("§7Du bist nun im §eCreative§8-§eModus§8!");
                                    u.sendMessage("§7Du hast §a"+t.getPlayer().getName()+"§7 in den §eCreative§8-§eModus§7 gesetzt§8!");
                                } else {
                                    u.sendErrorMessage(Strings.playerNotFound(args[1]));
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("2")||args[0].equalsIgnoreCase("adventure")) {
                            if(args.length == 1) {
                                u.getPlayer().setGameMode(GameMode.ADVENTURE);
                                u.sendMessage("§7Du bist nun im §eAdventure§8-§eModus§8!");
                            } else {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    User t = Zyneon.getUser(Bukkit.getPlayer(args[1]));
                                    t.getPlayer().setGameMode(GameMode.ADVENTURE);
                                    t.sendMessage("§7Du bist nun im §eAdventure§8-§eModus§8!");
                                    u.sendMessage("§7Du hast §a"+t.getPlayer().getName()+"§7 in den §eAdventure§8-§eModus§7 gesetzt§8!");
                                } else {
                                    u.sendErrorMessage(Strings.playerNotFound(args[1]));
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("3")||args[0].equalsIgnoreCase("spectator")) {
                            if(args.length == 1) {
                                u.getPlayer().setGameMode(GameMode.SPECTATOR);
                                u.sendMessage("§7Du bist nun im §eSpectator§8-§eModus§8!");
                            } else {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    User t = Zyneon.getUser(Bukkit.getPlayer(args[1]));
                                    t.getPlayer().setGameMode(GameMode.SPECTATOR);
                                    t.sendMessage("§7Du bist nun im §eSpectator§8-§eModus§8!");
                                    u.sendMessage("§7Du hast §a"+t.getPlayer().getName()+"§7 in den §eSpectator§8-§eModus§7 gesetzt§8!");
                                } else {
                                    u.sendErrorMessage(Strings.playerNotFound(args[1]));
                                }
                            }
                        } else {
                            u.sendErrorMessage("§4Fehler: §cDer Spielmodus §4"+args[0]+"§c existiert nicht§8! §cVersuche 0, 1, 2, 3, survival, creative, adventure oder spectator§8.");
                        }
                    }
                } else {
                    u.sendErrorMessage(Strings.noPerms());
                }
            } else {
                if (s.hasPermission("zyneon.team")) {
                    if (args.length == 2) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            User t = Zyneon.getUser(Bukkit.getPlayer(args[1]));
                            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                                t.getPlayer().setGameMode(GameMode.SURVIVAL);
                                t.sendMessage("§7Du bist nun im §eSurvival§8-§eModus§8!");
                            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                                t.getPlayer().setGameMode(GameMode.CREATIVE);
                                t.sendMessage("§7Du bist nun im §eCreative§8-§eModus§8!");
                            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                                t.getPlayer().setGameMode(GameMode.ADVENTURE);
                                t.sendMessage("§7Du bist nun im §eAdventure§8-§eModus§8!");
                            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                                t.getPlayer().setGameMode(GameMode.SPECTATOR);
                                t.sendMessage("§7Du bist nun im §eSpectator§8-§eModus§8!");
                            } else {
                                s.sendMessage("§4Fehler: §cDer Spielmodus §4" + args[0] + "§c existiert nicht§8! §cVersuche 0, 1, 2, 3, survival, creative, adventure oder spectator§8.");
                                return false;
                            }
                            s.sendMessage(Strings.prefix()+"§7Du hast den Spieler §a"+t.getPlayer().getName()+"§7 in den Spielmodus §e"+args[0]+"§7 gesetzt§8!");
                        } else {
                            s.sendMessage(Strings.playerNotFound(args[1]));
                        }
                    } else {
                        s.sendMessage("§4Fehler: §c/gamemode [Spielmodus] [Spieler]");
                    }
                } else {
                    s.sendMessage(Strings.noPerms());
                }
            }
        }
        return false;
    }
}
