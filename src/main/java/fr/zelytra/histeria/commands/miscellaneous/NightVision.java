package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aNight Vision effect has been removed.");
                return true;
            }
            PotionEffect nv = new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, false, false, false);
            player.addPotionEffect(nv);
            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aNight Vision effect has been set.");
            return true;
        }
        return false;
    }
}
