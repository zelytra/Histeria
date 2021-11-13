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
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Heal implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                if (!Cooldown.cooldownCheck(player, "healCommand", true)) {
                    return false;
                }

                if (!Utils.canByPass(player))
                    new Cooldown(player, 300, "healCommand");

                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                LangMessage.sendMessage(player, "miscellaneous.heal");
                return true;
            } else {
                if (Bukkit.getPlayer(args[0]) != null && Utils.canByPass(player)) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                    LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "miscellaneous.healOther", target.getName());
                    LangMessage.sendMessage(target, "miscellaneous.heal");
                    return true;
                } else if (!Utils.canByPass(player)) {
                    LangMessage.sendMessage(player, "miscellaneous.healNoOther");
                    return false;
                } else {
                    LangMessage.sendMessage(player,"command.noPermission");
                    return false;
                }
            }
        }
        return false;
    }
}
