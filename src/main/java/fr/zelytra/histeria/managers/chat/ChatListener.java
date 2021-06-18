package fr.zelytra.histeria.managers.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {

        e.setCancelled(true);
        ChatManager chatManager = new ChatManager(e.getPlayer(), PlainComponentSerializer.plain().serialize(e.message()));
        Bukkit.broadcast(Component.text(chatManager.getProcessMessage()));

    }
}
