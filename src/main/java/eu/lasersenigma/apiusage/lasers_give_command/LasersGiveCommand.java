package eu.lasersenigma.apiusage.lasers_give_command;

import eu.lasersenigma.apiusage.ApiUsage;

public class LasersGiveCommand {

    /**
     * private instance of this class (see Singleton design pattern)
     */
    private static LasersGiveCommand instance;

    /**
     * public getInstance method (see Singleton design pattern)
     *
     * @return the only instance of this class
     */
    public static LasersGiveCommand getInstance() {
        if (instance == null) {
            instance = new LasersGiveCommand();
        }
        return instance;
    }

    /**
     * Called when the plugin is enabled
     */
    @SuppressWarnings("null")
    public void onEnable() {
        ApiUsage.getInstance().getCommand("legive").setExecutor(new LasersGiveCommandExecutor());
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable() {

    }
}
