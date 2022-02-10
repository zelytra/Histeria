/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.commands.freeze.FreezeListener;
import fr.zelytra.histeria.commands.inventoryLooker.InventoryListener;
import fr.zelytra.histeria.commands.kit.KitCommand;
import fr.zelytra.histeria.commands.moderation.Ban.BanListener;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.vanish.VanishListener;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.commands.worldSpawn.WorldSpawnListener;
import fr.zelytra.histeria.events.antiCheat.ClickLogger;
import fr.zelytra.histeria.events.antiCheat.FlyDetector;
import fr.zelytra.histeria.events.antiCheat.XRayDetector;
import fr.zelytra.histeria.events.blocks.Elevator;
import fr.zelytra.histeria.events.blocks.LootBox;
import fr.zelytra.histeria.events.blocks.LuckyBlock;
import fr.zelytra.histeria.events.blocks.NocturiteGenerator;
import fr.zelytra.histeria.events.environement.*;
import fr.zelytra.histeria.events.gui.InterfaceHandler;
import fr.zelytra.histeria.events.items.armors.NocturiteBoots;
import fr.zelytra.histeria.events.items.armors.handler.ArmorListener;
import fr.zelytra.histeria.events.items.armors.handler.ArmorsHandler;
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemHandler;
import fr.zelytra.histeria.events.items.miscellaneous.*;
import fr.zelytra.histeria.events.items.projectile.Dynamite;
import fr.zelytra.histeria.events.items.projectile.Shuriken;
import fr.zelytra.histeria.events.items.repair.AnvilListener;
import fr.zelytra.histeria.events.items.tools.*;
import fr.zelytra.histeria.events.player.*;
import fr.zelytra.histeria.managers.afk.AfkListener;
import fr.zelytra.histeria.managers.arena.ArenaListener;
import fr.zelytra.histeria.managers.enchants.content.BlessOfKeeping;
import fr.zelytra.histeria.managers.enchants.content.Lightning;
import fr.zelytra.histeria.managers.enchants.listener.AnvilCustomEnchant;
import fr.zelytra.histeria.managers.enchants.listener.EnchantingTableCustomEnchant;
import fr.zelytra.histeria.managers.enchants.listener.GrindStoneCustomEnchant;
import fr.zelytra.histeria.managers.hguard.HGuardListener;
import fr.zelytra.histeria.managers.jobs.listener.*;
import fr.zelytra.histeria.managers.jobs.visual.JobVisualListener;
import fr.zelytra.histeria.managers.logs.listener.CommandLogger;
import fr.zelytra.histeria.managers.logs.listener.ErrorLogger;
import fr.zelytra.histeria.managers.logs.listener.OreLogger;
import fr.zelytra.histeria.managers.market.blackMarket.MarketListener;
import fr.zelytra.histeria.managers.market.shop.ShopListener;
import fr.zelytra.histeria.managers.market.stand.StandListener;
import fr.zelytra.histeria.managers.npc.listener.NPCListener;
import fr.zelytra.histeria.managers.pvp.PvPLogger;
import fr.zelytra.histeria.managers.visual.chat.ChatListener;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public static void regEvents(Histeria pl) {
        PluginManager pm = Bukkit.getPluginManager();

        /* Player */
        pm.registerEvents(new PlayerJoinSync(), pl);
        pm.registerEvents(new PlayerLeftSync(), pl);
        pm.registerEvents(new PlayerJoinData(), pl);
        pm.registerEvents(new BanListener(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);
        pm.registerEvents(new ChatListener(), pl);
        pm.registerEvents(new PlayerJoinVisual(), pl);
        pm.registerEvents(new PlayerLeftVisual(), pl);
        pm.registerEvents(new FreezeListener(), pl);
        pm.registerEvents(new InventoryListener(), pl);
        pm.registerEvents(new WorldSpawnListener(), pl);
        pm.registerEvents(new ReloadingServer(), pl);
        pm.registerEvents(new AfkListener(), pl);
        pm.registerEvents(new VanishListener(), pl);
        pm.registerEvents(new PvPLogger(), pl);
        pm.registerEvents(new PVPJoin(), pl);

        /* Interface */
        pm.registerEvents(new InterfaceHandler(), pl);
        pm.registerEvents(new ServerSelector(), pl);
        pm.registerEvents(new Wiki(), pl);
        pm.registerEvents(new ShopListener(), pl);
        pm.registerEvents(new StandListener(), pl);
        pm.registerEvents(new KitCommand(), pl);
        pm.registerEvents(new MarketListener(), pl);

        /* Items */
        pm.registerEvents(new CustomItemHandler(), pl);
        pm.registerEvents(new HealStick(), pl);
        pm.registerEvents(new JumpStick(), pl);
        pm.registerEvents(new SpeedStick(), pl);
        pm.registerEvents(new BubbleWand(), pl);
        pm.registerEvents(new ObsidianBreaker(), pl);
        pm.registerEvents(new AlpineHook(), pl);
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
        pm.registerEvents(new ServerPingListener(), pl);
        pm.registerEvents(new OreLogger(), pl);
        pm.registerEvents(new ErrorLogger(), pl);
        pm.registerEvents(new CommandLogger(), pl);
        pm.registerEvents(new PluginList(), pl);
        pm.registerEvents(new NPCListener(), pl);
        pm.registerEvents(new ClaimChecker(), pl);
        pm.registerEvents(new ArenaListener(), pl);
        pm.registerEvents(new TexturePack(), pl);
        pm.registerEvents(new MobSpawnArmor(), pl);
        pm.registerEvents(new VillagerTrade(), pl);

        /* Anti-Cheat */
        pm.registerEvents(new FlyDetector(), pl);
        pm.registerEvents(new ClickLogger(), pl);
        pm.registerEvents(new XRayDetector(), pl);

        /* Jobs */
        pm.registerEvents(new LevelUpListener(), pl);
        pm.registerEvents(new JobVisualListener(), pl);
        pm.registerEvents(new MinerListener(), pl);
        pm.registerEvents(new FarmerListener(), pl);
        pm.registerEvents(new FighterListener(), pl);
        pm.registerEvents(new EnchanterListener(), pl);

        /* Custom Enchants */
        pm.registerEvents(new EnchantingTableCustomEnchant(), pl);
        pm.registerEvents(new AnvilCustomEnchant(), pl);
        pm.registerEvents(new GrindStoneCustomEnchant(), pl);
        pm.registerEvents(new BlessOfKeeping(), pl);
        pm.registerEvents(new Lightning(), pl);

        /* LuckPerms */
        EventBus eventBus = Histeria.getLuckPerms().getEventBus();
        eventBus.subscribe(pl, UserDataRecalculateEvent.class, PlayerJoinVisual::onParentChange);

    }

}
