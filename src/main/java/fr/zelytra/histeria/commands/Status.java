package fr.zelytra.histeria.commands;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Status implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        player.sendMessage("§8---------------§6 [ Servers Status ] §8---------------");
        player.sendMessage("§3Server: faction");
        player.sendMessage("§3CPU: §c99%    §3| §3RAM: §c63.99Gb §3/ 64Gb");
        player.sendMessage("§3Status: §cCRITICAL CORE ERROR");
        player.sendMessage("§3Latest logs: §cNullPointerException could not pass event PlayerQuitEvent to Histeria");
        player.sendMessage("§cNullPointerExcept§ki§r§con could not pas§ks even§r§ct PlayerIn§kteract§r§cEvent to Histeria");
        player.sendMessage("§cNullPo§kinterException could not p§r§cass event PlayerQuitEvent to Hi§ksteria");
        //player.sendMessage("§4ERROR");
        Bukkit.getScheduler().runTaskLaterAsynchronously(Histeria.getInstance(),()->{
            try {
                player.sendMessage("§6Starting backup task...");
                Thread.sleep(580);
                player.sendMessage("§6Starting forced save task...");
                Thread.sleep(520);
                player.sendMessage("§6Saving hguard...");
                Thread.sleep(520);
                player.sendMessage("§6Saving shop...");
                Thread.sleep(520);
                player.sendMessage("§6Rebooting server...");
                Thread.sleep(1000);
                player.sendMessage("§4FAILED to backup server");
                Thread.sleep(600);
                player.sendMessage("§4FAILED to forced save players");
                Thread.sleep(600);
                player.sendMessage("§4FAILED to reboot server");
                Thread.sleep(600);
                player.sendMessage("§4FAILED to");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        },20);
        return true;
    }
}
