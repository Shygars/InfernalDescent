package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.InfernalDescent;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class SensorDeadPlayers extends SensorBase {
    protected final int targetSlot;
    public SensorDeadPlayers(@Nonnull BuilderSensorDeadPlayers builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.targetSlot = builder.getTargetSlot(support);
    }

    public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
        if (!super.matches(ref, role, dt, store)) {
            return false;
        } else {
            Ref<EntityStore> targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
            if (targetRef != null) {
                Store<EntityStore> targetStore = targetRef.getStore();
                return targetStore.getComponent(targetRef, InfernalDescent.instance.getSoulFormComponent()) != null;
            }
        }
        return false;
    }

    @NullableDecl
    @Override
    public InfoProvider getSensorInfo() {
        return null;
    }
}
