/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.visual.chat.Emote;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Social implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            sender.sendMessage("§8---------------§6 [ Social ] §8---------------");
            sender.sendMessage("§§7You can click on the messages to access the redirectory");
            TextComponent processMessage = Component.text()
                    .content(" "+ Emote.WWW +"§e - §6Website")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://histeria.fr"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,Component.text().content("§6Click here to access to the website").build()))
                    .build();
            sender.sendMessage(processMessage);
            processMessage = Component.text()
                    .content(" "+ Emote.DISCORD +"§e - §6Discord")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"http://histeria.zelytra.fr"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,Component.text().content("§6Click here to access to the Discord server").build()))
                    .build();
            sender.sendMessage(processMessage);
            processMessage = Component.text()
                    .content(" "+ Emote.SHOP +"§e - §6Shop")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://shop.histeria.fr"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,Component.text().content("§6Click here to access to the shop").build()))
                    .build();
            sender.sendMessage(processMessage);
            return true;
        }
        return false;
    }
}
