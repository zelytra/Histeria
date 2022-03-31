/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.dragonBoss;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.evenements.boss.Boss;
import fr.zelytra.histeria.managers.evenements.boss.BossProperty;
import fr.zelytra.histeria.managers.evenements.boss.PlayerDamager;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TheDragon extends BossProperty implements Boss {

    private final static Map<UUID, TheDragon> dragonList = new HashMap<>();

    private final static double maxHealth = 1500;
    private final static String name = "§bThe Dragon";

    private final UUID uuid = UUID.randomUUID();
    public final static NamespacedKey key = new NamespacedKey(Histeria.getInstance(), "TheDragon");
    public final static EntityType type = EntityType.ENDER_DRAGON;
    private EnderDragon dragon;
    private final Location origin;

    public TheDragon(Location location) {

        super(name);
        origin = location.clone();

        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
            location.setY(location.getY() + 50);
            dragon = (EnderDragon) location.getWorld().spawnEntity(location, EntityType.ENDER_DRAGON);
            dragon.setPhase(EnderDragon.Phase.CIRCLING);
            dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            dragon.setHealth(maxHealth);
            dragon.setCustomName(name);

            getBossBar().setProgress(1);
            getBossBar().setColor(BarColor.BLUE);
            getBossBar().setVisible(true);

            dragon.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());
            LangMessage.broadcast("", "boss.theDragonStart", "");
        }, 15 * 20);

        startBossBarListener(this);
        dragonList.put(uuid, this);

        summonPillar();

    }

    private void summonPillar() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            List<Material> pillarMaterials = new ArrayList<>(Arrays.asList(Material.OBSIDIAN, Material.CRYING_OBSIDIAN, CustomMaterial.REINFORCED_OBSIDIAN.getVanillaMaterial()));

            int radius = 60;
            for (int d = 0; d <= 12; d++) {

                Location pillarCenter = origin.clone();

                pillarCenter.setX(Math.round(pillarCenter.getX() + Math.cos(d) * radius));
                pillarCenter.setZ(Math.round(pillarCenter.getZ() + Math.sin(d) * radius));
                pillarCenter.toCenterLocation();

                int pillarHeight = new Random().nextInt(80, 140);

                Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
                    double pillarRadius = 2;
                    for (int y = 20; y <= pillarHeight; y++) {
                        for (int x = (int) -pillarRadius; x <= pillarRadius; x++) {
                            for (int z = (int) -pillarRadius; z <= pillarRadius; z++) {
                                int finalX = x, finalY = y, finalZ = z;
                                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(pillarRadius, 2)) {
                                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                                        origin.getWorld().getBlockAt((int) (finalX + pillarCenter.getX()), finalY, (int) (finalZ + pillarCenter.getZ())).setType(drawMaterial());
                                    });
                                }
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        pillarRadius += 0.025;
                    }

                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                        Location dragonCore = pillarCenter.clone();
                        dragonCore.setY(pillarHeight + 1);
                        dragonCore.setX(dragonCore.getX() + 0.5);
                        dragonCore.setZ(dragonCore.getZ() + 0.5);
                        dragonCore.getBlock().setType(Material.ANCIENT_DEBRIS);
                        dragonCore.setY(dragonCore.getY() + 1);
                        dragonCore.getWorld().spawnEntity(dragonCore, EntityType.ENDER_CRYSTAL);
                        dragonCore.getWorld().strikeLightningEffect(dragonCore);
                    });

                });
            }
        });
    }

    private Material drawMaterial() {
        List<PillarMaterial> pillarMaterials = new ArrayList<>();
        pillarMaterials.add(new PillarMaterial(Material.OBSIDIAN, 75));
        pillarMaterials.add(new PillarMaterial(Material.CRYING_OBSIDIAN, 15));
        pillarMaterials.add(new PillarMaterial(CustomMaterial.REINFORCED_OBSIDIAN.getVanillaMaterial(), 10));

        int draw = new Random().nextInt(1, 100);

        for (PillarMaterial pillarMaterial : pillarMaterials) {
            if (pillarMaterial.luck() >= draw)
                return pillarMaterial.material();
            draw -= pillarMaterial.luck();
        }

        return Material.OBSIDIAN;

    }

    @Override
    public void death(Location location) {
        killBossBar();

        // Spawn histerite block
        for (int x = 0; x <= new Random().nextInt(64, 128); x++)
            location.getWorld().dropItem(drawCustomLoc(location, 15), new CustomItemStack(CustomMaterial.HISTERITE_BLOCK, 1).getItem());

        // Spawn nocturite nuggets
        for (int x = 0; x <= new Random().nextInt(64, 128); x++)
            location.getWorld().dropItem(drawCustomLoc(location, 15), new CustomItemStack(CustomMaterial.NOCTURITE_NUGGET, 1).getItem());

        // Drop unique items
        location.getWorld().dropItem(location, new CustomItemStack(CustomMaterial.DRAGON_WING, 1).getItem());

        // Spawn red matter
        for (int x = 0; x <= new Random().nextInt(1, 8); x++)
            location.getWorld().dropItem(drawCustomLoc(location, 15), new CustomItemStack(CustomMaterial.RED_MATTER, 1).getItem());

        displayDamageStats();

        // Give rewards for the 5 best damagers
        List<PlayerDamager> bestDamagers = getSortedDamager();
        int multplier = 5;
        int goldReward = 250000;

        for (int x = 0; x <= multplier - 1; x++) {
            if (x >= bestDamagers.size()) return;
            CustomPlayer player = CustomPlayer.getCustomPlayer(bestDamagers.get(x).getPlayer().getName());
            player.getBankAccount().addMoney(goldReward * multplier);
            player.getPlayer().sendMessage(Message.PLAYER_PREFIX + "§a You won " + Utils.formatBigNumber(goldReward * multplier) + "§f" + Emote.GOLD + " §ato be the N°" + (x + 1) + " damage dealer !");
            multplier--;
        }
    }

    private Location drawCustomLoc(Location location, int radius) {
        Location radomLoc = location.clone();
        radomLoc.setZ(location.getZ() + new Random().nextInt(-radius, radius));
        radomLoc.setX(location.getX() + new Random().nextInt(-radius, radius));
        radomLoc.setY(location.getY() + new Random().nextInt(-radius, radius));
        return radomLoc;
    }

    @Override
    public void drawCustomAttack() {

    }

    @Override
    public LivingEntity getEntity() {
        return dragon;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Nullable
    public static TheDragon getSpecifiedBoss(Entity entity) {
        if (!entity.getPersistentDataContainer().has(key)) return null;
        UUID uuid = UUID.fromString(entity.getPersistentDataContainer().get(key, PersistentDataType.STRING));

        if (!dragonList.containsKey(uuid)) return null;

        return dragonList.get(uuid);
    }

}

record PillarMaterial(Material material, int luck) {
}
