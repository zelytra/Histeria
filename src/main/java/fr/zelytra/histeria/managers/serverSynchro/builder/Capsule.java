/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.builder;

import java.io.IOException;
import java.io.InputStream;

public interface Capsule {

    byte[] build();

    int getSize();

    int firstPacketSize();

    PlayerData uncaps(PlayerData data, byte[] message, InputStream stream) throws IOException;

}
