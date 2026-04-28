package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class ActionMessage extends ActionBase {

    protected final String messageString;

    public ActionMessage(@NonNullDecl BuilderActionMessage builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.messageString = builder.getMessageString(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        Ref<EntityStore> refES = role.getStateSupport().getInteractionIterationTarget();
        if (refES == null) return false;
        PlayerRef playerRef = store.getComponent(refES, PlayerRef.getComponentType());
        if (playerRef == null) return false;
        Player player = store.getComponent(refES, Player.getComponentType());
        if (player == null) return false;

        player.sendMessage(Message.raw(this.messageString));

        return true;
    }
}
