package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.managers.visual.chat.EmoteType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EmoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§8---------------§6 [ Emotes ] §8---------------");
            player.sendMessage("§c /!\\ Only player with grade can use emote in the chat /!\\");
            for (Emote emote : Emote.values()) {
                if (emote.getType() == EmoteType.EMOTE)
                    player.sendMessage(" " + emote + " §8- §etag:§6 " + emote.getTag());
            }
        }
        return false;
    }
}
