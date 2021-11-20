package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.managers.languages.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class TexturePack implements Listener {

    @EventHandler
    public void onTexturePackApplied(PlayerResourcePackStatusEvent e){
        if(e.getStatus()== PlayerResourcePackStatusEvent.Status.DECLINED){
            e.getPlayer().kick(Component.text(Lang.EN.get("server.texturePackDeclined")));
        }
    }
}
