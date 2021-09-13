package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Heal implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                if (!Cooldown.cooldownCheck(player, "healCommand")) {
                    return false;
                }

                if (!Utils.canByPass(player))
                    new Cooldown(player, 300, "healCommand");

                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou have been healed.");
                return true;
            } else {
                if (Bukkit.getPlayer(args[0]) != null && Utils.canByPass(player)) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou heal " + target.getName() + ".");
                    target.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou have been healed.");
                    return true;
                } else if (!Utils.canByPass(player)) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou cannot heal another player.");
                    return false;
                } else {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou don't have permission to perform this command.");
                    return false;
                }
            }
            //TODO Prendre en compte le statut de pvp dans l'exec de la commande
        }
        return false;
    }
}
