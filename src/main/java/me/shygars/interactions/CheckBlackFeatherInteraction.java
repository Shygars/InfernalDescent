package me.shygars.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.BlackFeather;

import javax.annotation.Nonnull;

public class CheckBlackFeatherInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<CheckBlackFeatherInteraction> CODEC = BuilderCodec.builder(CheckBlackFeatherInteraction.class, CheckBlackFeatherInteraction::new, SimpleInstantInteraction.CODEC)
            .build();

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        if (commandBuffer == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("CommandBuffer is null");
            return;
        }
        Ref<EntityStore> ref = interactionContext.getEntity();
        BlackFeather blackFeatherComp = commandBuffer.getComponent(ref, InfernalDescent.instance.getBlackFeather());
        if (blackFeatherComp == null) {
            interactionContext.getState().state = InteractionState.Failed;
        }
        else {
            interactionContext.getState().state = InteractionState.Finished;
        }
    }
}
