/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.managers.visual.chat.EmoteType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EmoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§8---------------§6 [ Emotes ] §8---------------");
            LangMessage.sendMessage(player,"emote.warn");
            for (Emote emote : Emote.values()) {
                if (emote.getType() == EmoteType.EMOTE)
                    player.sendMessage(" " + emote + " §8- §etag:§6 " + emote.getTag());
            }
        }
        return false;
    }
}
