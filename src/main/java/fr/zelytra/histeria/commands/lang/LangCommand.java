package fr.zelytra.histeria.commands.lang;

import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LangCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length != 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Lang lang = null;
        try {
            lang = Lang.valueOf(args[0]);
        } catch (Exception ignored) {
            LangMessage.sendMessage((Player) sender, "command.langNotExist");
            return false;
        }

        CustomPlayer.getCustomPlayer(sender.getName()).setLang(lang);
        LangMessage.sendMessage((Player) sender, "", "command.changeLang", lang.name());
        return true;

    }
}
