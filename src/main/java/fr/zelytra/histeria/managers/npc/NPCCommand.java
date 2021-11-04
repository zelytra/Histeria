package fr.zelytra.histeria.managers.npc;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NPCCommand implements CommandExecutor {

    /*
            /npc create {name}
            /npc modify {name} {parameter} {value}
            /npc action {SHOP|HMARKET|SERVER|TELEPORT} [serverName|x] [y] [z]
            /npc delete {name}
            /npc move {name}
            /npc skin {name} {url}
            /npc look //TODO

    */

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length < 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length != 2) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            new NPC(player.getLocation(), args[1]).showNPC();
            LangMessage.sendMessage(player,"npc.createNPC");
            return true;


        } else if (args[0].equalsIgnoreCase("modify")) {
            return true;

        } else if (args[0].equalsIgnoreCase("action")) {
            return true;

        }else if (args[0].equalsIgnoreCase("skin")) {

            if (args.length != 3) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc==null){
                LangMessage.sendMessage(player,"npc.notFound");
                return true;
            }

            npc.setSkin(args[2]);
            LangMessage.sendMessage(player,"npc.skin");
            return true;

        } else if (args[0].equalsIgnoreCase("move")) {
            NPC npc = NPC.getNPC(args[1]);

            if (npc==null){
                LangMessage.sendMessage(player,"npc.notFound");
                return true;
            }

            npc.move(player.getLocation());
            LangMessage.sendMessage(player,"npc.move");
            return true;

        }else if (args[0].equalsIgnoreCase("delete")) {

            if (args.length != 2) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc==null){
                LangMessage.sendMessage(player,"npc.notFound");
                return true;
            }

            npc.destroy();
            LangMessage.sendMessage(player,"npc.remove");
            return true;


        } else {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

    }
}
