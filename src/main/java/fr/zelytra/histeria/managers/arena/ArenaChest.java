/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.arena;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.parser.ItemLuck;
import fr.zelytra.histeria.builder.parser.ItemParser;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.utils.CLocation;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ArenaChest implements Serializable {

    public static List<ArenaChest> chestList = new ArrayList<>();
    private final CLocation location;
    private final UUID uuid = UUID.randomUUID();

    public ArenaChest(Location location) {

        this.location = new CLocation(location);
        chestList.add(this);

    }

    public void destroy() {
        chestList.remove(this);
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "ArenaChest";
        File file = new File(folderPath + File.separator + uuid + ".ac");

        if (file.exists())
            file.delete();

    }

    public void draw() {

        if (!isChest()) {
            destroy();
            return;
        }

        Chest chest = (Chest) getLocation().getBlock().getState();
        chest.setCustomName("§6Arena Chest");
        chest.update();
        ItemStack[] content = chest.getInventory().getContents();
        InputStream is = Histeria.getInstance().getResource("arenaChest.yml");
        content = randomLoot(content, new ItemParser(is).getList());
        chest.getInventory().setContents(content);

    }

    private ItemStack[] randomLoot(ItemStack[] content, List<ItemLuck> lootTable) {

        for (int x = 0; x < content.length; x++)
            content[x] = new ItemStack(Material.AIR);

        for (int i = 0; i <= 8; i++) {
            int slotRandom = (int) (Math.random() * (content.length));

            double random = new Random().nextDouble();
            for (ItemLuck loot : lootTable) {
                if (loot.luck / 100.0 > random) {
                    content[slotRandom] = loot.item;
                    break;
                }
                random -= loot.luck / 100.0;
            }

        }
        return content;
    }

    public Location getLocation() {
        return this.location.getLocation();
    }

    public boolean isChest() {
        return location.getLocation().getBlock().getType() == Material.CHEST;
    }

    public static void startTask() {
        long remain = (3600 - ((System.currentTimeMillis() / 1000) % 3600)) * 20 + 30;
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () ->
                Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> {
                    System.out.println(chestList.size());
                    for (ArenaChest chest : chestList)
                        chest.draw();

                    LangMessage.broadcast(Message.HISTALERT.getMsg(), "arena.refill", "");


                }, 0, 432000 * 20), remain);

    }

    public static void saveAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "ArenaChest";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdir();

        for (ArenaChest chest : chestList) {

            try {

                File file = new File(folderPath + File.separator + chest.uuid + ".ac");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(chest);
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Histeria.log("§6" + chestList.size() + "§a ArenaChest has been saved", LogType.INFO);
    }

    public static void loadAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "ArenaChest";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
            return;
        }

        for (File file : folder.listFiles()) {

            try {

                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                chestList.add((ArenaChest) oos.readObject());
                oos.close();

            } catch (Exception e) {
                Histeria.log("§cWrong ArenaChest file format for : " + file.getName(), LogType.ERROR);
                file.delete();
            }

        }

        Histeria.log("§6" + chestList.size() + "§a ArenaChest has been loaded", LogType.INFO);


    }

}
