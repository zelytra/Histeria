/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.command;

import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(9, "§6Jobs Menu");
        interfaceBuilder.setContent(getMenuContent(CustomPlayer.getCustomPlayer(player.getName())));
        interfaceBuilder.open(player);

        return true;

    }

    public ItemStack[] getMenuContent(CustomPlayer customPlayer) {
        ItemStack[] content = new ItemStack[9];

        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_LIGHT_GRAY_GLASSE.getItem();

        content[2] = customPlayer.getJob(JobType.MINER).getItemMenu().getItem();
        content[3] = customPlayer.getJob(JobType.FIGHTER).getItemMenu().getItem();
        content[5] = customPlayer.getJob(JobType.FARMER).getItemMenu().getItem();
        content[6] = customPlayer.getJob(JobType.ENCHANTER).getItemMenu().getItem();

        return content;

    }
}
