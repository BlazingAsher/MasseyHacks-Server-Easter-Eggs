package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RemoveLocation implements CommandExecutor {
    private ServerEasterEggs plugin;
    public RemoveLocation(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("eastereggs.remove")){
            double x,y,z;
            try{
                String world = args[0];
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);

                boolean result = plugin.interactLocations.remove(new Location(plugin.getServer().getWorld(world), x, y, z));

                SEEUtil.saveState(plugin);

                sender.sendMessage("Attempted removal, result: " + result);
            }
            catch(NumberFormatException e){
                sender.sendMessage("Unable to parse location to doubles!");
            }
            catch(ArrayIndexOutOfBoundsException e){
                sender.sendMessage("Required arguments: <world> <x> <y> <z>");
            }

        }

        return true;
    }
}
