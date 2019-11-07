package eu.lasersenigma.apiusage;

import eu.lasersenigma.Main;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApiUsage extends JavaPlugin {
    Main lasers;
    @Override
    public void onEnable() {
        // Plugin startup logic
        if(getServer().getPluginManager().isPluginEnabled("LasersEnigma")) {
            lasers = (Main) getServer().getPluginManager().getPlugin("LasersEnigma");
        } else {
            getServer().getLogger().severe("This Plugin requires LasersEnigma to run");
            
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
