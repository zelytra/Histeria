package fr.zelytra.histeria.managers.hguard;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldEditHandler {
    private String structureName;
    private Player player;
    private Location location;
    private Clipboard clipboard;
    private EditSession editSession;

    public WorldEditHandler(String structureName, Player player) {
        this.structureName = structureName;
        this.player = player;
    }

    public WorldEditHandler(Location location, Clipboard clipboard) {
        this.location = location;
        this.clipboard = clipboard;
    }

    public WorldEditHandler(org.bukkit.World w) {
        World world = BukkitAdapter.adapt(w);
        this.editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
    }



    public void pasteStructure() {
        World world = BukkitAdapter.adapt(this.location.getWorld());
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
            Operation operation = new ClipboardHolder(this.clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(this.location.getX(), this.location.getY(), this.location.getZ()))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }


    }

    private Region getWorldEditRegion(Player p) {
        World world = BukkitAdapter.adapt(p.getLocation().getWorld());
        com.sk89q.worldedit.entity.Player WEp = BukkitAdapter.adapt(p);
        Region region = null;
        try {
            region = WorldEdit.getInstance().getSessionManager().findByName(WEp.getSessionKey().getName()).getSelection(world);
        } catch (IncompleteRegionException e) {

        }
        return region;
    }

    public void setBlock(Location location, BlockType material) {
        try {
            this.editSession.setBlock(BlockVector3.at(location.getX(), location.getY(), location.getZ()), material.getDefaultState());
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }


    }

    public EditSession getEditSession(){
        return this.editSession;
    }

    public String getStructureName() {
        return this.structureName;
    }

    public Region getSelection() {
        return getWorldEditRegion(this.player);
    }
}

