package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                CustomPlayer customTarget = new CustomPlayer(args[0]);
                if (!customTarget.hasPlayedBefore()) {
                    LangMessage.sendMessage(player, "command.playerNotExist");
                    return;
                } else {
                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> teleportPlayer(customTarget, player, args[1]));
                    return;
                }

            });

            return true;

        } else {

            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(target.getName());
            teleportPlayer(customPlayer, player, args[1]);

            return true;

        }

    }

    private void teleportPlayer(CustomPlayer target, Player executor, String homeName) {

        for (Home home : target.getHomes()) {
            if (home.getName().equalsIgnoreCase(homeName)) {
                home.teleport(executor);
                return;
            }
        }

        LangMessage.sendMessage(executor, "home.notFound");
    }

}
