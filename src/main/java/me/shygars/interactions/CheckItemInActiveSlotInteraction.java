package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;

import javax.annotation.Nonnull;

public class CheckItemInActiveSlotInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<CheckItemInActiveSlotInteraction> CODEC = BuilderCodec.builder(CheckItemInActiveSlotInteraction.class, CheckItemInActiveSlotInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("ItemId", BuilderCodec.STRING),
                    (data, value) -> data.itemId = value,
                    (data) -> data.itemId)
            .addValidator(Validators.nonNull())
            .add()
            .build();

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public String itemId;

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        if (interactionContext.getHeldItem() != null) {
            if (interactionContext.getHeldItem().getItemId().equals(this.itemId)) {
                interactionContext.getState().state = InteractionState.Finished;
            }
            else interactionContext.getState().state = InteractionState.Failed;
        }
        else interactionContext.getState().state = InteractionState.Failed;
    }
}