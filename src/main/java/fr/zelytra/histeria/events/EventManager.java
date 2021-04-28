package fr.zelytra.histeria.events;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.gui.InterfaceHandler;
import fr.zelytra.histeria.events.gui.ServerSelector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public static void regEvents(Histeria pl) {
        PluginManager pm = Bukkit.getPluginManager();

        /* Player */
        //pm.registerEvents(new PlayerJoin(), pl);
        //pm.registerEvents(new PlayerLeft(), pl);

        /* Interface */
        pm.registerEvents(new InterfaceHandler(),pl);
        pm.registerEvents(new ServerSelector(),pl);

    }
}
