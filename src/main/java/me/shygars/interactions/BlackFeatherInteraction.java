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
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import me.shygars.InfernalDescent;
import me.shygars.components.BlackFeather;
import me.shygars.components.IsPlayer;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItemsDistribution;
import me.shygars.game.classes.PlayerClassNames;

import javax.annotation.Nonnull;

public class BlackFeatherInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<BlackFeatherInteraction> CODEC = BuilderCodec.builder(BlackFeatherInteraction.class, BlackFeatherInteraction::new, SimpleInstantInteraction.CODEC)
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
        commandBuffer.putComponent(ref, InfernalDescent.instance.getBlackFeather(), new BlackFeather());
    }
}
