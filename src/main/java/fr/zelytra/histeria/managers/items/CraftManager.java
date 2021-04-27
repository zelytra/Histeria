package fr.zelytra.histeria.managers.items;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

public class CraftManager {

    public CraftManager(){
        initCraftrecipe();
        initBlacklistedCraft();
    }

    private void initBlacklistedCraft() {
        List<Recipe> recipes = Bukkit.getRecipesFor(new ItemStack(Material.PURPUR_BLOCK));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.FLETCHING_TABLE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.HONEYCOMB_BLOCK)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHISELED_NETHER_BRICKS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CRACKED_NETHER_BRICKS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.PURPUR_PILLAR)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LODESTONE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_HELMET)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_LEGGINGS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_BOOTS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_HELMET)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_CHESTPLATE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_LEGGINGS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_BOOTS)));
        recipes.stream().filter(r -> r instanceof Keyed).map(r -> ((Keyed) r).getKey()).forEach(Bukkit::removeRecipe);
    }

    private void initCraftrecipe() {
    }
}
