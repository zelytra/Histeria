/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.builder;

import fr.zelytra.histeria.managers.serverSynchro.PlayerData;

import java.io.IOException;

public interface Capsule {

    byte[] build();

    int getSize();

    PlayerData uncaps(PlayerData data, byte[] message) throws IOException;

}
