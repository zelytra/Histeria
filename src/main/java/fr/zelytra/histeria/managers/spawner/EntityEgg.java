/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.spawner;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum EntityEgg {

    ZOMBIE(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG),
    CREEPER(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG),
    SKELETON(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SPAWN_EGG),
    SHEEP(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG),
    COW(EntityType.COW, Material.COW_SPAWN_EGG),
    CHICKEN(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG),
    SPIDER(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, Material.CAVE_SPIDER_SPAWN_EGG),
    ENDERMAN(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG),
    WITCH(EntityType.WITCH, Material.WITCH_SPAWN_EGG),
    VILLAGER(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG),
    PILLAGER(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG),
    BLAZE(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG),
    GLOW_SQUID(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG),
    SQUID(EntityType.SQUID, Material.SQUID_SPAWN_EGG),
    SLIME(EntityType.SLIME, Material.SLIME_SPAWN_EGG),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG),
    ;

    public final EntityType entity;
    public final Material egg;

    EntityEgg(EntityType entity, Material egg) {
        this.entity = entity;
        this.egg = egg;
    }

    @Contract(pure = true)
    public static @Nullable Material getEgg(EntityType entity) {
        for (EntityEgg entityEgg : EntityEgg.values())
            if (entity == entityEgg.entity)
                return entityEgg.egg;
        return null;
    }

    @Contract(pure = true)
    public static @Nullable EntityType getEntity(Material egg) {
        for (EntityEgg entityEgg : EntityEgg.values())
            if (egg == entityEgg.egg)
                return entityEgg.entity;
        return null;
    }
}
