/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.server;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.visual.tab.Tab;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PluginMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }
        final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        final SubChannel subChannel = SubChannel.get(in.readUTF());
        Histeria.log("§aPMessage received of : "+channel, LogType.INFO);
        switch (subChannel) {
            case SERVER_NAME:
                Histeria.server.setServerName(in.readUTF());
                break;
            case PLAYER_COUNT:
                in.readUTF();
                Histeria.server.setPlayerCount(in.readInt());
                Tab.updateFooterForAll();
                break;
            case SERVERS_NAME:
                Histeria.server.setServersList(Arrays.asList(in.readUTF().split(", ")));
                break;
        }

    }


}
