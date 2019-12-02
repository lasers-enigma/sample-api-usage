package eu.lasersenigma.apiusage;

import eu.lasersenigma.Main;
import eu.lasersenigma.apiusage.creepers_explosion.CreeperExplosion;
import eu.lasersenigma.apiusage.lasers_burns_blocks.LasersBurnsBlocks;
import eu.lasersenigma.apiusage.lasers_give_command.LasersGiveCommand;
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
        getLogger().warning("[LasersEnigmaApiUsage] Keep in mind that this plugin contains sample features and is not meant to be used as is in a server");
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

        // Setup the sample /lestats command feature
        LasersStatsShow.getInstance().onEnable();
        
        // Setup the sample Creeper explosion feature
        CreeperExplosion.getInstance().onEnable();
        
        // Setup the sample Lasers burns blocks feature
        LasersBurnsBlocks.getInstance().onEnable();
        
        // Setup the sample /legive Command feature
        LasersGiveCommand.getInstance().onEnable();
    }

    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        // Disable the sample LasersSenderRotate feature
        LasersSendersRotate.getInstance().onDisable();

        // Disable the sample /lestats command feature
        LasersStatsShow.getInstance().onDisable();
        
        // Disable the sample Creeper explosion
        CreeperExplosion.getInstance().onDisable();
        
        // Disable the sample Lasers burns blocks feature
        LasersBurnsBlocks.getInstance().onDisable();
        
        // Disable the sample /legive Command feature
        LasersGiveCommand.getInstance().onDisable();
    }
}
