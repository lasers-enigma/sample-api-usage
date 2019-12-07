package eu.lasersenigma.apiusage;

import eu.lasersenigma.Main;
import eu.lasersenigma.apiusage.creepers_explosion.CreeperExplosion;
import eu.lasersenigma.apiusage.lasers_burns_blocks.LasersBurnsBlocks;
import eu.lasersenigma.apiusage.swords_attacks_redirect_lasers.SwordsAttacksRedirectLasers;
import eu.lasersenigma.apiusage.lasers_give_command.LasersGiveCommand;
import eu.lasersenigma.apiusage.lasers_players_explosion.LasersPlayersExplosion;
import eu.lasersenigma.apiusage.lasers_sender_rotate.LasersSendersRotate;
import eu.lasersenigma.apiusage.lasers_stats_show.LasersStatsShow;
import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.configuration.file.FileConfiguration;
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
    
    private HashSet<IFeature> features;

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
        
        features = new HashSet<>(Arrays.asList(
                CreeperExplosion.getInstance(),
                LasersBurnsBlocks.getInstance(),
                LasersGiveCommand.getInstance(),
                LasersPlayersExplosion.getInstance(),
                LasersSendersRotate.getInstance(),
                LasersStatsShow.getInstance(),
                SwordsAttacksRedirectLasers.getInstance()
        ));

        // Initialize configuration
        FileConfiguration config = getConfig();
        features.forEach(feature -> feature.setConfigDefaults());
        config.options().copyDefaults(true);
        saveConfig();

        // Setup features
        features.forEach(feature -> feature.onEnable());
    }

    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        //Disable features
        features.forEach(feature -> feature.onDisable());
    }
}
