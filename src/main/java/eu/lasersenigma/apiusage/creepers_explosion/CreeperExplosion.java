package eu.lasersenigma.apiusage.creepers_explosion;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;
import org.bukkit.event.HandlerList;

public class CreeperExplosion implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "creepers_explosion";
    
    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static CreeperExplosion instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static CreeperExplosion getInstance() {
        if (instance == null) {
            instance = new CreeperExplosion();
        }
        return instance;
    }

    private CreeperExplosionEventListener eventListener = null;

    /**
     * Private constructor (Singleton design pattern)
     */
    private CreeperExplosion() {
    }

    @Override
    public void onEnable() {
        // initialize the event listeners
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED)) {
            eventListener = new CreeperExplosionEventListener();
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
