/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class XpBottle implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {

                int xp = player.getLevel();
                if (xp <= 10) {
                    LangMessage.sendMessage(player, "miscellaneous.notEnoughExperience");
                    return false;
                }

                if (Utils.isFullInv(player)) {
                    LangMessage.sendMessage(player, "miscellaneous.notEnoughSpace");
                    return false;
                }

                if (!Cooldown.cooldownCheck(player, "XpBottleCommand", true)) {
                    return false;
                }

                if (!Utils.canByPass(player))
                    new Cooldown(player, 600, "XpBottleCommand");


                if (Utils.getEmptySlots(player) > (xp / 64.0)) {

                    player.getInventory().addItem(new CustomItemStack(CustomMaterial.XP_ORB, xp).getItem());
                    player.setLevel(0);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                    return true;
                } else {
                    LangMessage.sendMessage(player, "miscellaneous.notEnoughSpace");
                    return false;
                }
            }
        }
        return false;
    }

}
