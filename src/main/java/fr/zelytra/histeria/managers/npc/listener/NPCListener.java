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
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.shop.PlayerShop;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class NPCListener implements Listener {
    private static List<Player> npcClickList = new ArrayList<>();

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
            case TELEPORT:
                e.getPlayer().teleport(e.getNpc().getTeleportLocation());
                LangMessage.sendMessage(e.getPlayer(),"npc.teleportAction");
                break;
            case SERVER:
                new SwitchServer(e.getPlayer()).switchTo(e.getNpc().getServer());
                LangMessage.sendMessage(e.getPlayer(),"npc.switchServer");
                break;
        }
    }

    @EventHandler
    public void onNPCClickEvent(PlayerUseUnknownEntityEvent e) {
        if (!Cooldown.cooldownCheck(e.getPlayer(), e.getEventName(),false)) {
            return;
        }
        new Cooldown(e.getPlayer(), 1, e.getEventName());

        for (NPC npc : NPC.npcList) {
            if (e.getEntityId() == npc.getEntityID()) {
                NPCClickEvent npcClickEvent = new NPCClickEvent(e.getPlayer(), npc);
                Bukkit.getPluginManager().callEvent(npcClickEvent);
                return;
            }
        }

    }

}
