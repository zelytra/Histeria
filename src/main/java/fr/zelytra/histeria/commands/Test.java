/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        //new MarketItem(player.getName(), new Random().nextInt(1, 500), player.getInventory().getItemInMainHand()).publish();
        //player.getInventory().getItemInMainHand().addEnchantment(new CustomEnchant(CustomEnchantData.BLESS_OF_KEEPING),1);
        //new PlayerMarket(player);
        //player.getInventory().getItemInMainHand().addEnchantment(Enchantment.DAMAGE_ALL,1);
        //player.getInventory().getItemInMainHand().addEnchantment(CustomEnchant.BLESS_OF_KEEPING,1);
        //player.getInventory().setItemInMainHand(Utils.BookEnchantedItemStack(player.getInventory().getItemInMainHand(), Enchantment.DAMAGE_ALL, 1));
        System.out.println(player.getLocation().getChunk().getPersistentDataContainer().getKeys());
        return true;

    }
}