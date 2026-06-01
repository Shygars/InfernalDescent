package me.shygars.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.PlayerClassNames;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class GetPlayerClass extends AbstractTargetPlayerCommand {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public GetPlayerClass() {
        super("getclass", "Get a player's class.");
    }

    @Override
    protected void execute(
            @NonNullDecl CommandContext commandContext,
            @NullableDecl Ref<EntityStore> sourceRef,
            @NonNullDecl Ref<EntityStore> targetRef,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world,
            @NonNullDecl Store<EntityStore> store) {
        PlayerRef targetPlayerRef = store.getComponent(targetRef, PlayerRef.getComponentType());
        PlayerClass playerClass = store.getComponent(targetRef, InfernalDescent.instance.getPlayerClassComponent());
        if (sourceRef != null) {
            playerRef = store.getComponent(sourceRef, PlayerRef.getComponentType());
        }
        if (playerRef == null) {
            LOGGER.atInfo().log("playerRef problem");
            return;
        }
        if (targetPlayerRef == null) {
            LOGGER.atInfo().log("targetPlayerRef problem");
            return;
        }
        if (playerClass != null) {
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " is the " + PlayerClassNames.getClassName(playerClass.getCurrentClass())));
        }
        else {
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " do not have a class."));
        }
    }
}