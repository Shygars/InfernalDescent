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
import me.shygars.components.SoulForm;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ToggleSoulForm extends AbstractTargetPlayerCommand {
    public ToggleSoulForm() {
        super("togglesoulform", "Toggle on/off soul form.");
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
        Player player = store.getComponent(targetRef, Player.getComponentType());
        Player targetPlayer = store.getComponent(targetRef, Player.getComponentType());
        PlayerClass playerClass = store.getComponent(targetRef, InfernalDescent.instance.getPlayerClassComponent());
        if (sourceRef != null) {
            player = store.getComponent(sourceRef, Player.getComponentType());
        }
        assert player != null;
        assert targetPlayer != null;
        store.tryRemoveComponent(targetRef, InfernalDescent.instance.getReturningSurface());
        if (playerClass != null) {
            if (store.getComponent(targetRef, InfernalDescent.instance.getSoulFormComponent()) == null) {
                player.sendMessage(Message.raw("Soul form activated for " + targetPlayer.getDisplayName()));
                store.putComponent(targetRef, InfernalDescent.instance.getSoulFormComponent(), new SoulForm());
            }
            else {
                player.sendMessage(Message.raw("Soul form deactivated for " + targetPlayer.getDisplayName()));
                store.removeComponent(targetRef, InfernalDescent.instance.getSoulFormComponent());
            }
        }
        else {
            player.sendMessage(Message.raw("Can't have soul form if no class."));
        }
    }
}