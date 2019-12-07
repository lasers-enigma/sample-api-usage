package eu.lasersenigma.apiusage.lasers_players_explosion;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.events.laserparticles.ParticleTryToHitEntityLEEvent;
import eu.lasersenigma.particles.LaserParticle;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class ParticleTryToHitEntityLEEventListener implements Listener {

    private final int nbHitBeforeExplosion;

    private final HashMap<UUID, Integer> playersNbHitsRemaining;

    /**
     * Constructor
     *
     * @param nbHitBeforeExplosion the numer of times the player can get hit
     * before an explosion happen.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ParticleTryToHitEntityLEEventListener(int nbHitBeforeExplosion) {
        this.nbHitBeforeExplosion = nbHitBeforeExplosion;
        this.playersNbHitsRemaining = new HashMap<>();
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
    public void onParticleTryToHitEntityLEEvent(ParticleTryToHitEntityLEEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        Integer playerNbHitsRemaining = playersNbHitsRemaining.get(player.getUniqueId());
        if (playerNbHitsRemaining == null) {
            playersNbHitsRemaining.put(player.getUniqueId(), nbHitBeforeExplosion);
        } else {
            playerNbHitsRemaining--;
            if (playerNbHitsRemaining == 0) {
                //The explosion will happen
                playerNbHitsRemaining = null; //reset the number of hit remaining before the next explosion.
                
                LaserParticle particle = event.getLaserParticle();
                
                final Location playerLocation = player.getLocation();
                final Location particleLocation = particle.getLocation();
                
                //Calculate a vectors to know where is the player according to the particle's location
                Vector particleToPlayerVector = particleLocation.toVector().subtract(playerLocation.toVector());
                particleToPlayerVector.normalize();
                
                //Retrieves the particle direction (also a vector)
                Vector particleDirectionVector = particle.getDirection().normalize();
                
                //Add both together to get the explosion direction
                Vector explosionVector = particleDirectionVector.add(particleToPlayerVector.multiply(2)).multiply(0.4);
                player.setVelocity(explosionVector);
                playerLocation.getWorld().spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5);
                playerLocation.getWorld().playSound(particleLocation, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 50, 2);
            }
            playersNbHitsRemaining.put(player.getUniqueId(), playerNbHitsRemaining);
        }
    }

}
