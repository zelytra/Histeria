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
import fr.zelytra.histeria.managers.evenements.visual.AttackIntensity;
import fr.zelytra.histeria.managers.evenements.visual.CustomAttack;
import fr.zelytra.histeria.managers.evenements.visual.Laser;
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
    private final static String name = "§bThe Dragon";

    private final double maxHealth;
    private final UUID uuid = UUID.randomUUID();
    private final Location origin;
    private final Location dragonSpawn;

    private EnderDragon dragon;
    private int attackSchedulerId;

    public final static NamespacedKey key = new NamespacedKey(Histeria.getInstance(), "TheDragon");
    public final static EntityType type = EntityType.ENDER_DRAGON;

    public TheDragon(Location location) {

        super(name);
        origin = location.clone();
        maxHealth = lifeScaling();
        location.setY(location.getY() + 50);
        dragonSpawn = location.clone();

        Bukkit.broadcastMessage("§6Something is approaching...");

        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
            dragon = (EnderDragon) dragonSpawn.getWorld().spawnEntity(dragonSpawn, EntityType.ENDER_DRAGON);
            dragon.setPhase(EnderDragon.Phase.CIRCLING);
            dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            dragon.setHealth(maxHealth);
            dragon.setCustomName(name);
            resistanceScaling(dragon);

            getBossBar().setProgress(1);
            getBossBar().setColor(BarColor.BLUE);
            getBossBar().setVisible(true);

            dragon.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());
            LangMessage.broadcast("", "boss.theDragonStart", "");
            startCustomAttackListener();
        }, 15 * 20);

        startBossBarListener(this);
        dragonList.put(uuid, this);

        summonPillar();

    }

    private void startCustomAttackListener() {
        attackSchedulerId = Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> drawCustomAttack(), 0, 5 * 20).getTaskId();
    }

    private void summonPillar() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            int radius = 60;
            for (int d = 0; d <= 12; d++) {

                Location pillarCenter = origin.clone();

                pillarCenter.setX(Math.round(pillarCenter.getX() + Math.cos(d) * radius));
                pillarCenter.setZ(Math.round(pillarCenter.getZ() + Math.sin(d) * radius));
                pillarCenter.setY(20);
                pillarCenter.toCenterLocation();

                int pillarHeight = new Random().nextInt(80, 140);

                // Build pillar
                Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
                    try {

                        double pillarRadius = 2;
                        Laser laserBuilder = new Laser.CrystalLaser(origin.clone(), pillarCenter.clone(), 200, radius);
                        laserBuilder.start(Histeria.getInstance());

                        for (int y = 20; y <= pillarHeight; y++) {
                            for (int x = (int) -pillarRadius; x <= pillarRadius; x++) {
                                for (int z = (int) -pillarRadius; z <= pillarRadius; z++) {

                                    int finalX = x, finalY = y, finalZ = z;

                                    if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(pillarRadius, 2)) {
                                        Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                                            origin.getWorld().getBlockAt((int) (finalX + pillarCenter.getX()), finalY, (int) (finalZ + pillarCenter.getZ())).setType(drawMaterial());
                                        });
                                    }

                                    Thread.sleep(1);
                                }
                            }
                            pillarRadius += 0.025;
                            pillarCenter.setY(y);
                            laserBuilder.moveEnd(pillarCenter);
                        }
                        laserBuilder.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Spawn ender crystal at the top of the pillar
                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                        Location dragonCore = pillarCenter.clone();
                        dragonCore.setY(pillarHeight + 1);
                        dragonCore.setX(dragonCore.getX() + 0.5);
                        dragonCore.setZ(dragonCore.getZ() + 0.5);
                        dragonCore.getBlock().setType(Material.ANCIENT_DEBRIS);
                        dragonCore.setY(dragonCore.getY() + 1);
                        dragonCore.getWorld().strikeLightningEffect(dragonCore);

                        Entity crystal = dragonCore.getWorld().spawnEntity(dragonCore, EntityType.ENDER_CRYSTAL);
                        crystal.getPersistentDataContainer().set(key, PersistentDataType.STRING, "crystal");
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
        Bukkit.getScheduler().cancelTask(attackSchedulerId);

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

        for (int x = 0; x <= 4; x++) {
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
        if (new Random().nextInt(1, 100) >= 25) return;

        switch (dragon.getPhase()) {
            case CIRCLING:
                CustomAttack.potionBombingRaid(dragon);
                break;
            case LAND_ON_PORTAL:
                CustomAttack.dragonBall(dragon, EntityType.DRAGON_FIREBALL);
                break;
            case FLY_TO_PORTAL:
                CustomAttack.vortex(dragon, AttackIntensity.MEDIUM, 50);
                break;
        }
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
