package fr.zelytra.histeria.managers.languages;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class LangMessage {

    public static void sendMessage(Player player, String messageTag) {
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        assert customPlayer != null;
        player.sendMessage(Message.PLAYER_PREFIX.getMsg() + customPlayer.getLang().get(messageTag));
    }

    public static void sendMessage(Player player, String prefix, String messageTag, String suffix) {
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        assert customPlayer != null;
        player.sendMessage(prefix + customPlayer.getLang().get(messageTag) + suffix);
    }

    public static void broadcast(String messageTag) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
            assert customPlayer != null;
            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + customPlayer.getLang().get(messageTag));
        }
    }

    public static void broadcast(String prefix, String messageTag, String suffix) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
            assert customPlayer != null;
            player.sendMessage(prefix + customPlayer.getLang().get(messageTag) + suffix);
        }
    }

    public static void sendTitle(Player player, String titleMessageTag, String subtitleMessageTag, int fadeIn, int stay, int fadeOut) {

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        assert customPlayer != null;
        player.sendTitle(customPlayer.getLang().get(titleMessageTag), customPlayer.getLang().get(subtitleMessageTag), fadeIn, stay, fadeOut);

    }
}
