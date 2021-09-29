/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.configuration;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.hguard.HGuardPersistent;

import java.io.File;

public class ConfigurationManager {

    public ConfigurationManager() {
        File pluginFolder = Histeria.getInstance().getDataFolder();

        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }

    }

    public void load() {
        loadHGuard();
    }

    public void unload() {
        unloadHGuard();
    }

    private void loadHGuard() {

        File hguardFolder = new File(HGuardPersistent.FOLDER_PATH);
        if (hguardFolder.exists()) {
            for (File file : hguardFolder.listFiles()) {
                HGuardPersistent.load(file);
            }
        }

    }

    private void unloadHGuard() {

        for (HGuard hGuard : HGuard.getList()) {
            hGuard.save();
        }
    }


}
