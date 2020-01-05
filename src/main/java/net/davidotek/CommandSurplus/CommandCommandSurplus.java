package net.davidotek.CommandSurplus;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandCommandSurplus implements CommandExecutor {

    FileConfiguration config;
    CommandSurplus csInstance;

    public CommandCommandSurplus(FileConfiguration c, CommandSurplus cs) {
        config = c;
        csInstance = cs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage("§6CommandSurplus Commands");
                for (String cmd : config.getConfigurationSection("commands").getKeys(false)) {  /* Check if command is already defined. Consider capitalization */
                    sender.sendMessage("§6" + cmd + " " + (config.getBoolean("commands." + cmd + ".disabled") ? "§cDISABLED§a" : "§aENABLED"));
                }
            }
        } else if (args.length < 2) {
            showHelp(sender);
        } else {
            /* Check if sender has permission. "commandsurplus.<command>" or "commandsurplus.*" */
            if (!sender.hasPermission("commandsurplus." + args[0]) && !sender.hasPermission("commandsurplus.*")) {
                sender.sendMessage("§cNo permissions!");
                return true;
            }

            if (args[0].equalsIgnoreCase("disable")) {              /* /commandsurplus disable <command> */
                if (args.length != 2) return showHelp(sender);

                setCommandOption(args[1],"disabled", true);
                sender.sendMessage("§aDisabled command §6" + args[1] + "§a!");
                return true;
            } else if (args[0].equalsIgnoreCase("enable")) {        /* /commandsurplus enable <command> */
                if (args.length != 2) return showHelp(sender);

                setCommandOption(args[1],"disabled", false);
                sender.sendMessage("§aEnabled command §6" + args[1] + "§a!");
                return true;
            } else if (args[0].equalsIgnoreCase("info")) {        /* /commandsurplus info <command> */
                if (args.length != 2) return showHelp(sender);

                for (String cmd : config.getConfigurationSection("commands").getKeys(false)) {
                    if (cmd.equalsIgnoreCase(args[1])) {
                        sender.sendMessage("§aCommand §6" + cmd + " §ais " + (config.getBoolean("commands." + cmd + ".disabled") ? ("§cDISABLED§a. Message: " + ChatColor.translateAlternateColorCodes('&', config.getString("commands." + cmd + ".message")) + "§r§a | Permission: " + config.getString("commands." + cmd + ".permission")) : "§6ENABLED"));
                        return true;
                    }
                }
                sender.sendMessage("§cCommand not found!");
                return true;
            } else if (args[0].equalsIgnoreCase("message")) {       /* /commandsurplus message <command> <message> */
                if (args.length < 3) return showHelp(sender);

                String message = args[2];
                for (int i = 3; i < args.length; i++) {
                    message += " " + args[i];
                }

                setCommandOption(args[1],"message", message);
                sender.sendMessage("§aChanged message of command §6" + args[1] + "§a!");
                return true;
            } else if (args[0].equalsIgnoreCase("permission")) {    /* /commandsurplus permission <command> <permission> */
                if (args.length != 3) return showHelp(sender);

                setCommandOption(args[1],"permission", args[2]);
                sender.sendMessage("§aChanged permission of command §6" + args[1] + "§a!");
                return true;
            }
        }
        return true;
    }

    private boolean showHelp(CommandSender sender) {
        sender.sendMessage("§c/commandsurplus <disable/enable/info> <command>");
        sender.sendMessage("§c/commandsurplus message <command> <message>");
        sender.sendMessage("§c/commandsurplus permission <command> <permission>");
        sender.sendMessage("§c/commandsurplus list");
        return true;
    }

    private boolean setCommandOption(String command, String option, Object value) {
        for (String cmd : config.getConfigurationSection("commands").getKeys(false)) {  /* Check if command is already defined. Consider capitalization */
            if (cmd.equalsIgnoreCase(command)) {
                config.set("commands." + cmd + "." + option, value);
                csInstance.saveConfig();
                return true;
            }
        }
        config.set("commands." + command.toLowerCase() + "." + option, value);
        csInstance.saveConfig();
        return true;
    }
}
