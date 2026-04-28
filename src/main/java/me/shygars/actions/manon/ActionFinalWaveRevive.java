package me.shygars.actions.manon;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.InfernalDescent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionFinalWaveRevive extends ActionBase {
    private final Vector3d revivePosDeadLands = new Vector3d(2, 80, -5);
    private final Vector3d revivePosLavaSprings = new Vector3d(3, 80, -3);
    private final Vector3d revivePosInfernalCastle = new Vector3d(16.5, 80, 0);

    public ActionFinalWaveRevive(@NonNullDecl BuilderActionFinalWaveRevive builder) {
        super(builder);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
        for (int i = 0; i < players.size(); i++) {
            PlayerRef playerRef = (PlayerRef) players.toArray()[i];
            Ref<EntityStore> refPlayer = playerRef.getReference();
            if (refPlayer != null) {
                Store<EntityStore> storePlayer = refPlayer.getStore();
                if (storePlayer.getComponent(refPlayer, InfernalDescent.instance.getSoulFormComponent()) != null) {
                    World world = storePlayer.getExternalData().getWorld();
                    Teleport teleport;
                    Vector3d particlePos;
                    if (world.getName().equals("TheDeadLands")) {
                        teleport = Teleport.createForPlayer(store.getExternalData().getWorld(), new Transform(revivePosDeadLands, new Vector3f(0, 0)));
                        SoundUtil.playSoundEvent3d(SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Enable_Local"), SoundCategory.SFX, revivePosDeadLands, storePlayer);
                        particlePos = new Vector3d(revivePosDeadLands.x, revivePosDeadLands.y + 1, revivePosDeadLands.z);
                    }
                    else if (world.getName().equals("LavaSprings")) {
                        teleport = Teleport.createForPlayer(store.getExternalData().getWorld(), new Transform(revivePosLavaSprings, new Vector3f(0, 0)));
                        SoundUtil.playSoundEvent3d(SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Enable_Local"), SoundCategory.SFX, revivePosLavaSprings, storePlayer);
                        particlePos = new Vector3d(revivePosLavaSprings.x, revivePosLavaSprings.y + 1, revivePosLavaSprings.z);
                    }
                    else {
                        teleport = Teleport.createForPlayer(store.getExternalData().getWorld(), new Transform(revivePosInfernalCastle, new Vector3f(0, 0)));
                        SoundUtil.playSoundEvent3d(SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Enable_Local"), SoundCategory.SFX, revivePosInfernalCastle, storePlayer);
                        particlePos = new Vector3d(revivePosInfernalCastle.x, revivePosInfernalCastle.y + 1, revivePosInfernalCastle.z);
                    }
                    storePlayer.tryRemoveComponent(refPlayer, InfernalDescent.instance.getSoulFormComponent());
                    storePlayer.addComponent(refPlayer, Teleport.getComponentType(), teleport);
                    ParticleUtil.spawnParticleEffect("Respawn", particlePos, storePlayer);
                    return true;
                }
            }
        }
        return false;
    }
}
