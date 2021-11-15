package fr.zelytra.histeria.managers.hologram;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HoloCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length != 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }
        /**
         * /holo create [name] {text...}
         * /holo add [name] {text...}
         * /hol
         */
        return false;
    }
}
