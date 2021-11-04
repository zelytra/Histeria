/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.npc;

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

}
