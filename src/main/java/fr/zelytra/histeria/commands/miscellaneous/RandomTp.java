package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTp implements CommandExecutor {

    private final int cd = 900; // time in seconds
    private final String cdTag = "randomTP";
    private List<Material> blackListMaterial = new ArrayList<>();

    {
        blackListMaterial.add(Material.WATER);
        blackListMaterial.add(Material.LAVA);
        blackListMaterial.add(Material.ACACIA_LEAVES);
        blackListMaterial.add(Material.BIRCH_LEAVES);
        blackListMaterial.add(Material.DARK_OAK_LEAVES);
        blackListMaterial.add(Material.JUNGLE_LEAVES);
        blackListMaterial.add(Material.OAK_LEAVES);
        blackListMaterial.add(Material.SPRUCE_LEAVES);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (!Utils.canByPass(player)) {
            if (!Cooldown.cooldownCheck(player, cdTag))
                return true;

            new Cooldown(player, cd, cdTag);
        }

        Random rand = new Random();

        int x, y, z;
        do {
            x = rand.nextInt() % 5000;
            z = rand.nextInt() % 5000;
            y = player.getWorld().getHighestBlockYAt(x, z);
        } while (blackListMaterial.contains(new Location(player.getWorld(), x + 0.5, y - 1, z + 0.5).getBlock().getType()));

        player.teleport(new Location(player.getWorld(), x + 0.5, y + 2, z + 0.5));
        LangMessage.sendMessage(player, "command.randomTP");
        //TODO Handle server command execution
        return true;

    }
}
