package fr.zelytra.histeria.commands.bank;

import fr.zelytra.histeria.builder.commandsHandler.HelpCommands;
import fr.zelytra.histeria.managers.player.CustomPlayer;
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
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cPlease enter a number");
                    return false;
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cThis player is not connected on the server");
                    return false;
                }

                int amount = Integer.parseInt(args[2]);

                if (amount < 1) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou cannot send less than 1$");
                    return false;
                }

                assert customPlayer != null;
                if (customPlayer.getBankAccount().hasEnoughFor(amount)) {

                    customPlayer.getBankAccount().takeMoney(amount);
                    Objects.requireNonNull(CustomPlayer.getCustomPlayer(args[1])).getBankAccount().addMoney(amount);

                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou sent §6" + amount + "$§a to " + args[1]);
                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou received §6" + amount + " §afrom " + player.getName());

                } else {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou don't have enough money");
                    return false;
                }

            } else if (args.length == 0) {

                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
                assert customPlayer != null;
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou currently have §6" + customPlayer.getBankAccount().getMoney() + "$§a in your bank account.");

            } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {

                HelpCommands help = new HelpCommands(command.getName());
                help.addCommand("[send]", "[player]","[amount]");
                help.printPlayer(player);

            } else {
                player.sendMessage(Message.getHelp(command.getName()));
                return false;
            }
        }

        return false;
    }
}
