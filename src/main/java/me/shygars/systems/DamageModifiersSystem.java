package me.shygars.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Rotation3f;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.joml.Vector3d;

import javax.annotation.Nonnull;
import java.util.Objects;

public class DamageModifiersSystem extends DamageEventSystem {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
    @Nonnull
    private final Query<EntityStore> query;

    public DamageModifiersSystem() {
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
        if (damage.getSource() instanceof Damage.EntitySource entitySource) {
            float originalDamage = damage.getAmount();
            float damageStatsUp = 1;
            float damageResistance = 1;
            float additionalResistance = 1;
            float damageMultiplier = 1;
            float resistanceHalved = 1;
            // Stats Up Damage Multiplier Augmentation
            Ref<EntityStore> attackerRef = entitySource.getRef();
            PlayerClass playerClass = commandBuffer.getComponent(attackerRef, InfernalDescent.instance.getPlayerClassComponent());
            if (playerClass != null) {
                damageStatsUp = playerClass.getStatsUpValue(playerClass.STRENGTH_STAT_UP);
            }
            // Stats Up Resistance Augmentation
            PlayerClass attackedPlayerClass = archetypeChunk.getComponent(i, InfernalDescent.instance.getPlayerClassComponent());
            if (attackedPlayerClass != null) {
                damageResistance = attackedPlayerClass.getStatsUpValue(attackedPlayerClass.RESISTANCE_STAT_UP);
            }
            // Potions and Cosmic Damage Multiplier
            EffectControllerComponent attackerEffectController = commandBuffer.getComponent(attackerRef, EffectControllerComponent.getComponentType());
            if (attackerEffectController != null) {
                ActiveEntityEffect[] attackerActiveEffects = attackerEffectController.getAllActiveEntityEffects();
                if (attackerActiveEffects != null) {
                    for (ActiveEntityEffect active : attackerActiveEffects) {
                        int effectIndex = active.getEntityEffectIndex();
                        EntityEffect effect = EntityEffect.getAssetMap().getAsset(effectIndex);
                        if (effect != null) {
                            if ("Cosmic_Grimoire_Strength".equals(effect.getId())) {
                                damageMultiplier += .25F;
                            }
                            if ("Potion_Strength".equals(effect.getId())) {
                                damageMultiplier += .25F;
                            }
                            if ("Potion_Strength_Big".equals(effect.getId())) {
                                damageMultiplier += .5F;
                            }
                        }
                    }
                }
            }
            // Resistance Multiplier and Invincibility check
            EffectControllerComponent defenderEffectController = archetypeChunk.getComponent(i, EffectControllerComponent.getComponentType());
            if (defenderEffectController != null) {
                ActiveEntityEffect[] defenderActiveEffects = defenderEffectController.getAllActiveEntityEffects();
                if (defenderActiveEffects != null) {
                    for (ActiveEntityEffect active : defenderActiveEffects) {
                        int effectIndex = active.getEntityEffectIndex();
                        EntityEffect effect = EntityEffect.getAssetMap().getAsset(effectIndex);
                        if (effect != null) {
                            if ("Potion_Resistance".equals(effect.getId())) {
                                additionalResistance = .75F;
                            }
                            if ("Potion_Resistance_Big".equals(effect.getId())) {
                                additionalResistance = .5F;
                            }
                            if ("Resistance_Halved".equals(effect.getId())) {
                                resistanceHalved = 2F;
                            }
                            if ("True_Invincibility".equals(effect.getId())) {
                                damage.setCancelled(true);
                                Vector3d pos = Objects.requireNonNull(archetypeChunk.getComponent(i, TransformComponent.getComponentType())).getPosition();
                                Rotation3f rot = Objects.requireNonNull(archetypeChunk.getComponent(i, TransformComponent.getComponentType())).getRotation();
                                Vector3d particlePos = new Vector3d(pos.x, pos.y + 1.5, pos.z);
                                SoundUtil.playSoundEvent3d(SoundEvent.getAssetMap().getIndex("SFX_Mace_T1_Block_Impact"), SoundCategory.SFX, pos.x, pos.y, pos.z, commandBuffer);
                                ParticleUtil.spawnParticleEffect("Shield_Block", particlePos, rot.yaw(), rot.pitch(), rot.roll(), 1, 1, commandBuffer);
                                return;
                            }
                        }
                    }
                }
            }
            // Damage calculation
            damage.setAmount(originalDamage * damageStatsUp * damageMultiplier * damageResistance * additionalResistance * resistanceHalved);
            // Minimum damage 1
            if (damage.getAmount() < 1) {
                damage.setAmount(1);
            }

            // Lost Crucifix repair
            ItemContainer attackerCombinedContainer = InventoryComponent.getCombined(commandBuffer, attackerRef, InventoryComponent.getComponentTypeById(-1), InventoryComponent.getComponentTypeById(-2));
            for (short slot = 2; slot < attackerCombinedContainer.getCapacity(); slot++) {
                ItemStack checkedSlot = attackerCombinedContainer.getItemStack(slot);
                if (checkedSlot != null && Objects.requireNonNull(checkedSlot).getItemId().equals("Lost_Crucifix")) {
                    EntityStatMap entityStatMapComponent = commandBuffer.getComponent(archetypeChunk.getReferenceTo(i), STAT_MAP_COMPONENT_TYPE);
                    if (entityStatMapComponent != null) {
                        float damageOverflow = Objects.requireNonNull(entityStatMapComponent.get(EntityStatType.getAssetMap().getIndex("Health"))).get() - damage.getAmount();
                        if (damageOverflow < 0) {
                            attackerCombinedContainer.replaceItemStackInSlot(slot, checkedSlot, checkedSlot.withDurability(checkedSlot.getDurability() + (damageOverflow + damage.getAmount())));
                            if (checkedSlot.withDurability(checkedSlot.getDurability() + (damageOverflow + damage.getAmount())).getDurability() == 1000) {
                                SoundUtil.playSoundEvent2dToPlayer(Objects.requireNonNull(commandBuffer.getComponent(attackerRef, PlayerRef.getComponentType())), SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Disable_Local"), SoundCategory.SFX);
                                attackerCombinedContainer.replaceItemStackInSlot(slot, checkedSlot.withDurability(checkedSlot.getDurability() + (damageOverflow + damage.getAmount())), new ItemStack("Lost_Crucifix_Activated"));
                            }
                        }
                        else {
                            attackerCombinedContainer.replaceItemStackInSlot(slot, checkedSlot, checkedSlot.withDurability(checkedSlot.getDurability() + damage.getAmount()));
                            if (checkedSlot.withDurability(checkedSlot.getDurability() + damage.getAmount()).getDurability() == 1000) {
                                SoundUtil.playSoundEvent2dToPlayer(Objects.requireNonNull(commandBuffer.getComponent(attackerRef, PlayerRef.getComponentType())), SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Disable_Local"), SoundCategory.SFX);
                                attackerCombinedContainer.replaceItemStackInSlot(slot, checkedSlot.withDurability(checkedSlot.getDurability() + damage.getAmount()), new ItemStack("Lost_Crucifix_Activated"));
                            }
                        }
                    }
                    break;
                }
            }

            // Lost Crucifix damage absorption
            // Need to find a way to make the crucifix not take damage when the player is blocking
            if (!damage.getMetaObject(Damage.BLOCKED)) {
                PlayerRef playerRef = archetypeChunk.getComponent(i, PlayerRef.getComponentType());
                ItemContainer combinedContainer = InventoryComponent.getCombined(commandBuffer, archetypeChunk.getReferenceTo(i), InventoryComponent.getComponentTypeById(-1), InventoryComponent.getComponentTypeById(-2));
                for (short slot = 2; slot < combinedContainer.getCapacity(); slot++) {
                    ItemStack checkedSlot = combinedContainer.getItemStack(slot);
                    if (checkedSlot != null && Objects.requireNonNull(checkedSlot).getItemId().equals("Lost_Crucifix_Activated")) {
                        double newDamage = checkedSlot.getDurability() - damage.getAmount() * 4;
                        combinedContainer.replaceItemStackInSlot(slot, checkedSlot, checkedSlot.withDurability(newDamage));
                        if (newDamage < 0) damage.setAmount((float) newDamage * -1 / 4);
                        else damage.setAmount(0F);
                        assert playerRef != null;
                        Vector3d playerPos = playerRef.getTransform().getPosition();
                        SoundUtil.playSoundEvent3d(archetypeChunk.getReferenceTo(i), SoundEvent.getAssetMap().getIndex("SFX_Lost_Crucifix_Block"), playerPos, commandBuffer);
                        Vector3d particlePos = new Vector3d(playerPos.x, playerPos.y + 1.5, playerPos.z);
                        ParticleUtil.spawnParticleEffect("Lost_Crucifix_Damage_Blocked", particlePos, commandBuffer);
                        if (checkedSlot.withDurability(newDamage).getDurability() == 0) {
                            SoundUtil.playSoundEvent2d(archetypeChunk.getReferenceTo(i), SoundEvent.getAssetMap().getIndex("SFX_Glass_Break"), SoundCategory.SFX, commandBuffer);
                            combinedContainer.replaceItemStackInSlot(slot, checkedSlot.withDurability(newDamage), new ItemStack("Lost_Crucifix", 1, 0, 1000, null));
                        }
                    }
                }
            }
        }
    }
}
