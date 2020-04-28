package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ToggleLocation implements CommandExecutor {
    private ServerEasterEggs plugin;
    public ToggleLocation(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("eastereggs.toggle")){
            double x,y,z;
            try{
                String world = args[0];
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);

                Location temp = new Location(plugin.getServer().getWorld(world), x, y, z);
                String tempDataString = SEEUtil.locationToDataString(plugin, temp);

                if(SEEUtil.locationClaimed(plugin, temp)){
                    plugin.claimedLocations.remove(tempDataString);
                    sender.sendMessage("Set location as unclaimed.");
                }
                else{
                    plugin.claimedLocations.add(tempDataString);
                    sender.sendMessage("Set location as claimed.");
                }

                SEEUtil.saveState(plugin);

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
