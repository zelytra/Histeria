/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.listener;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.jobs.events.JobLevelUpEvent;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;

public class LevelUpListener implements Listener {

    @EventHandler
    public void onJobLevelUp(JobLevelUpEvent e) {

        e.getJob().executeReward(e.getPlayer());
        e.getPlayer().getPlayer().showTitle(
                Title.title(Component.text(e.getJob().getJob().badge.toString())
                        , Component.empty()
                        , Title.Times.of(Duration.ofMillis(200), Duration.ofMillis(1200), Duration.ofMillis(200))));
        e.getPlayer().getPlayer().playSound(e.getPlayer().getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        LangMessage.sendMessage(e.getPlayer().getPlayer(), Message.JOB + "§8[" + e.getJob().getJob().name() + "] ", "jobs.levelUp", " §e" + e.getJob().getLevel());
        Histeria.log("§6" + e.getPlayer().getName() + " is now level §e" + e.getJob().getLevel() + " §6on §e" + e.getJob().getJob().name() + "§6 job's", LogType.INFO);

    }
}
