/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.shop;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.stand.Stand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        int price;
        ItemStack item;

        if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage("§8---------------§6 [ Stands ] §8---------------");

            for (Stand stand : Stand.serverStands) {
                player.sendMessage("§bUUID : §6" + stand.getUUID() + "§b " + stand.getItemName());
            }
            return true;

        } else if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
            Stand stand = Stand.getStand(args[1]);

            if (stand == null) {
                LangMessage.sendMessage(player, "command.standNotFound");
                return false;
            }
            stand.destroy();
            return true;

        } else {

            if (args.length != 2) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            try {
                price = Integer.parseInt(args[1]);

                CustomMaterial customMaterial = CustomMaterial.getByName(args[0]);

                if (customMaterial != null)
                    item = new CustomItemStack(customMaterial, 1).getItem();
                else
                    item = new ItemStack(Material.getMaterial(args[0]));

            } catch (Exception e) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            LangMessage.sendMessage(player, "command.standCreate");
            new Stand(player.getLocation(), price, item);
            return true;
        }

    }
}
