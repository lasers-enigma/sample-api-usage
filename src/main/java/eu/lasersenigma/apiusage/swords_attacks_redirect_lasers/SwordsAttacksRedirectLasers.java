package eu.lasersenigma.apiusage.swords_attacks_redirect_lasers;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;
import org.bukkit.event.HandlerList;

public class SwordsAttacksRedirectLasers implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "lasers_players_explosion";

    private static final String CONFIG_EXPLOSION_DELAY = "lasers_players_explosion_tick_delay";

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

    private PlayerInteractEventListener eventListener = null;

    /**
     * Private constructor (Singleton design pattern)
     */
    private SwordsAttacksRedirectLasers() {

    }

    @Override
    public void onEnable() {
        // initialize the event listeners
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED)) {
            eventListener = new PlayerInteractEventListener();
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
