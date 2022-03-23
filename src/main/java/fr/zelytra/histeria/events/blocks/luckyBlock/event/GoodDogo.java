/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GoodDogo implements LuckyEvent {

    private final int luck;

    public GoodDogo(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getBlock().setType(Material.BARREL);
        Barrel barrel = (Barrel) e.getBlock().getState();
        ItemStack[] contents = barrel.getInventory().getContents();

        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§eMdr je suis fonda (c fo)");
        skull.setOwningPlayer(Bukkit.getOfflinePlayer("dadodasyra"));
        item.setItemMeta(skull);

        ItemStack bone = new ItemStack(Material.BONE, 1);
        ItemMeta meta = bone.getItemMeta();
        meta.setDisplayName("Bon toutou");
        bone.setItemMeta(meta);

        contents[13] = new ItemStack(item);
        contents[11] = new ItemStack(bone);
        contents[15] = new ItemStack(bone);

        barrel.getInventory().setContents(contents);

    }
}
