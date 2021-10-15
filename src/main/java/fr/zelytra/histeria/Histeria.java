/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria;

import fr.zelytra.histeria.commands.Test;
import fr.zelytra.histeria.commands.bank.BankCommands;
import fr.zelytra.histeria.commands.bank.BankTab;
import fr.zelytra.histeria.commands.broadcast.Broadcast;
import fr.zelytra.histeria.commands.broadcast.BroadcastTab;
import fr.zelytra.histeria.commands.customItems.HGive;
import fr.zelytra.histeria.commands.customItems.HGiveTab;
import fr.zelytra.histeria.commands.freeze.Freeze;
import fr.zelytra.histeria.commands.hguard.HGuardCreator;
import fr.zelytra.histeria.commands.hguard.HGuardTabCompleter;
import fr.zelytra.histeria.commands.inventoryLooker.InventoryLooker;
import fr.zelytra.histeria.commands.lang.LangCommand;
import fr.zelytra.histeria.commands.lang.LangTabCommand;
import fr.zelytra.histeria.commands.miscellaneous.*;
import fr.zelytra.histeria.commands.moderation.Ban.BanCommand;
import fr.zelytra.histeria.commands.moderation.Ban.UnBanCommand;
import fr.zelytra.histeria.commands.moderation.Ban.UnBanTab;
import fr.zelytra.histeria.commands.moderation.HKick;
import fr.zelytra.histeria.commands.moderation.Mute.MuteCommand;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.shop.ShopCommand;
import fr.zelytra.histeria.commands.shop.StandCommand;
import fr.zelytra.histeria.commands.shop.StandTab;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.commands.worldSpawn.WorldSpawn;
import fr.zelytra.histeria.events.EventManager;
import fr.zelytra.histeria.events.pluginMessage.PluginMessage;
import fr.zelytra.histeria.managers.configuration.ConfigurationManager;
import fr.zelytra.histeria.managers.items.CraftManager;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.Logs;
import fr.zelytra.histeria.managers.loottable.LootTableManager;
import fr.zelytra.histeria.managers.market.shop.Shop;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.tab.VisualTeamManager;
import fr.zelytra.histeria.utils.Message;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class Histeria extends JavaPlugin {

    private static Histeria instance;
    public static boolean log = true;
    public static boolean synchro = false;
    private static boolean saberFaction = false;
    public static boolean isReloading = false;
    private static LuckPerms luckPerms;

    public static MySQL mySQL;
    public static Shop shop;
    public static LootTableManager lootTableManager;
    public static ConfigurationManager configurationManager;
    public static VisualTeamManager visualTeamManager;
    private static Logs logs;

    public static Histeria getInstance() {
        return instance;
    }


    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(Message.CONSOLE_STRATUP.getMsg());
        isReloading = false;
        regCommands();
        loadAPI();
        regRepeatingTask();
        regPluginMessage();
        setupServer();
        EventManager.regEvents(this);
        new CraftManager();

        lootTableManager = new LootTableManager();
        logs = new Logs();
        mySQL = new MySQL();
        shop = new Shop();
        configurationManager = new ConfigurationManager();
        configurationManager.load();

        visualTeamManager = new VisualTeamManager();
        logs.log("Histeria successfully start", LogType.INFO);
        CustomPlayer.forceLoadAll();
    }


    @Override
    public void onDisable() {
        configurationManager.unload();
        CustomPlayer.forceSaveAll();

        //TODO Save all data of players in case of shutting down server


    }

    private void regCommands() {
        getCommand("test").setExecutor(new Test());

        /* Server */
        getCommand("server").setExecutor(new ServerSelector());

        /* Wiki */
        getCommand("wiki").setExecutor(new Wiki());

        /* HGuard */
        getCommand("hguard").setExecutor(new HGuardCreator());
        getCommand("hguard").setTabCompleter(new HGuardTabCompleter());

        /* HGive */
        getCommand("hgive").setExecutor(new HGive());
        getCommand("hgive").setTabCompleter(new HGiveTab());

        /* Bank */
        getCommand("bank").setExecutor(new BankCommands());
        getCommand("bank").setTabCompleter(new BankTab());

        /* Lang */
        getCommand("lang").setExecutor(new LangCommand());
        getCommand("lang").setTabCompleter(new LangTabCommand());

        /* Broadcast */
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("broadcast").setTabCompleter(new BroadcastTab());

        /* Stand */
        getCommand("stand").setExecutor(new StandCommand());
        getCommand("stand").setTabCompleter(new StandTab());

        /* Miscellaneous */
        getCommand("speed").setExecutor(new Speed());
        getCommand("freeze").setExecutor(new Freeze());
        getCommand("unfreeze").setExecutor(new Freeze());
        getCommand("nightvision").setExecutor(new NightVision());
        getCommand("feed").setExecutor(new Feed());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("ping").setExecutor(new Ping());
        getCommand("craft").setExecutor(new Craft());
        getCommand("heal").setExecutor(new Heal());
        getCommand("hat").setExecutor(new Hat());
        getCommand("xpbottle").setExecutor(new XpBottle());
        getCommand("fly").setExecutor(new Fly());
        getCommand("gmc").setExecutor(new GameMode());
        getCommand("gms").setExecutor(new GameMode());
        getCommand("gmsc").setExecutor(new GameMode());
        getCommand("stats").setExecutor(new Stats());
        getCommand("emote").setExecutor(new EmoteCommand());
        getCommand("bug").setExecutor(new Bug());
        getCommand("social").setExecutor(new Social());
        getCommand("compress").setExecutor(new Compress());
        getCommand("near").setExecutor(new Near());
        getCommand("slot").setExecutor(new Slot());
        getCommand("inventorysee").setExecutor(new InventoryLooker());
        getCommand("hkick").setExecutor(new HKick());
        getCommand("setspawn").setExecutor(new WorldSpawn());
        getCommand("spawn").setExecutor(new WorldSpawn());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("shop").setExecutor(new ShopCommand());

        /* Ban */
        getCommand("hban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnBanCommand());
        getCommand("unban").setTabCompleter(new UnBanTab());


    }

    private void setupServer() {
        for (World world : Bukkit.getWorlds()) {
            world.setMonsterSpawnLimit(40);
            world.setAnimalSpawnLimit(10);
            world.setAmbientSpawnLimit(15);
            world.setWaterAnimalSpawnLimit(5);
        }
    }

    private void regPluginMessage() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }

    private void regRepeatingTask() {

    }

    private void loadAPI() {
        if (!(getServer().getPluginManager().getPlugin("Factions") == null)) {
            saberFaction = true;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public static void log(String msg, LogType type) {
        if (log) {
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.CONSOLE_PREFIX.getMsg() + msg);
        }
        logs.log(msg, type);

    }

    public static boolean isSaberFaction() {
        return saberFaction;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
