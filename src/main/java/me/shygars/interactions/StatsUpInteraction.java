package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.hud.StatsUpHUD;

import javax.annotation.Nonnull;

public class StatsUpInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<StatsUpInteraction> CODEC = BuilderCodec.builder(StatsUpInteraction.class, StatsUpInteraction::new, SimpleInstantInteraction.CODEC)
            .append(new KeyedCodec<>("HealthUps", BuilderCodec.INTEGER),
                    (data, value) -> data.healthUps = value,
                    (data) -> data.healthUps)
            .add()
            .append(new KeyedCodec<>("StrengthUps", BuilderCodec.INTEGER),
                    (data, value) -> data.strengthUps = value,
                    (data) -> data.strengthUps)
            .add()
            .append(new KeyedCodec<>("ResistanceUps", BuilderCodec.INTEGER),
                    (data, value) -> data.resistanceUps = value,
                    (data) -> data.resistanceUps)
            .add()
            .append(new KeyedCodec<>("StaminaUps", BuilderCodec.INTEGER),
                    (data, value) -> data.staminaUps = value,
                    (data) -> data.staminaUps)
            .add()
            .build();

    public int healthUps = 0;
    public int strengthUps = 0;
    public int resistanceUps = 0;
    public int staminaUps = 0;

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        Ref<EntityStore> ref = interactionContext.getEntity();
        assert commandBuffer != null;
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
        PlayerClass playerClass = commandBuffer.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        assert player != null;
        assert playerRef != null;
        assert playerClass != null;
        PlayerClass newPlayerClass = new PlayerClass(
                playerClass.getCurrentClass(),
                playerClass.getMainWeaponUpgrade(),
                playerClass.getSecWeaponUpgrade(),
                playerClass.getArmorUpgrade(),
                playerClass.getHealthStatsUp() + healthUps,
                playerClass.getStrengthStatsUp() + strengthUps,
                playerClass.getResistanceStatsUp() + resistanceUps * -1,
                playerClass.getStaminaStatsUp() + staminaUps
        );
        commandBuffer.replaceComponent(ref, InfernalDescent.instance.getPlayerClassComponent(), newPlayerClass);
    }
}
