/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.listener;

import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreLogger implements Listener {

    @EventHandler
    public void onOre(BlockBreakEvent e) {
        switch (e.getBlock().getType()) {
            case DIAMOND_ORE:
                new DiscordLog(WebHookType.ORE, e.getPlayer().getName() + " a miné un minerais de **DIAMAND** ");
                break;
            case CHISELED_NETHER_BRICKS:
                new DiscordLog(WebHookType.ORE, e.getPlayer().getName() + " a miné un minerais d' **HISTERITE** ");
                break;
            case ANCIENT_DEBRIS:
                new DiscordLog(WebHookType.ORE, e.getPlayer().getName() + " a miné un minerais d' **ANCIEN DEBRIS** ");
                break;
        }
    }
}
