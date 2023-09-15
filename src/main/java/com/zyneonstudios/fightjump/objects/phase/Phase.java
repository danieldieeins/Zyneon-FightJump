package com.zyneonstudios.fightjump.objects.phase;

public class Phase {

    PhaseType type;
    String display;

    public Phase(PhaseType type) {
        this.type = type;
        if(this.type.equals(PhaseType.STARTUP)) {
            display = "§6Herstellen§r";
        } else if(this.type.equals(PhaseType.LOBBY)) {
            display = "§aLobby§r";
        } else if(this.type.equals(PhaseType.LOBBY_FULL)) {
            display = "§cVoll§r";
        } else  {
            display = "§cIngame§r";
        }
    }

    public String getDisplay() {
        return display;
    }

    public PhaseType getType() {
        return this.type;
    }
}