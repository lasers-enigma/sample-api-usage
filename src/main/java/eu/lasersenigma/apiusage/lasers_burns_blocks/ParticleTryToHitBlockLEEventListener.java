package eu.lasersenigma.apiusage.lasers_burns_blocks;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.events.laserparticles.ParticleTryToHitBlockLEEvent;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ParticleTryToHitBlockLEEventListener implements Listener {

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ParticleTryToHitBlockLEEventListener() {
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
    public void onEntityExplode(ParticleTryToHitBlockLEEvent event) {
        
        //Only works for burnable blocks
        if (event.getBlock().getType().isBurnable()) {
            event.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
        }
    }

}
