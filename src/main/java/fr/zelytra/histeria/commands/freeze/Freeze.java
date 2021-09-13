package fr.zelytra.histeria.commands.freeze;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Freeze implements CommandExecutor {
    private static final List<String> frozenPlayer = new ArrayList();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length != 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            LangMessage.sendMessage((Player) sender, "command.playerOffLine");
            return false;
        }

        switch (command.getName()) {
            case "freeze":
                if (isFrozen(target)) {
                    LangMessage.sendMessage((Player) sender, "command.alreadyFreeze");
                    return false;
                }
                frozenPlayer.add(target.getName());
                LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§a" + target.getName() + " ", "command.freezePlayer", "");
                break;

            case "unfreeze":
                if (!isFrozen(target)) {
                    LangMessage.sendMessage((Player) sender, "command.notFrozen");
                    return false;
                }
                frozenPlayer.remove(target.getName());
                LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§a" + target.getName() + " ", "command.unfreezePlayer", "");
                LangMessage.sendMessage(target, "command.unfreezePlayerByAdmin");

                break;
            default:
                break;
        }

        return true;


    }

    public static boolean isFrozen(Player player) {
        return frozenPlayer.contains(player.getName());
    }


}
