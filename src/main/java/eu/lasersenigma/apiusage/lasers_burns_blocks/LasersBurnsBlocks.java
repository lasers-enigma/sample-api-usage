package eu.lasersenigma.apiusage.lasers_burns_blocks;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;
import org.bukkit.event.HandlerList;

public class LasersBurnsBlocks implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "lasers_burns_blocks";

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersBurnsBlocks instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static LasersBurnsBlocks getInstance() {
        if (instance == null) {
            instance = new LasersBurnsBlocks();
        }
        return instance;
    }

    private ParticleTryToHitBlockLEEventListener eventListener = null;

    /**
     * Private constructor (Singleton design pattern)
     */
    private LasersBurnsBlocks() {

    }

    @Override
    public void onEnable() {
        // initialize the event listeners
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED)) {
            eventListener = new ParticleTryToHitBlockLEEventListener();
        }
    }

    @Override
    public void onDisable() {
        // Unregister the event listener
        if (eventListener != null) {
            HandlerList.unregisterAll(eventListener);
            eventListener = null;
        }
    }

    @Override
    public void setConfigDefaults() {
        ApiUsage.getInstance().getConfig().addDefault(CONFIG_FEATURE_ACTIVATED, true);
    }
}
