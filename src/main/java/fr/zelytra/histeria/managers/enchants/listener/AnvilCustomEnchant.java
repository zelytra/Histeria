package fr.zelytra.histeria.managers.enchants.listener;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

public class AnvilCustomEnchant implements Listener {

    @EventHandler
    public void onFusion(PrepareAnvilEvent e) {

        ItemStack firstItem = e.getInventory().getFirstItem();
        ItemStack secondItem = e.getInventory().getSecondItem();
        ItemStack result = e.getInventory().getResult();
        boolean isVanillaFusion = result != null;

        if (firstItem == null || secondItem == null) return;
        if (secondItem.getType() != Material.ENCHANTED_BOOK && firstItem.getType() != secondItem.getType()) return;

        if (CustomEnchant.hasCustomEnchants(firstItem) || CustomEnchant.hasCustomEnchants(secondItem)) {

            if (!isVanillaFusion)
                result = firstItem.clone();

            if (secondItem.getItemMeta() instanceof EnchantmentStorageMeta)
                result.addEnchantments(getCustomEnchantFusion(firstItem.getEnchantments(), ((EnchantmentStorageMeta) secondItem.getItemMeta()).getStoredEnchants()));
            else
                result.addEnchantments(getCustomEnchantFusion(firstItem.getEnchantments(), secondItem.getEnchantments()));

            Utils.updateCustomEnchant(result);
            e.setResult(result);
        }

    }

    private Map<Enchantment, Integer> getCustomEnchantFusion(Map<Enchantment, Integer> enchantFrom, Map<Enchantment, Integer> enchantWith) {
        Map<Enchantment, Integer> finalEnchants = new HashMap<>();

        // Check the first item enchant list
        if (enchantFrom.size() >= 1)
            for (var enchants : enchantFrom.entrySet()) {

                if (!CustomEnchant.isCustom(enchants.getKey())) continue;

                // Check enchant not match with second item
                if (!enchantWith.containsKey(enchants.getKey())) {
                    finalEnchants.put(enchants.getKey(), enchants.getValue());
                    continue;
                }

                // Check not same lvl enchant for fusion
                if (enchants.getValue() != enchantWith.get(enchants.getKey())) {
                    if (enchants.getValue() >= enchantWith.get(enchants.getKey()))
                        finalEnchants.put(enchants.getKey(), enchants.getValue());
                    else
                        finalEnchants.put(enchants.getKey(), enchantWith.get(enchants.getKey()));
                    continue;
                }

                // Check enchant max level saturation
                if (enchants.getValue() >= enchants.getKey().getMaxLevel()) {
                    finalEnchants.put(enchants.getKey(), enchants.getValue());
                    continue;
                }

                // Adding custom enchant with lvl addition
                finalEnchants.put(enchants.getKey(), enchants.getValue() + 1);

            }
        else {
            return enchantWith;
        }

        return finalEnchants;
    }
}
