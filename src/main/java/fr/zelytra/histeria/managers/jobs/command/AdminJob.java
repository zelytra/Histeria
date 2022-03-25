/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.command;

import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminJob implements CommandExecutor {

    // /adminjob set Zelytra MINER 42
    // /adminjob info Zelytra


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 3) {

            String targetName = args[0];
            JobType jobType = JobType.valueOf(args[1]);

            if (!Utils.isInteger(args[2])){
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            int level = Integer.parseInt(args[2]);

            if (jobType == null) {
                LangMessage.sendMessage(player, "jobs.notFound");
                return false;
            }


        } else if (args.length == 2) {

        } else {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }


        return true;
    }
}
