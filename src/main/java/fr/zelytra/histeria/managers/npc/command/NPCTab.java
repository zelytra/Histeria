package fr.zelytra.histeria.managers.npc.command;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.npc.NPCAction;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NPCTab implements TabCompleter {

    /*
            /npc create {name}
            /npc modify {name} {parameter} {value} //TODO
            /npc action {name} {SHOP|HMARKET|SERVER|TELEPORT} [serverName|x] [y] [z]
            /npc delete {name}
            /npc move {name}
            /npc skin {name} {url}
            /npc look //TODO

    */

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {


        List<String> commandsList = new ArrayList<>();
        if (args.length == 1) {
            commandsList.add("create");
            commandsList.add("modify");
            commandsList.add("action");
            commandsList.add("delete");
            commandsList.add("move");
            commandsList.add("skin");
            commandsList.add("look");

            commandsList = Utils.dynamicTab(commandsList, args[0]);
        } else if (args.length == 2) {

            for (NPC npc : NPC.npcList)
                commandsList.add(npc.getName());

            commandsList = Utils.dynamicTab(commandsList, args[1]);

        } else if (args.length >= 3) {
            if (args[0].equalsIgnoreCase("skin")) {
                commandsList.add("<skin url>");
                commandsList = Utils.dynamicTab(commandsList, args[2]);

            } else if (args[0].equalsIgnoreCase("action")) {

                if (args.length == 3) {

                    for (NPCAction action : NPCAction.values())
                        commandsList.add(action.name().toLowerCase());

                    commandsList = Utils.dynamicTab(commandsList, args[2]);

                } else if (args.length == 4 && args[2].equalsIgnoreCase("server")) {

                    for (String server : Histeria.server.getServersList())
                        commandsList.add(server);
                    commandsList = Utils.dynamicTab(commandsList, args[3]);
                }

            } else if (args[0].equalsIgnoreCase("modify")) {

            }
        }

        return commandsList;
    }

}
