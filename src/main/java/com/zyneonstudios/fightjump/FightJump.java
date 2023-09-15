package com.zyneonstudios.fightjump;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.configuration.Config;
import com.zyneonstudios.api.paper.generators.VoidGenerator;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.utils.sql.SQLite;
import com.zyneonstudios.fightjump.apis.FightJumpAPI;
import com.zyneonstudios.fightjump.commands.*;
import com.zyneonstudios.fightjump.listeners.*;
import com.zyneonstudios.fightjump.manager.ChatManager;
import com.zyneonstudios.fightjump.manager.ProtectionManager;
import com.zyneonstudios.fightjump.objects.JumpUser;
import com.zyneonstudios.fightjump.objects.module.ModuleAPI;
import com.zyneonstudios.fightjump.objects.phase.Phase;
import com.zyneonstudios.fightjump.objects.phase.PhaseType;
import com.zyneonstudios.fightjump.phases.PhaseAPI;
import com.zyneonstudios.fightjump.phases.deathmatch.DeathmatchAPI;
import com.zyneonstudios.fightjump.phases.deathmatch.listeners.*;
import com.zyneonstudios.fightjump.phases.jumping.listeners.JumpingInteractEvent;
import com.zyneonstudios.fightjump.phases.jumping.listeners.JumpingMoveEvent;
import com.zyneonstudios.fightjump.phases.jumping.listeners.ModuleFinishEvent;
import com.zyneonstudios.fightjump.phases.lobby.listeners.LobbyInteractEvent;
import com.zyneonstudios.fightjump.phases.lobby.listeners.LobbyInventoryEvent;
import com.zyneonstudios.fightjump.phases.shop.listeners.ShopInteractEvent;
import com.zyneonstudios.fightjump.phases.shop.listeners.ShopInventoryEvent;
import com.zyneonstudios.fightjump.utils.Broadcaster;
import com.zyneonstudios.fightjump.utils.Receiver;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class FightJump extends JavaPlugin {

    public HashMap<UUID, JumpUser> jumpUsers = new HashMap<>();
    public HashMap<Integer, JumpUser> numberedUsers = new HashMap<>();
    private static FightJump instance;
    public static FightJump getInstance() {return instance;}
    public Phase phase;
    private int maxPlayers;
    private final Config config = new Config("plugins/FightJump/config.yml");
    private final Config moduleConfig = new Config("plugins/FightJump/modules.yml");
    public ArrayList<Player> freezedPlayers = new ArrayList<>();
    public ArrayList<JumpUser> playingUsers = new ArrayList<>();
    public boolean isStarted = false;
    private static PluginMessageListener listener;

    public Config getFightConfig() {
        return config;
    }

    public Config getModuleConfig() {
        return moduleConfig;
    }

    @Override
    public void onLoad() {
        Strings.setPrefixWord("§aFightJump");
        instance = this;
        PhaseAPI.setPhase(new Phase(PhaseType.STARTUP));
        config.checkEntry("General.API.Version", Zyneon.getInstance().getDescription().getVersion());
        config.checkEntry("General.MySQL.enable",false);
        config.checkEntry("General.MySQL.password","password");
        config.saveConfig();
        config.reloadConfig();
        moduleConfig.checkEntry("General.API.Version", Zyneon.getInstance().getDescription().getVersion());
        moduleConfig.saveConfig();
        moduleConfig.reloadConfig();
        Bukkit.setMaxPlayers(12);
    }

    @Override
    public void onEnable() {
        DeathmatchAPI.getLocations();
        instance = this;
        listener = new Receiver();
        if(config.getCFG().getBoolean("General.MySQL.enable")) {
            FightJumpAPI.sql = new MySQL("127.0.0.1", "3306", "zyneon_fightjump", "root", config.getCFG().getString("General.MySQL.password")).getConnection();
        } else {
            FightJumpAPI.sql = new SQLite("plugins/FightJump/database.sql").getConnection();
        }
        FightJumpAPI.initSQL();
        getServer().getMessenger().registerIncomingPluginChannel(instance,"base:bungee",listener);
        getServer().getMessenger().registerOutgoingPluginChannel(instance,"base:spigot");
        getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
        FightJumpAPI.date = Zyneon.getAPI().getTime();
        Broadcaster.send();
        initMaps();
        initCommands();
        initListeners();
        ModuleAPI.getModules();
        PhaseAPI.setPhase(new Phase(PhaseType.LOBBY));
        Bukkit.setMaxPlayers(12);
        FightJumpAPI.initCommandList();
        PlayerCommandListener.initBlocked();
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(instance,"base:bungee",listener);
        getServer().getMessenger().unregisterOutgoingPluginChannel(instance,"base:spigot");
        getServer().getMessenger().unregisterOutgoingPluginChannel(instance,"BungeeCord");
        listener = null;
        instance = null;
    }

    private void initMaps() {
        File mapFile = new File("map");
        if (mapFile.exists()) {
            try {
                FileUtils.deleteDirectory(mapFile);
            } catch (IOException ignore) {
            }
        }
        new WorldCreator("map").generateStructures(false).environment(World.Environment.NORMAL).type(WorldType.FLAT).generator(new VoidGenerator()).createWorld();
        new WorldCreator("arena").generateStructures(false).environment(World.Environment.NORMAL).type(WorldType.FLAT).createWorld();
        for(World all:Bukkit.getWorlds()) {
            initMap(all);
        }
    }

    private void initMap(World world) {
        world.setDifficulty(Difficulty.HARD);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES,false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
        world.setGameRule(GameRule.MOB_GRIEFING,false);
        world.setGameRule(GameRule.DO_MOB_LOOT,false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING,false);
        world.setGameRule(GameRule.SPAWN_RADIUS,0);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.KEEP_INVENTORY, false);
        world.setTime(6000);
        world.setThundering(false);
        world.setStorm(false);
    }

    private void initCommands() {
        getCommand("build",new BuildCommand());
        getCommand("ready",new ReadyCommand());
        getCommand("set",new SetCommand());
        getCommand("test",new TestCommand());
        getCommand("villager",new VillagerCommand());
        getCommand("world",new WorldCommand());
        getCommand("gamemode", new GamemodeCommand());
    }

    private void initListeners() {
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerCommandListener(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerInteractEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerJoinEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerQuitEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ServerListPingEvent(),this);

        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ChatManager(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ProtectionManager(),this);

        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new DeathmatchDamageEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new DeathmatchDeathEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new DeathmatchInteractEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new DeathmatchMoveEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new DeathmatchTNTEvent(),this);

        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new JumpingInteractEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new JumpingMoveEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ModuleFinishEvent(),this);

        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new LobbyInteractEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new LobbyInventoryEvent(),this);

        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ShopInteractEvent(),this);
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new ShopInventoryEvent(),this);
    }

    private void getCommand(String commandName, CommandExecutor executor) {
        Zyneon.getZyneonServer().sendMessage("§f  -> §7Lade Commandklasse §e"+executor.getClass().getSimpleName()+"§8...");
        Objects.requireNonNull(getCommand(commandName)).setExecutor(executor);
    }

    public Phase getPhase() {
        return phase;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public JumpUser getJumpUser(Player player) {
        return getJumpUser(player.getUniqueId());
    }

    public JumpUser getJumpUser(UUID uuid) {
        if (jumpUsers.containsKey(uuid)) {
            return jumpUsers.get(uuid);
        } else {
            jumpUsers.put(uuid, new JumpUser(uuid));
            return getJumpUser(uuid);
        }
    }

    public static Location getLobby() {
        return new Location(Bukkit.getWorlds().get(0),258.5, 31, 184.5, 90, 0);
    }
}