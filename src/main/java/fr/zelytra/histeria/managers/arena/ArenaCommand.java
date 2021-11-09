/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.arena;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCommand implements CommandExecutor {
    /*
    /arenachest
    /arenachest create
    /arenachest delete
    /arenachest refill
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {

            LangMessage.sendMessage(player, "", "arena.list", "");

            for (ArenaChest chest : ArenaChest.chestList)
                player.sendMessage("§8● x:§6" + chest.getLocation().getBlockX() + " §ey:§6" + chest.getLocation().getBlockY() + " §ez:§6" + chest.getLocation().getBlockZ());

            return true;

        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) {

                Block targetBlock = player.getTargetBlock(10);

                if (targetBlock != null && targetBlock.getType() == Material.CHEST) {
                    new ArenaChest(targetBlock.getLocation());
                    LangMessage.sendMessage(player, "arena.create");
                    return true;
                } else {
                    LangMessage.sendMessage(player, "arena.badTarget");
                    return false;
                }

            } else if (args[0].equalsIgnoreCase("delete")) {
                return true;

            } else if (args[0].equalsIgnoreCase("refill")) {

                for (ArenaChest chest : ArenaChest.chestList)
                    chest.draw();

                LangMessage.sendMessage(player, "arena.refill");
                return true;

            }
            return true;
        } else {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }
    }
}
