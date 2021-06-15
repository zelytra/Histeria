package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Speed implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        float speed;
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0 || !Utils.isNumeric(args[0])) {
                player.sendMessage(Message.getPlayerPrefixe() + "§cWrong argument please enter a number.");
                return false;
            } else if (Float.parseFloat(args[0]) > 0 && Float.parseFloat(args[0]) <= 10) {
                speed = (Float.parseFloat(args[0])) / 10;
                player.setFlySpeed(speed);
                player.sendMessage(Message.getPlayerPrefixe() + "§6Fly speed set to §a" + Float.valueOf(args[0]) + "§6 !");
                return true;
            } else {
                player.sendMessage(Message.getPlayerPrefixe() + "§cPlease enter a number between 1 and 10.");
                return false;
            }

        }
        return false;
    }

}