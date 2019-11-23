package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.components.LaserSender;
import java.util.HashSet;

public class LasersSendersRotate {
    
    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersSendersRotate instance;
    
    /**
     * public getInstance method (see Singleton design pattern)
     * @return the only instance of this class
     */
    public static LasersSendersRotate getInstance() {
        if (instance == null) {
            instance = new LasersSendersRotate();
        }
        return instance;
    }
    
    /**
     * the map that contains, for each area, the LaserSenders that will be periodically rotated
     */
    private final HashSet<LaserSender> laserSendersToRotate;
    
    /**
     * the task that will be run periodically to rotate laser senders
     */
    private LaserSendersRotateTask laserSendersRotateTask = null;
    
    /**
     * Private constructor (see Singleton design pattern)
     */
    private LasersSendersRotate() {
        // initialize the map that contains, for each area, the LaserSenders that will be periodically rotated
        laserSendersToRotate = new HashSet<>();
    }
    

    /**
     * Retrieves the set LaserSenders that must rotate
     * @return the LaserSenders that must rotate
     */
    public HashSet<LaserSender> getLaserSendersToRotate() {
        return laserSendersToRotate;
    }

    /**
     * Called when the plugin is enabled
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void onEnable() {
        // initialize and register the event listeners
        new PlayerEnteredAreaListener();
        new PlayerLeavedAreaListener();
        
        if (laserSendersRotateTask == null) {
            laserSendersRotateTask = new LaserSendersRotateTask();
        }
    }


    /**
     * Called when the plugin is disabled
     */
    public void onDisable() {
        laserSendersRotateTask.cancel();
        laserSendersRotateTask = null;
        laserSendersToRotate.clear();
    }
    
}
