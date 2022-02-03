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
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.jobs.command.JobMenuCommand;
import fr.zelytra.histeria.managers.jobs.visual.JobPage;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.blackMarket.builder.PlayerMarket;
import fr.zelytra.histeria.managers.market.shop.PlayerShop;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class NPCListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        for (NPC npc : NPC.npcList) {
            npc.showNPC(e.getPlayer());
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        for (NPC npc : NPC.npcList) {
            npc.showNPC(e.getPlayer());
        }
    }

    @EventHandler
    public void onNPClick(NPCClickEvent e) {
        switch (e.getNpc().getAction()) {

            case DEFAULT:
                break;
            case HMARKET:
                new PlayerMarket(e.getPlayer());
                break;
            case SHOP:
                new PlayerShop(e.getPlayer());
                break;
            case JOB:
                InterfaceBuilder interfaceBuilder = new InterfaceBuilder(9, JobMenuCommand.menuName + JobPage.MENU.pageName);
                interfaceBuilder.setContent(JobMenuCommand.getMenuContent(CustomPlayer.getCustomPlayer(e.getPlayer().getName())));
                interfaceBuilder.open(e.getPlayer());
                break;
            case TELEPORT:
                e.getPlayer().teleport(e.getNpc().getTeleportLocation());
                LangMessage.sendMessage(e.getPlayer(), "npc.teleportAction");
                break;
            case SERVER:
                new SwitchServer(e.getPlayer()).switchTo(e.getNpc().getServer());
                LangMessage.sendMessage(e.getPlayer(), "npc.switchServer");
                break;
        }
    }

    @EventHandler
    public void onNPCClickEvent(PlayerUseUnknownEntityEvent e) {
        if (!Cooldown.cooldownCheck(e.getPlayer(), e.getEventName(), false)) {
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
