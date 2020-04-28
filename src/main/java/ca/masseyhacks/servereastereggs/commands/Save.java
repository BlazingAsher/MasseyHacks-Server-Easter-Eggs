package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class Save implements CommandExecutor {
    private ServerEasterEggs plugin;
    public Save(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("eastereggs.save")){
            SEEUtil.saveState(plugin);
            sender.sendMessage("Saved plugin state.");
        }
        return true;
    }
}
