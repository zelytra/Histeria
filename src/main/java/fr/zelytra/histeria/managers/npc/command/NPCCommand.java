package fr.zelytra.histeria.managers.npc.command;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.npc.NPC;
import fr.zelytra.histeria.managers.npc.NPCAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

            if(args[1].length()>=16){
                LangMessage.sendMessage((Player) sender, "npc.exceedNameSpace");
                return false;
            }

            new NPC(player.getLocation(), args[1]).showNPC();
            LangMessage.sendMessage(player, "npc.createNPC");
            return true;


        } else if (args[0].equalsIgnoreCase("modify")) {
            return true;

        } else if (args[0].equalsIgnoreCase("action")) {
            if (args.length < 3) {
                LangMessage.sendMessage(player, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc == null) {
                LangMessage.sendMessage(player, "npc.notFound");
                return true;
            }

            NPCAction action = NPCAction.getByName(args[2]);

            if (action == null) {
                LangMessage.sendMessage(player, "npc.actionNotExist");
                return true;
            }

            switch (action) {
                case SERVER:

                    if (!Histeria.server.getServersList().contains(args[3])) {
                        LangMessage.sendMessage(player, "server.serverNotFound");
                        return false;
                    } else if (Histeria.server.getServerName().equalsIgnoreCase(args[3])) {
                        LangMessage.sendMessage(player, "server.alreadyOnTheServer");
                        return false;
                    }

                    npc.setServerName(args[3]);

                    break;
                case TELEPORT:
                    if (args.length != 7) {
                        LangMessage.sendMessage(player, "command.wrongSyntax");
                        return false;
                    }

                    double x = 0, y = 0, z = 0;
                    try {
                        x = Double.parseDouble(args[3]) + 0.5;
                        y = Double.parseDouble(args[4]);
                        z = Double.parseDouble(args[5]) + 0.5;
                    } catch (NumberFormatException exception) {
                        LangMessage.sendMessage(player, "command.wrongSyntax");
                    }

                    if (Bukkit.getWorld(args[6]) == null) {
                        LangMessage.sendMessage(player, "command.noWorldFound");
                        return true;
                    }

                    npc.setTeleportLocation(new Location(Bukkit.getWorld(args[6]), x, y, z));
                    break;

            }

            npc.setAction(action);
            LangMessage.sendMessage(player, "npc.actionSet");
            return true;

        } else if (args[0].equalsIgnoreCase("skin")) {

            if (args.length != 3) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc == null) {
                LangMessage.sendMessage(player, "npc.notFound");
                return true;
            }

            npc.setSkin(args[2]);
            LangMessage.sendMessage(player, "npc.skin");
            return true;

        } else if (args[0].equalsIgnoreCase("move")) {
            NPC npc = NPC.getNPC(args[1]);

            if (npc == null) {
                LangMessage.sendMessage(player, "npc.notFound");
                return true;
            }

            npc.move(player.getLocation());
            LangMessage.sendMessage(player, "npc.move");
            return true;

        } else if (args[0].equalsIgnoreCase("delete")) {

            if (args.length != 2) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            NPC npc = NPC.getNPC(args[1]);

            if (npc == null) {
                LangMessage.sendMessage(player, "npc.notFound");
                return true;
            }

            npc.destroy();
            LangMessage.sendMessage(player, "npc.remove");
            return true;


        } else {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

    }
}
