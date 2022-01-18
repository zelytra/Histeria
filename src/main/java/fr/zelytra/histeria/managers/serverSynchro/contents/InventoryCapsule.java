/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.ByteConverter;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.bukkit.entity.Player;

public class InventoryCapsule implements Capsule {

    private final byte[] message;

    public InventoryCapsule(Player player){
        this.message = ByteConverter.itemStackArrayToBase64(player.getInventory().getContents());
    }

    @Override
    public byte[] build() {
        return message;
    }

    @Override
    public int getSize() {
        return message.length;
    }


}
