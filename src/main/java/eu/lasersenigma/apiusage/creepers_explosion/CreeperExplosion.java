package eu.lasersenigma.apiusage.creepers_explosion;

import org.bukkit.event.HandlerList;

public class CreeperExplosion {

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

    /**
     * Private constructor (Singleton design pattern)
     */
    private CreeperExplosion() {

    }

    private CreeperExplosionEventListener eventListener;

    public void onEnable() {
        // initialize the event listeners
        eventListener = new CreeperExplosionEventListener();
    }

    public void onDisable() {
        // Unregister the event listener
        HandlerList.unregisterAll(eventListener);
    }
}
