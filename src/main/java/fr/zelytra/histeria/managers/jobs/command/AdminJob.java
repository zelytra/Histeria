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
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminJob implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 4 && args[0].equalsIgnoreCase("setLevel")) {

            String targetName = args[1];
            JobType jobType = JobType.getEnum(args[2]);

            if (!Utils.isInteger(args[3])) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            int level = Integer.parseInt(args[3]);

            if (jobType == null) {
                LangMessage.sendMessage(player, "jobs.notFound");
                return false;
            }

            if (Bukkit.getPlayer(targetName) != null) {
                CustomPlayer target = CustomPlayer.getCustomPlayer(targetName);
                target.getJob(jobType).setLevel(level);
                player.sendMessage(Message.PLAYER_PREFIX + "§aPlayer level set to " + level + " " + jobType);
                return true;
            } else {
                LangMessage.sendMessage(player, "command.playerOffLine");
                return false;
            }
            //TODO offline part
            /*else {
                Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
                    MySQL mySQL = Histeria.mySQL;
                    mySQL.update("UPDATE `Jobs` SET `experience` = "+ ExperienceMath.getXpFromLevel(level)+" WHERE ");
                });
            }*/


        } else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {

            String targetName = args[1];

            CustomPlayer target = CustomPlayer.getCustomPlayer(targetName);
            if (target != null) {
                player.sendMessage("§8---------------§6 [ " + targetName + " job's ] §8---------------");
                for (JobType jobType : JobType.values())
                    player.sendMessage("§8● §6" + jobType + " : " + target.getJob(jobType).getLevel() + " | " + target.getJob(jobType).getXP());
                return true;
            } else {
                LangMessage.sendMessage(player, "command.playerOffLine");
                return false;
            }


        } else {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }

    }
}
