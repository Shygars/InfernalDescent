package me.shygars.systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerClassStatsSystem extends RefChangeSystem<EntityStore, PlayerClass> {
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();

    @Nonnull
    @Override
    public ComponentType<EntityStore, PlayerClass> componentType() {
        return InfernalDescent.instance.getPlayerClassComponent();
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType());
    }

    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull PlayerClass component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
    }

    @Override
    public void onComponentSet(@Nonnull Ref<EntityStore> ref, @Nullable PlayerClass oldPlayerClass, @Nonnull PlayerClass newPlayerClass, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        commandBuffer.getExternalData().getWorld().execute(() ->{
            // Check if stats up index too high/low, then sets at max/min
            if (newPlayerClass.getHealthStatsUp() > 200) {
                newPlayerClass.setHealthStatsUp(200);
            }
            else if (newPlayerClass.getHealthStatsUp() < 0) {
                newPlayerClass.setHealthStatsUp(0);
            }
            if (newPlayerClass.getStrengthStatsUp() > 75) {
                newPlayerClass.setStrengthStatsUp(75);
            }
            else if (newPlayerClass.getStrengthStatsUp() < 12) {
                newPlayerClass.setStrengthStatsUp(12);
            }
            if (newPlayerClass.getResistanceStatsUp() > 150) {
                newPlayerClass.setResistanceStatsUp(150);
            }
            else if (newPlayerClass.getResistanceStatsUp() < 50) {
                newPlayerClass.setResistanceStatsUp(50);
            }
            if (newPlayerClass.getStaminaStatsUp() > 72) {
                newPlayerClass.setStaminaStatsUp(72);
            }
            else if (newPlayerClass.getStaminaStatsUp() < 0) {
                newPlayerClass.setStaminaStatsUp(0);
            }
            StaticModifier maxHealthModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, newPlayerClass.getStatsUpValue(newPlayerClass.HEALTH_STAT_UP));
            StaticModifier maxStaminaModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, newPlayerClass.getStatsUpValue(newPlayerClass.STAMINA_STAT_UP));
            EntityStatMap entityStatMapComponent = commandBuffer.getComponent(ref, STAT_MAP_COMPONENT_TYPE);
            assert entityStatMapComponent != null;
            entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Health"),"Health", maxHealthModifier);
            entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Stamina"), "Stamina", maxStaminaModifier);
            // Add missing health
            assert oldPlayerClass != null;
            if (newPlayerClass.getStatsUpValue(newPlayerClass.HEALTH_STAT_UP) > oldPlayerClass.getStatsUpValue(oldPlayerClass.HEALTH_STAT_UP)) {
                entityStatMapComponent.addStatValue(EntityStatType.getAssetMap().getIndex("Health"), newPlayerClass.getStatsUpValue(newPlayerClass.HEALTH_STAT_UP) - oldPlayerClass.getStatsUpValue(oldPlayerClass.HEALTH_STAT_UP));
            }
        });
    }

    @Override
    public void onComponentRemoved(@Nonnull Ref<EntityStore> ref, @Nonnull PlayerClass component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
    }
}
