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

import java.util.HashSet;

public class Vanish implements CommandExecutor {

    private static HashSet<String> vanishedPlayers = new HashSet<>();

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
                LangMessage.sendMessage(player, "vanish.unvanish");
                return true;
            }
        }

        return false;
    }

    public static void unvanish(Player player) {
        vanishedPlayers.remove(player.getName());
        Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!Utils.canByPass(p))
                    p.showPlayer(Histeria.getInstance(), player);
            }
        });
    }

    public static void vanish(Player player) {
        vanishedPlayers.add(player.getName());
        Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!Utils.canByPass(p))
                    p.hidePlayer(Histeria.getInstance(), player);
            }
        });
    }

    public static boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getName());
    }

}
