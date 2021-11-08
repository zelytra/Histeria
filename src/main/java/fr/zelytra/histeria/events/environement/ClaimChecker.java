/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ClaimChecker implements Listener {

    @EventHandler
    public void onClaimArea(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().contains("/f claim") ||
                e.getMessage().contains("/f claimfill") ||
                e.getMessage().contains("/f claimat") ||
                e.getMessage().contains("/f claimline") ||
                e.getMessage().contains("/f autoclaim")) {

            if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
                return;
            }

            e.setCancelled(true);
            LangMessage.sendMessage(e.getPlayer(), "hguard.cannotExec");

        }

    }
}
