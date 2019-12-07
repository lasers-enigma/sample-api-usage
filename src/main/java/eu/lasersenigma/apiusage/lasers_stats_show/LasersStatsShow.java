package eu.lasersenigma.apiusage.lasers_stats_show;

import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.apiusage.IFeature;

public class LasersStatsShow implements IFeature {

    /**
     * Path in configuration file
     */
    private static final String CONFIG_FEATURE_ACTIVATED = "lasers_stats_show";

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersStatsShow instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static LasersStatsShow getInstance() {
        if (instance == null) {
            instance = new LasersStatsShow();
        }
        return instance;
    }

    /**
     * Called when the plugin is enabled
     */
    @SuppressWarnings("null")
    @Override
    public void onEnable() {
        if (ApiUsage.getInstance().getConfig().getBoolean(CONFIG_FEATURE_ACTIVATED)) {
            ApiUsage.getInstance().getCommand("lestats").setExecutor(new LasersStatsCommandExecutor());
        }
    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {

    }

    @Override
    public void setConfigDefaults() {
        ApiUsage.getInstance().getConfig().addDefault(CONFIG_FEATURE_ACTIVATED, true);
    }
}
