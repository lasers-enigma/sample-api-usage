package eu.lasersenigma.apiusage.lasers_give_command;

import eu.lasersenigma.components.attributes.LasersColor;
import eu.lasersenigma.items.Item;
import eu.lasersenigma.items.ItemsFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LasersGiveCommandExecutor implements CommandExecutor {

    public static final String PLAYER_NOT_FOUND = "The player '%s' could not be found.";

    public static final String COLOR_NOT_FOUND = "Color not found. Supported colors are: %s.";

    public static final String ARMOR_PARAMETER = "armor";

    public static final String MIRROR_PARAMETER = "mirror";

    public static final String NO_BURN_PARAMETER = "noBurn";

    public static final String NO_DAMAGE_PARAMETER = "noDamage";

    public static final String NO_KNOCKBACK_PARAMETER = "noKnockback";

    public static final String REFLECTION_PARAMETER = "reflection";

    public static final String FOCUS_PARAMETER = "focus";

    public static final String PRISM_PARAMETER = "prism";

    public static final String EQUIP_PARAMETER = "equip";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> argsToProcess = new ArrayList<>(Arrays.asList(args));
        //"lestats" is the only command accepted
        if (!cmd.getName().equalsIgnoreCase("legive")) {
            return false;
        }

        //At least 2 arguments check
        if (argsToProcess.size() < 2) {
            return false;
        }

        //Defines the player that should get the item
        String playerName = argsToProcess.remove(0);
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(String.format(PLAYER_NOT_FOUND, playerName));
            return false;
        }

        //Defines if we want to create a mirror or an armor        
        String mirrorOrArmorString = argsToProcess.remove(0);
        switch (mirrorOrArmorString) {
            case ARMOR_PARAMETER:
                //legive <player> armor [noBurn] [noDamage] [noKnockback] [reflection] [focus] [prism] [<durability number from 1 to 9>] [equip]
                //Defines the other parameters
                Boolean noBurn = false;
                Boolean noDamage = false;
                Boolean noKnockback = false;
                Boolean reflection = false;
                Boolean focus = false;
                Boolean prism = false;
                Boolean equip = false;
                Integer durability = 0;
                while (argsToProcess.size() > 0) {
                    String argument = argsToProcess.remove(0);
                    switch (argument) {
                        case NO_BURN_PARAMETER:
                            noBurn = true;
                            break;
                        case NO_DAMAGE_PARAMETER:
                            noDamage = true;
                            break;
                        case NO_KNOCKBACK_PARAMETER:
                            noKnockback = true;
                            break;
                        case REFLECTION_PARAMETER:
                            reflection = true;
                            break;
                        case FOCUS_PARAMETER:
                            focus = true;
                            break;
                        case PRISM_PARAMETER:
                            prism = true;
                            break;
                        case EQUIP_PARAMETER:
                            equip = true;
                            break;
                        default:
                            Pattern pattern = Pattern.compile("^[0-9]$"); // Using regex to check that the argument is a single number.
                            if (!pattern.matcher(argument).find()) {
                                return false;
                            }
                            ;
                            durability = Integer.parseInt(argument);
                    }
                }
                giveArmor(player, noBurn, noDamage, noKnockback, reflection, focus, prism, durability, equip);
                break;
            case MIRROR_PARAMETER:
                //legive <player> mirror [color]
                //Defines the mirror color
                LasersColor mirrorColor;
                if (argsToProcess.isEmpty()) {
                    mirrorColor = LasersColor.WHITE;
                } else {
                    String mirrorColorStr = argsToProcess.remove(0);
                    mirrorColor = LasersColor.valueOf(mirrorColorStr.toUpperCase());
                    if (mirrorColor == null) {
                        List<String> colorsNameList = Arrays.asList(LasersColor.values()).stream()
                                .map(color -> color.name().toLowerCase())
                                .collect(Collectors.toList());
                        String colors = String.join(", ", colorsNameList);
                        sender.sendMessage(String.format(COLOR_NOT_FOUND, colors));
                        return false;
                    }
                }
                if (argsToProcess.size() > 0) {
                    return false;
                }
                giveMirror(player, mirrorColor);
                break;
            default:
                return false;
        }
        return true;
    }

    private void giveMirror(Player player, LasersColor mirrorColor) {
        //Retrieves a mirror item from its color.
        Item coloredMirrorItem = Item.getMirror(mirrorColor);
        //Gets an itemStack from it
        ItemStack mirrorItemStack = ItemsFactory.getInstance().getItemStack(coloredMirrorItem);
        //Gives it to the player
        player.getInventory().addItem(mirrorItemStack);
    }

    private void giveArmor(Player player, Boolean noBurn, Boolean noDamage, Boolean noKnockback, Boolean reflection, Boolean focus, Boolean prism, Integer durability, Boolean equip) {
        //Retrieves a mirror item from its color.
        ItemStack armorItemStack = ItemsFactory.getInstance().getArmorItemStack(noBurn, noDamage, noKnockback, reflection, focus, prism, durability);
        //Gives the armor to the player
        if (equip) {
            player.getInventory().setChestplate(armorItemStack);
        } else {
            player.getInventory().addItem(armorItemStack);
        }

    }
}
