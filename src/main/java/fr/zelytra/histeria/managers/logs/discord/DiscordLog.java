/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.discord;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import org.bukkit.Bukkit;

import java.io.IOException;

public class DiscordLog {
    private static Object sync = new Object();

    public DiscordLog(WebHookType type, String message) {
        synchronized (sync) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                DiscordWebhook webhook = new DiscordWebhook(type.getURL());
                webhook.setAvatarUrl(type.getAvatar());
                webhook.setUsername(type.getUserName());

                webhook.setContent(message.replace("@", "").replace("http", "").replace("https", "").replace("/", ""));

                try {
                    webhook.execute();
                } catch (IOException e) {
                    //e.printStackTrace();
                    Histeria.log("Failed to send a webhook : " + type.name() + " -> " + message, LogType.WARN);
                }

            });

        }

    }
}
