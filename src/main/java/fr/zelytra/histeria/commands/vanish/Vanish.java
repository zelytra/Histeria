package fr.zelytra.histeria.commands.vanish;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Vanish implements CommandExecutor {

    public static List<String> vanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("vanish")) {
            if (vanishedPlayers.contains(player.getName())) {
                LangMessage.sendMessage(player, "vanish.alreadyVanished");
                return false;
            } else {
                vanish(player);
                LangMessage.sendMessage(player, "vanish.vanished");
                return true;
            }

        } else if (command.getName().equalsIgnoreCase("unvanish")) {
            if (!vanishedPlayers.contains(player.getName())) {
                LangMessage.sendMessage(player, "vanish.notVanished");
                return false;
            } else {
                unvanish(player);
                LangMessage.sendMessage(player, "vanish.unvanished");
                return true;
            }
        }

        return false;
    }

    private void unvanish(Player player) {
        vanishedPlayers.remove(player.getName());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!Utils.canByPass(p))
                p.showPlayer(Histeria.getInstance(), player);
        }
    }

    private void vanish(Player player) {
        vanishedPlayers.add(player.getName());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!Utils.canByPass(p))
                p.hidePlayer(Histeria.getInstance(), player);
        }
    }

}
