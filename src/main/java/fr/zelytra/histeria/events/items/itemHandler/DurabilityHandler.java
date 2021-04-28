package fr.zelytra.histeria.events.items.itemHandler;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Random;

public class DurabilityHandler {
    private final ItemStack item;
    private final CustomMaterial material;
    private int durability;
    private final Player player;

    /**
     * @param player   Player who held the item
     * @param material The custom material
     * @param slot     Slot of helded item
     */

    public DurabilityHandler(Player player, CustomMaterial material, SlotEnum slot) {
        this.item = slot.getItem(player);
        this.material = material;
        this.player = player;
        if (this.item.getItemMeta().getPersistentDataContainer().has(CustomItemStack.getDurabilityKey(), PersistentDataType.INTEGER)) {
            this.durability = this.item.getItemMeta().getPersistentDataContainer().get(CustomItemStack.getDurabilityKey(), PersistentDataType.INTEGER);
        }
    }

    public void iterate() {
        Random r = new Random();
        int rand = r.nextInt(100 - 1) + 1;
        if (!(this.item.getEnchantments().isEmpty())) {
            if (this.item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                switch (this.item.getEnchantmentLevel(Enchantment.DURABILITY)) {
                    case 1:
                        if (rand > 25) {
                            durability--;
                        }
                        break;
                    case 2:
                        if (rand > 36) {
                            durability--;
                        }
                        break;
                    case 3:
                        if (rand > 43) {
                            durability--;
                            break;
                        }
                }
            }
        } else {
            durability--;
        }
        updateIndicator();

        if (durability <= 0) {
            this.item.setType(Material.AIR);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            }
        }
    }


    private void updateIndicator() {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(getIndicator());
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(CustomItemStack.getDurabilityKey(), PersistentDataType.INTEGER, durability);
        this.item.setItemMeta(meta);
    }

    private String getIndicator() {
        return "§bDurability§l>§r§f" + this.durability + "/" + this.material.getDurability();
    }


}
