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
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.block.Block;
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

public class HGuardListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlaceBlock(BlockPlaceEvent e) {
        if (HGuard.getByLocation(e.getBlock().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getBlock().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        if (!hguard.canPlaceBlock()) {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent e) {
        if (HGuard.getByLocation(e.getBlock().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getBlock().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        if (!hguard.canBreakBlock()) {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketFill(PlayerBucketFillEvent e) {
        if (HGuard.getByLocation(e.getBlock().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getBlock().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }
        if (!hguard.canPlaceBlock()) {
            e.setCancelled(true);
        }
        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        if (HGuard.getByLocation(e.getBlock().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getBlock().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }
        if (!hguard.canPlaceBlock()) {
            e.setCancelled(true);
        }
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
            if (hguard.getInteractWhiteList().contains(e.getClickedBlock().getType()))
                return;
        } else {
            if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
                return;
            }
            hguard = HGuard.getByLocation(e.getPlayer().getLocation());
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
        if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getPlayer().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getPlayer().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onCustomItemUse(CustomItemEvent e) {
        if (HGuard.getByLocation(e.getPlayer().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getPlayer().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        if (hguard.getCustomItemWhiteList().contains(e.getCustomMaterial()))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) == null) {
            return;
        }
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

    @EventHandler
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
        if (HGuard.getByLocation(e.getEntity().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getEntity().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser((Player) e.getRemover());
            if (hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }
        e.setCancelled(true);

    }


    //TODO Liquid in border area

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (HGuard.getByLocation(e.getEntity().getLocation()) == null) {
            return;
        }

        if (e.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.COMMAND) {
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


}
