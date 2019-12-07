package eu.lasersenigma.apiusage.lasers_sender_rotate;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;

public class LasersSendersRotate implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "lasers_sender_rotate";

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
    @Override
    public void onEnable() {
        // start the task
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED)) {
            if (laserSendersRotateTask == null) {
                laserSendersRotateTask = new LaserSendersRotateTask();
            }
        }
    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {
        // stops the task
        if (laserSendersRotateTask != null) {
            laserSendersRotateTask.cancel();
            laserSendersRotateTask = null;
        }
    }

    @Override
    public void setConfigDefaults() {
        ApiUsage.getInstance().getConfig().addDefault(CONFIG_FEATURE_ACTIVATED, true);
    }

}
