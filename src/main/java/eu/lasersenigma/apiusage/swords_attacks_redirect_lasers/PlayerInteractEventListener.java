package eu.lasersenigma.apiusage.swords_attacks_redirect_lasers;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.areas.Area;
import eu.lasersenigma.areas.Areas;
import eu.lasersenigma.components.attributes.Direction;
import eu.lasersenigma.particles.LaserParticle;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEventListener implements Listener {

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerInteractEventListener() {
        ApiUsage plugin = ApiUsage.getInstance();

        //Register the event listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When a particles hits a block
     *
     * @param event an explode event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.DIAMOND_SWORD) {
            return;
        }
        Location pLoc = event.getPlayer().getLocation();
        Area area = Areas.getInstance().getAreaFromLocation(pLoc);
        if (area == null) {
            return;
        }
        HashSet<LaserParticle> particles = area.getLaserParticles();
        particles.forEach(p -> {
            if (p.getLocation().distance(pLoc) < 2) {
                p.setDirection(new Direction(event.getPlayer().getEyeLocation().getDirection()));
            }
        });
    }

}
