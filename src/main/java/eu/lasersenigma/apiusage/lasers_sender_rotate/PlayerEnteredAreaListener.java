package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.components.LaserSender;
import eu.lasersenigma.events.areas.PlayerEnteredAreaLEEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * EventListener for SongEndEvent
 */
public class PlayerEnteredAreaListener implements Listener {

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerEnteredAreaListener() {
        ApiUsage apiUsagePlugin = ApiUsage.getInstance();

        //register this eventListener to make it able to be called when events happens
        apiUsagePlugin.getServer().getPluginManager().registerEvents(this, apiUsagePlugin);
    }

    /**
     * executed when a players entered an area
     *
     * @param e a onPlayerEnteredAreaLEEvent instance.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerEnteredAreaLEEvent(PlayerEnteredAreaLEEvent e) {
        e.getArea().getComponents().stream() // Get the components of the area in a stream (see Java 8 streams)
                .filter(iComponent -> iComponent instanceof LaserSender) //get only the components that are LaserSenders
                .forEach(iComponent -> { // loop over laserSenders
                    LaserSender laserSender = (LaserSender) iComponent; // cast those components as LaserSender
                    if (!LasersSendersRotate.getInstance().getLaserSendersToRotate().contains(laserSender)) {  //if the component is not already in the collection of components to rotate
                        LasersSendersRotate.getInstance().getLaserSendersToRotate().add((laserSender)); //add the component to the collection of components to rotate
                    }
                });

    }
}
