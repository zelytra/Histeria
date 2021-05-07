package fr.zelytra.histeria.managers.items;

import fr.zelytra.histeria.builder.customCraft.ShapeLessRecipeBuilder;
import fr.zelytra.histeria.builder.customCraft.ShapedRecipeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
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
        shapedRecipeBuilder.assigneSymbol('r', CustomMaterial.HISTERITE_CORE);
        shapedRecipeBuilder.register();

        ShapeLessRecipeBuilder shapelessRecipe = new ShapeLessRecipeBuilder(CustomMaterial.HISTERITE_INGOT, 9);
        shapelessRecipe.setIngredient(CustomMaterial.HISTERITE_BLOCK);
        shapelessRecipe.register();

        shapelessRecipe = new ShapeLessRecipeBuilder(CustomMaterial.NOCTURITE_INGOT, 9);
        shapelessRecipe.setIngredient(CustomMaterial.NOCTURITE_BLOCK);
        shapelessRecipe.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.HISTERITE_NUGGET,9);
        shapedRecipeBuilder.setShape("/**", "***", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.HISTERITE_INGOT);
        shapedRecipeBuilder.register();

        shapedRecipeBuilder = new ShapedRecipeBuilder(CustomMaterial.NOCTURITE_NUGGET,9);
        shapedRecipeBuilder.setShape("/**", "***", "***");
        shapedRecipeBuilder.assigneSymbol('/', CustomMaterial.NOCTURITE_INGOT);
        shapedRecipeBuilder.register();


        /*
        shapelessRecipe = new ShapeLessRecipeBuilder(CustomMaterial.NOCTURITE_NUGGET, 9);
        shapelessRecipe.setIngredient(CustomMaterial.NOCTURITE_INGOT);
        shapelessRecipe.register();*/

        /*
        List<Recipe> recipes = Bukkit.getServer().getRecipesFor(new CustomItemStack(CustomMaterial.HISTERITE_HELMET, 1).getItem());
        for (Recipe recipe : recipes) {
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                for (Map.Entry<Character, ItemStack> map : shapedRecipe.getIngredientMap().entrySet()) {
                    if (map.getValue() != null) {
                        if (CustomItemStack.hasTag(map.getValue())) {
                            System.out.println(map.getKey() + " " + CustomItemStack.getCustomMaterial(map.getValue()).getName());
                        } else {
                            System.out.println(map.getKey() + " " + map.getValue().getType());
                        }
                    }
                }
            }
        }*/

    }


}
