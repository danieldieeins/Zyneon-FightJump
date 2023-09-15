package com.zyneonstudios.fightjump.apis;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.deathmatch.DeathmatchAPI;
import com.zyneonstudios.fightjump.phases.jumping.JumpAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class FightJumpAPI {

    public static String date;
    public static Connection sql;
    public static Collection<String> commands = new ArrayList<>();

    public static void initCommandList() {
        for(Command all: PluginCommandYamlParser.parse(FightJump.getInstance())) {
            commands.add(all.getName().toLowerCase());
            for(String aliases:all.getAliases()) {
                commands.add(aliases.toLowerCase());
            }
        }
        commands.add("party");
        commands.add("give");
        commands.add("sit");
        commands.add("crawl");
        commands.add("lay");
    }

    public static void initSQL() {
        try {
            PreparedStatement ps = sql.prepareStatement("CREATE TABLE IF NOT EXISTS fightjump_kills (UUID VARCHAR(100),Kills DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = sql.prepareStatement("CREATE TABLE IF NOT EXISTS fightjump_games (UUID VARCHAR(100),Games DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = sql.prepareStatement("CREATE TABLE IF NOT EXISTS fightjump_wins (UUID VARCHAR(100),Wins DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = sql.prepareStatement("CREATE TABLE IF NOT EXISTS fightjump_modules (UUID VARCHAR(100),Modules DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasSQL(UUID uuid,String type) {
        String rowName;
        String SID = uuid.toString();
        if(type.equalsIgnoreCase("wins")) {
            rowName = "Wins";
        } else if(type.equalsIgnoreCase("kills")) {
            rowName = "Kills";
        } else if(type.equalsIgnoreCase("modules")) {
            rowName = "Modules";
        } else {
            rowName = "Games";
        }
        try {
            PreparedStatement ps = sql.prepareStatement("SELECT "+rowName+" FROM fightjump_"+rowName.toLowerCase()+" WHERE UUID = ?");
            ps.setString(1, SID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setSQL(UUID uuid,String type, int amount) {
        String rowName;
        String SID = uuid.toString();
        if(type.equalsIgnoreCase("wins")) {
            rowName = "Wins";
        } else if(type.equalsIgnoreCase("kills")) {
            rowName = "Kills";
        } else if(type.equalsIgnoreCase("modules")) {
            rowName = "Modules";
        } else {
            rowName = "Games";
        }
        if ((double)amount < 0.0D) {
            return false;
        } else {
            try {
                if (hasSQL(uuid,type)) {
                    PreparedStatement ps = sql.prepareStatement("DELETE FROM fightjump_"+rowName.toLowerCase()+" WHERE UUID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = sql.prepareStatement("INSERT INTO fightjump_"+rowName.toLowerCase()+" (UUID,"+rowName+") VALUES (?,?)");
                ps.setString(1, SID);
                ps.setDouble(2, amount);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static int getSQL(UUID uuid,String type) {
        String rowName;
        String SID = uuid.toString();
        if(type.equalsIgnoreCase("wins")) {
            rowName = "Wins";
        } else if(type.equalsIgnoreCase("kills")) {
            rowName = "Kills";
        } else if(type.equalsIgnoreCase("modules")) {
            rowName = "Modules";
        } else {
            rowName = "Games";
        }
        double data;
        if (hasSQL(uuid,type)) {
            try {
                PreparedStatement ps = sql.prepareStatement("SELECT "+rowName+" FROM fightjump_"+rowName.toLowerCase()+" WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    data = rs.getDouble(rowName);
                } else {
                    data = 0.0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                data = 0.0;
            }
        } else {
            data = 0.0;
        }
        return (int)data;
    }

    public static void setScoreboard(Player player) {
        JumpUser u = FightJump.getInstance().getJumpUser(player);
        Scoreboard board = player.getScoreboard();
        Objective boardContent = board.getObjective("ZYNEON");
        board.resetScores("ZYNEON");
        boardContent.setDisplayName(animatedString());
        if(FightJump.getInstance().getPhase().getType().equals(PhaseType.STARTUP)) {
            player.kickPlayer("§cDer Server startet noch§8...");
        } else if(FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY)|| FightJump.getInstance().getPhase().getType().equals(PhaseType.LOBBY_FULL)) {
            boardContent.getScore("§0").setScore(15);
            boardContent.getScore("§fGespielte Spiele§8:").setScore(14);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"games")+"§0 §5").setScore(13);
            boardContent.getScore("§1").setScore(12);
            boardContent.getScore("§fGewonnene Spiele§8:").setScore(11);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"wins")+"§1 §a").setScore(10);
            boardContent.getScore("§2").setScore(9);
            boardContent.getScore("§fKills insgesamt§8:").setScore(8);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"kills")+"§2 §d").setScore(7);
            boardContent.getScore("§3").setScore(6);
            boardContent.getScore("§fModule geschafft§8:").setScore(5);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"modules")+"§3 §o").setScore(4);
            boardContent.getScore("§4").setScore(3);
            boardContent.getScore("§fServer§7-§fIP§8:").setScore(2);
            boardContent.getScore("§e"+Strings.getServerDomain()).setScore(1);
        } else if(FightJump.getInstance().getPhase().getType().equals(PhaseType.JUMPING)) {
            boardContent.getScore("§2").setScore(1007);
            boardContent.getScore("§fServer§7-§fIP§8:").setScore(1006);
            boardContent.getScore("§e"+Strings.getServerDomain()).setScore(1005);
            boardContent.getScore("§1").setScore(1004);
            boardContent.getScore("§fTokens§8:").setScore(1003);
            boardContent.getScore("§e"+u.getTokens()).setScore(1002);
            boardContent.getScore("§0").setScore(1001);
            boardContent.getScore("§fFortschritt§8:").setScore(1000);
            for(JumpUser user: FightJump.getInstance().playingUsers) {
                boardContent.getScore("§e"+user.getUser().getName()).setScore(JumpAPI.getPlayerScore(user));
            }
        } else if(FightJump.getInstance().getPhase().getType().equals(PhaseType.SHOP)) {
            boardContent.getScore("§2").setScore(3208);
            boardContent.getScore("§fServer§7-§fIP§8:").setScore(3207);
            boardContent.getScore("§e"+Strings.getServerDomain()).setScore(3206);
            boardContent.getScore("§1").setScore(3205);
            boardContent.getScore("§fTokens§8:").setScore(3204);
            boardContent.getScore("§e"+u.getTokens()).setScore(3203);
            boardContent.getScore("§0").setScore(3202);
            boardContent.getScore("§fToplist§8:").setScore(3201);
            for(JumpUser user: FightJump.getInstance().playingUsers) {
                boardContent.getScore("§e"+user.getUser().getName()).setScore(user.getTokens());
            }
        } else if(FightJump.getInstance().getPhase().getType().equals(PhaseType.DEATHMATCH)) {
            boardContent.getScore("§2").setScore(128);
            boardContent.getScore("§fServer§7-§fIP§8:").setScore(127);
            boardContent.getScore("§e"+Strings.getServerDomain()).setScore(126);
            boardContent.getScore("§1").setScore(125);
            boardContent.getScore("§fLeben§8:").setScore(124);
            int lives;
            if(u.getDeaths() == 0) {
                lives = 3;
            } else if(u.getDeaths() == 1) {
                lives = 2;
            } else if(u.getDeaths() == 2) {
                lives = 1;
            } else {
                lives = 0;
            }
            if(u.isSpectator()) {
                lives = 0;
            }
            boardContent.getScore("§e"+lives).setScore(123);
            boardContent.getScore("§0").setScore(122);
            boardContent.getScore("§fTopliste§8:").setScore(121);
            for(JumpUser user: FightJump.getInstance().playingUsers) {
                boardContent.getScore("§e"+user.getUser().getName()).setScore(DeathmatchAPI.getScore(user.getUser().getPlayer()));
            }
        } else if(FightJump.getInstance().getPhase().getType().equals(PhaseType.END)) {
            boardContent.getScore("§0").setScore(15);
            boardContent.getScore("§fGespielte Spiele§8:").setScore(14);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"games")+"§0 §5").setScore(13);
            boardContent.getScore("§1").setScore(12);
            boardContent.getScore("§fGewonnene Spiele§8:").setScore(11);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"wins")+"§1 §a").setScore(10);
            boardContent.getScore("§2").setScore(9);
            boardContent.getScore("§fKills insgesamt§8:").setScore(8);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"kills")+"§2 §d").setScore(7);
            boardContent.getScore("§3").setScore(6);
            boardContent.getScore("§fModule geschafft§8:").setScore(5);
            boardContent.getScore("§e"+getSQL(player.getUniqueId(),"modules")+"§3 §o").setScore(4);
            boardContent.getScore("§4").setScore(3);
            boardContent.getScore("§fServer§7-§fIP§8:").setScore(2);
            boardContent.getScore("§e"+Strings.getServerDomain()).setScore(1);
        }
        //setRank(player);
    }

    public static void renewScoreboard(Player player) {
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        player.setScoreboard(sm.getNewScoreboard());
        Scoreboard board = player.getScoreboard();
        if (board.getObjective("ZYNEON") == null) {
            board.registerNewObjective("ZYNEON", "ZYNEON");
        }
        Objective boardContent = board.getObjective("ZYNEON");
        boardContent.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScoreboard(player);
    }

    public static int animatedState;
    public static String animatedString() {
        int state = animatedState;
        String string = "§fFightJump";
        if(state == 1) {
            string = "§fFightJump";
        } else if(state == 2) {
            string = "§aF§fightJump";
        } else if(state == 3) {
            string = "§fF§ai§fghtJump";
        } else if(state == 4) {
            string = "§fFi§ag§fhtJump";
        } else if(state == 5) {
            string = "§fFig§ah§ftJump";
        } else if(state == 6) {
            string = "§fFigh§at§fJump";
        } else if(state == 7) {
            string = "§fFight§aJ§fump";
        } else if(state == 8) {
            string = "§fFightJ§au§fmp";
        } else if(state == 9) {
            string = "§fFightJu§am§fp";
        } else if(state == 10) {
            string = "§fFightJum§ap§f";
        }
        return string.toUpperCase();
    }

    public static void setPrefix(Player player) {
        String Name = player.getName();
        org.bukkit.scoreboard.Scoreboard Scoreboard = player.getScoreboard();
        if(Scoreboard.getTeam("03Spieler")==null) {
            Scoreboard.registerNewTeam("00000Team");
            Scoreboard.registerNewTeam("01Creator");
            Scoreboard.registerNewTeam("02Premium");
            Scoreboard.registerNewTeam("03Spieler");
            Scoreboard.getTeam("00000Team").setPrefix("§cTeam §8● §f");
            Scoreboard.getTeam("01Creator").setPrefix("§dCreator §8● §f");
            Scoreboard.getTeam("02Premium").setPrefix("§6Premium §8● §f");
            Scoreboard.getTeam("03Spieler").setPrefix("§7User §8● §f");
            Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("00000Team").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            Scoreboard.getTeam("01Creator").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            Scoreboard.getTeam("02Premium").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
            Scoreboard.getTeam("03Spieler").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        }
        for(Player p:Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("zyneon.team")) {
                Scoreboard.getTeam("00000Team").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.creator")) {
                Scoreboard.getTeam("01Creator").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.premium")) {
                Scoreboard.getTeam("02Premium").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
            } else {
                Scoreboard.getTeam("03Spieler").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
            }
        }
    }
}