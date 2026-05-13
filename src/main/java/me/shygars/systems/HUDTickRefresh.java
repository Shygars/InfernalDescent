package me.shygars.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.hud.StatsUpHUD;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class HUDTickRefresh extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float v, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Player player = archetypeChunk.getComponent(i, Player.getComponentType());
        PlayerRef playerRef = archetypeChunk.getComponent(i, PlayerRef.getComponentType());
        PlayerClass playerClass = archetypeChunk.getComponent(i, InfernalDescent.instance.getPlayerClassComponent());
        assert player != null;
        assert playerRef != null;
        assert playerClass != null;
        player.getHudManager().setCustomHud(playerRef, new StatsUpHUD(playerRef, playerClass));
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType());
    }
}