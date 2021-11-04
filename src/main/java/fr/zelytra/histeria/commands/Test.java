/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands;

import fr.zelytra.histeria.managers.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;


        if (args.length > 1){
            player.sendMessage("destroying");

            for(NPC pc : NPC.npcList){
                pc.destroy();
            }

        }else {
            NPC npc = new NPC(player.getLocation(), "§6Oskour");
            npc.showNPC(player);
        }

            return true;

    }
}