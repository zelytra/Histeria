package fr.zelytra.histeria.managers.visual.chat;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.utils.Utils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {

        e.setCancelled(true);

        if (PlainComponentSerializer.plain().serialize(e.message()).startsWith("!") && Utils.canByPass(e.getPlayer())) {
            for (Player player : Bukkit.getOnlinePlayers())
                if (Utils.canByPass(player))
                    player.sendMessage("§6[§eSTAFF§6] §e" + e.getPlayer().getName() + "§6 > §f" + PlainComponentSerializer.plain().serialize(e.message()).substring(1));

            Histeria.log("§6[§eSTAFF§6] §e" + e.getPlayer().getName() + "§6 > §f" + PlainComponentSerializer.plain().serialize(e.message()).substring(1), LogType.INFO);
            return;
        }


        ChatManager chatManager = new ChatManager(e.getPlayer(), PlainComponentSerializer.plain().serialize(e.message()));
        Bukkit.broadcast(chatManager.getProcessMessage());
        new DiscordLog(WebHookType.CHAT,e.getPlayer().getName() +" > " + PlainComponentSerializer.plain().serialize(e.message()));
    }
}
