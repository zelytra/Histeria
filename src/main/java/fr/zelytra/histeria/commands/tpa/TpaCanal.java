package fr.zelytra.histeria.commands.tpa;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpaCanal {
    public static List<TpaCanal> tpaCanalList = new ArrayList<>();

    private Player requester;
    private Player target;
    private final int timeOut = 10; //Time in seconds

    public TpaCanal(Player requester, Player target) {

        this.requester = requester;
        this.target = target;
        startTimeOutTask();
        tpaCanalList.add(this);

        LangMessage.sendMessage(requester, "tpa.requestSend");

        CustomPlayer customTarget = CustomPlayer.getCustomPlayer(target.getName());
        TextComponent processMessage = Component.text().content(Message.PLAYER_PREFIX.getMsg() + "§a" + requester.getName() + " " + customTarget.getLang().get("tpa.tpRequest"))
                .append(Component.text().content("§a[" + customTarget.getLang().get("tpa.acceptMsg") + "] §6| ")
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")))
                .append(Component.text()
                        .content("§c[" + customTarget.getLang().get("tpa.denyMsg") + "]")
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny")))
                .build();
        target.sendMessage(processMessage);

    }

    public void accept() {
        LangMessage.sendMessage(requester, "tpa.teleportation");
        LangMessage.sendMessage(target, "tpa.acceptRequest");
        teleport();
        tpaCanalList.remove(this);
    }

    public void deny() {
        LangMessage.sendMessage(requester, "tpa.requestDeny");
        LangMessage.sendMessage(target, "tpa.requestDeny");
        tpaCanalList.remove(this);
    }

    public void teleport() {
        requester.teleport(target.getLocation());
    }

    private void startTimeOutTask() {
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {

            if (!tpaCanalList.contains(this))
                return;

            tpaCanalList.remove(this);
            LangMessage.sendMessage(requester, "tpa.requestTimeOut");
            LangMessage.sendMessage(target, "tpa.requestTimeOut");
        }, timeOut * 20);

    }

    public Player getRequester() {
        return requester;
    }

    public Player getTarget() {
        return target;
    }

    public static TpaCanal getCanalForRequester(Player requester) {
        for (TpaCanal tpaCanal : tpaCanalList) {
            if (tpaCanal.getTarget().getName().equals(requester.getName())) {
                return tpaCanal;
            }
        }
        return null;
    }

}
