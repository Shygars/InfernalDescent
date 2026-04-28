package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class ActionTeleport extends ActionBase {
    protected final Teleport teleportComponent;
    protected final boolean selfTeleport;

    public ActionTeleport(@NonNullDecl BuilderActionTeleport builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.teleportComponent = builder.getTeleportComponent(support);
        this.selfTeleport = builder.selfTeleport(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        if (!selfTeleport) {
            Ref<EntityStore> refInteract = role.getStateSupport().getInteractionIterationTarget();
            if (refInteract == null) return false;
            store.addComponent(refInteract, Teleport.getComponentType(), teleportComponent);
        }
        else {
            store.addComponent(ref, Teleport.getComponentType(), teleportComponent);
        }
        return true;
    }
}
