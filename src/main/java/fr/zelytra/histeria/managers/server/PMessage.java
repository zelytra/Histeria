/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.server;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class PMessage {
    private BukkitTask task;

    public PMessage(@NotNull SubChannel channel, Player player, String args[]) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel.getName());

        if (args != null)
            for (String arg : args)
                out.writeUTF(arg);
        else {
            out.writeUTF("null");
        }


        if (player == null) {
            task = Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> {
                Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                if (p != null) {
                    p.sendPluginMessage(Histeria.getInstance(), "BungeeCord", out.toByteArray());
                    Histeria.log("§aPMessage send on : "+channel, LogType.INFO);
                    task.cancel();
                }

            }, 0L, 60L);
        } else {
            player.sendPluginMessage(Histeria.getInstance(), "BungeeCord", out.toByteArray());
        }

    }


}
