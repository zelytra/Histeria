package fr.zelytra.histeria.events.blocks;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.loottable.LootsEnum;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class LootBox implements Listener {
    @EventHandler
    public void useKey(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == CustomMaterial.LOOT_BOX.getVanillaMaterial()) {
            if (e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasTag(e.getPlayer().getInventory().getItemInMainHand())) {
                CustomMaterial material = CustomMaterial.getByName(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(CustomItemStack.getItemKey(), PersistentDataType.STRING));
                Player player = e.getPlayer();
                ItemStack item;
                if (Utils.getEmptySlots(player) < 2) {
                    player.sendMessage(Message.getPlayerPrefixe() + "§cYou don't have enough place in your inventory to claim the reward.");
                    return;
                }
                switch (material) {
                    case VOTE_KEY:
                        item = drawKey(Histeria.lootTableManager.getVoteKey().getLoots());
                        Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§a§l" + player.getName() + "§r§a just opened a loot box with a §lVOTE KEY");
                        break;
                    case DIAMOND_KEY:
                        item = drawKey(Histeria.lootTableManager.getDiamondKey().getLoots());
                        Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§a§l" + player.getName() + "§r§a just opened a loot box with a §b§lDIAMOND KEY");
                        break;
                    case HISTERITE_KEY:
                        item = drawKey(Histeria.lootTableManager.getHisteriteKey().getLoots());
                        Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§a§l" + player.getName() + "§r§a just opened a loot box with a §c§lHISTERITE KEY");
                        break;
                    case NOCTURITE_KEY:
                        item = drawKey(Histeria.lootTableManager.getNocturiteKey().getLoots());
                        Bukkit.broadcastMessage(Message.getPlayerPrefixe() + "§a§l" + player.getName() + "§r§a just opened a loot box with a §5§lNOCTURITE KEY");
                        break;
                    default:
                        return;
                }
                player.getInventory().addItem(item);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
                }
                ItemFunction.removeHeldItem(player,material);
            }
        }
    }

    private ItemStack drawKey(ArrayList<LootsEnum> loots) {
        int random = (int) (Math.random() * (100 - 1) + 1);
        for (LootsEnum loot : loots) {
            if (random < loot.getLuck()) {
                return loot.getItem();
            }
            random -= loot.getLuck();
        }
        return new ItemStack(Material.AIR);
    }
}
