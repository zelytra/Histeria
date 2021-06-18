package fr.zelytra.histeria.managers.chat;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private final Player player;
    private final String group;
    private String message;
    private boolean canUseEmote;

    private static final List<String> groupWhitelist = new ArrayList<>();

    {
        groupWhitelist.add("monarch");
        groupWhitelist.add("lord");
        groupWhitelist.add("demigod");
    }


    public ChatManager(Player sender, String message) {

        this.message = message;
        this.player = sender;
        this.group = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(sender).getPrimaryGroup();
        this.canUseEmote = Utils.canByPass(player) || groupWhitelist.contains(group);

    }

    public String getProcessMessage() {
        if (canUseEmote)
            return prefix() + emoteMessage();
        else {
            System.out.println(prefix());
            System.out.println(rawMessage());
            return prefix() + rawMessage();
        }

    }

    private String rawMessage() {
        return message;
    }

    private String emoteMessage() {
        if (message.contains(":")) {

            String[] splitMessage = message.split(" ");
            StringBuilder finalMessage = new StringBuilder();

            for (String word : splitMessage) {

                if (word.contains(":")) {
                    word = Emote.getByName(word);
                }

                finalMessage.append(word + " ");
            }
            message = finalMessage.toString();
        }

        return message;
    }

    private String prefix() {
        return player.getName() + " > ";
    }


}
