package me.shygars.actions.manon;

import com.hypixel.hytale.server.npc.asset.builder.*;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class BuilderActionRevivePlayers extends BuilderActionBase {
        @NullableDecl
    @Override
    public String getShortDescription() {
        return "Revive all players";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport builder) {
        return new ActionRevivePlayers(this);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }
}
