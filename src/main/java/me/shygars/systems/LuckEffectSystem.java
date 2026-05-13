package me.shygars.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.ItemUtils;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.modules.item.ItemModule;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class LuckEffectSystem extends DeathSystems.OnDeathSystem {

    @Override
    public void onComponentAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl DeathComponent deathComponent, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        if (deathComponent.getDeathInfo() != null) {
            Damage.Source source = deathComponent.getDeathInfo().getSource();
            if (source.getClass() == Damage.EntitySource.class) {
                Damage.EntitySource entitySource = (Damage.EntitySource) source;
                Ref<EntityStore> attackerRef = entitySource.getRef();
                // Check if under the effect of the Luck Potion
                EffectControllerComponent attackerEffectController = commandBuffer.getComponent(attackerRef, EffectControllerComponent.getComponentType());
                if (attackerEffectController != null) {
                    ActiveEntityEffect[] attackerActiveEffects = attackerEffectController.getAllActiveEntityEffects();
                    if (attackerActiveEffects != null) {
                        for (ActiveEntityEffect active : attackerActiveEffects) {
                            int effectIndex = active.getEntityEffectIndex();
                            EntityEffect effect = EntityEffect.getAssetMap().getAsset(effectIndex);
                            if (effect != null) {
                                if ("Potion_Luck".equals(effect.getId())) {
                                    ItemModule itemModule = ItemModule.get();
                                    if (itemModule.isEnabled()) {
                                        for (ItemStack randomItem : itemModule.getRandomItemDrops("Drop_Rewards")) {
                                            ItemUtils.throwItem(ref, commandBuffer, randomItem, new Vector3d(0, 0, 0), 6);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.not(Player.getComponentType());
    }
}
