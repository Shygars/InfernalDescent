package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;

import javax.annotation.Nonnull;

public class SendEventTitleInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<SendEventTitleInteraction> CODEC = BuilderCodec.builder(SendEventTitleInteraction.class, SendEventTitleInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("ToEveryone", BuilderCodec.BOOLEAN),
                    (data, value) -> data.toEveryone = value,
                    (data) -> data.toEveryone)
            .add()
            .append(new KeyedCodec<>("Message", BuilderCodec.STRING),
                    (data, value) -> data.message = value,
                    (data) -> data.message)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("SecondaryMessage", BuilderCodec.STRING),
                    (data, value) -> data.secMessage = value,
                    (data) -> data.secMessage)
            .add()
            .append(new KeyedCodec<>("IsMajor", BuilderCodec.BOOLEAN),
                    (data, value) -> data.isMajor = value,
                    (data) -> data.isMajor)
            .add()
            .append(new KeyedCodec<>("Duration", BuilderCodec.FLOAT),
                    (data, value) -> data.duration = value,
                    (data) -> data.duration)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("FadeInDuration", BuilderCodec.FLOAT),
                    (data, value) -> data.fadeInDuration = value,
                    (data) -> data.fadeInDuration)
            .addValidator(Validators.nonNull())
            .add()
            .append(new KeyedCodec<>("FadeOutDuration", BuilderCodec.FLOAT),
                    (data, value) -> data.fadeOutDuration = value,
                    (data) -> data.fadeOutDuration)
            .addValidator(Validators.nonNull())
            .add()
            .build();

    public boolean toEveryone = false;
    public String message = "Your message here";
    public String secMessage;
    public boolean isMajor = false;
    public Float duration = (float) 5;
    public Float fadeInDuration = (float) 1;
    public Float fadeOutDuration = (float) 1;

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        if (commandBuffer == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("CommandBuffer is null");
            return;
        }
        if (toEveryone) {
            EventTitleUtil.showEventTitleToUniverse(Message.raw(message), Message.raw(secMessage), isMajor, null, duration, fadeInDuration, fadeOutDuration);
        }
        else {
            Ref<EntityStore> ref = interactionContext.getEntity();
            PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
            if (playerRef == null) {
                interactionContext.getState().state = InteractionState.Failed;
                LOGGER.atInfo().log("Player is null");
                return;
            }
            EventTitleUtil.showEventTitleToPlayer(playerRef, Message.raw(message), Message.raw(secMessage), isMajor, null, duration, fadeInDuration, fadeOutDuration);
        }
    }
}
