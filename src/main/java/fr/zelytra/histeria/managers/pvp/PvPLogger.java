package fr.zelytra.histeria.managers.pvp;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PvPLogger implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();

        if (HGuard.getByLocation(damager.getLocation()) != null)
            if (!HGuard.getByLocation(damager.getLocation()).isPvp()) return;

        if (Histeria.isSaberFaction()) {
            FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
            FPlayer fdamager = FPlayers.getInstance().getByPlayer(damager);

            if (fplayer.getTag().equalsIgnoreCase(fdamager.getTag())) {
                if (!fplayer.getFaction().isWilderness()) {
                    return;
                }
            }

        }

        CustomPlayer.getCustomPlayer(player.getName()).getPvp().setPvP();
        CustomPlayer.getCustomPlayer(damager.getName()).getPvp().setPvP();

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        if (CustomPlayer.getCustomPlayer(player.getName()).getPvp().isPvP()){
            CustomPlayer.getCustomPlayer(player.getName()).getPvp().removePvP();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisconnection(PlayerQuitEvent e) {

        if (SwitchServer.getPlayerSwitching().contains(e.getPlayer())) return;
        if (CustomPlayer.getCustomPlayer(e.getPlayer().getName()) == null) return;

        PvP pvpStatus = CustomPlayer.getCustomPlayer(e.getPlayer().getName()).getPvp();

        if (pvpStatus.isPvP()) {
            pvpStatus.killInventory();
            fxPvPLog(e.getPlayer());
        }

    }

    @EventHandler
    public void onCommandExec(PlayerCommandPreprocessEvent e) {
        PvP pvpStatus = CustomPlayer.getCustomPlayer(e.getPlayer().getName()).getPvp();

        if (pvpStatus.isPvP()) {
            e.setCancelled(true);
            LangMessage.sendMessage(e.getPlayer(), "pvp.commandExec");
        }
    }

    private void fxPvPLog(Player player) {
        LangMessage.broadcast(Message.HISTLOGGER.getMsg() + "§b" + player.getName() + " ", "pvp.playerDisconnect", "");
        player.getWorld().strikeLightningEffect(player.getLocation());
    }


}
