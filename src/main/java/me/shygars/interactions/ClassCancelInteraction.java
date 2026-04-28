package me.shygars.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItemsDistribution;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ClassCancelInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<ClassCancelInteraction> CODEC = BuilderCodec.builder(ClassCancelInteraction.class, ClassCancelInteraction::new, SimpleInstantInteraction.CODEC)
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
        Store<EntityStore> store = commandBuffer.getExternalData().getStore();
        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
        if (player == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player is null");
            return;
        }
        PlayerClass playerClass = store.getComponent(ref, InfernalDescent.instance.getPlayerClassComponent());
        if (playerClass == null) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player Class is null");
            return;
        }
        if (playerClass.getCurrentClass() <= -1 || playerClass.getCurrentClass() >= 4) {
            interactionContext.getState().state = InteractionState.Failed;
            LOGGER.atInfo().log("Player has no Class");
            return;
        }

        World world = commandBuffer.getExternalData().getWorld();
        BlockPosition classBannerPosition = null;
        String bannerCurrentClass = null;

        if (playerClass.getCurrentClass() == 0) {
            classBannerPosition = new BlockPosition(-3, 76, -5);
            bannerCurrentClass = "Banner_Demon_Hunter";
        }
        else if (playerClass.getCurrentClass() == 1) {
            classBannerPosition = new BlockPosition(-1, 76, -5);
            bannerCurrentClass = "Banner_Paladin_Of_Light";
        }
        else if (playerClass.getCurrentClass() == 2) {
            classBannerPosition = new BlockPosition(1, 76, -5);
            bannerCurrentClass = "Banner_Shadow_Knight";
        }
        else if (playerClass.getCurrentClass() == 3) {
            classBannerPosition = new BlockPosition(3, 76, -5);
            bannerCurrentClass = "Banner_Ethereal_Mage";
        }
        assert classBannerPosition != null;
        world.setBlock(classBannerPosition.x, classBannerPosition.y, classBannerPosition.z, bannerCurrentClass);

        commandBuffer.removeComponent(ref, InfernalDescent.instance.getIsPlayer());
        playerClass.setClass(4);

        ItemContainer hotbarContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-1)), ref, store);
        ItemContainer armorContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-3)), ref, store);
        ItemContainer utilityContainer = ClassItemsDistribution.getItemContainer(Objects.requireNonNull(InventoryComponent.getComponentTypeById(-5)), ref, store);
        ClassItemsDistribution.removeClassItems(hotbarContainer, armorContainer, utilityContainer, player, playerRef);
        player.sendMessage(Message.raw("Class removed."));
    }
}
