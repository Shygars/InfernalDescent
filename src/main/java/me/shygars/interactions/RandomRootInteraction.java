package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionChain;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.InteractionManager;
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Objects;

public class RandomRootInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<RandomRootInteraction> CODEC = BuilderCodec.builder(RandomRootInteraction.class, RandomRootInteraction::new, SimpleInstantInteraction.CODEC)
        .append(new KeyedCodec<>("Interactions", BuilderCodec.STRING_ARRAY),
                (data, value) -> data.interactions = value,
                (data) -> data.interactions)
        .addValidator(Validators.nonNull())
        .add()
        .append(new KeyedCodec<>("Chance Indexes", BuilderCodec.DOUBLE_ARRAY),
                (data, value) -> data.chanceIndexes = value,
                (data) -> data.chanceIndexes)
        .addValidator(Validators.nonNull())
        .add()
        .build();

    public String[] interactions;
    public double[] chanceIndexes;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private boolean chanceIndexOutOfBound(double[] chanceIndexes) {
        for (double index : chanceIndexes) {
            if (index < 0 || index >= 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void firstRun(@NonNullDecl InteractionType interactionType, @NonNullDecl InteractionContext interactionContext, @NonNullDecl CooldownHandler cooldownHandler) {
        if (interactions.length != chanceIndexes.length) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Number of root interactions must match number of chance indexes");
            return;
        }
        if (!chanceIndexOutOfBound(chanceIndexes)) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Chance index out of bounds");
            return;
        }
        double rand = Math.random();
        InteractionChain chain = null;
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        assert commandBuffer != null;
        Ref<EntityStore> ref = interactionContext.getOwningEntity();
        InteractionManager interactionManagerComponent = commandBuffer.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
        assert interactionManagerComponent != null;
        for (int i = 0; i < interactions.length; i++) {
            if (chanceIndexes[i] < rand) {
                chain = interactionManagerComponent.initChain(interactionType, interactionContext, Objects.requireNonNull(RootInteraction.getRootInteractionOrUnknown(interactions[i])), false);
            }
        }
        if (chain == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("No root interaction chosen, total chance index are not equal to 1");
            return;
        }
        interactionManagerComponent.executeChain(ref, commandBuffer, chain);
    }
}
