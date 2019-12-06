package eu.lasersenigma.apiusage.swords_attacks_redirect_lasers;

import org.bukkit.event.HandlerList;

public class SwordsAttacksRedirectLasers {

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static SwordsAttacksRedirectLasers instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static SwordsAttacksRedirectLasers getInstance() {
        if (instance == null) {
            instance = new SwordsAttacksRedirectLasers();
        }
        return instance;
    }

    /**
     * Private constructor (Singleton design pattern)
     */
    private SwordsAttacksRedirectLasers() {

    }

    private PlayerInteractEventListener eventListener;

    public void onEnable() {
        // initialize the event listeners
        eventListener = new PlayerInteractEventListener();
    }

    public void onDisable() {
        // Unregister the event listener
        HandlerList.unregisterAll(eventListener);
    }
}
