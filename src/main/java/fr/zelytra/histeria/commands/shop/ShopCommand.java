package fr.zelytra.histeria.commands.shop;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.market.shop.PlayerShop;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if ((sender.isOp() || Utils.canByPass((Player) sender))&& args.length != 0 && args[0].equalsIgnoreCase("reload")) {
            Histeria.shop.reload();
            sender.sendMessage(Message.PLAYER_PREFIX+"§aShop reloaded");
        }

        new PlayerShop((Player) sender);

        return true;
    }
}
