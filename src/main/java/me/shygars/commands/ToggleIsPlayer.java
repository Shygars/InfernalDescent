package me.shygars.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.IsPlayer;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ToggleIsPlayer extends AbstractTargetPlayerCommand {

    public ToggleIsPlayer() {
        super("toggleisplayer", "Toggle on/off if this player will actually play the map.");
    }


    @Override
    protected void execute(
            @NonNullDecl CommandContext commandContext,
            @NullableDecl Ref<EntityStore> sourceRef,
            @NonNullDecl Ref<EntityStore> targetRef,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world,
            @NonNullDecl Store<EntityStore> store)
    {
        PlayerRef targetPlayerRef = store.getComponent(targetRef, PlayerRef.getComponentType());
        if (sourceRef != null) {
            playerRef = store.getComponent(sourceRef, PlayerRef.getComponentType());
        }
        assert playerRef != null;
        assert targetPlayerRef != null;
        if (store.getComponent(targetRef, InfernalDescent.instance.getIsPlayer()) == null) {
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " is now a Player."));
            store.putComponent(targetRef, InfernalDescent.instance.getIsPlayer(), new IsPlayer());
        }
        else {
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " just want to spectate."));
            store.removeComponent(targetRef, InfernalDescent.instance.getIsPlayer());
        }
    }
}