package fr.zelytra.histeria.utils;

import fr.zelytra.histeria.Histeria;
import net.luckperms.api.model.user.User;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static List<String> dynamicTab(List<String> list, String arg) {
        List<String> finalList = new ArrayList<>(list);
        for (String s : list) {
            if (!s.toLowerCase().startsWith(arg.toLowerCase())) {
                finalList.remove(s);
            }
        }
        Collections.sort(finalList);
        return finalList;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int getEmptySlots(Player player) {
        int i = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (!(is == null))
                continue;
            i++;
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (!(item == null))
                continue;
            i--;
        }
        return i;
    }

    public static ItemStack EnchantedItemStack(Material material, Enchantment enchantment, int lvl) {
        ItemStack item = new ItemStack(material);
        item.addUnsafeEnchantment(enchantment, lvl);
        return item;
    }

    public static void runTotemDisplay(Player player) {
        EntityPlayer ep = ((CraftPlayer) player).getHandle();
        PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 35);
        ep.playerConnection.sendPacket(status);
    }

    public static boolean isFullInv(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            return true;
        }
        return false;
    }

    public static boolean canByPass(Player player) {
        User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        switch (user.getPrimaryGroup()) {
            case "fondateur":
            case "moderator":
            case "administrator":
                return true;

            default:
                return false;
        }

    }
}
