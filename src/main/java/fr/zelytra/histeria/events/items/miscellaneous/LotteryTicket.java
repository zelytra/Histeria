/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LotteryTicket implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.LOTTERY_TICKET;
    private final int MAX_GAIN = 250000;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if (e.isCancelled()) return;

        Player player = e.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        int gain = (int) (Math.random() * MAX_GAIN);
        customPlayer.getBankAccount().addMoney(gain);

        LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "miscellaneous.lottery", "§6" + gain + Emote.GOLD);

        ItemFunction.removeHeldItem(player, customMaterial);
        return;
    }


}
