package fr.zelytra.histeria.managers.enchants.listener;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
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

        if (CustomEnchantUtils.hasCustomEnchants(firstItem) || CustomEnchantUtils.hasCustomEnchants(secondItem)) {

            if (!isVanillaFusion)
                result = firstItem.clone();

            // Getting customEnchant to add
            Map<Enchantment, Integer> enchantToAdd;
            if (secondItem.getItemMeta() instanceof EnchantmentStorageMeta && firstItem.getItemMeta() instanceof EnchantmentStorageMeta)
                enchantToAdd = getCustomEnchantFusion(((EnchantmentStorageMeta) firstItem.getItemMeta()).getStoredEnchants(), ((EnchantmentStorageMeta) secondItem.getItemMeta()).getStoredEnchants());
            else if (secondItem.getItemMeta() instanceof EnchantmentStorageMeta)
                enchantToAdd = getCustomEnchantFusion(firstItem.getEnchantments(), ((EnchantmentStorageMeta) secondItem.getItemMeta()).getStoredEnchants());
            else
                enchantToAdd = getCustomEnchantFusion(firstItem.getEnchantments(), secondItem.getEnchantments());

            // Check item type conflict
            for (var enchantConflict : enchantToAdd.entrySet())
                if (!enchantConflict.getKey().canEnchantItem(firstItem))
                    return;


            // Cancel handler when no enchant custom
            if (enchantToAdd.isEmpty())
                return;

            //Adding enchant depending of result type
            if (result.getItemMeta() instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) result.getItemMeta();

                for (var enchantsToAdd : enchantToAdd.entrySet())
                    meta.addStoredEnchant(enchantsToAdd.getKey(), enchantsToAdd.getValue(), false);

                result.setItemMeta(meta);

            } else
                result.addEnchantments(enchantToAdd);

            CustomEnchantUtils.updateCustomEnchant(result);
            e.setResult(result);
        }

    }

    private Map<Enchantment, Integer> getCustomEnchantFusion(Map<Enchantment, Integer> enchantFrom, Map<Enchantment, Integer> enchantWith) {
        Map<Enchantment, Integer> finalEnchants = new HashMap<>();

        // Check the first item enchant list
        if (enchantFrom.size() >= 1)
            for (var enchants : enchantFrom.entrySet()) {

                //Checking enchant conflicts
                boolean needBreak = false;
                for (var enchantWithList : enchantWith.entrySet())
                    needBreak = enchants.getKey().conflictsWith(enchantWithList.getKey());
                if (needBreak) break;

                if (!CustomEnchantUtils.isCustom(enchants.getKey())) continue;

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
