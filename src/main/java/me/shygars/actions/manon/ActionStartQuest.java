package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import me.shygars.game.classes.ClassItemsDistribution;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionStartQuest extends ActionBase {
    public ActionStartQuest(@NonNullDecl BuilderActionBase builderActionBase) {
        super(builderActionBase);
    }

    @Override
    public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        return super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null;
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);
        World world = store.getExternalData().getWorld();
        world.setBlock(-7, 78, -15, "Hell_Portal");
        world.breakBlock(-1, 83, 1, 0);
        world.setBlock(-8, 83, -16, "TeleportL1");
        world.setBlock(-7, 83, -16, "TeleportL1");
        world.setBlock(-6, 83, -16, "TeleportL1");
        world.setBlock(-5, 83, -16, "TeleportL1");
        world.setBlock(-8, 82, -16, "TeleportL1");
        world.setBlock(-7, 82, -16, "TeleportL1");
        world.setBlock(-6, 82, -16, "TeleportL1");
        world.setBlock(-5, 82, -16, "TeleportL1");
        world.setBlock(-8, 81, -16, "TeleportL1");
        world.setBlock(-7, 81, -16, "TeleportL1");
        world.setBlock(-6, 81, -16, "TeleportL1");
        world.setBlock(-5, 81, -16, "TeleportL1");
        world.setBlock(-8, 80, -16, "TeleportL1");
        world.setBlock(-7, 80, -16, "TeleportL1");
        world.setBlock(-6, 80, -16, "TeleportL1");
        world.setBlock(-5, 80, -16, "TeleportL1");

        Collection<PlayerRef> players = world.getPlayerRefs();
        for (int i = 0; i < players.size(); i++) {
            ClassItemsDistribution.giveClassStarterPotions((PlayerRef) players.toArray()[i]);
        }

        return true;
    }
}
