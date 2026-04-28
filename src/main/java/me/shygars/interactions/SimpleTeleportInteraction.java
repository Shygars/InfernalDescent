package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class SimpleTeleportInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<SimpleTeleportInteraction> CODEC = BuilderCodec.builder(SimpleTeleportInteraction.class, SimpleTeleportInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("X", BuilderCodec.DOUBLE),
                    (data, value) -> data.x = value,
                    (data) -> data.x)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("Y", BuilderCodec.DOUBLE),
                    (data, value) -> data.y = value,
                    (data) -> data.y)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("Z", BuilderCodec.DOUBLE),
                    (data, value) -> data.z = value,
                    (data) -> data.z)
            .addValidator(Validators.nonNull())
            .add()
            .build();

    public double x = 0;
    public double y = 0;
    public double z = 0;
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
        Vector3f headRotation = playerRef.getHeadRotation();
        Teleport teleportComponent = Teleport.createForPlayer(new Transform(new Vector3d(x, y, z), headRotation));
        commandBuffer.addComponent(ref, Teleport.getComponentType(), teleportComponent);
    }
}
