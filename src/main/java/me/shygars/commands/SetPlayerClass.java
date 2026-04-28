package me.shygars.commands;

import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
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

public class SetPlayerClass extends AbstractTargetPlayerCommand {
    private final RequiredArg<Integer> playerClassArg;

    public SetPlayerClass() {
        super("setclass", "Set a player's class.");

        this.playerClassArg = this.withRequiredArg("class", "Class Id", ArgTypes.INTEGER)
                .addValidator(Validators.greaterThan(-1))
                .addValidator(Validators.lessThan(5));
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
        PlayerClass playerClass = store.ensureAndGetComponent(targetRef, InfernalDescent.instance.getPlayerClassComponent());
        if (sourceRef != null) {
            player = store.getComponent(sourceRef, Player.getComponentType());
        }
        assert player != null;
        playerClass.setClass(playerClassArg.get(commandContext));
        assert targetPlayer != null;
        player.sendMessage(Message.raw("Class of " + targetPlayer.getDisplayName() + " changed to " + PlayerClassNames.getClassName(playerClassArg.get(commandContext))));
    }
}