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

        Map<Enchantment, Integer> enchantToAdd = draw(e.getExpLevelCost(),e.getItem());

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

        HashMap<Enchantment, Integer> E = new HashMap<>(); //Contient les enchants possibles
        NavigableMap<Integer, Enchantment> weightMap = new TreeMap<>(); //Contient les poids des enchants
        Map<Enchantment, Integer> results = new HashMap<>(); //Enchants obtenus

        int total = 0; //total du poids

        for (Enchantment ench : CustomEnchant.values()) {
            if (CustomEnchantUtils.isCustom(ench) && ench.canEnchantItem(item)) { //Pour tous les enchants custom possibles
                //TODO Enchant conflict

                int a = ((CustomEnchant) ench).getData().enchantRarity.startWindow(); //fenêtre dans laquelle on peut tirer l'enchant au lvl 1
                int b = ((CustomEnchant) ench).getData().enchantRarity.getMaxWindows();
                int n = ench.getMaxLevel();

                for (int i = ench.getStartLevel(); i <= n; i++) { //Pour tous les niveaux de l'enchant
                    if ((costLevel <= (30 - b) / n + b) && (costLevel >= (30 - b) / n + a)) { //La fenêtre se décale par niveau jusqu'à atteindre le lvl 30
                        if (!E.containsKey(ench)) {
                            total += ((CustomEnchant) ench).getData().enchantRarity.weight(); //pour le calcul du poids
                            weightMap.put(total, ench);
                        }
                        E.put(ench, i); //on garde l'enchant au niveau le plus grand trouvé TODO Pas tout le temp le max
                    }
                }
            }
        }

        int voidWeight = 0; //TODO à changer si on veut tirer moins souvent les enchants custom
        total += voidWeight;
        weightMap.put(total, null);

        int rdm = (int) (random.nextDouble(0, 1) * total); //tire selon le poids un enchant
        Enchantment tirage = weightMap.higherEntry(rdm).getValue();

        if (tirage == null) return results;

        results.put(tirage, E.get(tirage));

        while (random.nextDouble(0, 1) < costLevel / 35f) { //Pour les éventuels enchants supplémentaires
            rdm = (int) (random.nextDouble(0, 1) * total);
            tirage = weightMap.higherEntry(rdm).getValue();

            if (tirage == null) return results;
            results.put(tirage, E.get(tirage));

            costLevel = costLevel / 3; //Au lvl 30, 2% de chance d'avoir 3 tirages d'enchant
        }

        return results;
    }
}
