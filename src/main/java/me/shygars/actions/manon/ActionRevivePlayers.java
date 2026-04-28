package me.shygars.actions.manon;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
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

public class ActionRevivePlayers extends ActionBase {
    public ActionRevivePlayers(@NonNullDecl BuilderActionRevivePlayers builder) {
        super(builder);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        Collection<PlayerRef> players = store.getExternalData().getWorld().getPlayerRefs();
        for (int i = 0; i < players.size(); i++) {
            PlayerRef playerRef = (PlayerRef) players.toArray()[i];
            Ref<EntityStore> refPlayer = playerRef.getReference();
            if (refPlayer != null) {
                Store<EntityStore> storePlayer = refPlayer.getStore();
                Player player = storePlayer.getComponent(refPlayer, Player.getComponentType());
                assert player != null;
                World world = storePlayer.getExternalData().getWorld();
                storePlayer.tryRemoveComponent(refPlayer, InfernalDescent.instance.getReturningSurface());
                storePlayer.tryRemoveComponent(refPlayer, InfernalDescent.instance.getSoulFormComponent());
                SoundUtil.playSoundEvent2d(refPlayer, SoundEvent.getAssetMap().getIndex("SFX_Avatar_Powers_Enable_Local"), SoundCategory.SFX, storePlayer);
                Vector3d pos = playerRef.getTransform().getPosition();
                Vector3d particlePos =  new Vector3d(pos.x, pos.y + 1, pos.z);
                ParticleUtil.spawnParticleEffect("Respawn", particlePos, storePlayer);
                world.execute(() -> {
                    ItemStack stack = (new ItemStack("Potion_Health_Big", 1));
                    player.giveItem(stack, refPlayer, storePlayer);});
            }
        }
        return true;
    }
}