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
import me.shygars.components.PlayerClass;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class GetPlayerClassStats extends AbstractTargetPlayerCommand {

    public GetPlayerClassStats() {
        super("getclassstats", "Get a player's class stats modifiers.");
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
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " stats:"));
            playerRef.sendMessage(Message.raw("Max Health - Index: " + playerClass.getHealthStatsUp() + " | Value: " + playerClass.getStatsUpValue(playerClass.HEALTH_STAT_UP)));
            playerRef.sendMessage(Message.raw("Strength - Index: " + playerClass.getStrengthStatsUp() + " | Value: " + playerClass.getStatsUpValue(playerClass.STRENGTH_STAT_UP)));
            playerRef.sendMessage(Message.raw("Resistance - Index: " + playerClass.getResistanceStatsUp() + " | Value: " + playerClass.getStatsUpValue(playerClass.RESISTANCE_STAT_UP)));
            playerRef.sendMessage(Message.raw("Max Stamina - Index: " + playerClass.getStaminaStatsUp() + " | Value: " + playerClass.getStatsUpValue(playerClass.STAMINA_STAT_UP)));
        }
        else {
            playerRef.sendMessage(Message.raw(targetPlayerRef.getUsername() + " do not have a class."));
        }
    }
}