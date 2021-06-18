package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameMode implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player target;
            boolean isOtherPlayer = false;
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage(Message.getPlayerPrefixe() + "§cThis player is not connected to the server.");
                    return false;
                }
                target = Bukkit.getPlayer(args[0]);
                isOtherPlayer = true;
            } else {
                target = (Player) sender;
            }
            assert target != null;
            switch (command.getName()) {
                case "gmc":
                    target.setGameMode(org.bukkit.GameMode.CREATIVE);
                    target.sendMessage(Message.getPlayerPrefixe() + "§aYour gamemode has been set to CREATIVE.");
                    if (isOtherPlayer)
                        sender.sendMessage(Message.getPlayerPrefixe() + "§a" + target.getName() + " gamemode set to CREATIVE.");
                    break;
                case "gms":
                    target.setGameMode(org.bukkit.GameMode.SURVIVAL);
                    target.sendMessage(Message.getPlayerPrefixe() + "§aYour gamemode has been set to SURVIVAL.");
                    if (isOtherPlayer)
                        sender.sendMessage(Message.getPlayerPrefixe() + "§a" + target.getName() + " gamemode set to SURVIVAL.");
                    break;
                case "gmsc":
                    target.setGameMode(org.bukkit.GameMode.SPECTATOR);
                    target.sendMessage(Message.getPlayerPrefixe() + "§aYour gamemode has been set to SPECTATOR.");
                    if (isOtherPlayer)
                        sender.sendMessage(Message.getPlayerPrefixe() + "§a" + target.getName() + " gamemode set to SPECTATOR.");
                    break;
                default:
                    return false;
            }

            return true;
        }
        return false;

    }
}
