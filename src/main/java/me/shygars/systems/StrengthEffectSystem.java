package me.shygars.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

import java.util.Arrays;

public class StrengthEffectSystem extends DamageEventSystem {
    @Nonnull
    private final Query<EntityStore> query;

    public StrengthEffectSystem() {
        this.query = EffectControllerComponent.getComponentType();
    }

    @Nonnull
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getGatherDamageGroup();
    }

    @Nonnull
    public Query<EntityStore> getQuery() {
        return this.query;
    }

    @Override
    public void handle(int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl Damage damage) {
        Damage.Source source = damage.getSource();
        if (source.getClass() == Damage.EntitySource.class) {
            Damage.EntitySource entitySource = (Damage.EntitySource) source;
            Ref<EntityStore> attackerRef = entitySource.getRef();
            EffectControllerComponent effectController = commandBuffer.getComponent(attackerRef, EffectControllerComponent.getComponentType());
            if (effectController != null) {
                ActiveEntityEffect[] activeEffects = effectController.getAllActiveEntityEffects();
                if (activeEffects != null) {
                    for (ActiveEntityEffect active : activeEffects) {
                        int effectIndex = active.getEntityEffectIndex();
                        EntityEffect effect = EntityEffect.getAssetMap().getAsset(effectIndex);
                        if (effect != null) {
                            if ("Cosmic_Grimoire_Strength".equals(effect.getId())) {
                                float original = damage.getAmount();
                                damage.setAmount(original * 1.25F);
                            }
                            if ("Potion_Strength".equals(effect.getId())) {
                                float original = damage.getAmount();
                                damage.setAmount(original * 1.5F);
                            }
                        }
                    }
                }
            }
        }
    }
}
