/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.bank;

import fr.zelytra.histeria.builder.commandsHandler.HelpCommands;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BankCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 3 && args[0].equalsIgnoreCase("send")) {
                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());

                if (!Utils.isNumeric(args[2])) {
                    LangMessage.sendMessage(player, "bank.wrongNumberFormat");
                    return false;
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    LangMessage.sendMessage(player, "command.playerOffLine");
                    return false;
                }

                int amount = Integer.parseInt(args[2]);

                if (amount < 1) {
                    LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "bank.notEnough", Emote.GOLD.toString());
                    return false;
                }

                assert customPlayer != null;
                if (customPlayer.getBankAccount().hasEnoughFor(amount)) {

                    customPlayer.getBankAccount().takeMoney(amount);
                    Objects.requireNonNull(CustomPlayer.getCustomPlayer(args[1])).getBankAccount().addMoney(amount);


                    LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "bank.sendMoney", "§6" + amount + Emote.GOLD + " §e-> §6" + args[1]);
                    LangMessage.sendMessage(Bukkit.getPlayer(args[1]), Message.PLAYER_PREFIX + "§6" + player.getName(), "bank.receiveMoney", "§6" + amount + Emote.GOLD);

                } else {
                    LangMessage.sendMessage(player, "bank.notEnoughMoney");
                    return false;
                }

            } else if (args.length == 0) {

                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
                assert customPlayer != null;
                LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "bank.yourAccount", "§6" + customPlayer.getBankAccount().getMoney() + Emote.GOLD);

            } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {

                HelpCommands help = new HelpCommands(command.getName());
                help.addCommand("[send]", "[player]", "[amount]");
                help.printPlayer(player);

            } else {
                player.sendMessage(Message.getHelp(command.getName()));
                return false;
            }
        }

        return false;
    }
}
