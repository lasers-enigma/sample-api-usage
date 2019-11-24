package eu.lasersenigma.apiusage.creepers_explosion;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.areas.Area;
import eu.lasersenigma.areas.Areas;
import eu.lasersenigma.components.attributes.Direction;
import eu.lasersenigma.components.attributes.LasersColor;
import eu.lasersenigma.particles.LaserParticle;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class CreeperExplosionEventListener implements Listener {

    public static float SPHERE_SIZE = 1;

    public static LasersColor PARTICLE_COLOR = LasersColor.GREEN;

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public CreeperExplosionEventListener() {
        ApiUsage plugin = ApiUsage.getInstance();
        
        //Register the event listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When an entity explode
     *
     * @param event an explode event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        //Only works for creepers
        if (event.getEntity() instanceof Creeper) {
            //Get the location of the explosion
            Location baseLocation = event.getLocation().clone();
            //Lasers particle can't spawn outside of areas
            Area area = Areas.getInstance().getAreaFromLocation(baseLocation);
            if (area == null) {
                return;
            }
            //Calculate a sphere of SPHERE_SIZE around the explosion location
            double nbParticlesSquared = 50;
            double TAU = 2 * Math.PI;
            double increment = TAU / nbParticlesSquared;
            for (double angle1 = 0; angle1 < TAU; angle1 += increment) {
                double y = (Math.cos(angle1) * SPHERE_SIZE);
                double tmpRadius = Math.sin(angle1);
                for (double angle2 = 0; angle2 < TAU; angle2 += increment) {
                    double x = Math.cos(angle2) * tmpRadius * SPHERE_SIZE;
                    double z = Math.sin(angle2) * tmpRadius * SPHERE_SIZE;
                    // The location of the new particle
                    Location particleSpawnLocation = new Location(baseLocation.getWorld(), baseLocation.getX() + x, baseLocation.getY() + y, baseLocation.getZ() + z);
                    // The new particle direction
                    Vector directionVector = baseLocation.toVector().subtract(particleSpawnLocation.toVector());
                    Direction direction = new Direction(directionVector);
                    // Send the new laser particle
                    area.addLaserParticle(new LaserParticle(event.getEntity().getUniqueId(), baseLocation, direction, PARTICLE_COLOR, area));
                }
            }
        }
    }

}
