package ca.masseyhacks.servereastereggs.commands;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class Reload implements CommandExecutor {
    private ServerEasterEggs plugin;
    public Reload(ServerEasterEggs plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("eastereggs.reload")){
            plugin.reloadConfig();
            plugin.config = plugin.getConfig();
            plugin.interactLocations = SEEUtil.configToLocationList(plugin, plugin.config.getStringList("interactBonusLocations"));
            plugin.interactLocations = Collections.synchronizedList(plugin.interactLocations);

            plugin.claimedLocations = SEEUtil.configToClaimedSet(plugin, plugin.config.getStringList("interactBonusLocations"));
            plugin.claimedLocations = Collections.synchronizedSet(plugin.claimedLocations);

            sender.sendMessage("Reloaded MasseyHacks Easter Eggs.");
        }
        return true;
    }
}
