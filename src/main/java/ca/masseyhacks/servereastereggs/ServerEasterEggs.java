package ca.masseyhacks.servereastereggs;

import ca.masseyhacks.servereastereggs.commands.*;
import ca.masseyhacks.servereastereggs.listeners.EasterEggTabCompleter;
import ca.masseyhacks.servereastereggs.listeners.InteractEventListener;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ServerEasterEggs extends JavaPlugin {

    public FileConfiguration config;
    public List<Location> interactLocations;
    public Set<String> claimedLocations;
    @Override
    public void onEnable() {
        // Plugin startup logic
        config = this.getConfig();
        config.addDefault("interactBonusLocations", new ArrayList<String>());
        config.addDefault("listenerEnabledWorlds", new ArrayList<String>());
        config.addDefault("foundMessage", "YOU FOUND IT!");
        config.addDefault("claimKey", "1234");
        config.addDefault("foundPrize", 100.0D);

        config.options().copyDefaults(true);
        saveConfig();

        // Convert saved info to Location objects
        getLogger().info("Loading plugin state.");
        interactLocations = SEEUtil.configToLocationList(this, config.getStringList("interactBonusLocations"));
        interactLocations = Collections.synchronizedList(interactLocations);

        claimedLocations = SEEUtil.configToClaimedSet(this, config.getStringList("interactBonusLocations"));
        claimedLocations = Collections.synchronizedSet(claimedLocations);

        // Register commands
        getLogger().info("Registering commands.");

        BaseCommand baseCommand = new BaseCommand(this);
        getCommand("mheggs").setExecutor(baseCommand);
        getCommand("mheggs").setTabCompleter(new EasterEggTabCompleter(this, baseCommand));

        baseCommand.registerCommand("reload", new Reload(this));
        baseCommand.registerCommand("add", new AddLocation(this));
        baseCommand.registerCommand("remove", new RemoveLocation(this));
        baseCommand.registerCommand("list", new ListLocations(this));
        baseCommand.registerCommand("save", new Save(this));
        baseCommand.registerCommand("toggle", new ToggleLocation(this));

        //getCommand("mheggsreload").setExecutor(new Reload(this));
        //getCommand("mheggsadd").setExecutor(new AddLocation(this));
        //getCommand("mheggsremove").setExecutor(new RemoveLocation(this));
        //getCommand("mheggslist").setExecutor(new ListLocations(this));
        //getCommand("mheggssave").setExecutor(new Save(this));
        //getCommand("mheggstoggle").setExecutor(new ToggleLocation(this));

        getLogger().info("Registering event handlers.");
        getServer().getPluginManager().registerEvents(new InteractEventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Saving plugin state.");
        SEEUtil.saveState(this);
    }
}
