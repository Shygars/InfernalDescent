package me.shygars.actions;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionTeleport extends ActionBase {
    protected final Teleport teleportComponent;
    protected final boolean selfTeleport;
    protected final boolean teleportEveryone;

    public ActionTeleport(@NonNullDecl BuilderActionTeleport builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.teleportComponent = builder.getTeleportComponent(support);
        this.selfTeleport = builder.getSelfTeleport(support);
        this.teleportEveryone = builder.getTeleportEveryone(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        if (teleportEveryone) {
            Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
            for (int i = 0; i < players.size(); i++) {
                PlayerRef playerRef = (PlayerRef) players.toArray()[i];
                Ref<EntityStore> refPlayer = playerRef.getReference();
                if (refPlayer != null) {
                    store.addComponent(refPlayer, Teleport.getComponentType(), teleportComponent);
                }
                else return false;
            }
            return true;
        }
        else if (!selfTeleport) {
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
