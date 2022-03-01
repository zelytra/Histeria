package fr.zelytra.histeria.managers.market.trade;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TradeCommand implements CommandExecutor {

    public static List<TradeRequest> requestList = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length != 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Check double trade request
        if (getRequest(target.getName(), player.getName()) != null) {
            System.out.println("double trade request");
            requestList.remove(getRequest(target.getName(), player.getName()));
            new TradeCanal(target, player);
            return true;

        }

        if (target == null) {
            LangMessage.sendMessage((Player) sender, "command.playerOffLine");
            return false;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            LangMessage.sendMessage((Player) sender, "trade.notOnYourSelf");
            return false;
        }

        System.out.println("start request");
        new TradeRequest(player, target).startTimeOut();

        return true;


    }

    public static TradeRequest getRequest(String requester, String target) {
        for (TradeRequest tradeRequest : requestList)
            if (tradeRequest.getRequester().getName().equalsIgnoreCase(requester) && tradeRequest.getTarget().getName().equalsIgnoreCase(target))
                return tradeRequest;
        return null;
    }
}

class TradeRequest {

    public final Player requester, target;
    private int timeOut = 5;

    TradeRequest(Player requester, Player target) {
        this.requester = requester;
        this.target = target;

        CustomPlayer customTarget = CustomPlayer.getCustomPlayer(target.getName());
        TextComponent processMessage = Component.text().content(Message.PLAYER_PREFIX.getMsg() + "§a" + requester.getName() + " " + customTarget.getLang().get("trade.tpRequest"))
                .append(Component.text().content("§a[" + customTarget.getLang().get("trade.acceptMsg") + "]")
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trade " + requester.getName())))
                .build();
        target.sendMessage(processMessage);

        TradeCommand.requestList.add(this);

    }

    public Player getRequester() {
        return requester;
    }

    public Player getTarget() {
        return target;
    }

    public void startTimeOut() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Histeria.getInstance(), () -> {

            if (TradeCommand.getRequest(requester.getName(), target.getName()) == null)
                return;

            TradeCommand.requestList.remove(this);

            LangMessage.sendMessage(requester, "tpa.requestTimeOut");
            LangMessage.sendMessage(target, "tpa.requestTimeOut");
            TradeCommand.requestList.remove(this);


        }, timeOut * 20);
    }
}
