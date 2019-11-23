package eu.lasersenigma.apiusage.lasers_stats_show;

import eu.lasersenigma.Main;
import eu.lasersenigma.apiusage.ApiUsage;
import eu.lasersenigma.areas.Area;
import eu.lasersenigma.areas.Areas;
import eu.lasersenigma.config.Message;
import eu.lasersenigma.exceptions.PlayerStatsNotFoundException;
import eu.lasersenigma.player.LEPlayers;
import eu.lasersenigma.stats.AreaStats;
import static eu.lasersenigma.stats.AreaStats.NEW_LINE;
import static eu.lasersenigma.stats.AreaStats.RANK_TITLE;
import static eu.lasersenigma.stats.AreaStats.SEPARATOR;
import static eu.lasersenigma.stats.AreaStats.TABULATION;
import static eu.lasersenigma.stats.AreaStats.getMaxLength;
import static eu.lasersenigma.stats.AreaStats.toStr;
import eu.lasersenigma.stats.PlayerStats;
import eu.lasersenigma.text.TabText;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LasersStatsCommandExecutor implements CommandExecutor {

    public static String USAGE = "lestats [global] [<playerName>]";

    public static final String KEYWORD_GLOBAL = "global";

    public static final String PLAYER_NOT_FOUND = "Requested player could not be found.";

    public static final String NO_PLAYER_STATS_IN_AREA = "The player did not won this area already";

    public static final String NO_RECORDS_FOR_THIS_AREA = "No players has finished this area yet";

    public static final int TOP_SIZE = 5;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> argsToProcess = Arrays.asList(args);
        //Only players can use this command
        if (!(sender instanceof Player)) {
            return false;
        }
        //"lestats" is the only command accepted
        if (!cmd.getName().equalsIgnoreCase("lestats")) {
            return false;
        }
        Player player = (Player) sender;

        //Which area do we want. null for every areas (global stats).
        Area area = null;

        //Define which area stats should be shown or if we want global stats
        if (argsToProcess.size() > 0 && argsToProcess.get(0).equals(KEYWORD_GLOBAL)) {
            argsToProcess.remove(0);
        } else {
            area = Areas.getInstance().getAreaFromLocation(player.getLocation()); //If the player is not in an area null will be returned and the command will search for global stats.
        }

        //Which player do we want. null for top players.
        String playerName = null;

        //Define playerName
        if (argsToProcess.size() > 0) {
            playerName = argsToProcess.get(0);
            argsToProcess.remove(0);
        }

        if (!argsToProcess.isEmpty()) {
            player.sendMessage(USAGE);
            return true;
        }

        ArrayList<String> statsMessages = getStatsMessage(area, playerName);
        statsMessages.forEach(message -> player.sendMessage(message));

        return true;
    }

    private ArrayList<String> getStatsMessage(Area area, String playerName) {

        String headers;

        ArrayList<String> statsMessages = new ArrayList<>();

        Player player = null;
        if (playerName != null) {
            player = ApiUsage.getInstance().getServer().getPlayer(playerName);
            if (player == null) {
                statsMessages.add(PLAYER_NOT_FOUND);
                return statsMessages;
            }
            headers = playerName;
        } else {
            headers = "Top players";
        }

        headers += area != null ? " records for this area " : " records ";

        headers += playerName == null ? "(number of area solved) " : "";

        headers += ":";

        statsMessages.add(headers);

        if (player != null) {
            if (area != null) {
                //Retrieving player records for this area
                getPlayerAreaRecords(area, player, statsMessages);
            } else {
                //Retrieving player records
                getPlayerRecords(player, statsMessages);
            }
        } else {
            if (area != null) {
                //Retrieving record for this area
                getTopAreaRecords(area, statsMessages);
            } else {
                //Retrieving records
                getTopRecords(statsMessages);
            }
        }
        return statsMessages;
    }

    private void getPlayerAreaRecords(Area area, Player player, ArrayList<String> statsMessages) {
        try {
            PlayerStats stats = area.getStats().getStats(player.getUniqueId());
            statsMessages.add("Number of steps : " + stats.getNbStep());
            statsMessages.add("Time spent : " + AreaStats.toStr(stats.getDuration()));
            statsMessages.add("Number of actions : " + stats.getNbAction());
        } catch (PlayerStatsNotFoundException ex) {
            statsMessages.add(NO_PLAYER_STATS_IN_AREA);
        }
    }

    private void getPlayerRecords(Player player, ArrayList<String> statsMessages) {
        HashMap<Area, PlayerStats> statsPerArea = LEPlayers.getInstance().findLEPlayer(player.getUniqueId()).getPerAreaRecords(null);
        int nbAreaSolved = statsPerArea.size();
        long totalPlayerNbStepsInRecords = statsPerArea.values().stream()
                .map(playerStats -> playerStats.getNbStep())
                .collect(Collectors.summingLong(Integer::toUnsignedLong));
        Duration totalTimeSpentInRecords = statsPerArea.values().stream()
                .map(playerStats -> playerStats.getDuration())
                .reduce(Duration.ZERO, (a, b) -> a.plus(b));
        long totalPlayerNbActionsInRecords = statsPerArea.values().stream()
                .map(playerStats -> playerStats.getNbAction())
                .collect(Collectors.summingLong(Integer::toUnsignedLong));
        statsMessages.add("Total number of steps : " + totalTimeSpentInRecords);
        statsMessages.add("Total time spent : " + AreaStats.toStr(totalTimeSpentInRecords));
        statsMessages.add("Total number of actions : " + totalPlayerNbActionsInRecords);
    }

    private void getTopAreaRecords(Area area, ArrayList<String> statsMessages) {
        //This is the equivalent of the method eu.lasersenigma.stats.AreaStats.showRecords(Player player)
        if (area.getStats().isLinked()) {
            getTopAreaRecords(area.getStats().getLinkedArea(), statsMessages);
            return;
        }
        ArrayList<String> rankStrs = new ArrayList<>();
        rankStrs.add(RANK_TITLE);
        for (Integer i = 1; i <= 10; ++i) {
            rankStrs.add(i.toString());
        }
        int rankStrsMaxLength = getMaxLength(rankStrs) + 1;

        ArrayList<String> durationRecordsStrs = area.getStats().getDurationPlayersRecord().entrySet().stream()
                .limit(10)
                .map(e -> {
                    StringBuilder sb = new StringBuilder(Main.getInstance().getServer().getOfflinePlayer(e.getKey()).getName());
                    sb.append(SEPARATOR);
                    sb.append(toStr(e.getValue()));
                    return sb.toString();
                })
                .collect(Collectors.toCollection(ArrayList::new));
        durationRecordsStrs.add(0, Message.DURATION.getMessage());
        int durationRecordsStrsMaxLength = rankStrsMaxLength + getMaxLength(durationRecordsStrs) + 1;

        ArrayList<String> nbActionRecordsStrs = area.getStats().getNbActionPlayersRecord().entrySet().stream()
                .limit(10)
                .map(e -> {
                    StringBuilder sb = new StringBuilder(Main.getInstance().getServer().getOfflinePlayer(e.getKey()).getName());
                    sb.append(SEPARATOR);
                    sb.append(e.getValue());
                    return sb.toString();
                })
                .collect(Collectors.toCollection(ArrayList::new));
        nbActionRecordsStrs.add(0, Message.NBACTIONS.getMessage());
        int nbActionRecordsStrsMaxLength = durationRecordsStrsMaxLength + getMaxLength(nbActionRecordsStrs) + 1;

        ArrayList<String> nbStepRecordsStrs = area.getStats().getNbStepPlayersRecord().entrySet().stream()
                .limit(10)
                .map(e -> {
                    StringBuilder sb = new StringBuilder(Main.getInstance().getServer().getOfflinePlayer(e.getKey()).getName());
                    sb.append(SEPARATOR);
                    sb.append(e.getValue());
                    return sb.toString();
                })
                .collect(Collectors.toCollection(ArrayList::new));
        nbStepRecordsStrs.add(0, Message.NBSTEPS.getMessage());

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i <= 10; ++i) {
            if (i >= durationRecordsStrs.size()) {
                break;
            }
            sb.append(rankStrs.get(i));
            sb.append(TABULATION);
            sb.append(durationRecordsStrs.get(i));
            sb.append(TABULATION);
            sb.append(nbStepRecordsStrs.get(i));
            sb.append(TABULATION);
            sb.append(nbActionRecordsStrs.get(i));
            if (i != 10 && i != durationRecordsStrs.size()) {
                sb.append(NEW_LINE);
            }
        }
        if (i == 0) {
            statsMessages.add(NO_RECORDS_FOR_THIS_AREA);
            return;
        }
        TabText tt = new TabText(sb.toString());
        tt.setPageHeight(11);
        tt.setTabs(new int[]{rankStrsMaxLength, durationRecordsStrsMaxLength, nbActionRecordsStrsMaxLength});
        statsMessages.add(tt.getPage(1, false));
        statsMessages.add(tt.getPage(2, false));
    }

    private void getTopRecords(ArrayList<String> statsMessages) {
        HashMap<UUID, Integer> nbAreaSolvedPerPlayer = new LinkedHashMap<>();
        Areas.getInstance().getAreaSet().forEach(area -> {
            AreaStats areaStats = area.getStats();
            if (areaStats.isLinked()) {
                return; //pass linked areas in order not to count the same puzzle multiple times
            }
            areaStats.getNbStepPlayersRecord().keySet().forEach(playerUUID -> {
                Integer playerNbAreaSolved = nbAreaSolvedPerPlayer.get(playerUUID);
                if (playerNbAreaSolved == null) {
                    playerNbAreaSolved = 0;
                }
                playerNbAreaSolved++;
                nbAreaSolvedPerPlayer.put(playerUUID, playerNbAreaSolved);
            });
        });
        List<Entry<UUID, Integer>> topPlayers = nbAreaSolvedPerPlayer.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry<UUID, Integer>::getValue).reversed())
                .limit(10)
                .collect(Collectors.toList());
        for(int i = 1; i <= topPlayers.size(); ++i) {
            Entry<UUID, Integer> entry = topPlayers.get(i-1);
            statsMessages.add(i + ". " + Main.getInstance().getServer().getOfflinePlayer(entry.getKey()).getName() + " (" + String.valueOf(entry.getValue()) + ")");
        }
    }
}
