# CommandSurplus
CommandSurplus i a very simplified permissions plugin which allows to disable certain commands.  
Commands can be completely disabled or use-permissions can be set.  
If a command is disabled or the player doesn't have the needed permissions a message can be displayed.

## Commands
Command|Explaination
-------|------------
/commandsurplus list | List commands
/commandsurplus enable <command> | Enable command
/commandsurplus disable <command> | Disable command
/commandsurplus info <command> | Show command info (disabled etc.)
/commandsurplus message <command> <message> | Change command message
/commandsurplus permission <command> <permission> | Change command permission

Commands from other plugins/spigot can be disabled in config/using ingame commands.

## Configuration
```yaml
commands:
  plugins:
    disabled: true
    permission: perms.plugins
  somecommand:
    disabled: true
    permission: permission.to.use.cmd
    message: "&cNo permissions!"
```
Parameter|Explaination
---------|------------
disabled|Disable the plugin
permission|Permission needed to use the command anyway
message|Message gets displayed when the player doesn't have the permissions

## Installation
1. Copy the CommandSurplus-*.jar file to the plugins folder
2. Configure the plugin
