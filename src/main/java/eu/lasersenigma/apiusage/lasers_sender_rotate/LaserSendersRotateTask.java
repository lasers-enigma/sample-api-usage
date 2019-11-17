package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.apiusage.ApiUsage;
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
     * The method that will be run repeatedly
     */
    @Override
    public void run() {
        LasersSendersRotate.getInstance()
                .getLaserSendersToRotate() //Retrieves the LaserSenders to rotate collection
                .stream() //Create a stream (see Java 8 streams)
                .forEach(laserSender -> { //Loop over LaserSenders
                    laserSender.rotate(RotationType.RIGHT, false); //rotate the laserSender horizontally and do not save this modification
                });
    }

}
