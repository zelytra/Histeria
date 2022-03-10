package fr.zelytra.histeria.managers.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NPCPacket {

    private final ServerGamePacketListenerImpl connection;
    private final ServerPlayer npc;

    public NPCPacket(Player player, ServerPlayer npc) {
        connection = ((CraftPlayer) player).getHandle().connection;
        this.npc = npc;
    }

    public void setSkin(Skin skin) {

        if (skin != null) {
            GameProfile gameProfile = npc.getGameProfile();
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
        }

        SynchedEntityData watcher = npc.getEntityData();
        //watcher.watch(10, (byte) 127); no more functional
        connection.send(new ClientboundSetEntityDataPacket(npc.getId(), watcher, true));
    }

    public void setPosition(Location location) {
        npc.setPosRaw(location.getBlockX() + 0.5, location.getY(), location.getBlockZ() + 0.5);
        npc.setRot(location.getYaw(), location.getPitch());
        connection.send(new ClientboundTeleportEntityPacket(npc));
        Float yaw = location.getYaw() * 256.0F / 360.0F;
        connection.send(new ClientboundRotateHeadPacket(npc, yaw.byteValue()));
    }

    public void show() {
        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
        connection.send(new ClientboundAddPlayerPacket(npc));
    }

    public void hide() {
        connection.send(new ClientboundRemoveEntitiesPacket(npc.getId()));
    }

    public void removeFromTab() {
        //delay by  one tick ?
        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc));
    }


}
