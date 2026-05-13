package me.shygars.actions;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.InfernalDescent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionMessage extends ActionBase {
    protected final String messageString;
    protected final Boolean toEveryone;

    public ActionMessage(@NonNullDecl BuilderActionMessage builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.messageString = builder.getMessageString(support);
        this.toEveryone = builder.getIsToEveryone(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        if (toEveryone) {
            Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
            for (int i = 0; i < players.size(); i++) {
                PlayerRef playerRef = (PlayerRef) players.toArray()[i];
                Ref<EntityStore> refPlayer = playerRef.getReference();
                if (refPlayer != null) {
                    Store<EntityStore> storePlayer = refPlayer.getStore();
                    Player player = storePlayer.getComponent(refPlayer, Player.getComponentType());
                    if (player == null) return false;
                    player.sendMessage(Message.raw(this.messageString));
                }
                else return false;
            }
        }
        else {
            Ref<EntityStore> refES = role.getStateSupport().getInteractionIterationTarget();
            if (refES == null) return false;
            PlayerRef playerRef = store.getComponent(refES, PlayerRef.getComponentType());
            if (playerRef == null) return false;
            Player player = store.getComponent(refES, Player.getComponentType());
            if (player == null) return false;
            player.sendMessage(Message.raw(this.messageString));
        }

        return true;
    }
}
