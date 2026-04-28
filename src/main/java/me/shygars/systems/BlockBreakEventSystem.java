package me.shygars.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import me.shygars.config.BlockBreakConfig;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Arrays;

public class BlockBreakEventSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
    private final Config<BlockBreakConfig> config;

    public BlockBreakEventSystem(Config<BlockBreakConfig> config) {
        super(BreakBlockEvent.class);
        this.config = config;
    }

    @Override
    public void handle(int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store,
                       @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl BreakBlockEvent event) {
        // Archetype = a unique combination of component types
        // (example: Player + Health + Inventory

        /* ArchetypeChunk = a chunk of entities that all share one archetype
        *  Archetype: Player + Health + Inventory
        *  Chunk rows:
        *  index 0 = entity A (Player, Health, Inventory data)
        *  index 1 = entity B (Player, Health, Inventory data)
        */

        // index = the row of the entity inside this archetype

        // Get the entity reference for the entity at this chunk row
        Ref<EntityStore> entityStoreRef = archetypeChunk.getReferenceTo(index);

        // Retrieve the Player component data for this entity (guaranteed by the query)
        Player player = store.getComponent(entityStoreRef, Player.getComponentType());

        // Not really necessary because the above is guaranteed by the getQuerry method
        if (player == null) return;

        //Get config class from Config<> Object
        BlockBreakConfig blockBreakConfig = config.get();

        String blockId = event.getBlockType().getId();

        //Get the list of allowed blocks, change "anyMatch" to "noneMatch" to check for unallowed blocks
        boolean hasBrokenBlockId = Arrays.stream(blockBreakConfig.getAllowedBlocks()).anyMatch(id -> id.equalsIgnoreCase(blockId));

        //Check for multiple specific block
        if (!(hasBrokenBlockId)) return;

        // Spawn Particle at broken block position
        Vector3i brokenBlockPosition = event.getTargetBlock();

        ParticleUtil.spawnParticleEffect(blockBreakConfig.getParticleId(),
                new Vector3d(brokenBlockPosition.x,brokenBlockPosition.y,brokenBlockPosition.z),
                commandBuffer);

        //Play sound at broken block position
        SoundUtil.playSoundEvent2d(SoundEvent.getAssetMap().getIndex(blockBreakConfig.getSoundId()),
                SoundCategory.SFX,
                commandBuffer);

        //Send message to player "You mined: BLOCK ID"
        player.sendMessage(Message.raw("You mined: %s".formatted(blockId)));
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}