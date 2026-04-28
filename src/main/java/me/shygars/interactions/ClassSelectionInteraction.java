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
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import me.shygars.InfernalDescent;
import me.shygars.components.IsPlayer;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItemsDistribution;
import me.shygars.game.classes.PlayerClassNames;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class ClassSelectionInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<ClassSelectionInteraction> CODEC = BuilderCodec.builder(ClassSelectionInteraction.class, ClassSelectionInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("ClassId", BuilderCodec.INTEGER),
                    (data, value) -> data.classId = value,
                    (data) -> data.classId)
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(4))
            .add()
            .build();

    public int classId = 0;
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
        Store<EntityStore> store = commandBuffer.getExternalData().getStore();
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        if (player == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player is null");
            return;
        }
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (playerRef == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player Ref is null");
            return;
        }
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        if (playerClass != null) {
            playerClass.setClass(classId);
            ClassItemsDistribution.giveClassItems(player);
        }
        commandBuffer.putComponent(ref, InfernalDescent.instance.getIsPlayer(), new IsPlayer());
        player.sendMessage(Message.raw("You are now the " + PlayerClassNames.getClassName(classId)));
        EventTitleUtil.hideEventTitleFromPlayer(playerRef, 1);
    }
}
