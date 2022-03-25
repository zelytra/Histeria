package fr.zelytra.histeria.commands.message;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PrivateMessage {

    private Player sender;
    private final String commandLine;

    public PrivateMessage(Player player, String commandLine) {
        this.commandLine = commandLine;
        this.sender = player;
    }

    public void send() {
        final String[] arg = commandLine.split(" ");

        if (arg.length < 2) {
            LangMessage.sendMessage(sender, "command.wrongSyntax");
            return;
        }
        final String targetName = arg[1];

        if (Bukkit.getPlayer(targetName) == null) {
            LangMessage.sendMessage(sender, "command.playerOffLine");
            return;
        }
        Player receiver = Bukkit.getPlayer(targetName);
        StringBuilder message = new StringBuilder();

        for (int x = 2; x < arg.length; x++) {
            if (x == arg.length - 1) {
                message.append(arg[x]);
                continue;
            }
            message.append(arg[x]).append(" ");
        }
        assert receiver != null;

        receiver.sendMessage("§o§6» from " + sender.getName() + "§f > §o" + message);
        sender.sendMessage("§o§6» to " + receiver.getName() + "§f > §o" + message);
    }
}
