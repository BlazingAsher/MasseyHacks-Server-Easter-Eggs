package ca.masseyhacks.servereastereggs.listeners;

import ca.masseyhacks.servereastereggs.ServerEasterEggs;
import ca.masseyhacks.servereastereggs.structures.ClaimToken;
import ca.masseyhacks.servereastereggs.util.SEEUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class InteractEventListener implements Listener {
    private ServerEasterEggs plugin;

    public InteractEventListener(ServerEasterEggs plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if(block == null){
            return;
        }

        if(plugin.config.getStringList("listenerEnabledWorlds").contains(block.getLocation().getWorld().getName())){
            //System.out.println(block.getLocation());

            if(plugin.interactLocations.contains(block.getLocation()) && !plugin.claimedLocations.contains(SEEUtil.locationToDataString(plugin, block.getLocation()))){
                Location location = block.getLocation();
                // Spawn a firework
                Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                FireworkEffect.Type type = FireworkEffect.Type.BALL;

                FireworkEffect effect = FireworkEffect.builder().with(type).withColor(Color.YELLOW).build();
                fireworkMeta.addEffect(effect);

                fireworkMeta.setPower(0);
                firework.setFireworkMeta(fireworkMeta);

                block.setType(Material.AIR);

                // Send the player the message
                player.sendMessage(plugin.config.getString("foundMessage"));
                player.sendMessage("Message the string below to a team member on Discord to claim your prize! (Click on it to put it into your command bar)");

                String claimToken = new ClaimToken(plugin.config.getString("claimKey"),
                        player.getUniqueId().toString(),
                        plugin.config.getDouble("foundPrize"),
                        location.getX(),
                        location.getY(),
                        location.getZ()).getClaimString();

                TextComponent temp = new TextComponent(claimToken);
                temp.setColor(ChatColor.DARK_PURPLE);

                temp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to put the string into your command bar.").create()));
                temp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, claimToken));

                player.spigot().sendMessage(temp);

                plugin.claimedLocations.add(SEEUtil.locationToDataString(plugin, location));

                SEEUtil.saveState(plugin);

            }
        }
    }
}
