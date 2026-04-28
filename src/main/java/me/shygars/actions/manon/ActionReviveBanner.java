package me.shygars.actions.manon;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.InfernalDescent;
import me.shygars.game.classes.ClassItemsDistribution;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ActionReviveBanner extends ActionBase {
    protected final int reviveClass;
    protected final int targetSlot;

    public ActionReviveBanner(@NonNullDecl BuilderActionReviveBanner builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.reviveClass = builder.getReviveClass(support);
        this.targetSlot = builder.getTargetSlot(support);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        if (!super.canExecute(ref, role, sensorInfo, dt, store) || role.getStateSupport().getInteractionIterationTarget() == null) {
            return false;
        }
        else {
            Ref<EntityStore> targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
            if (targetRef != null) {
                Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
                for (int i = 0; i < players.size(); i++) {
                    PlayerRef playerRef = (PlayerRef) players.toArray()[i];
                    Ref<EntityStore> refPlayer = playerRef.getReference();
                    if (refPlayer != null) {
                        Store<EntityStore> storePlayer = refPlayer.getStore();
                        if (Objects.requireNonNull(storePlayer.getComponent(refPlayer, InfernalDescent.instance.getPlayerClassComponent())).getCurrentClass() == this.reviveClass) {
                            return storePlayer.getComponent(refPlayer, InfernalDescent.instance.getSoulFormComponent()) != null;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        Ref<EntityStore> targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
        if (targetRef != null) {
            Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
            for (int i = 0; i < players.size(); i++) {
                PlayerRef playerRef = (PlayerRef) players.toArray()[i];
                Ref<EntityStore> refPlayer = playerRef.getReference();
                if (refPlayer != null) {
                    Store<EntityStore> storePlayer = refPlayer.getStore();
                    World world = storePlayer.getExternalData().getWorld();
                    if (Objects.requireNonNull(storePlayer.getComponent(refPlayer, InfernalDescent.instance.getPlayerClassComponent())).getCurrentClass() == this.reviveClass) {
                        Vector3d bannerPos = Objects.requireNonNull(store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
                        Vector3f bannerRot = Objects.requireNonNull(store.getComponent(ref, TransformComponent.getComponentType())).getRotation();
                        Teleport teleport = Teleport.createForPlayer(store.getExternalData().getWorld(), new Transform(bannerPos, bannerRot));
                        storePlayer.addComponent(refPlayer, Teleport.getComponentType(), teleport);
                        storePlayer.tryRemoveComponent(refPlayer, InfernalDescent.instance.getSoulFormComponent());
                        SoundUtil.playSoundEvent3d(SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Enable_Local"), SoundCategory.SFX, bannerPos, storePlayer);
                        Vector3d particlePos = new Vector3d(bannerPos.x, bannerPos.y + 1, bannerPos.z);
                        ParticleUtil.spawnParticleEffect("Respawn", particlePos, storePlayer);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
