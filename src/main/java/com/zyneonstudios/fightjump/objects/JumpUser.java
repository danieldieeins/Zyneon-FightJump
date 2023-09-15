package com.zyneonstudios.fightjump.objects;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.Countdown;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.objects.module.Module;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.PhaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JumpUser {

    private UUID uuid;
    private boolean isSpectator;
    private Module jumpModule;
    private int moduleInt;
    private int playerInt;
    private boolean hasFinished;
    private boolean build;
    private int tokens;
    private int deaths;
    private int score;
    private int back;
    private boolean ready;
    private int kills;

    public JumpUser(UUID uuid) {
        this.uuid = uuid;
        isSpectator = false;
        jumpModule = null;
        setPlayerInt(0);
        hasFinished = false;
        build = false;
        score = 0;
        kills = 0;
    }

    private void setPlayerInt(int i) {
        if(!isSpectator) {
            if(FightJump.getInstance().numberedUsers.containsKey(i)) {
                setPlayerInt(i+1);
            } else {
                if(i>=12) {
                    playerInt = 999;
                } else {
                    playerInt = i;
                    FightJump.getInstance().numberedUsers.put(i, this);
                }
            }
        } else {
            playerInt = 999;
        }
    }

    public int getBack() {
        return back;
    }

    public int getKills() {
        return kills;
    }

    public User getUser() {
        return Zyneon.getAPI().getOnlineUser(uuid);
    }

    public int getTokens() {
        return tokens;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean hasBuild() {
        return build;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        deaths = deaths+1;
        if(deaths>=3) {
            Bukkit.broadcastMessage(Strings.prefix()+"§a"+Bukkit.getPlayer(uuid).getName()+"§7 ist ausgeschieden§8!");
            FightJump.getInstance().playingUsers.remove(this);
            getUser().getPlayer().teleport(FightJump.getLobby());
            setSpectator(true);
        }
    }

    public int backint;
    public void setBack(int bacc) {
        backint = new Countdown(3, FightJump.getInstance()) {
            @Override
            public void count(int i)  {
                if(i==0) {
                    back = 0;
                    Bukkit.getScheduler().cancelTask(backint);
                    backint=0;
                }
            }
        }.getTaskID();
        this.back = bacc;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public int getPlayerInt() {
        return playerInt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public void setWinner(boolean realWin) {
        Bukkit.broadcastMessage(Strings.prefix()+"§e"+Bukkit.getPlayer(uuid).getName()+"§7 hat gewonnen§8!");
        for(Player all:Bukkit.getOnlinePlayers()) {
            all.sendTitle("§a"+Bukkit.getPlayer(uuid).getName(),"§7hat gewonnen§8!");
            all.playSound(all.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,100,100);
        }
        if(realWin) {
            getUser().addCoins(250);
            if(getUser().getPlayer().hasPermission("zyneon.premium")) {
                getUser().addCoins(250);
                getUser().sendRawMessage("§a+500 Coins");
            } else {
                getUser().sendRawMessage("§a+250 Coins");
            }
        }
        FightJumpAPI.setSQL(getUUID(),"wins", FightJumpAPI.getSQL(getUUID(),"wins")+1);
        PhaseAPI.setPhase(new Phase(PhaseType.END));
    }

    public Module getJumpModule() {
        return jumpModule;
    }

    public int getModuleInt() {
        return moduleInt;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
        Bukkit.getPlayer(uuid).setLevel(this.tokens);
        if(tokens!=0) {
            Bukkit.getPlayer(uuid).setExp(1);
        } else {
            Bukkit.getPlayer(uuid).setExp(0);
        }
        FightJumpAPI.renewScoreboard(Bukkit.getPlayer(uuid));
    }

    public void addTokens(int tokens) {
        setTokens(this.tokens+tokens);
    }

    public void removeTokens(int tokens) {
        setTokens(this.tokens-tokens);
    }

    public void setSpectator(boolean isSpectator) {
        this.isSpectator = isSpectator;
        Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
    }

    public void setJumpModule(Module jumpModule) {
        this.jumpModule = jumpModule;
    }

    public void setModuleInt(int moduleInt) {
        this.moduleInt = moduleInt;
    }

    public void destroy(String reason) {
        isSpectator = false;
        jumpModule = null;
        moduleInt = -1;
        FightJump.getInstance().numberedUsers.remove(playerInt);
        FightJump.getInstance().jumpUsers.remove(getUUID());
        playerInt = -1;
        hasFinished = false;
        build = false;
        uuid = null;
        score = -1;
        System.gc();
    }

    public void destroy() {
        isSpectator = false;
        jumpModule = null;
        moduleInt = -1;
        FightJump.getInstance().numberedUsers.remove(playerInt);
        FightJump.getInstance().jumpUsers.remove(getUUID());
        playerInt = -1;
        hasFinished = false;
        build = false;
        uuid = null;
        score = -1;
        System.gc();
    }
}