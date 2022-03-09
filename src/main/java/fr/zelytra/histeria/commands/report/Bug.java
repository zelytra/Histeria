/*
 * Copyright (c) 2021-2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.report;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Bug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length < 1) {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < args.length; x++)
            stringBuilder.append(args[x]).append(" ");

        new DiscordLog(WebHookType.ERROR, "**[:exclamation:] " + player.getName() + "** have reported a bug: " + stringBuilder);
        LangMessage.sendMessage(player, "bug.report");

        return true;

    }
}
