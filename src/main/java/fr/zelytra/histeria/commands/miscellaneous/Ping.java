package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ping implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            CraftPlayer player = (CraftPlayer) sender;
            player.sendMessage(Message.getPlayerPrefixe() + "§aPong ! §6[" + player.getHandle().ping + "ms]");
            return true;

        }
        return false;
    }

}
