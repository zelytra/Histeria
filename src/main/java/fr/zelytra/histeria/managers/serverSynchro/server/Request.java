/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.server;

public enum Request {
    SEND((byte) 1),
    GET((byte) 0);

    public byte id;

    Request(byte id) {
        this.id = id;
    }
}
