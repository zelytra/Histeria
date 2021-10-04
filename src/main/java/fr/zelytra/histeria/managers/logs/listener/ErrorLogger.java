/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.listener;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ErrorLogger implements Listener {

    @EventHandler
    public void onError(ServerExceptionEvent e) {
        Histeria.log("An error has occurred, please report this to an admin.\n " + e.getException().getMessage(), LogType.ERROR);
        new DiscordLog(WebHookType.ERROR, "**An error has occurred on server :** " + Histeria.getInstance().getDataFolder().getAbsolutePath().replace("\\",".").replace("/",".") + " **, please report this to an admin.** " + e.getException().getMessage());
    }
}
