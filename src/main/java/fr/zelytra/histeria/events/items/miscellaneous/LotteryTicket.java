package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LotteryTicket implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.LOTTERY_TICKET;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {
        if (e.getMaterial() != customMaterial) return;
        Player player = e.getPlayer();

        //TODO Event action

        ItemFunction.removeHeldItem(player, customMaterial);
        return;
    }


}
