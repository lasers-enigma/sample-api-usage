package eu.lasersenigma.apiusage.lasers_burns_blocks;

import org.bukkit.event.HandlerList;

public class LasersBurnsBlocks {

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

    /**
     * Private constructor (Singleton design pattern)
     */
    private LasersBurnsBlocks() {

    }

    private ParticleTryToHitBlockLEEventListener eventListener;

    public void onEnable() {
        // initialize the event listeners
        eventListener = new ParticleTryToHitBlockLEEventListener();
    }

    public void onDisable() {
        // Unregister the event listener
        HandlerList.unregisterAll(eventListener);
    }
}
