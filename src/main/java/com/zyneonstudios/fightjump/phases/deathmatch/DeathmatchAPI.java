package com.zyneonstudios.fightjump.phases.deathmatch;

import com.zyneonstudios.fightjump.FightJump;
import com.zyneonstudios.fightjump.objects.JumpUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DeathmatchAPI {

    public static ArrayList<Location> locations;
    public static void getLocations() {
        locations = new ArrayList<>();
        locations.add(getLocation(1));
        locations.add(getLocation(2));
        locations.add(getLocation(3));
        locations.add(getLocation(4));
        locations.add(getLocation(5));
        locations.add(getLocation(6));
        locations.add(getLocation(7));
        locations.add(getLocation(8));
        locations.add(getLocation(9));
        locations.add(getLocation(10));
        locations.add(getLocation(11));
        locations.add(getLocation(12));
    }

    public static Location getArenaLocation() {
        Location location = locations.get(ThreadLocalRandom.current().nextInt(0, 11));
        for(JumpUser users:FightJump.getInstance().playingUsers) {
            Player players = users.getUser().getPlayer();
            if(players.getWorld().equals(location.getWorld())) {
                if(location.distance(players.getLocation())<2) {
                    return getArenaLocation();
                }
            }
        }
        return location;
    }

    public static Integer getScore(Player player) {
        JumpUser u = FightJump.getInstance().getJumpUser(player);
        return u.getKills();
    }

    public static Location getLocation(int i) {
        File file = new File("plugins/FightJump/locations/"+i+".yml");
        YamlConfiguration fil = YamlConfiguration.loadConfiguration(file);
        if(Bukkit.getWorld("arena")==null) {
            Bukkit.createWorld(new WorldCreator("arena"));
        }
        World world = Bukkit.getWorld("arena");
        double x = fil.getDouble("Location.X");
        double y = fil.getDouble("Location.Y");
        double z = fil.getDouble("Location.Z");
        float yaw = (float)fil.getDouble("Location.y");
        float pitch = (float)fil.getDouble("Location.p");
        return new Location(world,x,y,z,yaw,pitch);
    }
}