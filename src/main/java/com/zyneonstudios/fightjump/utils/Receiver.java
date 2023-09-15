package com.zyneonstudios.fightjump.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonObject;
import com.zyneonstudios.api.paper.Zyneon;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class Receiver implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if ( !channel.equalsIgnoreCase("base:bungee")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("spigot")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
        } else if(subChannel.contains("stopServer")) {
            Zyneon.getZyneonServer().stopServer();
        } else if(subChannel.contains("playSound_")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
            if(Bukkit.getPlayer(data1)!=null) {
                String s = subChannel.replace("playSound_","");
                Player p = Bukkit.getPlayer(data1);
                p.playSound(p.getLocation(), Sound.valueOf(s),100,100);
            }
        }
    }

    public JsonObject addSecret(JsonObject jsonObject, String hasKey, String key, UUID secret, String domain) {
        jsonObject.addProperty( hasKey, true );
        jsonObject.addProperty( key, secret.toString() + ":" + domain );
        return jsonObject;
    }
}