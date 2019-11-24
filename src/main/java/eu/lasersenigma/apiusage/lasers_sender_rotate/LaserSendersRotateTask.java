package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.areas.Areas;
import eu.lasersenigma.components.LaserSender;
import eu.lasersenigma.components.attributes.RotationType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A task updating areas
 */
public class LaserSendersRotateTask extends BukkitRunnable {

    /**
     * Constructor
     */
    public LaserSendersRotateTask() {
        //Starts the task
        this.runTaskTimer(ApiUsage.getInstance(), 40, 40);
    }

    /**
     * The method that will be executed repeatedly
     */
    @Override
    public void run() {
        Areas.getInstance().getAreaSet().stream() // Loop over areas
                .filter(area -> area.isActivated()) // Only activated areas
                .forEach(area -> {
                    area.getComponents().stream() // Loop over area's components
                            .filter(component -> component instanceof LaserSender) // Only laser senders
                            .forEach(component -> {
                                ((LaserSender) component).rotate(RotationType.RIGHT, false); // Rotate component without saving the rotation
                            });
                });
    }
}
