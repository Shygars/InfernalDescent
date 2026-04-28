package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class SensorQuestStarted extends SensorBase {
    public SensorQuestStarted(@NonNullDecl BuilderSensorBase builder) {
        super(builder);
    }

    @NullableDecl
    @Override
    public InfoProvider getSensorInfo() {
        return null;
    }

    public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
        World world = store.getExternalData().getWorld();
        return world.getBlock(-7, 78, -15) == BlockType.getBlockIdOrUnknown("Hell_Portal", null, (Object) null);
    }
}