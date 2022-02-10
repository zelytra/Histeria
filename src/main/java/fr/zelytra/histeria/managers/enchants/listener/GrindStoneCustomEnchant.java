package fr.zelytra.histeria.managers.enchants.listener;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class GrindStoneCustomEnchant implements Listener {

    @EventHandler
    public void onDisenchant(PrepareResultEvent e) {

        if (e.getInventory().getType() != InventoryType.GRINDSTONE) return;
        if (e.getResult() == null || e.getInventory().getItem(0) == null) return;

        ItemStack result = e.getResult();
        if (!CustomEnchantUtils.hasCustomEnchants(e.getInventory().getItem(0))) return;

        CustomEnchantUtils.updateCustomEnchant(result);
        e.setResult(result);


    }
}
