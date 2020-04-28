package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ListLocations implements CommandExecutor {
    private ServerEasterEggs plugin;
    public ListLocations(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("eastereggs.list")){
            sender.sendMessage("List of enabled worlds:");
            for(String worldName : plugin.config.getStringList("listenerEnabledWorlds")){
                sender.sendMessage(worldName);
            }
            sender.sendMessage("List of easter eggs:");
            for(Location location : plugin.interactLocations){
                sender.sendMessage(location.getWorld().getName() + " - " + location.getX() + ", " + location.getY() + ", " + location.getZ() + " - " + SEEUtil.locationClaimed(plugin, location));
            }
        }

        return true;
    }
}
