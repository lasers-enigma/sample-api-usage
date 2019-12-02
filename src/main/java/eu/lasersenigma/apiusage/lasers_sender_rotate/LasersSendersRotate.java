package eu.lasersenigma.apiusage.lasers_sender_rotate;

public class LasersSendersRotate {

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersSendersRotate instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static LasersSendersRotate getInstance() {
        if (instance == null) {
            instance = new LasersSendersRotate();
        }
        return instance;
    }

    /**
     * the task that will be run periodically to rotate laser senders
     */
    private LaserSendersRotateTask laserSendersRotateTask = null;

    /*
     * Private constructor
     */
    private LasersSendersRotate() {

    }

    /**
     * Called when the plugin is enabled
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void onEnable() {
        // start the task
        if (laserSendersRotateTask == null) {
            laserSendersRotateTask = new LaserSendersRotateTask();
        }
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable() {
        // stops the task
        laserSendersRotateTask.cancel();
        laserSendersRotateTask = null;
    }

}
