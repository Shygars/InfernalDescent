package me.shygars.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.event.events.ecs.DropItemEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;

import javax.annotation.Nonnull;

public class DropBlocker extends EntityEventSystem<EntityStore, DropItemEvent.PlayerRequest> {
    public DropBlocker() {
        super(DropItemEvent.PlayerRequest.class);
    }

    @Override
    public void handle(int index,
                       @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                       @Nonnull Store<EntityStore> store,
                       @Nonnull CommandBuffer<EntityStore> commandBuffer,
                       @Nonnull DropItemEvent.PlayerRequest event) {
        // Cancel g key item drop from the 2 first slots of the hotbar
        if (event.getInventorySectionId() == -1) {
            if (event.getSlotId() == 0 || event.getSlotId() == 1) {
                event.setCancelled(true);
            }
        }
        // Cancel g key item drop from the armor section
        if (event.getInventorySectionId() == -3) {
            event.setCancelled(true);
        }
    }

    @Override
    @Nonnull
    public Query<EntityStore> getQuery() {
        return Query.and(InfernalDescent.instance.getIsPlayer());
    }
}