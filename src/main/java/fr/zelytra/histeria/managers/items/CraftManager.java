package fr.zelytra.histeria.managers.items;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.customCraft.ShapeLessRecipeBuilder;
import fr.zelytra.histeria.builder.customCraft.ShapedRecipeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

public class CraftManager {

    public CraftManager() {
        initBlacklistedCraft();
        initCraftRecipe();
    }

    private void initBlacklistedCraft() {
        List<Recipe> recipes = Bukkit.getRecipesFor(new ItemStack(Material.PURPUR_BLOCK));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.FLETCHING_TABLE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.HONEYCOMB_BLOCK)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHISELED_NETHER_BRICKS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CRACKED_NETHER_BRICKS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.PURPUR_PILLAR)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.PURPUR_SLAB)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LODESTONE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_HELMET)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_LEGGINGS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.CHAINMAIL_BOOTS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_HELMET)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_CHESTPLATE)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_LEGGINGS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.LEATHER_BOOTS)));
        recipes.addAll(Bukkit.getRecipesFor(new ItemStack(Material.WRITABLE_BOOK)));
        recipes.stream().filter(r -> r instanceof Keyed).map(r -> ((Keyed) r).getKey()).forEach(Bukkit::removeRecipe);
    }

    private void initCraftRecipe() {
        ShapedRecipeBuilder shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.ELEVATOR);
        shapedRecipeBuilder.setShape("A*A", "*B*", "A*A");
        shapedRecipeBuilder.assigneSymbol('*', Material.DIAMOND);
        shapedRecipeBuilder.assigneSymbol('A', Material.IRON_BLOCK);
        shapedRecipeBuilder.assigneSymbol('B', Material.ENDER_PEARL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.ALPIN_HOOK);
        shapedRecipeBuilder.setShape("aaa", "*d*", "*n*");
        shapedRecipeBuilder.assigneSymbol('a', Material.IRON_INGOT);
        shapedRecipeBuilder.assigneSymbol('d', Material.DIAMOND);
        shapedRecipeBuilder.assigneSymbol('n', Material.NETHERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.CAVE_BLOCK);
        shapedRecipeBuilder.setShape("aaa", "a%a", "aaa");
        shapedRecipeBuilder.assigneSymbol('a', Material.GLASS);
        shapedRecipeBuilder.assigneSymbol('%', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_HELMET);
        shapedRecipeBuilder.setShape("///", "/*/", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_CHESTPLATE);
        shapedRecipeBuilder.setShape("/*/", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_LEGGINGS);
        shapedRecipeBuilder.setShape("///", "/*/", "/*/");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_BOOTS);
        shapedRecipeBuilder.setShape("/*/", "/*/", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_HELMET);
        shapedRecipeBuilder.setShape("///", "/*/", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_CHESTPLATE);
        shapedRecipeBuilder.setShape("/*/", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_LEGGINGS);
        shapedRecipeBuilder.setShape("///", "/*/", "/*/");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_BOOTS);
        shapedRecipeBuilder.setShape("/*/", "/*/", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_APPLE);
        shapedRecipeBuilder.setShape("///", "/a/", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('a', Material.APPLE);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.OBSIDIAN_BREAKER);
        shapedRecipeBuilder.setShape("b/b", "*z*", "*a*");
        shapedRecipeBuilder.assigneSymbol('/', Material.CRYING_OBSIDIAN);
        shapedRecipeBuilder.assigneSymbol('z', Material.STICK);
        shapedRecipeBuilder.assigneSymbol('a', Material.DIAMOND);
        shapedRecipeBuilder.assigneSymbol('b', Material.IRON_BLOCK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_BLOCK);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_BLOCK);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.setShape("%**", "s**", "%**");
        shapedRecipeBuilder.assigneSymbol('%', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('s', Material.STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_PICKAXE);
        shapedRecipeBuilder.setShape("///", "*%*", "*%*");
        shapedRecipeBuilder.assigneSymbol('%', CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_BLOCK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_BLOCK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_CRYSTAL);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_BLOCK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.COMPRESS_COBBLESTONE);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', Material.COBBLESTONE);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HAMMER);
        shapedRecipeBuilder.setShape("/%/", "*s*", "*x*");
        shapedRecipeBuilder.assigneSymbol('/', Material.NETHERITE_BLOCK);
        shapedRecipeBuilder.assigneSymbol('%', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.assigneSymbol('s', Material.STICK);
        shapedRecipeBuilder.assigneSymbol('x', Material.GOLD_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.DYNAMITE);
        shapedRecipeBuilder.setShape("/**", "%**", "s**");
        shapedRecipeBuilder.assigneSymbol('%', Material.GUNPOWDER);
        shapedRecipeBuilder.assigneSymbol('/', Material.STRING);
        shapedRecipeBuilder.assigneSymbol('s', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_CORE);
        shapedRecipeBuilder.setShape("a/a", "/r/", "a/a");
        shapedRecipeBuilder.assigneSymbol('a', Material.IRON_INGOT);
        shapedRecipeBuilder.assigneSymbol('/', Material.REDSTONE);
        shapedRecipeBuilder.assigneSymbol('r', CustomMaterial.HISTERITE_CRYSTAL);
        shapedRecipeBuilder.register();

        ShapeLessRecipeBuilder shapelessRecipe = new ShapeLessRecipeBuilder(CustomMaterial.HISTERITE_INGOT, 9);
        shapelessRecipe.setIngredient(CustomMaterial.HISTERITE_BLOCK);
        shapelessRecipe.register();

        shapelessRecipe = new ShapeLessRecipeBuilder(CustomMaterial.NOCTURITE_INGOT, 9);
        shapelessRecipe.setIngredient(CustomMaterial.NOCTURITE_BLOCK);
        shapelessRecipe.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_NUGGET, 9);
        shapedRecipeBuilder.setShape("/**", "***", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_NUGGET, 9);
        shapedRecipeBuilder.setShape("/**", "***", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_CORE);
        shapedRecipeBuilder.setShape("a/a", "/r/", "a/a");
        shapedRecipeBuilder.assigneSymbol('a', Material.IRON_INGOT);
        shapedRecipeBuilder.assigneSymbol('/', Material.REDSTONE);
        shapedRecipeBuilder.assigneSymbol('r', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.CRUSHING_TABLE);
        shapedRecipeBuilder.setShape("aa*", "ss*", "xx*");
        shapedRecipeBuilder.assigneSymbol('a', Material.IRON_INGOT);
        shapedRecipeBuilder.assigneSymbol('s', Material.SPRUCE_PLANKS);
        shapedRecipeBuilder.assigneSymbol('x', Material.NETHERITE_BLOCK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.REINFORCED_OBSIDIAN);
        shapedRecipeBuilder.setShape("a/a", "///", "a/a");
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('/', Material.OBSIDIAN);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.SHURIKEN);
        shapedRecipeBuilder.setShape("*/*", "///", "*/*");
        shapedRecipeBuilder.assigneSymbol('/', Material.IRON_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.UNCLAIM_FINDER);
        shapedRecipeBuilder.setShape("ici", "dod", "ivi");
        shapedRecipeBuilder.assigneSymbol('i', Material.IRON_BLOCK);
        shapedRecipeBuilder.assigneSymbol('d', Material.DIAMOND_BLOCK);
        shapedRecipeBuilder.assigneSymbol('c', Material.TRAPPED_CHEST);
        shapedRecipeBuilder.assigneSymbol('o', Material.COMPASS);
        shapedRecipeBuilder.assigneSymbol('v', Material.ENDER_CHEST);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_NUGGET);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_INGOT);
        shapedRecipeBuilder.setShape("///", "///", "///");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_NUGGET);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_SWORD);
        shapedRecipeBuilder.setShape("/**", "/**", "a**");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_AXE);
        shapedRecipeBuilder.setShape("//*", "a/*", "a**");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_SHOVEL);
        shapedRecipeBuilder.setShape("/**", "a**", "a**");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_HOE);
        shapedRecipeBuilder.setShape("//*", "a**", "a**");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_SWORD);
        shapedRecipeBuilder.setShape("/**", "/**", "a**");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_CRYSTAL);
        shapedRecipeBuilder.assigneSymbol('a', Material.STICK);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_GENERATOR);
        shapedRecipeBuilder.setShape("///", "aba", "///");
        shapedRecipeBuilder.assigneSymbol('/', Material.CRYING_OBSIDIAN);
        shapedRecipeBuilder.assigneSymbol('a', CustomMaterial.HISTERITE_CRYSTAL);
        shapedRecipeBuilder.assigneSymbol('b', CustomMaterial.NOCTURITE_CORE);
        shapedRecipeBuilder.register();

        NamespacedKey key = new NamespacedKey(Histeria.getInstance(), "histerite_ore");
        Bukkit.getServer().addRecipe(new FurnaceRecipe(key, new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 1).getItem(), CustomMaterial.HISTERITE_ORE.getVanillaMaterial(), (float) 1.5,180));
        Bukkit.getServer().addRecipe(new BlastingRecipe(key, new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 1).getItem(), CustomMaterial.HISTERITE_ORE.getVanillaMaterial(),(float) 1.5, 100));


    }


}
