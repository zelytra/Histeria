/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.hguard;

import com.sk89q.worldedit.regions.Region;
import fr.zelytra.histeria.builder.commandsHandler.HelpCommands;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.hguard.Shape;
import fr.zelytra.histeria.managers.hguard.WorldEditHandler;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
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
                    LangMessage.sendMessage(player,"hguard.create");
                    return true;

                } else {
                    LangMessage.sendMessage(player,"hguard.selection");
                    return false;
                }

            } else if (args.length == 4 && args[2].equalsIgnoreCase("CYLINDER")) {

                new HGuard(args[1]
                        , player.getLocation()
                        , Shape.CYLINDER
                        , player.getWorld()
                        , Integer.parseInt(args[3]));
                LangMessage.sendMessage(player,"hguard.create");
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
                LangMessage.sendMessage(player,"hguard.notExist");
                return false;
            }

            if (args.length == 4) {

                if (args[2].equalsIgnoreCase("pvp")) {

                    if (args[3].equalsIgnoreCase("true")) {
                        hGuard.setPvp(true);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aPVP parameter set to TRUE");
                        return true;
                    } else if (args[3].equalsIgnoreCase("false")) {
                        hGuard.setPvp(false);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aPVP parameter set to §cFALSE");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }

                } else if (args[2].equalsIgnoreCase("priorityLevel")) {
                    if (Utils.isNumeric(args[3])) {
                        int value = Integer.parseInt(args[3]);
                        if (value < 0) {
                            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cPlease enter a positive value");
                            return false;
                        }
                        hGuard.setPriorityLevel(value);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aPriorityLevel parameter set to " + value);
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cPlease enter a number");
                        return false;
                    }


                } else if (args[2].equalsIgnoreCase("breakBlock")) {
                    if (args[3].equalsIgnoreCase("true")) {
                        hGuard.setBreakBlock(true);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aBreakBlock parameter set to TRUE");
                        return true;
                    } else if (args[3].equalsIgnoreCase("false")) {
                        hGuard.setBreakBlock(false);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aBreakBlock parameter set to §cFALSE");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }

                } else if (args[2].equalsIgnoreCase("placeBlock")) {
                    if (args[3].equalsIgnoreCase("true")) {
                        hGuard.setPlaceBlock(true);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aPlaceBlock parameter set to TRUE");
                        return true;
                    } else if (args[3].equalsIgnoreCase("false")) {
                        hGuard.setPlaceBlock(false);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aPlaceBlock parameter set to §cFALSE");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }

                } else {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                    return false;
                }
            } else if (args.length == 5) {
                if (args[2].equalsIgnoreCase("group")) {
                    if (args[3].equalsIgnoreCase("add")) {
                        hGuard.addGroupe(args[4]);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " group added to the  whitelist ");
                        return true;
                    } else if (args[2].equalsIgnoreCase("remove")) {
                        hGuard.removeGroupe(args[4]);
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " group removed from the  whitelist ");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }
                } else if (args[2].equalsIgnoreCase("customItem")) {
                    if (args[3].equalsIgnoreCase("add")) {
                        hGuard.addCustomItem(CustomMaterial.getByName(args[4]));
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " custom item added to the whitelist ");
                        return true;
                    } else if (args[3].equalsIgnoreCase("remove")) {
                        hGuard.removeCustomItem(CustomMaterial.getByName(args[4]));
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " custom item removed from the  whitelist ");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }

                } else if (args[2].equalsIgnoreCase("interact")) {
                    if (args[3].equalsIgnoreCase("add")) {
                        hGuard.addInteract(Material.getMaterial(args[4]));
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " material added to the whitelist ");
                        return true;
                    } else if (args[3].equalsIgnoreCase("remove")) {
                        hGuard.removeInteract(Material.getMaterial(args[4]));
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[4] + " material removed from the  whitelist ");
                        return true;
                    } else {
                        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cWrong parameter");
                        return false;
                    }
                }
            }
            player.sendMessage(Message.getHelp(cmd.getName()));
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {

            if (HGuard.getByName(args[1]) != null) {
                HGuard.getByName(args[1]).delete();
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§a" + args[1] + " has been deleted");
            } else
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cThis area doesn't exist");

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
