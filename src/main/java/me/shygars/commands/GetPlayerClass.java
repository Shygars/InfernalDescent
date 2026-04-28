package me.shygars.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.shygars.InfernalDescent;
import me.shygars.components.PlayerClass;
import me.shygars.game.classes.PlayerClassNames;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Objects;

public class GetPlayerClass extends AbstractTargetPlayerCommand {

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
        Player player = store.getComponent(targetRef, Player.getComponentType());
        Player targetPlayer = store.getComponent(targetRef, Player.getComponentType());
        PlayerClass playerClass = store.getComponent(targetRef, InfernalDescent.instance.getPlayerClassComponent());
        if (sourceRef != null) {
            player = store.getComponent(sourceRef, Player.getComponentType());
        }
        assert player != null;
        assert targetPlayer != null;
        if (playerClass != null) {
            player.sendMessage(Message.raw(targetPlayer.getDisplayName() + " is the " + PlayerClassNames.getClassName(playerClass.getCurrentClass())));
        }
        else {
            player.sendMessage(Message.raw(targetPlayer.getDisplayName() + " do not have a class."));
        }
    }
}