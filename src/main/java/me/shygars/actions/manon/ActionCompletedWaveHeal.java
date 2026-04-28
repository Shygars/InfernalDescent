package me.shygars.actions.manon;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionCompletedWaveHeal extends ActionBase {
    protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
    protected final int stat;

    public ActionCompletedWaveHeal(@NonNullDecl BuilderActionCompletedWaveHeal builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.stat = builder.getStat(support);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
        for (int i = 0; i < players.size(); i++) {
            PlayerRef playerRef = (PlayerRef) players.toArray()[i];
            Ref<EntityStore> refPlayer = playerRef.getReference();
            if (refPlayer != null) {
                EntityStatMap entityStatMapComponent = store.getComponent(refPlayer, STAT_MAP_COMPONENT_TYPE);
                assert entityStatMapComponent != null;
                entityStatMapComponent.addStatValue(this.stat, 75);
            }
        }
        return true;
    }
}