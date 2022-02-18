/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.listener;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.*;

public class EnchantingTableCustomEnchant implements Listener {

    private final static Random random = new Random();

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEnchanter().getName());
        if (player == null) return;

        if (player.getJob(JobType.ENCHANTER).getLevel() != 100) return;
        if (e.getItem().getType() == Material.BOOK) e.getItem().setType(Material.ENCHANTED_BOOK);

        Map<Enchantment, Integer> enchantToAdd = draw(e.getExpLevelCost(), e.getItem());

        //Adding enchant depending of result type
        if (e.getItem().getItemMeta() instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) e.getItem().getItemMeta();

            for (var enchantsToAdd : enchantToAdd.entrySet())
                meta.addStoredEnchant(enchantsToAdd.getKey(), enchantsToAdd.getValue(), false);

            e.getItem().setItemMeta(meta);

        } else
            e.getItem().addEnchantments(enchantToAdd);

        CustomEnchantUtils.updateCustomEnchant(e.getItem());

    }

    /**
     * @param costLevel Cout d'enchant en XP (0-30)
     * @param item
     * @return
     */
    private Map<Enchantment, Integer> draw(int costLevel, ItemStack item) {

        costLevel += random.nextGaussian();
        costLevel = Math.max(0, Math.min(30, costLevel));

        HashMap<Enchantment, Integer> E = new HashMap<>(); //Contient les enchants possibles
        NavigableMap<Integer, Enchantment> weightMap = new TreeMap<>(); //Contient les poids des enchants
        Map<Enchantment, Integer> results = new HashMap<>(); //Enchants obtenus

        int total = 0; //total du poids

        for (Enchantment ench : CustomEnchant.values()) {
            if (!CustomEnchantUtils.isCustom(ench) || !ench.canEnchantItem(item)) continue;
            //Pour tous les enchants custom possibles

            int a = ((CustomEnchant) ench).getData().enchantRarity.startWindow(); //fenêtre dans laquelle on peut tirer l'enchant au lvl 1
            int b = ((CustomEnchant) ench).getData().enchantRarity.drawSaturation();
            int n = ench.getMaxLevel();

            float delta = (float) (b - a) / n;

            for (int i = ench.getStartLevel(); i <= n; i++) { //Pour tous les niveaux de l'enchant
                if ((costLevel < a + (i - 1) * delta) || (costLevel > a + i * delta))
                    continue; //La fenêtre se décale par niveau jusqu'à atteindre le lvl 30

                total += ((CustomEnchant) ench).getData().enchantRarity.weight(); //pour le calcul du poids

                weightMap.put(total, ench);
                E.put(ench, i);
            }
        }

        int voidWeight = total;
        total += voidWeight;
        weightMap.put(total, null);

        int rdm = (random.nextInt(total)); //tire selon le poids un enchant
        Enchantment tirage = weightMap.higherEntry(rdm).getValue();

        if (tirage == null) return results;

        results.put(tirage, E.get(tirage));

        while (random.nextFloat() < costLevel / 35f) { //Pour les éventuels enchants supplémentaires
            rdm = random.nextInt(total);
            tirage = weightMap.higherEntry(rdm).getValue();

            if (tirage == null) return results;

            for (Enchantment conflict : results.keySet()) {
                if (conflict.conflictsWith(tirage)) return results;
            }
            results.put(tirage, E.get(tirage));

            costLevel = costLevel / 3; //Au lvl 30, 2% de chance d'avoir 3 tirages d'enchant
        }

        return results;
    }
}
