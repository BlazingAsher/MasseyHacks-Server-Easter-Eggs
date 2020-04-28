package ca.masseyhacks.servereastereggs.util;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class SEEUtil {
//    public static void addLocation(ServerEasterEggs plugin, EasterEggLocation location){
//        plugin.interactLocations.add(location);
//        plugin.getConfig().set("interactBonusLocations", plugin.interactLocations);
//    }
    public static List<String> locationToConfigList(ServerEasterEggs plugin, List<Location> locationList, Set<String> claimed){
        List<String> ret = new ArrayList<>();

        for(Location loc : locationList){
            String s = loc.getWorld().getName() +
                    "," +
                    loc.getX() +
                    "," +
                    loc.getY() +
                    "," +
                    loc.getZ();
            s += "/" + claimed.contains(s);
            ret.add(s);
        }

        return ret;
    }
    public static List<Location> configToLocationList(ServerEasterEggs plugin, List<String> configList){
        List<Location> ret = new ArrayList<>();
        for(String loc:configList){
            String[] info = loc.split("/")[0].split(",");
            String world = info[0];
            double x = Double.parseDouble(info[1]);
            double y = Double.parseDouble(info[2]);
            double z = Double.parseDouble(info[3]);
            ret.add(new Location(plugin.getServer().getWorld(world), x, y, z));

        }
        return ret;
    }

    public static Set<String> configToClaimedSet(ServerEasterEggs plugin, List<String> configList){
        Set<String> ret = new HashSet<>();
        for(String loc:configList){
            String[] info = loc.split("/");
            if(info.length > 1 && info[1].equals("true")){
                ret.add(info[0]);
            }
        }
        return ret;
    }

    public static String locationToDataString(ServerEasterEggs plugin , Location loc){
        return loc.getWorld().getName() +
                "," +
                loc.getX() +
                "," +
                loc.getY() +
                "," +
                loc.getZ();
    }

    public static boolean locationClaimed(ServerEasterEggs plugin, Location loc){
        return plugin.claimedLocations.contains(locationToDataString(plugin, loc));
    }

    public static void saveState(ServerEasterEggs plugin){
        plugin.config.set("interactBonusLocations", SEEUtil.locationToConfigList(plugin, plugin.interactLocations, plugin.claimedLocations));
        plugin.saveConfig();
    }
}
