package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;

import javax.annotation.Nonnull;

public class TeleportLayerInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<TeleportLayerInteraction> CODEC = BuilderCodec.builder(TeleportLayerInteraction.class, TeleportLayerInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("LayerId", BuilderCodec.INTEGER),
                    (data, value) -> data.layerId = value,
                    (data) -> data.layerId)
            .addValidator(Validators.greaterThan(0))
            .addValidator(Validators.lessThan(5))
            .add()
            .build();

    // 0 = Hub | 1 = TheDeadLands | 2 = LavaSprings | 3 = InfernalCastle | 4 = FrozenCore
    public int layerId = 1;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        Ref<EntityStore> ref = interactionContext.getEntity();
        String worldName;
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
        if (commandBuffer.getComponent(ref, InfernalDescent.instance.getSoulFormComponent()) == null) {
            if (layerId == 1) {
                worldName = "TheDeadLands";
            } else if (layerId == 2) {
                worldName = "LavaSprings";
            } else if (layerId == 3) {
                worldName = "InfernalCastle";
            } else {
                worldName = "FrozenCore";
            }
            World targetWorld = Universe.get().getWorld(worldName);
            if (targetWorld == null) {
                playerRef.sendMessage(Message.translation("server.world.notFound").param("worldName", worldName));
                interactionContext.getState().state = InteractionState.Failed;
                LOGGER.atInfo().log("Target World is null");
                return;
            }
            Teleport teleportComponent = Teleport.createForPlayer(targetWorld, new Transform(new Vector3d(0, 80, 0), new Vector3f(0, 0)));
            commandBuffer.addComponent(ref, Teleport.getComponentType(), teleportComponent);
        }
        else {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Can't go back to Hell if you're still a soul!");
        }
    }
}
