package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Fly implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {

                if (player.getAllowFlight()) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.sendMessage(Message.getPlayerPrefixe() + "§aFly disabled.");
                    return true;
                }

                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(Message.getPlayerPrefixe() + "§aFly enabled.");
                return true;
            }

        }
        return false;
    }

}
