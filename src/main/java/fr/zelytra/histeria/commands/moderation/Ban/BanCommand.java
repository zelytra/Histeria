/*
 * Copyright (c) 2021-2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Ban;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.TimeString;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BanCommand implements CommandExecutor {

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

        int time = TimeString.getTime(args[1]);

        StringBuilder reason = new StringBuilder();

        for (int x = 2; x < args.length; x++) {
            reason.append(args[x] + " ");
        }

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(target.getName());
        customPlayer.ban(time, reason.toString(), (Player) sender);

        LangMessage.broadcast(Message.HISTBAN.getMsg() + "§c" + target.getName() + " ", "command.ban", "§c " + args[1] + " : " + reason);

        target.kick(Component.text().content(Message.HISTBAN.getMsg()
                + "§c" + customPlayer.getLang().get("command.playerBan")
                + "§6" + reason
                + "§c " + customPlayer.getLang().get("command.playerBanTime") + "§6" + args[1]).build());
        new DiscordLog(WebHookType.BAN, target.getName() + " has been banned for : " + reason + " during : " + args[1] + " by " + sender.getName());
        return true;
    }
}
