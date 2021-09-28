/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Bug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            TextComponent processMessage = Component.text()
                    .content(Message.PLAYER_PREFIX.getMsg()+"§6If you find a bug please contact us on Discord by creating a ticket. §e[CLICK THE MESSAGE]")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://discord.gg/9AQKPVB87r"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,Component.text().content("Click here to access to the support channel").build()))
                    .build();
            sender.sendMessage(processMessage);
            return true;
        }
        return false;
    }
}
