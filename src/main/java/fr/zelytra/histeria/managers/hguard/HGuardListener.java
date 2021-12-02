/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.hguard;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemLaunchEvent;
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.utils.Utils;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HGuardListener implements Listener {
    private final static List<EntityType> whitelist = new ArrayList<>();

    {
        whitelist.add(EntityType.ITEM_FRAME);
        whitelist.add(EntityType.ARMOR_STAND);
        whitelist.add(EntityType.DROPPED_ITEM);
        whitelist.add(EntityType.FIREWORK);
        whitelist.add(EntityType.SNOWBALL);
        whitelist.add(EntityType.ARROW);
        whitelist.add(EntityType.ENDER_PEARL);
    }

    public static void startEntityKiller() {
        Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Entity entity : player.getLocation().getNearbyEntities(64, 64, 64)) {
                    if (HGuard.getByLocation(entity.getLocation()) == null)
                        continue;

                    if (!(entity instanceof Player) && !whitelist.contains(entity.getType()))
                        entity.remove();

                }
            }
        }, 0L, 10L);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlaceBlock(BlockPlaceEvent e) {
        HGuard hguard = getHguard(e.getBlock().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (!hguard.canPlaceBlock()) {
            e.setCancelled(true);
        }


    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent e) {

        HGuard hguard = getHguard(e.getBlock().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (!hguard.canBreakBlock()) {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketFill(PlayerBucketFillEvent e) {
        HGuard hguard = getHguard(e.getBlock().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (!hguard.canPlaceBlock())
            e.setCancelled(true);


    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {

        HGuard hguard = getHguard(e.getBlock().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (!hguard.canPlaceBlock())
            e.setCancelled(true);


    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent e) {
        HGuard hguard;

        if (e.getClickedBlock() != null) {
            if (HGuard.getByLocation(e.getClickedBlock().getLocation()) == null) {
                return;
            }
            hguard = HGuard.getByLocation(e.getClickedBlock().getLocation());
            if (hguard.getInteractWhiteList().contains(e.getClickedBlock().getType()) || (hguard.getInteractWhiteList().contains(e.getMaterial())) || Utils.isFood(e.getMaterial()))
                return;
        } else {
            if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
                return;
            }
            hguard = HGuard.getByLocation(e.getPlayer().getLocation());
            if (hguard.getInteractWhiteList().contains(e.getMaterial()) || Utils.isFood(e.getMaterial()))
                return;
        }
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }
        if (hguard.canBreakBlock() || hguard.canPlaceBlock())
            return;


        e.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {

        HGuard hguard = getHguard(e.getPlayer().getLocation(), e.getPlayer());
        if (hguard == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {

        HGuard hguard = getHguard(e.getPlayer().getLocation(), e.getPlayer());
        if (hguard == null) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCustomItemUse(CustomItemUseEvent e) {

        HGuard hguard = getHguard(e.getPlayer().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (hguard.getCustomItemWhiteList().contains(e.getMaterial()))
            return;

        e.setCancelled(true);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileLaunch(CustomItemLaunchEvent e) {

        HGuard hguard = getHguard(e.getPlayer().getLocation(), e.getPlayer());
        if (hguard == null) return;

        if (hguard.getCustomItemWhiteList().contains(e.getMaterial()))
            return;

        if (!hguard.isPvp())
            e.setCancelled(true);


    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        HGuard hguard = getHguard(e.getEntity().getLocation());
        if (hguard == null) return;

        if (!hguard.isPvp())
            e.setCancelled(true);

    }


    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks()) {
            if (HGuard.getByLocation(block.getLocation()) != null) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent e) {

        for (Block block : e.getBlocks()) {
            if (HGuard.getByLocation(block.getLocation()) != null) {
                e.setCancelled(true);
            }
        }


    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {

        if (HGuard.getByLocation(e.getLocation()) == null) {
            return;
        }

        HGuard hguard = HGuard.getByLocation(e.getLocation());

        if (!hguard.isPvp())
            e.setCancelled(true);

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        HGuard hguard;
        if (e.getHitEntity() != null) {
            if (HGuard.getByLocation(e.getHitEntity().getLocation()) == null) {
                return;
            }
            hguard = HGuard.getByLocation(e.getHitEntity().getLocation());
        } else {
            if (HGuard.getByLocation(e.getHitBlock().getLocation()) == null) {
                return;
            }
            hguard = HGuard.getByLocation(e.getHitBlock().getLocation());
        }
        if (!hguard.isPvp())
            e.setCancelled(true);

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getEntity().getLocation());

        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) e;
            if (damageByEntityEvent.getDamager() instanceof Player) {
                if (Histeria.getLuckPerms() != null) {
                    User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser((Player) damageByEntityEvent.getDamager());
                    if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                        return;
                }
            }
        }

        if (!hguard.isPvp())
            e.setCancelled(true);
        else if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);


    }


    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
            return;
        }
        e.setCancelled(true);


    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent e) {
        HGuard hguard = getHguard(e.getEntity().getLocation(), (Player) e.getRemover());
        if (hguard == null) return;
        e.setCancelled(true);

    }


    //TODO Liquid in border area

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {

        if (HGuard.getByLocation(e.getEntity().getLocation()) == null) {
            return;
        }

        if (whitelist.contains(e.getEntity().getType())) return;

        if (e.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.COMMAND ||
                e.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM ||
                e.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.ENDER_PEARL) {
            return;
        }

        e.setCancelled(true);

    }

    @EventHandler
    public void onBlockEat(EntityChangeBlockEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) != null) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) != null) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onWitherSpawn(ExplosionPrimeEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) != null) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        for (Block block : e.blockList()) {
            if (HGuard.getByLocation(block.getLocation()) != null) {
                e.setCancelled(true);
            }
        }

    }

    private @Nullable HGuard getHguard(Location location, Player player) {
        HGuard hguard = null;
        if (HGuard.getByLocation(location) != null) {
            hguard = HGuard.getByLocation(location);
            if (Histeria.getLuckPerms() != null) {
                User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
                if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                    return hguard;
            }
        }
        return hguard;
    }

    private @Nullable HGuard getHguard(Location location) {

        HGuard hguard = null;

        if (HGuard.getByLocation(location) != null)
            hguard = HGuard.getByLocation(location);

        return hguard;
    }

}
