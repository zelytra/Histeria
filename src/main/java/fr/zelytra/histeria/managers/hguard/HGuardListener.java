package fr.zelytra.histeria.managers.hguard;

import fr.zelytra.histeria.Histeria;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HGuardListener implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if (HGuard.getByLocation(e.getBlock().getLocation()) == null) {
            return;
        }
        HGuard hguard = HGuard.getByLocation(e.getBlock().getLocation());
        if (Histeria.getLuckPerms() != null) {
            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(e.getPlayer());
            if(hguard.getGroupWhiteList().contains(user.getPrimaryGroup()))
                return;
        }

        if (!hguard.canPlaceBlock()) {
            e.setCancelled(true);
            return;
        }

    }

    //TODO BreakBlock

    //TODO WaterPlace

    //TODO WaterRemove

    //TODO Interact

    //TODO CustomItem

    //TODO Saturation

    //TODO Entity Listener

    //TODO Piston

    //TODO Projectile Launch

    //TODO Projectile hit

    //TODO EntityDamageEvent

    //TODO Drop item

    //TODO HangingBreakEvent

    //TODO Liquid in border area

}
