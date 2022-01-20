/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Mute;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.timer.TimeString;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length < 2) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            LangMessage.sendMessage((Player) sender, "command.playerOffLine");
            return false;
        }

        int time = 0;

        try {
            time = TimeString.getTime(args[1]);
        } catch (Exception e) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        StringBuilder reason = new StringBuilder();

        for (int x = 2; x < args.length; x++) {
            reason.append(args[x] + " ");
        }

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(target.getName());
        customPlayer.mute(time, reason.toString(), (Player) sender);
        customPlayer.saveData();

        LangMessage.broadcast(Message.HISTBAN.getMsg() + "§c" + target.getName()+" ", "command.mute", "§c " + args[1] + " : " + reason);

        return true;
    }
}
