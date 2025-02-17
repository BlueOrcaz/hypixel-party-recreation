package dev.blueorcaz.partySystem;

import dev.blueorcaz.partySystem.Commands.PartyCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class PartySystem extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Party Chat Enabled"); // startup msg

        this.getCommand("sigma").setExecutor(new PartyCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Party Chat Disabled");
    }
}
