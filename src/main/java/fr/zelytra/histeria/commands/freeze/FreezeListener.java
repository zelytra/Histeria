package fr.zelytra.histeria.commands.freeze;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        if (!Freeze.isFrozen(e.getPlayer())) return;
        LangMessage.sendTitle(e.getPlayer(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);

    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (!Freeze.isFrozen(e.getPlayer())) return;
        LangMessage.sendTitle(e.getPlayer(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (!Freeze.isFrozen(e.getPlayer())) return;
        LangMessage.sendTitle(e.getPlayer(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e) {
        if (!Freeze.isFrozen(e.getPlayer())) return;
        LangMessage.sendTitle(e.getPlayer(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (!Freeze.isFrozen(e.getPlayer())) return;
        LangMessage.sendTitle(e.getPlayer(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

    @EventHandler
    public void inventory(InventoryClickEvent e) {
        if (!Freeze.isFrozen((Player) e.getWhoClicked())) return;
        LangMessage.sendTitle((Player) e.getWhoClicked(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        if (!Freeze.isFrozen((Player) e.getEntity())) return;
        LangMessage.sendTitle((Player) e.getEntity(),"command.freezeTitle","command.freezeSubTitle",0,100,10);
        e.setCancelled(true);
    }

}
