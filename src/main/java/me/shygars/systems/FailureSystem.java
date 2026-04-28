package me.shygars.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import me.shygars.InfernalDescent;
import me.shygars.components.ReturningSurface;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.TimeUnit;

public class FailureSystem extends EntityTickingSystem<EntityStore> {

    @Override
    public void tick(float dt, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        int numberOfPlayers = store.getEntityCountFor(Query.and(InfernalDescent.instance.getIsPlayer()));
        int numberOfDeadPlayers = store.getEntityCountFor(Query.and(InfernalDescent.instance.getSoulFormComponent()));
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        World world = ref.getStore().getExternalData().getWorld();
        String worldName = world.getName();
        if (!worldName.equals("default")) {
            if (numberOfPlayers == numberOfDeadPlayers) {
                //If Solo
                if (numberOfPlayers == 1) {
                    EventTitleUtil.showEventTitleToUniverse(Message.raw("You're Dead!"), Message.raw("Returning to the surface..."), false, null, 5, 1, 1);
                }
                //If Multi
                else {
                    EventTitleUtil.showEventTitleToUniverse(Message.raw("Everyone is Dead!"), Message.raw("Returning to the surface..."), false, null, 5, 1, 1);
                }
                SoundUtil.playSoundEvent2d(ref, SoundEvent.getAssetMap().getIndex("SFX_Everyone_Is_Dead"), SoundCategory.SFX, store);
                Teleport teleportComponent = Teleport.createForPlayer(Universe.get().getWorld("default"), new Transform(new Vector3d(0, 80, 0), new Vector3f(0, 0)));
                commandBuffer.putComponent(ref, InfernalDescent.instance.getReturningSurface(), new ReturningSurface());
                HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> {
                    commandBuffer.removeComponent(ref, InfernalDescent.instance.getReturningSurface());
                    commandBuffer.addComponent(ref, Teleport.getComponentType(), teleportComponent);
                }, 7L, TimeUnit.SECONDS);
            }
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(InfernalDescent.instance.getSoulFormComponent(), Query.not(InfernalDescent.instance.getReturningSurface()));
    }
}