package fr.zelytra.histeria.commands.bank;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BankCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 3 && args[0].equalsIgnoreCase("send")) {
                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());

                if (!Utils.isNumeric(args[2])) {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cPlease enter a number");
                    return false;
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cThis player is not connected on the server");
                    return false;
                }

                int amount = Integer.parseInt(args[2]);

                if (amount < 1) {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cYou cannot send less than 1$");
                    return false;
                }

                if (customPlayer.getBankAccount().hasEnoughFor(amount)) {

                    customPlayer.getBankAccount().takeMoney(amount);
                    CustomPlayer.getCustomPlayer(args[1]).getBankAccount().addMoney(amount);

                    player.sendMessage(Message.getPlayerPrefixe() + "§aYou sent §6" + amount + "$§a to " + args[1]);
                    Bukkit.getPlayer(args[1]).sendMessage(Message.getPlayerPrefixe() + "§aYou received §6" + amount + " §afrom " + player.getName());

                } else {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cYou don't have enough money");
                    return false;
                }

            } else {
                player.sendMessage(Message.getHelp(command.getName()));
                return false;
            }
        }


        return false;
    }
}
