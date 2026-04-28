package me.shygars.actions.manon;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class ActionOpenLayerPortal extends ActionBase {
    public ActionOpenLayerPortal(@NonNullDecl BuilderActionBase builderActionBase) {
        super(builderActionBase);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        World world = store.getExternalData().getWorld();
        if (world.getName().equals("TheDeadLands")) {
            //Portal add
            world.setBlock(-31, 78,0, "Hell_Portal_Bigger_Rotated");
            //Teleporter add
            world.setBlock(-31, 80, 4, "TeleportL2");
            world.setBlock(-31, 80, 3,"TeleportL2");
            world.setBlock(-31, 80, 2,"TeleportL2");
            world.setBlock(-31, 80, 1,"TeleportL2");
            world.setBlock(-31, 80,0,"TeleportL2");
            world.setBlock(-31, 80, -1,"TeleportL2");
            world.setBlock(-31, 80, -2,"TeleportL2");
            world.setBlock(-31, 80, -3,"TeleportL2");
            world.setBlock(-31, 80, -4,"TeleportL2");

            world.setBlock(-31, 81, 4,"TeleportL2");
            world.setBlock(-31, 81, 3,"TeleportL2");
            world.setBlock(-31, 81, 2,"TeleportL2");
            world.setBlock(-31, 81, 1,"TeleportL2");
            world.setBlock(-31, 81,0,"TeleportL2");
            world.setBlock(-31, 81, -1,"TeleportL2");
            world.setBlock(-31, 81, -2,"TeleportL2");
            world.setBlock(-31, 81, -3,"TeleportL2");
            world.setBlock(-31, 81, -4,"TeleportL2");

            world.setBlock(-31, 82, 4,"TeleportL2");
            world.setBlock(-31, 82, 3,"TeleportL2");
            world.setBlock(-31, 82, 2,"TeleportL2");
            world.setBlock(-31, 82, 1,"TeleportL2");
            world.setBlock(-31, 82,0,"TeleportL2");
            world.setBlock(-31, 82, -1,"TeleportL2");
            world.setBlock(-31, 82, -2,"TeleportL2");
            world.setBlock(-31, 82, -3,"TeleportL2");
            world.setBlock(-31, 82, -4,"TeleportL2");

            world.setBlock(-31, 83, 4,"TeleportL2");
            world.setBlock(-31, 83, 3,"TeleportL2");
            world.setBlock(-31, 83, 2,"TeleportL2");
            world.setBlock(-31, 83, 1,"TeleportL2");
            world.setBlock(-31, 83,0,"TeleportL2");
            world.setBlock(-31, 83, -1,"TeleportL2");
            world.setBlock(-31, 83, -2,"TeleportL2");
            world.setBlock(-31, 83, -3,"TeleportL2");
            world.setBlock(-31, 83, -4,"TeleportL2");

            world.setBlock(-31, 84, 4,"TeleportL2");
            world.setBlock(-31, 84, 3,"TeleportL2");
            world.setBlock(-31, 84, 2,"TeleportL2");
            world.setBlock(-31, 84, 1,"TeleportL2");
            world.setBlock(-31, 84,0,"TeleportL2");
            world.setBlock(-31, 84, -1,"TeleportL2");
            world.setBlock(-31, 84, -2,"TeleportL2");
            world.setBlock(-31, 84, -3,"TeleportL2");
            world.setBlock(-31, 84, -4,"TeleportL2");

            world.setBlock(-31, 85, 4,"TeleportL2");
            world.setBlock(-31, 85, 3,"TeleportL2");
            world.setBlock(-31, 85, 2,"TeleportL2");
            world.setBlock(-31, 85, 1,"TeleportL2");
            world.setBlock(-31, 85,0,"TeleportL2");
            world.setBlock(-31, 85, -1,"TeleportL2");
            world.setBlock(-31, 85, -2,"TeleportL2");
            world.setBlock(-31, 85, -3,"TeleportL2");
            world.setBlock(-31, 85, -4,"TeleportL2");

            world.setBlock(-31, 86, 4,"TeleportL2");
            world.setBlock(-31, 86, 3,"TeleportL2");
            world.setBlock(-31, 86, 2,"TeleportL2");
            world.setBlock(-31, 86, 1,"TeleportL2");
            world.setBlock(-31, 86,0,"TeleportL2");
            world.setBlock(-31, 86, -1,"TeleportL2");
            world.setBlock(-31, 86, -2,"TeleportL2");
            world.setBlock(-31, 86, -3,"TeleportL2");
            world.setBlock(-31, 86, -4,"TeleportL2");

            world.setBlock(-31, 87, 4,"TeleportL2");
            world.setBlock(-31, 87, 3,"TeleportL2");
            world.setBlock(-31, 87, 2,"TeleportL2");
            world.setBlock(-31, 87, 1,"TeleportL2");
            world.setBlock(-31, 87,0,"TeleportL2");
            world.setBlock(-31, 87, -1,"TeleportL2");
            world.setBlock(-31, 87, -2,"TeleportL2");
            world.setBlock(-31, 87, -3,"TeleportL2");
            world.setBlock(-31, 87, -4,"TeleportL2");

            world.setBlock(-31, 88, 4,"TeleportL2");
            world.setBlock(-31, 88, 3,"TeleportL2");
            world.setBlock(-31, 88, 2,"TeleportL2");
            world.setBlock(-31, 88, 1,"TeleportL2");
            world.setBlock(-31, 88,0,"TeleportL2");
            world.setBlock(-31, 88, -1,"TeleportL2");
            world.setBlock(-31, 88, -2,"TeleportL2");
            world.setBlock(-31, 88, -3,"TeleportL2");
            world.setBlock(-31, 88, -4,"TeleportL2");

            world.setBlock(-31, 89, 4,"TeleportL2");
            world.setBlock(-31, 89, 3,"TeleportL2");
            world.setBlock(-31, 89, 2,"TeleportL2");
            world.setBlock(-31, 89, 1,"TeleportL2");
            world.setBlock(-31, 89,0,"TeleportL2");
            world.setBlock(-31, 89, -1,"TeleportL2");
            world.setBlock(-31, 89, -2,"TeleportL2");
            world.setBlock(-31, 89, -3,"TeleportL2");
            world.setBlock(-31, 89, -4,"TeleportL2");
        }
        else if (world.getName().equals("LavaSprings")) {
            //Portal add
            world.setBlock(-12, 79, 32, "Hell_Portal_Big");
            //Teleporter add
            world.setBlock(-8, 82, 32, "TeleportL3");
            world.setBlock(-9, 82, 32, "TeleportL3");
            world.setBlock(-10, 82, 32, "TeleportL3");
            world.setBlock(-11, 82, 32, "TeleportL3");
            world.setBlock(-12, 82, 32, "TeleportL3");
            world.setBlock(-13, 82, 32, "TeleportL3");
            world.setBlock(-14, 82, 32, "TeleportL3");

            world.setBlock(-8, 83, 32, "TeleportL3");
            world.setBlock(-9, 83, 32, "TeleportL3");
            world.setBlock(-10, 83, 32, "TeleportL3");
            world.setBlock(-11, 83, 32, "TeleportL3");
            world.setBlock(-12, 83, 32, "TeleportL3");
            world.setBlock(-13, 83, 32, "TeleportL3");
            world.setBlock(-14, 83, 32, "TeleportL3");

            world.setBlock(-8, 84, 32, "TeleportL3");
            world.setBlock(-9, 84, 32, "TeleportL3");
            world.setBlock(-10, 84, 32, "TeleportL3");
            world.setBlock(-11, 84, 32, "TeleportL3");
            world.setBlock(-12, 84, 32, "TeleportL3");
            world.setBlock(-13, 84, 32, "TeleportL3");
            world.setBlock(-14, 84, 32, "TeleportL3");

            world.setBlock(-8, 85, 32, "TeleportL3");
            world.setBlock(-9, 85, 32, "TeleportL3");
            world.setBlock(-10, 85, 32, "TeleportL3");
            world.setBlock(-11, 85, 32, "TeleportL3");
            world.setBlock(-12, 85, 32, "TeleportL3");
            world.setBlock(-13, 85, 32, "TeleportL3");
            world.setBlock(-14, 85, 32, "TeleportL3");

            world.setBlock(-8, 86, 32, "TeleportL3");
            world.setBlock(-9, 86, 32, "TeleportL3");
            world.setBlock(-10, 86, 32, "TeleportL3");
            world.setBlock(-11, 86, 32, "TeleportL3");
            world.setBlock(-12, 86, 32, "TeleportL3");
            world.setBlock(-13, 86, 32, "TeleportL3");
            world.setBlock(-14, 86, 32, "TeleportL3");

            world.setBlock(-8, 87, 32, "TeleportL3");
            world.setBlock(-9, 87, 32, "TeleportL3");
            world.setBlock(-10, 87, 32, "TeleportL3");
            world.setBlock(-11, 87, 32, "TeleportL3");
            world.setBlock(-12, 87, 32, "TeleportL3");
            world.setBlock(-13, 87, 32, "TeleportL3");
            world.setBlock(-14, 87, 32, "TeleportL3");
        }
        return true;
    }
}
