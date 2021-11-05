package fr.zelytra.histeria.managers.npc.command;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.npc.NPCAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NPCCommand implements CommandExecutor {


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
            if (args.length != 3) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc==null){
                LangMessage.sendMessage(player,"npc.notFound");
                return true;
            }

            NPCAction action = NPCAction.getByName(args[2]);

            if (action == null){
                LangMessage.sendMessage(player,"npc.actionNotExist");
                return true;
            }

            npc.setAction(action);
            LangMessage.sendMessage(player,"npc.actionSet");
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
