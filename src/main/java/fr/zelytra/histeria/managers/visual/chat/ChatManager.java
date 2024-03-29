/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.visual.chat;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private final Player player;
    private final String group;
    private final String message;
    private final boolean canUseEmote;

    private static final List<String> groupWhitelist = new ArrayList<>();

    static {
        groupWhitelist.add("monarch");
        groupWhitelist.add("lord");
        groupWhitelist.add("demigod");
        groupWhitelist.add("guide");
    }


    public ChatManager(Player sender, String message) {

        this.message = message;
        this.player = sender;
        this.group = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(sender).getPrimaryGroup();
        this.canUseEmote = Utils.canByPass(player) || groupWhitelist.contains(group);

    }

    private TextComponent.Builder processEmote(TextComponent.Builder component) {
        GroupFX groupFX = GroupFX.getByName(this.group);
        for (String word : message.split(" ")) {
            if (word.contains(":")) {
                component.append(Component.text().content(Emote.getByName(word) + " ").color(NamedTextColor.WHITE));
            } else {
                component.append(Component.text().content(word + " ").color(groupFX.getMessageColor()));
            }
        }
        return component;
    }

    public TextComponent getProcessMessage() {

        GroupFX groupFX = GroupFX.getByName(this.group);
        TextComponent.Builder processMessage;

        processMessage = Component.text()
                .content(groupFX.getBadge().toString() + " ")
                .color(NamedTextColor.WHITE);

        if (Histeria.isSaberFaction()) {
            FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);

            processMessage.append(Component.text().content("§7[" +
                    (fplayer.getFaction().getTag().equalsIgnoreCase("§2wilderness") ? "" : fplayer.getFaction().getTag())
                    + "] "));
        }

        processMessage.append(Component.text().content(player.getName()).color(groupFX.getNameColor()))
                .append(Component.text().content(groupFX.getSplitter()).color(groupFX.getSplittercolor()));

        if (canUseEmote)
            processMessage = processEmote(processMessage);
        else
            for (String word : message.split(" "))
                processMessage.append(Component.text().content(word + " ").color(groupFX.getMessageColor()));


        return processMessage.build();

    }

    private String rawMessage() {
        return this.message;
    }


}
