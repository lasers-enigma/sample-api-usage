package eu.lasersenigma.apiusage.lasers_stats_show;

import eu.lasersenigma.apiusage.ApiUsage;

public class LasersStatsShow {

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
    public void onEnable() {
        ApiUsage.getInstance().getCommand("lestats").setExecutor(new LasersStatsCommandExecutor());
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable() {

    }
}
