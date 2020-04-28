package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BaseCommand implements CommandExecutor {

    private ServerEasterEggs plugin;
    private HashMap<String, CommandExecutor> commandMap = new HashMap<String, CommandExecutor>();

    public BaseCommand(ServerEasterEggs plugin){
        this.plugin = plugin;
    }

    public CommandExecutor getCommandExecutor(String command) {
        return this.commandMap.get(command.toLowerCase());
    }

    public void registerCommand(String command, CommandExecutor commandExecutor) {
        if (commandExecutor == null || command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Invalid command registration.");
        }
        this.commandMap.put(command.toLowerCase(), commandExecutor);
    }

    public void unregisterCommand(String command) {
        this.commandMap.remove(command.toLowerCase());
    }


    public boolean hasCommand(String command) {
        return this.commandMap.containsKey(command.toLowerCase());
    }

    public Set<String> registeredCommands(){
        return commandMap.keySet();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && hasCommand(args[0])) {
            CommandExecutor executor = this.getCommandExecutor(args[0]);
            return executor.onCommand(sender, command, label, popArray(args));
        }
        else if(args.length > 0 && args[0].toLowerCase().equals("help")){
            sender.sendMessage("Commands:");
            for(String commandName : commandMap.keySet()){
                sender.sendMessage("/mheggs "+ commandName);
            }
            return true;
        }
        else {
            sender.sendMessage("MasseyHacks Easter Eggs version " + plugin.getDescription().getVersion());
            return true;
        }
    }

    private static String[] popArray(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}
