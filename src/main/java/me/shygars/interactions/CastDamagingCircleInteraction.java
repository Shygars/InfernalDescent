package me.shygars.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Rotation3f;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import org.joml.Vector3d;

import javax.annotation.Nonnull;

public class CastDamagingCircleInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<CastDamagingCircleInteraction> CODEC = BuilderCodec.builder(CastDamagingCircleInteraction.class, CastDamagingCircleInteraction::new, SimpleInstantInteraction.CODEC)
            .build();

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        Ref<EntityStore> ref = interactionContext.getEntity();
        if (commandBuffer == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("CommandBuffer is null");
            return;
        }
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
        if (playerRef == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player is null");
            return;
        }
        Vector3d pos = playerRef.getTransform().getPosition();
        commandBuffer.getExternalData().getWorld().execute(() -> {
            NPCPlugin.get().spawnNPC(commandBuffer.getStore(), "Damaging_Circle", null, pos, new Rotation3f(0, 0, 0));
        });
    }
}
