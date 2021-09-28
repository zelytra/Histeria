/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.hguard;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.hguard.Shape;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Utils;
import net.luckperms.api.model.group.Group;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HGuardTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {

            commandsList.add("info");
            commandsList.add("create");
            commandsList.add("modify");
            commandsList.add("remove");
            commandsList.add("help");

            commandsList = Utils.dynamicTab(commandsList, args[0]);

        } else if (args.length == 2 && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("modify") | args[0].equalsIgnoreCase("remove"))) {

            for (HGuard hguard : HGuard.getList()) {

                commandsList.add(hguard.getName());

            }
            commandsList = Utils.dynamicTab(commandsList, args[1]);

        } else if (args.length == 3 && args[0].equalsIgnoreCase("create")) {

            for (Shape shape : Shape.values()) {

                commandsList.add(shape.name());

            }
        } else if (args.length > 2 && args[0].equalsIgnoreCase("modify")) {

            if (args.length == 3) {

                commandsList.add("pvp");
                commandsList.add("breakBlock");
                commandsList.add("placeBlock");

                commandsList.add("priorityLevel");

                commandsList.add("group");
                commandsList.add("interact");
                commandsList.add("customItem");
                commandsList = Utils.dynamicTab(commandsList, args[2]);

            } else if (args.length == 4) {
                if (args[2].equalsIgnoreCase("pvp") || args[2].equalsIgnoreCase("breakBlock") || args[2].equalsIgnoreCase("placeBlock")) {

                    commandsList.add("TRUE");
                    commandsList.add("FALSE");
                    commandsList = Utils.dynamicTab(commandsList, args[3]);

                }

                if (args[2].equalsIgnoreCase("group") || args[2].equalsIgnoreCase("customItem") || args[2].equalsIgnoreCase("interact")) {
                    commandsList.add("add");
                    commandsList.add("remove");
                    commandsList = Utils.dynamicTab(commandsList, args[3]);
                }
            }

            if (args.length == 5) {
                if (args[2].equalsIgnoreCase("group") || args[2].equalsIgnoreCase("customItem") || args[2].equalsIgnoreCase("interact")) {
                    if (args[3].equalsIgnoreCase("add")) {
                        if (args[2].equalsIgnoreCase("interact")) {

                            for (Material material : Material.values())
                                commandsList.add(material.name());

                        } else if (args[2].equalsIgnoreCase("customItem")) {

                            for (CustomMaterial material : CustomMaterial.values())
                                commandsList.add(material.getName());

                        } else if (args[2].equalsIgnoreCase("group")) {
                            for (Group group : Histeria.getLuckPerms().getGroupManager().getLoadedGroups())
                                commandsList.add(group.getName());
                        }
                    }
                }
                if (args[3].equalsIgnoreCase("remove")) {
                    HGuard hguard = HGuard.getByName(args[1]);

                    if (hguard == null)
                        return commandsList;

                    if (args[2].equalsIgnoreCase("interact")) {

                        for (Material material : hguard.getInteractWhiteList())
                            commandsList.add(material.name());

                    } else if (args[2].equalsIgnoreCase("customItem")) {

                        for (CustomMaterial material : hguard.getCustomItemWhiteList())
                            commandsList.add(material.name());

                    } else if (args[2].equalsIgnoreCase("group")) {
                        commandsList.addAll(hguard.getGroupWhiteList());
                    }
                }
                commandsList = Utils.dynamicTab(commandsList, args[4]);
            }
        }


        return commandsList;
    }
}
