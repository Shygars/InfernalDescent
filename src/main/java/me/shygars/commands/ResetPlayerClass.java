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
import me.shygars.game.classes.ClassItemsDistribution;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Objects;

public class ResetPlayerClass extends AbstractTargetPlayerCommand {

    public ResetPlayerClass() {
        super("resetclass", "Reset a player's class upgrades.");
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
        assert playerRef != null;
        assert targetPlayerRef != null;
        if (playerClass != null) {
            PlayerClass newPlayerClass = new PlayerClass(playerClass.getCurrentClass());
            store.replaceComponent(targetRef, InfernalDescent.instance.getPlayerClassComponent(), newPlayerClass);
            ClassItemsDistribution.giveClassItems(Objects.requireNonNull(store.getComponent(targetRef, Player.getComponentType())));
        }
        else playerRef.sendMessage(Message.raw("Player has no Class"));
        playerRef.sendMessage(Message.raw("Class of " + targetPlayerRef.getUsername() + " reseted"));
    }
}