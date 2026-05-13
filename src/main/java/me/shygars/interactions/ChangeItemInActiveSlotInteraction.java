package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.game.classes.ClassItemsDistribution;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ChangeItemInActiveSlotInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<ChangeItemInActiveSlotInteraction> CODEC = BuilderCodec.builder(ChangeItemInActiveSlotInteraction.class, ChangeItemInActiveSlotInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("ItemId", BuilderCodec.STRING),
                    (data, value) -> data.itemId = value,
                    (data) -> data.itemId)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("Quantity", BuilderCodec.INTEGER),
                    (data, value) -> data.quantity = value,
                    (data) -> data.quantity)
            .add()
            .append(new KeyedCodec<>("Durability", BuilderCodec.FLOAT),
                    (data, value) -> data.durability = value,
                    (data) -> data.durability)
            .add()
            .append(new KeyedCodec<>("MaxDurability", BuilderCodec.FLOAT),
                    (data, value) -> data.maxDurability = value,
                    (data) -> data.maxDurability)
            .add()
            .build();

    public String itemId;
    public int quantity = 1;
    public float durability;
    public float maxDurability;

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        if (interactionContext.getHeldItem() != null) {
            Store<EntityStore> store = Objects.requireNonNull(interactionContext.getCommandBuffer()).getStore();
            Ref<EntityStore> ref = interactionContext.getEntity();
            ItemContainer hotbarContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
            if (hotbarContainer != null) {
                hotbarContainer.replaceItemStackInSlot(interactionContext.getHeldItemSlot(), interactionContext.getHeldItem(),new ItemStack(this.itemId, this.quantity, this.durability, this.maxDurability, null));
            }
        }
    }
}