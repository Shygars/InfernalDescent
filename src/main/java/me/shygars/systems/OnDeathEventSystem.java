package me.shygars.systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.ItemWithAllMetadata;
import com.hypixel.hytale.protocol.packets.interface_.NotificationStyle;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;
import me.shygars.InfernalDescent;
import me.shygars.components.IsPlayer;
import me.shygars.components.PlayerClass;
import me.shygars.components.SoulForm;

import javax.annotation.Nonnull;

public class OnDeathEventSystem extends DeathSystems.OnDeathSystem {
    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType());
    }

    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull DeathComponent component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        IsPlayer isPlayer = commandBuffer.getComponent(ref, InfernalDescent.instance.getIsPlayer());
        ItemWithAllMetadata icon = null;
        assert player != null;
        if (isPlayer != null) {
            if (playerClass != null) {
                if (playerClass.getCurrentClass() == 0) {
                    icon = new ItemStack("Demon_Hunter_Armor_Head").toPacket();
                }
                else if (playerClass.getCurrentClass() == 1) {
                    icon = new ItemStack("Paladin_Of_Light_Armor_Head").toPacket();
                }
                else if (playerClass.getCurrentClass() == 2) {
                    icon = new ItemStack("Shadow_Knight_Armor_Head").toPacket();
                }
                else if (playerClass.getCurrentClass() == 3) {
                    icon = new ItemStack("Ethereal_Mage_Armor_Head").toPacket();
                }
                NotificationUtil.sendNotificationToUniverse(Message.raw(player.getDisplayName() + " has died!"), Message.raw(""), icon, NotificationStyle.Danger);
            }
        }
    }

    @Override
    public void onComponentRemoved(@Nonnull Ref<EntityStore> ref, @Nonnull DeathComponent component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        IsPlayer isPlayer = commandBuffer.getComponent(ref, InfernalDescent.instance.getIsPlayer());
        assert playerRef != null;
        Vector3d pos = playerRef.getTransform().getPosition();
        Vector3f rot = playerRef.getTransform().getRotation();
        if (isPlayer != null) {
            commandBuffer.getExternalData().getWorld().execute(() -> {
                //Leave a banner behind to indicate the place where the player can be revived
                assert playerClass != null;
                if (playerClass.getCurrentClass() == 0) {
                    NPCPlugin.get().spawnNPC(store, "Banner_Demon_Hunter", null, pos, rot);
                }
                if (playerClass.getCurrentClass() == 1) {
                    NPCPlugin.get().spawnNPC(store, "Banner_Paladin_Of_Light", null, pos, rot);
                }
                if (playerClass.getCurrentClass() == 2) {
                    NPCPlugin.get().spawnNPC(store, "Banner_Shadow_Knight", null, pos, rot);
                }
                if (playerClass.getCurrentClass() == 3) {
                    NPCPlugin.get().spawnNPC(store, "Banner_Ethereal_Mage", null, pos, rot);
                }
                //Put the Soul Form Components
                commandBuffer.putComponent(ref, InfernalDescent.instance.getSoulFormComponent(), new SoulForm());
            });
        }
    }
}