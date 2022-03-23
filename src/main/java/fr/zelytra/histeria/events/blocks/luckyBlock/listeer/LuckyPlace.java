/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.listeer;

import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import fr.zelytra.histeria.events.blocks.luckyBlock.event.*;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyPlace implements Listener {

    private final static Material luckyBlockType = CustomMaterial.LUCKY_BLOCK.getVanillaMaterial();
    private final static Random random = new Random();

    private final static List<LuckyEvent> luckyEventList = new ArrayList<>();
    private final static int outOf = 1000, origin = 1;

    {
        luckyEventList.add(new AnvilRain(1));
        luckyEventList.add(new AttackOnDado(1));
        luckyEventList.add(new BadPotions(1));
        luckyEventList.add(new BBQ(1));
        luckyEventList.add(new BlockSpreading(1));
        luckyEventList.add(new CakeLie(1));
        luckyEventList.add(new Foucault(1));
        luckyEventList.add(new FakeBedrock(1));
        luckyEventList.add(new GoodDogo(1));
        luckyEventList.add(new HisteriteIngot(1));
        luckyEventList.add(new Kaboom(1));
        luckyEventList.add(new LuckyBlockCeption(1));
        luckyEventList.add(new NocturiteCore(1));
        luckyEventList.add(new NotchWither(1));
        luckyEventList.add(new ObsidianTrap(1));
        luckyEventList.add(new Raid(1));
        luckyEventList.add(new SpiderTrap(1));
        luckyEventList.add(new VillagerHero(1));
        luckyEventList.add(new XPShower(1));
        luckyEventList.add(new Zaucisse(1));
        luckyEventList.add(new CuteFish(1));
    }

    @EventHandler
    public void onPlace(BlockBreakEvent e) {

        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

        //Checking silk touch enchant
        if (e.getPlayer().getItemInUse() != null) {
            if (e.getPlayer().getItemInUse().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
                return;
        }

        e.setDropItems(false);

        int draw = random.nextInt(origin, outOf);

        //Executing draw and event
        for (LuckyEvent event : luckyEventList) {
            if (draw <= event.getLuck()) {
                event.run(e);
                break;
            }

            draw -= event.getLuck();
        }
    }
}
