/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.Histeria;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Admin implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        System.out.println(user.getPrimaryGroup());
        if (user.getPrimaryGroup().equalsIgnoreCase("administrator")) {
            user.data().add(Node.builder("group.demigod").build());
            user.data().remove(Node.builder("group.administrator").build());
        } else {
            user.data().add(Node.builder("group.administrator").build());
            user.data().remove(Node.builder("group.demigod").build());
        }

        Histeria.getLuckPerms().getUserManager().saveUser(user);

        return true;

    }
}
