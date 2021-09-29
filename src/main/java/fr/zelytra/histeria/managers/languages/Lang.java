/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.languages;

import java.util.HashMap;

public enum Lang {

    EN("en.conf", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0="),
    FR("fr.conf", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==");


    private final String fileName;
    private final String texture;
    private final HashMap<String, String> text;

    Lang(String fileName, String texture) {
        this.fileName = fileName;
        this.texture = texture;
        this.text = new LangFile(this).getText();
    }

    public String getFileName() {
        return fileName;
    }

    public String getTexture() {
        return texture;
    }

    public String get(String tag) {
        if (tag.isEmpty() || tag == "")
            return "";
        else if (text.containsKey(tag))
            return text.get(tag);
        else
            return "§c404 text not found";
    }
}
