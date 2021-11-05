/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.npc.listener;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import fr.zelytra.histeria.managers.market.shop.PlayerShop;
import fr.zelytra.histeria.managers.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NPCListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        for (NPC npc : NPC.npcList) {
            npc.showNPC(e.getPlayer());
        }
    }

    @EventHandler
    public void onNPClick(NPCClickEvent e) {
        switch (e.getNpc().getAction()) {

            case DEFAULT:
                break;
            case SHOP:
                new PlayerShop(e.getPlayer());
                break;
        }
    }

    @EventHandler
    public void onNPCClickEvent(PlayerUseUnknownEntityEvent e) {
        //if(e.getRightClicked() instanceof Player)return;

        for (NPC npc : NPC.npcList) {
            if (e.getEntityId() == npc.getEntityID()) {
                NPCClickEvent npcClickEvent = new NPCClickEvent(e.getPlayer(), npc);
                Bukkit.getPluginManager().callEvent(npcClickEvent);
            }
        }

    }

}
