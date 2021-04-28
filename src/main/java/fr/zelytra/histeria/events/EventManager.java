package fr.zelytra.histeria.events;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.gui.InterfaceHandler;
import fr.zelytra.histeria.events.gui.ServerSelector;
import fr.zelytra.histeria.events.items.miscellaneous.*;
import fr.zelytra.histeria.events.items.projectile.Dynamite;
import fr.zelytra.histeria.events.items.projectile.Shuriken;
import fr.zelytra.histeria.events.items.tools.AlpinHook;
import fr.zelytra.histeria.events.items.tools.HisteritePickaxe;
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

        /* Items */
        pm.registerEvents(new HealStick(),pl);
        pm.registerEvents(new JumpStick(),pl);
        pm.registerEvents(new SpeedStick(),pl);
        pm.registerEvents(new BubbleWand(),pl);
        pm.registerEvents(new ObsidianBreaker(),pl);
        pm.registerEvents(new AlpinHook(),pl);
        pm.registerEvents(new UnclaimFinder(),pl);
        pm.registerEvents(new ChestExplorer(),pl);
        pm.registerEvents(new Dynamite(),pl);
        pm.registerEvents(new Shuriken(),pl);

        /* Tools */
        pm.registerEvents(new HisteritePickaxe(),pl);

    }
}
