package net.davidotek.CommandSurplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CommandSurplus extends JavaPlugin {

    FileConfiguration config = getConfig();

    ArrayList<String> disabledCommands = new ArrayList<String>();

    @Override
    public void onEnable() {
        loadConfiguration();
        getServer().getPluginManager().registerEvents(new CommandHandler(disabledCommands, config), this);

        this.getCommand("commandsurplus").setExecutor(new CommandCommandSurplus(config, this));
    }

    @Override
    public void onDisable() {

    }

    void loadConfiguration() {
        saveDefaultConfig();

        for (String c : config.getConfigurationSection("commands").getKeys(false)) {
            if (config.getBoolean("commands." + c + ".disabled")) {
                disabledCommands.add(c);
            }
        }
    }


}