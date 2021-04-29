package fr.zelytra.histeria.commands;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        DurabilityHandler durabilityHandler = new DurabilityHandler((Player) sender,CustomMaterial.HISTERITE_PICKAXE, SlotEnum.MAIN_HAND);
        durabilityHandler.setDurability(15);

        return true;
    }
}
