/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.antiCheat;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class XRayDetector implements Listener {

    private static List<XRayProfiler> profilers = new ArrayList<>();
    private final static int timer = 10; //time in second

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        XRayProfiler profiler = getProfiler(e.getPlayer());

        if (profiler == null) {
            profiler = new XRayProfiler(e.getPlayer().getName());
            profilers.add(profiler);
        }

        profiler.mine(e.getBlock().getType());
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        XRayProfiler xRayProfiler = getProfiler(e.getPlayer());

        if (xRayProfiler != null && xRayProfiler.getCommonBlockCount() <= 5.0)
            profilers.remove(xRayProfiler);

    }

    private XRayProfiler getProfiler(Player player) {
        for (XRayProfiler profile : profilers)
            if (profile.getName().equalsIgnoreCase(player.getName()))
                return profile;
        return null;
    }

    public static void profilerAnalysis() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Histeria.getInstance(), () -> {
            for (XRayProfiler profile : profilers) {
                if (profile.compute(StatType.DIAMOND) >= 10 || profile.compute(StatType.HISTERITE) >= 10 || profile.compute(StatType.NETHERITE) >= 10) {
                    Histeria.log(profile.toString(), LogType.WARN);
                    new DiscordLog(WebHookType.CHEATER, profile.toString());
                }
                profile.reset();
            }
        }, timer * 20, timer * 20);
    }


}

class XRayProfiler {
    private final String name;
    private double commonBlockCount = 1, diamondCount, histeriteCount, netheriteCount;

    public XRayProfiler(String name) {
        this.name = name;
    }

    public void mine(Material material) {
        switch (material) {
            case STONE:
            case DIORITE:
            case ANDESITE:
            case DIRT:
            case GRANITE:
                commonBlockCount++;
                break;

            case DIAMOND_ORE:
                diamondCount++;
                break;

            case CHISELED_NETHER_BRICKS:
                histeriteCount++;
                break;

            case ANCIENT_DEBRIS:
                netheriteCount++;
                break;
        }
    }

    public double getCommonBlockCount() {
        return commonBlockCount;
    }

    public double compute(StatType stat) {
        switch (stat) {
            case DIAMOND:
                return (diamondCount / commonBlockCount) * 100.0;
            case HISTERITE:
                return (histeriteCount / commonBlockCount) * 100.0;
            case NETHERITE:
                return (netheriteCount / commonBlockCount) * 100.0;
            default:
                return 0;
        }
    }

    public void reset() {
        diamondCount = 0;
        histeriteCount = 0;
        netheriteCount = 0;
        commonBlockCount = 1;
    }

    @Override
    public String toString() {
        String format = "0.00";
        NumberFormat formatter = new DecimalFormat(format);
        return name + " mining luck: Diamond(" + diamondCount + ") = " + formatter.format(compute(StatType.DIAMOND)) + "% | Histerite(" + histeriteCount + ") = " + formatter.format(compute(StatType.HISTERITE)) + "% | Netherite(" + netheriteCount + ") = " + formatter.format(compute(StatType.NETHERITE)) + "% | Stone(" + commonBlockCount + ")";
    }


    public String getName() {
        return name;
    }
}

enum StatType {
    DIAMOND,
    HISTERITE,
    NETHERITE;

}
