package fr.zelytra.histeria;

import fr.zelytra.histeria.commands.customItems.HGive;
import fr.zelytra.histeria.commands.customItems.HGiveTab;
import fr.zelytra.histeria.commands.serverSwitch.ServerSelector;
import fr.zelytra.histeria.events.EventManager;
import fr.zelytra.histeria.events.pluginMessage.PluginMessage;
import fr.zelytra.histeria.managers.items.CraftManager;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class Histeria extends JavaPlugin {
    private static Histeria instance;
    public static boolean log = true;

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
    }

    @Override
    public void onDisable() {

    }

    private void regCommands() {
        getCommand("server").setExecutor(new ServerSelector());

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

    public static void log(String msg) {
        if (log) {
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.getConsolePrefixe() + msg);
        }
    }



}
