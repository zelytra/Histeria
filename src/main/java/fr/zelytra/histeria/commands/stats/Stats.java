/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.stats;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.timer.TimeFormater;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class Stats implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            CustomPlayer target;

            if (args.length > 0) {

                if (Bukkit.getPlayer(args[0]) == null) {
                    target = new CustomPlayer(args[0]);

                    if (!target.hasPlayedBefore()) {
                        LangMessage.sendMessage(player, "command.playerNotExist");
                        return false;
                    }

                } else
                    target = CustomPlayer.getCustomPlayer(args[0]);

            } else
                target = CustomPlayer.getCustomPlayer(player.getName());


            double ratio = 0;
            assert target != null;
            if (target.getKill() != 0 && target.getDeath() != 0) {
                ratio = (double) target.getKill() / target.getDeath();

            }
            DecimalFormat dec = new DecimalFormat("#0.00");

            player.sendMessage("§9|----------[§bHisteria Tracker§9]----------|");

            player.sendMessage("§6 Player  : §e" + target.getName());

            if (Utils.canByPass(player)) {
                player.sendMessage("§6 UUID     : §e" + target.getUuid());
                player.sendMessage("§6 IP        : §e" + target.getIp());
            }

            player.sendMessage("§6 First Connection: §e" + target.getPrimConnection());
            player.sendMessage("§6 Last Connection: §e" + TimeFormater.compareToNow(target.getLastConnection()));
            player.sendMessage("");
            player.sendMessage("§6 Played Time: " + TimeFormater.display(target.getTimePlayed()));
            player.sendMessage("§6 Kill       : §e" + target.getKill());
            player.sendMessage("§6 Death   : §e" + target.getDeath());
            player.sendMessage("§6 Ratio    : §e" + dec.format(ratio));
            player.sendMessage("§6 Money   : §e" + target.getBankAccount().getMoney());

            player.sendMessage("§9|-----------------------------------|");

            if (args.length > 0 && Bukkit.getPlayer(args[0]) == null) {
                target.destroy();
            }
            return true;


        }
        return false;
    }

}

