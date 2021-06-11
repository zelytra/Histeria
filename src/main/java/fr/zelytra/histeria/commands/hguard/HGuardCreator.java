package fr.zelytra.histeria.commands.hguard;

import com.sk89q.worldedit.regions.Region;
import fr.zelytra.histeria.builder.commandsHandler.HelpCommands;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.hguard.Shape;
import fr.zelytra.histeria.managers.hguard.WorldEditHandler;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class HGuardCreator implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("create")) {

            if (args.length == 3 && args[2].equalsIgnoreCase("CUBE")) {

                WorldEditHandler WEH = new WorldEditHandler(args[1], player);
                Region selection = WEH.getSelection();

                if (selection != null) {
                    new HGuard(args[1]
                            , new BoundingBox(selection.getMinimumPoint().getBlockX(), selection.getMinimumPoint().getBlockY(), selection.getMinimumPoint().getBlockZ(), selection.getMaximumPoint().getBlockX(), selection.getMaximumPoint().getBlockY(), selection.getMaximumPoint().getBlockZ())
                            , Shape.CUBE
                            , player.getWorld());
                    player.sendMessage(Message.getPlayerPrefixe() + "§a" + args[1] + " has been created");
                    return true;

                } else {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cPlease make a selection");
                    return false;
                }

            } else if (args.length == 4 && args[2].equalsIgnoreCase("CYLINDER")) {

                new HGuard(args[1]
                        , player.getLocation()
                        , Shape.CYLINDER
                        , player.getWorld()
                        , Integer.parseInt(args[3]));
                player.sendMessage(Message.getPlayerPrefixe() + "§a" + args[1] + " has been created");
                return true;

            } else {
                player.sendMessage(Message.getHelp(cmd.getName()));
                return false;
            }

        } else if (args.length > 0 && args[0].equalsIgnoreCase("modify")) {
            //modify test pvp TRUE
            //modify test customItem add CUSTOM_MATERIAL
            //modify test customItem remove CUSTOM_MATERIAL
            HGuard hGuard;
            if (HGuard.getByName(args[1]) != null) {
                hGuard = HGuard.getByName(args[1]);
            } else {
                player.sendMessage(Message.getPlayerPrefixe() + "§cThis area doesn't exist");
                return false;
            }

            if (args.length == 3) {

                if (args[1].equalsIgnoreCase("pvp")) {

                    if (args[2].equalsIgnoreCase("true")) {
                        hGuard.setPvp(true);
                        player.sendMessage(Message.getPlayerPrefixe() + "§aPVP parameter set to TRUE");
                    } else if (args[2].equalsIgnoreCase("false")) {
                        hGuard.setPvp(false);
                        player.sendMessage(Message.getPlayerPrefixe() + "§aPVP parameter set to §cFALSE");
                    } else {
                        player.sendMessage(Message.getPlayerPrefixe() + "§cWrong parameter");
                        return false;
                    }

                } else if (args[1].equalsIgnoreCase("priorityLevel")) {

                } else if (args[1].equalsIgnoreCase("breakBlock")) {

                } else if (args[1].equalsIgnoreCase("placeBlock")) {

                } else {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cWrong parameter");
                    return false;
                }
            } else if (args.length == 4) {
                if (args[1].equalsIgnoreCase("group")) {

                } else if (args[1].equalsIgnoreCase("customItem")) {

                } else if (args[1].equalsIgnoreCase("interact")) {

                }
            }
            player.sendMessage(Message.getHelp(cmd.getName()));
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {

            if (HGuard.getByName(args[1]) != null) {
                HGuard.getByName(args[1]).delete();
                player.sendMessage(Message.getPlayerPrefixe() + "§a" + args[1] + " has been deleted");
            } else
                player.sendMessage(Message.getPlayerPrefixe() + "§cThis area doesn't exist");

            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {

            player.sendMessage(HGuard.getByName(args[1]).toString());
            return true;

        } else if (args.length > 0 && args[0].equalsIgnoreCase("help")) {

            HelpCommands help = new HelpCommands(cmd.getName());
            help.addCommand("create", "[name]", "[shape]", "{radius}");
            help.addCommand("remove", "[name]");
            help.addCommand("info", "[name]");
            help.addCommand("modify", "[name]", "[parameter]", "{parameter operator}", "[value]");
            help.printPlayer(player);
            return true;

        } else {
            player.sendMessage(Message.getHelp(cmd.getName()));
            return false;
        }

    }
}
