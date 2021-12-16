package fr.zelytra.histeria.managers.hologram;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HoloCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }
        /**
         * /holo create [name] {text...}
         * /holo add [name] {text...}
         * /holo delete [name]
         * /holo edit [name] [line] {text}
         * /holo move [name] {-c}
         */

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 3) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            if (Hologram.exist(args[1])) {
                LangMessage.sendMessage(player, "hologram.alreadyExist");
                return true;
            }

            new Hologram(player.getLocation(), args[1], concatContent(2, args));
            LangMessage.sendMessage(player, "hologram.create");

        } else if (args[0].equalsIgnoreCase("add")) {

            if (args.length < 3) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            if (!Hologram.exist(args[1])) {
                LangMessage.sendMessage(player, "hologram.notExist");
                return true;
            }

            Hologram hologram = Hologram.get(args[1]);
            
            String[] content = concatContent(2, args).split("#");
            for (String line : content)
                hologram.addLine(line);

            LangMessage.sendMessage(player, "hologram.lineAdd");

        } else if (args[0].equalsIgnoreCase("delete")) {

            if (args.length != 2) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            if (!Hologram.exist(args[1])) {
                LangMessage.sendMessage(player, "hologram.notExist");
                return true;
            }

            Hologram hologram = Hologram.get(args[1]);
            hologram.destroy();
            LangMessage.sendMessage(player, "hologram.delete");

        } else if (args[0].equalsIgnoreCase("edit")) {

            if (args.length < 3) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            if (!Hologram.exist(args[1])) {
                LangMessage.sendMessage(player, "hologram.notExist");
                return true;
            }

            Hologram hologram = Hologram.get(args[1]);
            try {


                if (hologram.editLine(Integer.parseInt(args[2]), concatContent(3, args)))
                    LangMessage.sendMessage(player, "hologram.edit");
                else
                    LangMessage.sendMessage(player, "hologram.failedEdition");

            } catch (NumberFormatException ignored) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }


        } else if (args[0].equalsIgnoreCase("move")) {

            if (args.length < 2) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            if (!Hologram.exist(args[1])) {
                LangMessage.sendMessage(player, "hologram.notExist");
                return true;
            }

            Hologram hologram = Hologram.get(args[1]);

            if (args.length == 3 && args[2].equalsIgnoreCase("-c")) {
                Location target = player.getLocation().clone();
                target.setX(target.getBlockX() + 0.5);
                target.setZ(target.getBlockZ() + 0.5);
                hologram.move(target);

            } else {
                hologram.move(player.getLocation());
            }

            LangMessage.sendMessage(player, "hologram.move");

        } else {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }
        return true;
    }

    private String concatContent(int beginAt, String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int x = beginAt; x < args.length; x++) {

            stringBuilder.append(args[x]);

            if (x < args.length - 1)
                stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}
