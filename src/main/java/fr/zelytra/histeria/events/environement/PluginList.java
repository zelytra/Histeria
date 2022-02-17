/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.managers.languages.LangMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PluginList implements Listener {

    @EventHandler
    public void onPluginList(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().contains("/plugins") || e.getMessage().contains("/pl")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§8---------------§6 [ Histeria ] §8---------------");
            LangMessage.sendMessage(e.getPlayer(), "", "command.pluginList1", "");
            e.getPlayer().sendMessage("§9● §bLuckPerms" + "\n" + "§9● §bWorldEdit" + "\n" + "§9● §bSaberFaction");
            LangMessage.sendMessage(e.getPlayer(), "", "command.pluginList2", "");
            TextComponent processMessage = Component.text()
                    .content("§bGitHub.com")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zelytra/Histeria"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text().content("§cOpen source don't mean not licensed =)").build()))
                    .build();
            e.getPlayer().sendMessage(processMessage);
        }
    }
}
