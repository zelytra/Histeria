/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.kickBan;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HKick implements CommandExecutor {
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
        CustomPlayer customTarget = CustomPlayer.getCustomPlayer(target.getName());

        StringBuilder reason = new StringBuilder();
        for (int x = 1; x < args.length; x++) {
            reason.append(args[x]+" ");
        }

        target.kick(Component.text().content(Message.HISTBAN.getMsg()
                + "§c" + target.getName()
                + customTarget.getLang().get("command.kickTarget")
                + "§6 " + reason).build());

        return true;
    }
}
