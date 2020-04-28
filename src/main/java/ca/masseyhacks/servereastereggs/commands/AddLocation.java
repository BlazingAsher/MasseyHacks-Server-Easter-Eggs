package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AddLocation implements CommandExecutor {
    private ServerEasterEggs plugin;
    public AddLocation(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(sender.hasPermission("eastereggs.add")){
                Player player = (Player) sender;
                double x,y,z;
                try{
                    x = Double.parseDouble(args[0]);
                    y = Double.parseDouble(args[1]);
                    z = Double.parseDouble(args[2]);

                    plugin.interactLocations.add(new Location(player.getWorld(), x, y, z));

                    SEEUtil.saveState(plugin);

                    player.sendMessage("Easter egg location added!");
                }
                catch(NumberFormatException e){
                    player.sendMessage("Unable to parse location to doubles!");
                }
                catch(ArrayIndexOutOfBoundsException e){
                    player.sendMessage("Required arguments: <x> <y> <z>");
                }

            }
        }

        return true;
    }
}
