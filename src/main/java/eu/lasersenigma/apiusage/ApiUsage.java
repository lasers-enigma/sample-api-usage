package eu.lasersenigma.apiusage;

import eu.lasersenigma.Main;
import eu.lasersenigma.apiusage.lasers_sender_rotate.LasersSendersRotate;
import eu.lasersenigma.apiusage.lasers_stats_show.LasersStatsShow;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApiUsage extends JavaPlugin {

    private static ApiUsage instance;

    private static Main lasersEnigmaInstance = null;

    public static Main getLasersEnigmaInstance() {
        return lasersEnigmaInstance;
    }

    public static ApiUsage getInstance() {
        return instance;
    }

    /**
     * Plugin statup logic
     */
    @Override
    public void onEnable() {
        if (lasersEnigmaInstance == null) {
            if (getServer().getPluginManager().isPluginEnabled("LasersEnigma")) {
                lasersEnigmaInstance = (Main) getServer().getPluginManager().getPlugin("LasersEnigma");
                instance = this;
                onEnableConfirmed();
            }
        } else {
            getServer().getLogger().severe("This Plugin requires LasersEnigma to run.");
            throw new IllegalStateException("This Plugin requires LasersEnigma to run.");
        }
    }

    /**
     * Initialize each sample codes
     */
    private void onEnableConfirmed() {
        // Initialize configuration
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Setup the sample LasersSenderRotate feature
        LasersSendersRotate.getInstance().onEnable();

        // Setup the sample LasersStatsShow feature
        LasersStatsShow.getInstance().onEnable();
    }

    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        // Disable the sample LasersSenderRotate feature
        LasersSendersRotate.getInstance().onDisable();

        // Disable the sample LasersStatsShow feature
        LasersStatsShow.getInstance().onDisable();
    }
}
