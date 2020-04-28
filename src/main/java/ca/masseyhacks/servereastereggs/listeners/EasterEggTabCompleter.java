package ca.masseyhacks.servereastereggs.listeners;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.commands.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EasterEggTabCompleter implements TabCompleter {
    private ServerEasterEggs plugin;
    private BaseCommand baseCommand;

    public EasterEggTabCompleter(ServerEasterEggs plugin, BaseCommand baseCommand){
        this.plugin = plugin;
        this.baseCommand = baseCommand;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            Set<String> tempSet = baseCommand.registeredCommands();
            String[] tempArray = new String[tempSet.size()];
            tempSet.toArray(tempArray);
            return Arrays.asList(tempArray);
        }

        return null;
    }
}
