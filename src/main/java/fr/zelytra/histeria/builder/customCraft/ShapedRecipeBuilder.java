package fr.zelytra.histeria.builder.customCraft;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Map;


public class ShapedRecipeBuilder {

    private final ShapedRecipe shapedRecipe;
    private final NamespacedKey itemKey;
    private final CustomMaterial material;
    private int amount = 1;

    public ShapedRecipeBuilder(CustomMaterial material) {
        this.material = material;
        this.itemKey = new NamespacedKey(Histeria.getInstance(), material.getName());
        this.shapedRecipe = new ShapedRecipe(this.itemKey, new CustomItemStack(material, amount).getItem());
    }

    public ShapedRecipeBuilder(CustomMaterial material, int amount) {
        this.material = material;
        this.amount = amount;
        this.itemKey = new NamespacedKey(Histeria.getInstance(), material.getName());
        this.shapedRecipe = new ShapedRecipe(this.itemKey, new CustomItemStack(material, amount).getItem());
    }

    public void setShape(String line1, String line2, String line3) {
        this.shapedRecipe.shape(line1, line2, line3);
    }

    public void assigneSymbol(char symbol, Material material) {
        this.shapedRecipe.setIngredient(symbol, material);
    }

    public void assigneSymbol(char symbol, CustomMaterial material) {
        RecipeChoice.ExactChoice item = new RecipeChoice.ExactChoice(new CustomItemStack(material, 1).getItem());
        this.shapedRecipe.setIngredient(symbol, item);
    }

    public String[] getShape() {
        return this.shapedRecipe.getShape();
    }

    public void register() {
        //MultiPatterns finder registering
        CraftPattern[] craftPatterns = new CraftPattern(this.shapedRecipe.getShape()).getAllPossibilities();
        int count = 0;
        for (CraftPattern pattern : craftPatterns) {
            NamespacedKey key = new NamespacedKey(Histeria.getInstance(), material.getName() + count);
            ShapedRecipe recipe = new ShapedRecipe(key, new CustomItemStack(material, this.amount).getItem());
            recipe.shape(pattern.getShapeArray());

            for (Map.Entry<Character, ItemStack> map : this.shapedRecipe.getIngredientMap().entrySet()) {
                if (map.getValue() != null) {
                    if (CustomItemStack.hasTag(map.getValue())) {
                        RecipeChoice.ExactChoice item = new RecipeChoice.ExactChoice(new CustomItemStack(CustomItemStack.getCustomMaterial(map.getValue()), amount).getItem());
                        recipe.setIngredient(map.getKey(), item);
                    } else {
                        recipe.setIngredient(map.getKey(), map.getValue().getType());
                    }
                }
            }
            Histeria.getInstance().getServer().addRecipe(recipe);
            count++;
        }
    }
}
