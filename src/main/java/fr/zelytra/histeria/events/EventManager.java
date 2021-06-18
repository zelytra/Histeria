package fr.zelytra.histeria.events;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.events.blocks.Elevator;
import fr.zelytra.histeria.events.blocks.LootBox;
import fr.zelytra.histeria.events.blocks.LuckyBlock;
import fr.zelytra.histeria.events.blocks.NocturiteGenerator;
import fr.zelytra.histeria.events.environement.PortalListener;
import fr.zelytra.histeria.events.environement.ShulkerStorage;
import fr.zelytra.histeria.events.environement.SilverFish;
import fr.zelytra.histeria.events.gui.InterfaceHandler;
import fr.zelytra.histeria.events.items.armors.NocturiteBoots;
import fr.zelytra.histeria.events.items.armors.handler.ArmorListener;
import fr.zelytra.histeria.events.items.armors.handler.ArmorsHandler;
import fr.zelytra.histeria.events.items.miscellaneous.*;
import fr.zelytra.histeria.events.items.projectile.Dynamite;
import fr.zelytra.histeria.events.items.projectile.Shuriken;
import fr.zelytra.histeria.events.items.repair.AnvilListener;
import fr.zelytra.histeria.events.items.tools.*;
import fr.zelytra.histeria.events.player.*;
import fr.zelytra.histeria.managers.hguard.HGuardListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public static void regEvents(Histeria pl) {
        PluginManager pm = Bukkit.getPluginManager();

        /* Player */
        if (Histeria.synchro) {
            pm.registerEvents(new PlayerJoinSync(), pl);
            pm.registerEvents(new PlayerLeftSync(), pl);
        }
        pm.registerEvents(new PlayerJoinData(), pl);
        pm.registerEvents(new PlayerLeftData(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);

        /* Interface */
        pm.registerEvents(new InterfaceHandler(), pl);
        pm.registerEvents(new ServerSelector(), pl);
        pm.registerEvents(new Wiki(), pl);

        /* Items */
        pm.registerEvents(new HealStick(), pl);
        pm.registerEvents(new JumpStick(), pl);
        pm.registerEvents(new SpeedStick(), pl);
        pm.registerEvents(new BubbleWand(), pl);
        pm.registerEvents(new ObsidianBreaker(), pl);
        pm.registerEvents(new AlpinHook(), pl);
        pm.registerEvents(new UnclaimFinder(), pl);
        pm.registerEvents(new ChestExplorer(), pl);
        pm.registerEvents(new Dynamite(), pl);
        pm.registerEvents(new Shuriken(), pl);
        pm.registerEvents(new HisteriteApple(), pl);
        pm.registerEvents(new LotteryTicket(), pl);
        pm.registerEvents(new XPOrb(), pl);
        pm.registerEvents(new CompressCobblestone(), pl);

        /* Tools */
        pm.registerEvents(new HisteritePickaxe(), pl);
        pm.registerEvents(new Hammer(), pl);
        pm.registerEvents(new HisteriteAxe(), pl);
        pm.registerEvents(new HisteriteHoe(), pl);
        pm.registerEvents(new HisteriteShovel(), pl);
        pm.registerEvents(new HisteriteSword(), pl);
        pm.registerEvents(new NocturiteSword(), pl);

        /* Armor */
        pm.registerEvents(new ArmorListener(pl.getConfig().getStringList("blocked")), pl);
        pm.registerEvents(new ArmorsHandler(), pl);
        pm.registerEvents(new NocturiteBoots(), pl);

        /* Blocks */
        pm.registerEvents(new Elevator(), pl);
        pm.registerEvents(new LuckyBlock(), pl);
        pm.registerEvents(new NocturiteGenerator(), pl);
        pm.registerEvents(new LootBox(), pl);

        /* Environment */
        pm.registerEvents(new ShulkerStorage(), pl);
        pm.registerEvents(new SilverFish(), pl);
        pm.registerEvents(new AnvilListener(), pl);
        pm.registerEvents(new HGuardListener(), pl);
        pm.registerEvents(new PortalListener(), pl);

    }

}
