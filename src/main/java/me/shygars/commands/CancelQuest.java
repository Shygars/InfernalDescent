package me.shygars.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CancelQuest extends AbstractPlayerCommand {
    public CancelQuest() {
        super("cancelquest", "Restore starting state of the experience.");
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        if (world.getName().equals("default")) {
            world.breakBlock(-7, 78, -15, 0);
            world.setBlock(-1, 83, 1, "Banner_Cancel");
            world.breakBlock(-8, 83, -16, 0);
            world.breakBlock(-7, 83, -16, 0);
            world.breakBlock(-6, 83, -16, 0);
            world.breakBlock(-5, 83, -16, 0);
            world.breakBlock(-8, 82, -16, 0);
            world.breakBlock(-7, 82, -16, 0);
            world.breakBlock(-6, 82, -16, 0);
            world.breakBlock(-5, 82, -16, 0);
            world.breakBlock(-8, 81, -16, 0);
            world.breakBlock(-7, 81, -16, 0);
            world.breakBlock(-6, 81, -16, 0);
            world.breakBlock(-5, 81, -16, 0);
            world.breakBlock(-8, 80, -16, 0);
            world.breakBlock(-7, 80, -16, 0);
            world.breakBlock(-6, 80, -16, 0);
            world.breakBlock(-5, 80, -16, 0);
        }
    }
}
