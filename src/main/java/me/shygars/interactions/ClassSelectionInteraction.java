package me.shygars.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.common.util.ArrayUtil;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
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
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nonnull;

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
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();


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

        // If for some reason the PlayerReadyEvent didn't work
        if (playerClass == null) {
            HudManager hudManager = player.getHudManager();
            if (hudManager.getVisibleHudComponents().contains(HudComponent.Compass)) {
                hudManager.hideHudComponents(playerRef, HudComponent.Compass);
            }
            store.putComponent(ref, InfernalDescent.instance.getPlayerClassComponent(), new PlayerClass());
            // Set the respawn points on every layers
            PlayerRespawnPointData[] deadLandsRespawnPoints = player.getPlayerConfigData().getPerWorldData("TheDeadLands").getRespawnPoints();
            PlayerRespawnPointData[] lavaSpringsRespawnPoints = player.getPlayerConfigData().getPerWorldData("LavaSprings").getRespawnPoints();
            PlayerRespawnPointData[] infernalCastleRespawnPoints = player.getPlayerConfigData().getPerWorldData("InfernalCastle").getRespawnPoints();

            PlayerWorldData perWorldDataDeadLands = player.getPlayerConfigData().getPerWorldData("TheDeadLands");
            PlayerWorldData perWorldDataLavaSprings = player.getPlayerConfigData().getPerWorldData("LavaSprings");
            PlayerWorldData perWorldDataInfernalCastle = player.getPlayerConfigData().getPerWorldData("InfernalCastle");

            perWorldDataDeadLands.setRespawnPoints(ArrayUtil.append(deadLandsRespawnPoints, new PlayerRespawnPointData(new Vector3i(1, 94, -6), new Vector3d(1, 95, -6), "Dead Lands Respawn Point")));
            perWorldDataLavaSprings.setRespawnPoints(ArrayUtil.append(lavaSpringsRespawnPoints, new PlayerRespawnPointData(new Vector3i(0, 97, 1), new Vector3d(0, 98, 1), "Lava Springs Respawn Point")));
            perWorldDataInfernalCastle.setRespawnPoints(ArrayUtil.append(infernalCastleRespawnPoints, new PlayerRespawnPointData(new Vector3i(32, 105, -1), new Vector3d(32, 106, -1), "Infernal Castle Respawn Point")));

            // Initialize stats
            StaticModifier maxHealthModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, 100);
            StaticModifier maxStaminaModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, 3);
            EntityStatMap entityStatMapComponent = store.getComponent(ref, STAT_MAP_COMPONENT_TYPE);
            assert entityStatMapComponent != null;
            entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Health"),"Health", maxHealthModifier);
            entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Stamina"), "Stamina", maxStaminaModifier);
            entityStatMapComponent.addStatValue(EntityStatType.getAssetMap().getIndex("Health"), 100);
        }
        if (playerClass != null) {
            playerClass.setClass(classId);
            ClassItemsDistribution.giveClassItems(player);
            commandBuffer.putComponent(ref, InfernalDescent.instance.getIsPlayer(), new IsPlayer());
            playerRef.sendMessage(Message.raw("You are now the " + PlayerClassNames.getClassName(classId)));
            EventTitleUtil.hideEventTitleFromPlayer(playerRef, 1);
        }
    }
}
