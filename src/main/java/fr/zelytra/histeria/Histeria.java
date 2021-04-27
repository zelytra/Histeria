package fr.zelytra.histeria;

import fr.zelytra.histeria.commands.ServerSelector;
import fr.zelytra.histeria.events.EventManager;
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
        regCommands();
        regRepeatingTask();
        setupServer();
        EventManager.regEvents(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void regCommands() {
        getCommand("server").setExecutor(new ServerSelector());
    }

    private void setupServer() {
        for (World world : Bukkit.getWorlds()) {
            world.setMonsterSpawnLimit(40);
            world.setAnimalSpawnLimit(10);
            world.setAmbientSpawnLimit(15);
            world.setWaterAnimalSpawnLimit(5);
        }
    }

    private void regRepeatingTask() {

    }

    public static void log(String msg) {
        if (log) {
            Histeria.getInstance().getServer().getConsoleSender().sendMessage(Message.getConsolePrefixe() + msg);
        }
    }

}
