package me.shygars.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.components.SoulForm;
import me.shygars.game.classes.ClassItemsDistribution;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SoulFormSystem extends RefChangeSystem<EntityStore, SoulForm> {
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();

    @Nonnull
    @Override
    public ComponentType<EntityStore, SoulForm> componentType() {
        return InfernalDescent.instance.getSoulFormComponent();
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType());
    }

    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull SoulForm component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        assert player != null;

        if (playerClass != null) {
            commandBuffer.getExternalData().getWorld().execute(() -> {
                //Replace player model
                if (playerClass.getCurrentClass() == 0) commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Demon_Hunter_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 1) commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Paladin_Of_Light_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 2) commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Shadow_Knight_Soul", 1, null, false).toModel()));
                if (playerClass.getCurrentClass() == 3) commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(new Model.ModelReference("Ethereal_Mage_Soul", 1, null, false).toModel()));
            });

            ItemContainer hotbarContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
            ItemContainer armorContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-3)), ref, store);
            ItemContainer utilityContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-5)), ref, store);
            ClassItemsDistribution.removeClassItems(hotbarContainer, armorContainer, utilityContainer, player, playerRef);
        }
    }

    @Override
    public void onComponentSet(@Nonnull Ref<EntityStore> ref, @Nullable SoulForm oldAttachment, @Nonnull SoulForm newAttachment, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
    }

    @Override
    public void onComponentRemoved(@Nonnull Ref<EntityStore> ref, @Nonnull SoulForm component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        assert player != null;

        commandBuffer.getExternalData().getWorld().execute(() -> {
            //Reset player model
            PlayerSkinComponent skinComponent = store.getComponent(ref, PlayerSkinComponent.getComponentType());
            if (skinComponent == null) {
                return;
            }
            PlayerSkinComponent playerSkinComponent = store.getComponent(ref, PlayerSkinComponent.getComponentType());
            assert playerSkinComponent != null;
            Model newModel = CosmeticsModule.get().createModel(playerSkinComponent.getPlayerSkin());
            store.putComponent(ref, ModelComponent.getComponentType(), new ModelComponent(newModel));
            playerSkinComponent.setNetworkOutdated();

            // Give back class items
            ClassItemsDistribution.giveClassItems(player);
        });
    }
}
