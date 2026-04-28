package me.shygars.systems;

import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class DespawnMobSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float dt, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        World world = ref.getStore().getExternalData().getWorld();
        String worldName = world.getName();
        if (!worldName.equals("default")) {
            int numberOfPlayersInWorld = world.getPlayerCount();
            if (numberOfPlayersInWorld == 0) {
                if (worldName.equals("TheDeadLands")) {
                    world.setBlock(1, 80, -6, "Wave1_Starter_Dead_Lands");
                    //Portal remove
                    world.breakBlock(-31, 78, 0, 0);
                    //Teleporter remove
                    world.breakBlock(-31, 80, 4, 0);
                    world.breakBlock(-31, 80, 3, 0);
                    world.breakBlock(-31, 80, 2, 0);
                    world.breakBlock(-31, 80, 1, 0);
                    world.breakBlock(-31, 80, 0, 0);
                    world.breakBlock(-31, 80, -1, 0);
                    world.breakBlock(-31, 80, -2, 0);
                    world.breakBlock(-31, 80, -3, 0);
                    world.breakBlock(-31, 80, -4, 0);

                    world.breakBlock(-31, 81, 4, 0);
                    world.breakBlock(-31, 81, 3, 0);
                    world.breakBlock(-31, 81, 2, 0);
                    world.breakBlock(-31, 81, 1, 0);
                    world.breakBlock(-31, 81, 0, 0);
                    world.breakBlock(-31, 81, -1, 0);
                    world.breakBlock(-31, 81, -2, 0);
                    world.breakBlock(-31, 81, -3, 0);
                    world.breakBlock(-31, 81, -4, 0);

                    world.breakBlock(-31, 82, 4, 0);
                    world.breakBlock(-31, 82, 3, 0);
                    world.breakBlock(-31, 82, 2, 0);
                    world.breakBlock(-31, 82, 1, 0);
                    world.breakBlock(-31, 82, 0, 0);
                    world.breakBlock(-31, 82, -1, 0);
                    world.breakBlock(-31, 82, -2, 0);
                    world.breakBlock(-31, 82, -3, 0);
                    world.breakBlock(-31, 82, -4, 0);

                    world.breakBlock(-31, 83, 4, 0);
                    world.breakBlock(-31, 83, 3, 0);
                    world.breakBlock(-31, 83, 2, 0);
                    world.breakBlock(-31, 83, 1, 0);
                    world.breakBlock(-31, 83, 0, 0);
                    world.breakBlock(-31, 83, -1, 0);
                    world.breakBlock(-31, 83, -2, 0);
                    world.breakBlock(-31, 83, -3, 0);
                    world.breakBlock(-31, 83, -4, 0);

                    world.breakBlock(-31, 84, 4, 0);
                    world.breakBlock(-31, 84, 3, 0);
                    world.breakBlock(-31, 84, 2, 0);
                    world.breakBlock(-31, 84, 1, 0);
                    world.breakBlock(-31, 84, 0, 0);
                    world.breakBlock(-31, 84, -1, 0);
                    world.breakBlock(-31, 84, -2, 0);
                    world.breakBlock(-31, 84, -3, 0);
                    world.breakBlock(-31, 84, -4, 0);

                    world.breakBlock(-31, 85, 4, 0);
                    world.breakBlock(-31, 85, 3, 0);
                    world.breakBlock(-31, 85, 2, 0);
                    world.breakBlock(-31, 85, 1, 0);
                    world.breakBlock(-31, 85, 0, 0);
                    world.breakBlock(-31, 85, -1, 0);
                    world.breakBlock(-31, 85, -2, 0);
                    world.breakBlock(-31, 85, -3, 0);
                    world.breakBlock(-31, 85, -4, 0);

                    world.breakBlock(-31, 86, 4, 0);
                    world.breakBlock(-31, 86, 3, 0);
                    world.breakBlock(-31, 86, 2, 0);
                    world.breakBlock(-31, 86, 1, 0);
                    world.breakBlock(-31, 86, 0, 0);
                    world.breakBlock(-31, 86, -1, 0);
                    world.breakBlock(-31, 86, -2, 0);
                    world.breakBlock(-31, 86, -3, 0);
                    world.breakBlock(-31, 86, -4, 0);

                    world.breakBlock(-31, 87, 4, 0);
                    world.breakBlock(-31, 87, 3, 0);
                    world.breakBlock(-31, 87, 2, 0);
                    world.breakBlock(-31, 87, 1, 0);
                    world.breakBlock(-31, 87, 0, 0);
                    world.breakBlock(-31, 87, -1, 0);
                    world.breakBlock(-31, 87, -2, 0);
                    world.breakBlock(-31, 87, -3, 0);
                    world.breakBlock(-31, 87, -4, 0);

                    world.breakBlock(-31, 88, 4, 0);
                    world.breakBlock(-31, 88, 3, 0);
                    world.breakBlock(-31, 88, 2, 0);
                    world.breakBlock(-31, 88, 1, 0);
                    world.breakBlock(-31, 88, 0, 0);
                    world.breakBlock(-31, 88, -1, 0);
                    world.breakBlock(-31, 88, -2, 0);
                    world.breakBlock(-31, 88, -3, 0);
                    world.breakBlock(-31, 88, -4, 0);

                    world.breakBlock(-31, 89, 4, 0);
                    world.breakBlock(-31, 89, 3, 0);
                    world.breakBlock(-31, 89, 2, 0);
                    world.breakBlock(-31, 89, 1, 0);
                    world.breakBlock(-31, 89, 0, 0);
                    world.breakBlock(-31, 89, -1, 0);
                    world.breakBlock(-31, 89, -2, 0);
                    world.breakBlock(-31, 89, -3, 0);
                    world.breakBlock(-31, 89, -4, 0);
                }
                if (worldName.equals("LavaSprings")) {
                    world.setBlock(1, 80, -5, "Wave1_Starter_Lava_Springs");
                    //Portal remove
                    world.breakBlock(-12, 79, 32, 0);
                    //Teleporter remove
                    world.breakBlock(-8, 82, 32, 0);
                    world.breakBlock(-9, 82, 32, 0);
                    world.breakBlock(-10, 82, 32, 0);
                    world.breakBlock(-11, 82, 32, 0);
                    world.breakBlock(-12, 82, 32, 0);
                    world.breakBlock(-13, 82, 32, 0);
                    world.breakBlock(-14, 82, 32, 0);

                    world.breakBlock(-8, 83, 32, 0);
                    world.breakBlock(-9, 83, 32, 0);
                    world.breakBlock(-10, 83, 32, 0);
                    world.breakBlock(-11, 83, 32, 0);
                    world.breakBlock(-12, 83, 32, 0);
                    world.breakBlock(-13, 83, 32, 0);
                    world.breakBlock(-14, 83, 32, 0);

                    world.breakBlock(-8, 84, 32, 0);
                    world.breakBlock(-9, 84, 32, 0);
                    world.breakBlock(-10, 84, 32, 0);
                    world.breakBlock(-11, 84, 32, 0);
                    world.breakBlock(-12, 84, 32, 0);
                    world.breakBlock(-13, 84, 32, 0);
                    world.breakBlock(-14, 84, 32, 0);

                    world.breakBlock(-8, 85, 32, 0);
                    world.breakBlock(-9, 85, 32, 0);
                    world.breakBlock(-10, 85, 32, 0);
                    world.breakBlock(-11, 85, 32, 0);
                    world.breakBlock(-12, 85, 32, 0);
                    world.breakBlock(-13, 85, 32, 0);
                    world.breakBlock(-14, 85, 32, 0);

                    world.breakBlock(-8, 86, 32, 0);
                    world.breakBlock(-9, 86, 32, 0);
                    world.breakBlock(-10, 86, 32, 0);
                    world.breakBlock(-11, 86, 32, 0);
                    world.breakBlock(-12, 86, 32, 0);
                    world.breakBlock(-13, 86, 32, 0);
                    world.breakBlock(-14, 86, 32, 0);

                    world.breakBlock(-8, 87, 32, 0);
                    world.breakBlock(-9, 87, 32, 0);
                    world.breakBlock(-10, 87, 32, 0);
                    world.breakBlock(-11, 87, 32, 0);
                    world.breakBlock(-12, 87, 32, 0);
                    world.breakBlock(-13, 87, 32, 0);
                    world.breakBlock(-14, 87, 32, 0);
                }
                if (worldName.equals("InfernalCastle")) {
                    world.setBlock(13, 80, -1, "Wave1_Starter_Infernal_Castle");

                    WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
                    weatherResource.setForcedWeather("Dead_Lands_Weather");
                    WorldConfig config = world.getWorldConfig();
                    config.setForcedWeather("Dead_Lands_Weather");
                    config.markChanged();
                }
                if (ref.isValid()) {
                    commandBuffer.getExternalData().getWorld().execute(() -> {
                        if (commandBuffer.getComponent(ref, SpawnMarkerEntity.getComponentType()) == null) {
                            commandBuffer.tryRemoveEntity(ref, RemoveReason.REMOVE);
                        }
                    });
                }
            }
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.not(Player.getComponentType());
    }
}
