/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Hat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            ItemStack inHand = player.getInventory().getItemInMainHand();
            ItemStack onHead = player.getInventory().getHelmet();

            if (inHand.getType() == Material.AIR) {
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou don't hold any item to put on your head.");
                return false;
            }

            if (onHead != null) {
                player.getInventory().setItemInMainHand(onHead);
                player.getInventory().setHelmet(inHand);
            } else {
                player.getInventory().setHelmet(inHand);
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }

            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aItem has been correctly set on your head !");
            return true;

        }
        return false;
    }

}
