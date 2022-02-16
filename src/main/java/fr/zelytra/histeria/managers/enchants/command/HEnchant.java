package fr.zelytra.histeria.managers.enchants.command;

import fr.zelytra.histeria.builder.commandsHandler.HelpCommands;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

public class HEnchant implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            HelpCommands help = new HelpCommands("henchant");
            help.addCommand("[customEnchant]", "{level}");
            help.printPlayer(player);
            return true;

        } else if (args.length == 2 && Utils.isInteger(args[1])) {
            if (CustomEnchant.getByKey(NamespacedKey.minecraft(args[0])) != null && CustomEnchantUtils.isCustom(CustomEnchant.getByKey(NamespacedKey.minecraft(args[0])))) {

                Enchantment enchant = CustomEnchant.getByKey(NamespacedKey.minecraft(args[0]));
                ItemStack item = player.getInventory().getItemInMainHand();

                int level = Integer.parseInt(args[1]);
                if (level < enchant.getStartLevel() || level > enchant.getMaxLevel()) {
                    LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "miscellaneous.outOfBound", String.valueOf(enchant.getMaxLevel()));
                    return false;
                }

                try {
                    if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                        meta.addStoredEnchant(enchant, level, false);
                        item.setItemMeta(meta);
                    } else {
                        item.addEnchantment(enchant, level);
                    }

                    CustomEnchantUtils.updateCustomEnchant(item);
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou enchant this item with §6§l" + PlainTextComponentSerializer.plainText().serialize(enchant.displayName(level)));
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§c" + e.getMessage());
                }

                return true;
            } else {
                LangMessage.sendMessage(player, "miscellaneous.enchantNotExist");
                return false;
            }
        } else {
            player.sendMessage(Message.getHelp(command.getName()));
            return false;
        }
    }
}
