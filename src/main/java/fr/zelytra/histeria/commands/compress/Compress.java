package fr.zelytra.histeria.commands.compress;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class Compress implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        PlayerInventory inv = player.getInventory();

        int compress = 0;
        int cobble = 0;

        for (int count = 0; count <= inv.getSize(); count++) {
            if (inv.getItem(count) == null) {
                continue;
            }
            if (inv.getItem(count).getType() == Material.COBBLESTONE) {
                cobble += inv.getItem(count).getAmount();
                inv.setItem(count, new ItemStack(Material.AIR));
            }
        }

        compress = (int) Math.ceil(cobble / 9);
        cobble = cobble % 9;

        inv.addItem(new ItemStack(Material.COBBLESTONE, cobble));
        inv.addItem(new CustomItemStack(CustomMaterial.COMPRESS_COBBLESTONE, compress).getItem());

        LangMessage.sendMessage(player, "§a" + compress * 9, "command.compress", "");
        return true;
    }
}
