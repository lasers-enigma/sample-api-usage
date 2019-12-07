package eu.lasersenigma.apiusage.lasers_players_explosion;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;

public class LasersPlayersExplosion implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "lasers_players_explosion";

    private static final String CONFIG_NB_HIT_BEFORE_EXPLOSION = "lasers_players_explosion_nb_hit_before_explosion";

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersPlayersExplosion instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static LasersPlayersExplosion getInstance() {
        if (instance == null) {
            instance = new LasersPlayersExplosion();
        }
        return instance;
    }

    private ParticleTryToHitEntityLEEventListener eventListener = null;

    /**
     * Private constructor (Singleton design pattern)
     */
    private LasersPlayersExplosion() {

    }

    @Override
    public void onEnable() {
        // initialize the event listeners
        FileConfiguration config = ApiUsage.getInstance().getConfig();
        int nbHitBeforeExplosion = config.getInt(CONFIG_NB_HIT_BEFORE_EXPLOSION);
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED) && nbHitBeforeExplosion >= 0) {
            eventListener = new ParticleTryToHitEntityLEEventListener(nbHitBeforeExplosion);
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
        ApiUsage.getInstance().getConfig().addDefault(CONFIG_NB_HIT_BEFORE_EXPLOSION, 2);
    }
}
