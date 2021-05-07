package fr.zelytra.histeria;

import fr.zelytra.histeria.commands.Test;
import fr.zelytra.histeria.commands.customItems.HGive;
import fr.zelytra.histeria.commands.customItems.HGiveTab;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.commands.wiki.Wiki;
import fr.zelytra.histeria.events.EventManager;
import fr.zelytra.histeria.events.pluginMessage.PluginMessage;
import fr.zelytra.histeria.managers.items.CraftManager;
import fr.zelytra.histeria.managers.loottable.LootTableManager;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class Histeria extends JavaPlugin {
    private static Histeria instance;
    public static boolean log = true;
    public static boolean synchro = false;
    public static LootTableManager lootTableManager;
    private static boolean saberFaction = false;

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
        regRepeatingTask();
        regPluginMessage();
        setupServer();
        EventManager.regEvents(this);
        new CraftManager();
        lootTableManager = new LootTableManager();
    }

    @Override
    public void onDisable() {

    }

    private void regCommands() {
        getCommand("test").setExecutor(new Test());

        getCommand("server").setExecutor(new ServerSelector());
        getCommand("wiki").setExecutor(new Wiki());

        getCommand("hgive").setExecutor(new HGive());
        getCommand("hgive").setTabCompleter(new HGiveTab());
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
    }

    public static void log(String msg) {
        if (log) {
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.getConsolePrefixe() + msg);
        }
    }

    public static boolean isSaberFaction() {
        return saberFaction;
    }
}
