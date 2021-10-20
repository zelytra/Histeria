package fr.zelytra.histeria.commands.tpa;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Tpa implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("tpa")) {

            if (args.length != 1) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                LangMessage.sendMessage((Player) sender, "command.playerOffLine");
                return false;
            }

            new TpaCanal(player, target);

            return true;


        } else if (command.getName().equalsIgnoreCase("tpaccept")) {
            TpaCanal tpaCanal = TpaCanal.getCanalForRequester(player);

            if (tpaCanal == null) {
                LangMessage.sendMessage(player, "tpa.noWaitingRequest");
                return true;
            }
            tpaCanal.accept();

        } else if (command.getName().equalsIgnoreCase("tpdeny")) {
            TpaCanal tpaCanal = TpaCanal.getCanalForRequester(player);

            if (tpaCanal == null) {
                LangMessage.sendMessage(player, "tpa.noWaitingRequest");
                return true;
            }
            tpaCanal.deny();
        }

        return false;
    }
}
