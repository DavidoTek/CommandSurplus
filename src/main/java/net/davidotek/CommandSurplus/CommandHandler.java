package net.davidotek.CommandSurplus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class CommandHandler implements Listener {

    ArrayList<String> disabledCommands;

    FileConfiguration config;

    public CommandHandler(ArrayList<String> dc, FileConfiguration c) {
        disabledCommands = dc;
        config = c;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String eCommand = e.getMessage();

        for (String c : disabledCommands) {

            String commandMessage = "/" + c.toLowerCase();

            if (eCommand.toLowerCase().startsWith(commandMessage + " ") || eCommand.equalsIgnoreCase(commandMessage)) {
                String commandPermission = config.getString("commands." + c + ".permission", "");
                if (commandPermission != "") {
                    if (e.getPlayer().hasPermission(commandPermission)) {
                        return;
                    }
                }

                String disabledMessage = config.getString("commands." + c + ".message", "");

                if (disabledMessage != "") {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', disabledMessage));
                }

                e.setCancelled(true);
            }
        }
    }
}
