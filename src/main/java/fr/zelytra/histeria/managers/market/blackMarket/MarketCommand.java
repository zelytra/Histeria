package fr.zelytra.histeria.managers.market.blackMarket;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.blackMarket.builder.MarketItem;
import fr.zelytra.histeria.managers.market.blackMarket.builder.MarketLimit;
import fr.zelytra.histeria.managers.market.blackMarket.builder.PlayerMarket;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.utils.Utils;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            new PlayerMarket(player);
            return true;

        } else if (args.length == 1) {
            if (!Utils.isNumeric(args[0])) {
                LangMessage.sendMessage(player, "bank.wrongNumberFormat");
                return false;
            }

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                LangMessage.sendMessage(player, "blackmarket.selectItem");
                return false;
            }

            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
            MarketLimit playerMarketLimit = MarketLimit.getMarketLimit(user.getPrimaryGroup());
            MySQL mySQL = Histeria.mySQL;

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
                ResultSet result = mySQL.query("SELECT * FROM `BlackMarket` WHERE `seller` = '" + player.getName() + "';");
                int count = 0;

                try {

                    while (result.next())
                        count++;
                    result.close();

                } catch (SQLException ignored) {

                }

                if (count >= playerMarketLimit.getMarketLimit()) {
                    LangMessage.sendMessage(player, "home.reachLimit");
                    return;
                }

                Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    new MarketItem(player.getName(), Integer.parseInt(args[0]), item).publish();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    LangMessage.sendMessage(player, "blackmarket.itemPublish");
                });

            });
            return true;
        }
        return false;
    }
}
