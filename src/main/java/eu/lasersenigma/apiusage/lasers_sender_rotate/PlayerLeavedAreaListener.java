package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.components.LaserSender;
import eu.lasersenigma.events.areas.PlayerLeavedAreaLEEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * EventListener for SongEndEvent
 */
public class PlayerLeavedAreaListener implements Listener {

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerLeavedAreaListener() {
        ApiUsage apiUsagePlugin = ApiUsage.getInstance();

        //register this eventListener to make it able to be called when events happens
        apiUsagePlugin.getServer().getPluginManager().registerEvents(this, apiUsagePlugin);
    }

    /**
     * executed when a players leaves an area
     *
     * @param e a PlayerLeavedAreaLEEvent instance.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeavedAreaLEEvent(PlayerLeavedAreaLEEvent e) {
        if (e.getArea().getPlayersExceptSpectators().isEmpty()) { // If there is no more players in the area
            e.getArea().getComponents().stream() // Get the components of this area
                    .filter(iComponent -> iComponent instanceof LaserSender) // Get only laser senders
                    .map(iComponent -> iComponent.getComponentId()) // Retrieves their componentId
                    .forEach(componentId -> {
                        LasersSendersRotate.getInstance().getLaserSendersToRotate().removeIf(laserSender -> laserSender.getComponentId() == componentId); //Delete the components with the same componentId from the laserSendersToRotate set.
                    });
        }
    }
}
