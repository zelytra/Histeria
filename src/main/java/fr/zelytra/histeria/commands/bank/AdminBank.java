package fr.zelytra.histeria.commands.bank;

import fr.zelytra.histeria.managers.economy.Bank;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminBank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length != 3) {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        Bank targetBank;
        CustomPlayer customPlayer = null;

        if (target == null) {

            customPlayer = new CustomPlayer(args[1]);
            targetBank = customPlayer.getBankAccount();

            if (!customPlayer.hasPlayedBefore()) {
                LangMessage.sendMessage(player, "command.playerNotExist");
                return false;
            }

        } else {
            targetBank = CustomPlayer.getCustomPlayer(target.getName()).getBankAccount();
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            LangMessage.sendMessage(player, "command.playerOffLine");
            return false;
        }

        if (args[0].equalsIgnoreCase("set")) {

            targetBank.setMoney(amount);
            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "adminBank.setMoney", "§6" + amount + Emote.GOLD);

        } else if (args[0].equalsIgnoreCase("take")) {

            targetBank.takeMoney(amount);
            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "adminBank.takeMoney", "§6" + amount + Emote.GOLD);

        } else if (args[0].equalsIgnoreCase("give")) {

            targetBank.addMoney(amount);
            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "adminBank.giveMoney", "§6" + amount + Emote.GOLD);

        } else
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");

        if (customPlayer != null && target == null) {
            customPlayer.saveData();
            customPlayer.destroy();
        }

        return true;

    }

}
