/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import net.minecraft.world.entity.monster.Spider;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.io.InputStream;

public class SpiderTrap implements LuckyEvent {

    private final int luck;

    public SpiderTrap(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        CraftWorld world = (CraftWorld) e.getBlock().getWorld();
        for (int x = 0; x < 3; x++) {
            Spider spider = new Spider(net.minecraft.world.entity.EntityType.CAVE_SPIDER, world.getHandle());
            spider.setPos(e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
            world.getHandle().addFreshEntity(spider);
        }
        WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "cobweb", e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());

    }

    private void WEgenerate(World world, String fileName, double locX, double locY, double locZ) {

        // Load the file selected
        InputStream is = Histeria.getInstance().getResource("luckyBlock/" + fileName + ".schem");
        BuiltInClipboardFormat format = BuiltInClipboardFormat.SPONGE_SCHEMATIC;
        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(is)) {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(locX, locY, locZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }
}
