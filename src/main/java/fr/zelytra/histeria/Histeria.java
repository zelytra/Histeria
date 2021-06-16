package fr.zelytra.histeria;

import fr.zelytra.histeria.commands.Test;
import fr.zelytra.histeria.commands.customItems.HGive;
import fr.zelytra.histeria.commands.customItems.HGiveTab;
import fr.zelytra.histeria.commands.hguard.HGuardCreator;
import fr.zelytra.histeria.commands.hguard.HGuardTabCompleter;
import fr.zelytra.histeria.commands.miscellaneous.*;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.events.EventManager;
import fr.zelytra.histeria.events.pluginMessage.PluginMessage;
import fr.zelytra.histeria.managers.configuration.ConfigurationManager;
import fr.zelytra.histeria.managers.items.CraftManager;
import fr.zelytra.histeria.managers.loottable.LootTableManager;
import fr.zelytra.histeria.managers.mysql.MySQL;
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
    private static LuckPerms luckPerms;

    public static MySQL mySQL;
    public static LootTableManager lootTableManager;
    public static ConfigurationManager configurationManager;

    public static Histeria getInstance() {
        return instance;
    }


    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Message.startup();
        regCommands();
        loadAPI();
        regRepeatingTask();
        regPluginMessage();
        setupServer();
        EventManager.regEvents(this);
        new CraftManager();

        lootTableManager = new LootTableManager();
        mySQL = new MySQL();
        configurationManager = new ConfigurationManager();
        configurationManager.load();
    }



    @Override
    public void onDisable() {
        configurationManager.unload();

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

        /* Miscellaneous */
        getCommand("speed").setExecutor(new Speed());
        getCommand("nightvision").setExecutor(new NightVision());
        getCommand("feed").setExecutor(new Feed());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("ping").setExecutor(new Ping());
        getCommand("craft").setExecutor(new Craft());
        getCommand("heal").setExecutor(new Heal());
        getCommand("hat").setExecutor(new Hat());
        getCommand("xpbottle").setExecutor(new XpBottle());
        getCommand("fly").setExecutor(new Fly());

    }

    private void setupServer() {
        for (World world : Bukkit.getWorlds()) {
            world.setMonsterSpawnLimit(40);
            world.setAnimalSpawnLimit(10);
            world.setAmbientSpawnLimit(15);
            world.setWaterAnimalSpawnLimit(5);
        }
    }

    private void regPluginMessage(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }

    private void regRepeatingTask() {

    }

    private void loadAPI(){
        if (!(getServer().getPluginManager().getPlugin("Factions") == null)) {
            saberFaction = true;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public static void log(String msg) {
        if (log) {
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.getConsolePrefixe() + msg);
        }
    }

    public static boolean isSaberFaction() {
        return saberFaction;
    }

    public static LuckPerms getLuckPerms(){
        return luckPerms;
    }
}
