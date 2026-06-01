package me.shygars.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Rotation3f;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import me.shygars.InfernalDescent;
import me.shygars.components.IsPlayer;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.ClassItemsDistribution;
import org.joml.Vector3d;

import javax.annotation.Nonnull;
import java.util.Collection;

public class NewGameInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<NewGameInteraction> CODEC = BuilderCodec.builder(NewGameInteraction.class, NewGameInteraction::new, SimpleInstantInteraction.CODEC)
            .build();

    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();


    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        World world = Universe.get().getWorld("default");
        assert world != null;
        Teleport teleportComponent = Teleport.createForPlayer(world, new Transform(new Vector3d(0.5, 74, 3.5), new Rotation3f(0, 0, 0)));
        assert commandBuffer != null;
        Collection<PlayerRef> players = commandBuffer.getExternalData().getWorld().getPlayerRefs();
        world.execute(() ->{
            world.setBlock(-7, 79, -15, "Rock_Magma_Cooled");
            world.setBlock(-1, 83, 1, "Banner_Cancel");
            world.setBlock(-3, 76, -5, "Banner_Demon_Hunter");
            world.setBlock(-1, 76, -5, "Banner_Paladin_Of_Light");
            world.setBlock(1, 76, -5, "Banner_Shadow_Knight");
            world.setBlock(3, 76, -5, "Banner_Ethereal_Mage");
            world.breakBlock(-8, 83, -16, 0);
            world.breakBlock(-7, 83, -16, 0);
            world.breakBlock(-6, 83, -16, 0);
            world.breakBlock(-5, 83, -16, 0);
            world.breakBlock(-8, 82, -16, 0);
            world.breakBlock(-7, 82, -16, 0);
            world.breakBlock(-6, 82, -16, 0);
            world.breakBlock(-5, 82, -16, 0);
            world.breakBlock(-8, 81, -16, 0);
            world.breakBlock(-7, 81, -16, 0);
            world.breakBlock(-6, 81, -16, 0);
            world.breakBlock(-5, 81, -16, 0);
            world.breakBlock(-8, 80, -16, 0);
            world.breakBlock(-7, 80, -16, 0);
            world.breakBlock(-6, 80, -16, 0);
            world.breakBlock(-5, 80, -16, 0);
        });
        for (int i = 0; i < players.size(); i++) {
            PlayerRef playerRef = (PlayerRef) players.toArray()[i];
            Ref<EntityStore> refPlayer = playerRef.getReference();
            if (refPlayer != null) {
                Player player = commandBuffer.getComponent(refPlayer, Player.getComponentType());
                IsPlayer isPlayer = commandBuffer.getComponent(refPlayer, InfernalDescent.instance.getIsPlayer());
                if (player != null && isPlayer != null) {
                    PlayerClass newPlayerClass = new PlayerClass();
                    commandBuffer.replaceComponent(refPlayer, InfernalDescent.instance.getPlayerClassComponent(), newPlayerClass);
                    commandBuffer.removeComponent(refPlayer, InfernalDescent.instance.getIsPlayer());

                    // Fully reset stats by removing modifiers
                    StaticModifier maxHealthModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, 100);
                    StaticModifier maxStaminaModifier = new StaticModifier(Modifier.ModifierTarget.MAX, StaticModifier.CalculationType.ADDITIVE, 3);
                    EntityStatMap entityStatMapComponent = commandBuffer.getComponent(refPlayer, STAT_MAP_COMPONENT_TYPE);
                    assert entityStatMapComponent != null;
                    entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Health"),"Health", maxHealthModifier);
                    entityStatMapComponent.putModifier(EntityStatType.getAssetMap().getIndex("Stamina"), "Stamina", maxStaminaModifier);

                    ClassItemsDistribution.clearInventory(player);
                }
                commandBuffer.addComponent(refPlayer, Teleport.getComponentType(), teleportComponent);
            }
        }
        EventTitleUtil.showEventTitleToUniverse(Message.raw("Please choose your Class"), Message.raw("You won't be able to change your class once you accept the quest"), false, null, 15, 1, 1);
    }
}
