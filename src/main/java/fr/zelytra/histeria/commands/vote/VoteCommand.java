package fr.zelytra.histeria.commands.vote;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("vote")) {

            TextComponent processMessage = Component.text()
                    .content(Message.PLAYER_PREFIX.getMsg() + CustomPlayer.getCustomPlayer(player.getName()).getLang().get("vote.link"))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://serveur-prive.net/minecraft/histeria-7438/vote"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text().content("§6Click here to access to the website").build()))
                    .build();

            player.sendMessage(processMessage);
            return true;

        } else if (command.getName().equalsIgnoreCase("forcevote")) {

            if (args.length != 1) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                LangMessage.sendMessage((Player) sender, "command.playerOffLine");
                return false;
            }

            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "vote.forceVote", args[0]);
            Histeria.vote.vote(target);
            return true;
        }
        return false;
    }
}
