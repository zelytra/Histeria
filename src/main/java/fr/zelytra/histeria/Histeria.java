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
import fr.zelytra.histeria.commands.bank.AdminBank;
import fr.zelytra.histeria.commands.bank.AdminBankTab;
import fr.zelytra.histeria.commands.bank.BankCommands;
import fr.zelytra.histeria.commands.bank.BankTab;
import fr.zelytra.histeria.commands.broadcast.Broadcast;
import fr.zelytra.histeria.commands.broadcast.BroadcastTab;
import fr.zelytra.histeria.commands.customItems.HGive;
import fr.zelytra.histeria.commands.customItems.HGiveTab;
import fr.zelytra.histeria.commands.freeze.Freeze;
import fr.zelytra.histeria.commands.hardDelete.HardDelete;
import fr.zelytra.histeria.commands.hardDelete.HardDeleteTab;
import fr.zelytra.histeria.commands.inventoryLooker.InventoryLooker;
import fr.zelytra.histeria.commands.kit.KitCommand;
import fr.zelytra.histeria.commands.lang.LangCommand;
import fr.zelytra.histeria.commands.lang.LangTabCommand;
import fr.zelytra.histeria.commands.miscellaneous.*;
import fr.zelytra.histeria.commands.moderation.Ban.BanCommand;
import fr.zelytra.histeria.commands.moderation.Ban.UnBanCommand;
import fr.zelytra.histeria.commands.moderation.Ban.UnBanTab;
import fr.zelytra.histeria.commands.moderation.HKick;
import fr.zelytra.histeria.commands.moderation.Mute.MuteCommand;
import fr.zelytra.histeria.commands.moderation.Mute.UnMuteCommand;
import fr.zelytra.histeria.commands.moderation.Mute.UnMuteTab;
import fr.zelytra.histeria.commands.report.Bug;
import fr.zelytra.histeria.commands.report.BugTab;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.shop.ShopCommand;
import fr.zelytra.histeria.commands.shop.StandCommand;
import fr.zelytra.histeria.commands.shop.StandTab;
import fr.zelytra.histeria.commands.stats.Stats;
import fr.zelytra.histeria.commands.stats.TopStats;
import fr.zelytra.histeria.commands.stats.TopStatsTab;
import fr.zelytra.histeria.commands.switchServer.SwitchServerCommand;
import fr.zelytra.histeria.commands.switchServer.SwitchServerTab;
import fr.zelytra.histeria.commands.tpa.Tpa;
import fr.zelytra.histeria.commands.vanish.Vanish;
import fr.zelytra.histeria.commands.vote.Vote;
import fr.zelytra.histeria.commands.vote.VoteCommand;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.commands.worldSpawn.WorldSpawn;
import fr.zelytra.histeria.events.EventManager;
import fr.zelytra.histeria.events.antiCheat.ClickLogger;
import fr.zelytra.histeria.events.antiCheat.XRayDetector;
import fr.zelytra.histeria.events.blocks.miningDrill.DrillDataPersistor;
import fr.zelytra.histeria.managers.afk.Afk;
import fr.zelytra.histeria.managers.arena.ArenaChest;
import fr.zelytra.histeria.managers.arena.ArenaCommand;
import fr.zelytra.histeria.managers.arena.ArenaTab;
import fr.zelytra.histeria.managers.clearLag.ClearLag;
import fr.zelytra.histeria.managers.configuration.ConfigurationManager;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.command.HEnchant;
import fr.zelytra.histeria.managers.enchants.command.HEnchantTab;
import fr.zelytra.histeria.managers.evenements.command.EventCommand;
import fr.zelytra.histeria.managers.evenements.command.EventTab;
import fr.zelytra.histeria.managers.hguard.HGuardListener;
import fr.zelytra.histeria.managers.hguard.command.HGuardCreator;
import fr.zelytra.histeria.managers.hguard.command.HGuardTabCompleter;
import fr.zelytra.histeria.managers.hologram.HoloCommand;
import fr.zelytra.histeria.managers.hologram.HoloTab;
import fr.zelytra.histeria.managers.hologram.Hologram;
import fr.zelytra.histeria.managers.home.AdminHomeCommand;
import fr.zelytra.histeria.managers.home.AdminHomeTab;
import fr.zelytra.histeria.managers.home.HomeCommand;
import fr.zelytra.histeria.managers.home.HomeTab;
import fr.zelytra.histeria.managers.items.CraftManager;
import fr.zelytra.histeria.managers.jobs.command.AdminJob;
import fr.zelytra.histeria.managers.jobs.command.AdminJobTab;
import fr.zelytra.histeria.managers.jobs.command.JobMenuCommand;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.Logs;
import fr.zelytra.histeria.managers.loottable.LootTableManager;
import fr.zelytra.histeria.managers.market.blackMarket.MarketCommand;
import fr.zelytra.histeria.managers.market.shop.Shop;
import fr.zelytra.histeria.managers.market.stand.StandSerializer;
import fr.zelytra.histeria.managers.market.trade.TradeCommand;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.npc.command.NPCCommand;
import fr.zelytra.histeria.managers.npc.command.NPCTab;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.server.PluginMessage;
import fr.zelytra.histeria.managers.server.Server;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
import fr.zelytra.histeria.managers.visual.tab.VisualTeamManager;
import fr.zelytra.histeria.utils.Message;
import net.luckperms.api.LuckPerms;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class Histeria extends JavaPlugin {

    private static Histeria instance;
    public static boolean log = true;
    public static boolean synchro = false;
    private static boolean saberFaction = false;
    public static boolean isReloading = false;
    private static LuckPerms luckPerms;

    public static String version = "v3.10";
    public static MySQL mySQL;
    public static Shop shop;
    public static Vote vote;
    public static Server server;
    public static ClearLag clearLag;
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
        Message.startUpMessage();
        isReloading = false;
        regCommands();
        regEnchants();
        loadAPI();
        regRepeatingTask();
        regPluginMessage();
        setupServer();
        EventManager.regEvents(this);

        new CraftManager();
        vote = new Vote();
        server = new Server();
        lootTableManager = new LootTableManager();
        logs = new Logs();
        mySQL = new MySQL();

        if (mySQL.isConnected())
            shop = new Shop();

        clearLag = new ClearLag();
        configurationManager = new ConfigurationManager();
        visualTeamManager = new VisualTeamManager();

        configurationManager.load();
        StandSerializer.loadAll();
        NPC.loadAll();
        ArenaChest.loadAll();
        Hologram.load();
        DrillDataPersistor.load();
        autoReboot();

        logs.log("Histeria successfully start", LogType.INFO);
        CustomPlayer.forceLoadAll();
    }


    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (synchro) {
                SyncServer server = new SyncServer(player, Request.SEND);
                server.execute();
            }
            if (mySQL.isConnected()) {
                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
                customPlayer.saveData();
            }
            log("§a" + player.getName() + " has been saved in data lake", LogType.INFO);
        }
        NPC.saveAll();
        ArenaChest.saveAll();
        Hologram.save();
        DrillDataPersistor.saveAll();
        configurationManager.unload();

        if (mySQL.isConnected())
            mySQL.closeConnection();
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
        getCommand("adminbank").setExecutor(new AdminBank());
        getCommand("adminbank").setTabCompleter(new AdminBankTab());

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
        getCommand("bug").setTabCompleter(new BugTab());
        getCommand("social").setExecutor(new Social());
        getCommand("compress").setExecutor(new Compress());
        getCommand("near").setExecutor(new Near());
        getCommand("slot").setExecutor(new Slot());
        getCommand("inventorysee").setExecutor(new InventoryLooker());
        getCommand("hkick").setExecutor(new HKick());
        getCommand("setspawn").setExecutor(new WorldSpawn());
        getCommand("spawn").setExecutor(new WorldSpawn());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("afk").setExecutor(new AfkCommand());
        getCommand("randomtp").setExecutor(new RandomTp());
        getCommand("clearlag").setExecutor(new ClearLagCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("admin").setExecutor(new Admin());

        /* HardDelete */
        getCommand("harddelete").setExecutor(new HardDelete());
        getCommand("harddelete").setTabCompleter(new HardDeleteTab());

        /* Hologram */
        getCommand("hologram").setExecutor(new HoloCommand());
        getCommand("hologram").setTabCompleter(new HoloTab());

        /* TopStats */
        getCommand("topstats").setExecutor(new TopStats());
        getCommand("topstats").setTabCompleter(new TopStatsTab());

        /* ArenaChest */
        getCommand("arenachest").setExecutor(new ArenaCommand());
        getCommand("arenachest").setTabCompleter(new ArenaTab());

        /* NPC */
        getCommand("npc").setExecutor(new NPCCommand());
        getCommand("npc").setTabCompleter(new NPCTab());

        /* SwitchServer */
        getCommand("switchserver").setExecutor(new SwitchServerCommand());
        getCommand("switchserver").setTabCompleter(new SwitchServerTab());

        /* Home */
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTab());
        getCommand("sethome").setExecutor(new HomeCommand());
        getCommand("delhome").setExecutor(new HomeCommand());
        getCommand("delhome").setTabCompleter(new HomeTab());
        getCommand("listhome").setExecutor(new HomeCommand());
        getCommand("adminhome").setExecutor(new AdminHomeCommand());
        getCommand("adminhome").setTabCompleter(new AdminHomeTab());

        /* TPA */
        getCommand("tpa").setExecutor(new Tpa());
        getCommand("tpaccept").setExecutor(new Tpa());
        getCommand("tpdeny").setExecutor(new Tpa());

        /* Vanish */
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("unvanish").setExecutor(new Vanish());

        /* Vote */
        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("forcevote").setExecutor(new VoteCommand());

        /* Ban */
        getCommand("hban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnBanCommand());
        getCommand("unban").setTabCompleter(new UnBanTab());

        /* Mute */
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnMuteCommand());
        getCommand("unmute").setTabCompleter(new UnMuteTab());

        /* Job */
        getCommand("job").setExecutor(new JobMenuCommand());
        getCommand("adminjob").setExecutor(new AdminJob());
        getCommand("adminjob").setTabCompleter(new AdminJobTab());

        /* Custom Enchant */
        getCommand("henchant").setExecutor(new HEnchant());
        getCommand("henchant").setTabCompleter(new HEnchantTab());

        /* BlackMarket */
        getCommand("blackmarket").setExecutor(new MarketCommand());

        /* Trade */
        getCommand("trade").setExecutor(new TradeCommand());

        /* Event */
        getCommand("event").setExecutor(new EventCommand());
        getCommand("event").setTabCompleter(new EventTab());

    }


    private void regPluginMessage() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }

    private void regRepeatingTask() {
        Afk.startAfkListener();
        HGuardListener.startEntityKiller();
        ArenaChest.startTask();
        ClickLogger.clickerTask();
        XRayDetector.profilerAnalysis();
    }

    private void regEnchants() {
        try {

            // Opening accessibility field
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            // Enchants
            CustomEnchant.BLESS_OF_KEEPING.register();
            CustomEnchant.LIGHTNING.register();
            CustomEnchant.SHIELD.register();
            CustomEnchant.VAMPIRISME.register();
            CustomEnchant.STUN.register();
            CustomEnchant.PHOENIX_BIRTH.register();

        } catch (Exception e) {
            Histeria.log("Failed to register custom enchants", LogType.ERROR);
        }
    }


    private void loadAPI() {
        if (!(getServer().getPluginManager().getPlugin("Factions") == null)) {
            saberFaction = true;
        }

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
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.CONSOLE_PREFIX.getMsg() + type.color + msg);
        }
        logs.log(msg, type);
    }

    private void setupServer() {

        if (Bukkit.getPluginManager().getPlugin("WorldEdit") != null) {
            File configLegacy = new File(Bukkit.getPluginManager().getPlugin("WorldEdit").getDataFolder().getPath() + File.separator + "config.yml");
            FileConfiguration yml = YamlConfiguration.loadConfiguration(configLegacy);

            if (yml.getString(("navigation-wand.item")) != null && !yml.getString(("navigation-wand.item")).equals("minecraft:lead"))
                yml.set("navigation-wand.item", "minecraft:lead");
        }


        for (World world : Bukkit.getWorlds()) {
            Slot.setSlot(100);
            world.setMonsterSpawnLimit(30);
            world.setAnimalSpawnLimit(10);
            world.setAmbientSpawnLimit(15);
            world.setWaterAnimalSpawnLimit(5);
        }

        try {

            DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
            DedicatedServerProperties properties = server.getProperties();
            Field spawnProtectionField = properties.getClass().getField("spawnProtection");
            spawnProtectionField.setAccessible(true);
            spawnProtectionField.set(properties, 0);

        } catch (Exception ignored) {
        }

    }

    private void autoReboot() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");

            String hour = sdfHour.format(cal.getTime());
            if (isReloading == false && hour.contains("05:00")) {
                LangMessage.broadcast(Message.HISTALERT.getMsg(), "server.restart", "");
                isReloading = true;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bukkit.shutdown();
            }

        }, 1200, 80);


    }

    public static boolean isSaberFaction() {
        return saberFaction;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
