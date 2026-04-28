package me.shygars.actions.manon;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.Feature;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.IntHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.IntSingleValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BuilderActionReviveBanner extends BuilderActionBase {
    @Nonnull
    protected final IntHolder reviveClass = new IntHolder();
    protected final StringHolder targetSlot = new StringHolder();

    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Revive the targeted class player using a Revive Item";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionReviveBanner(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionReviveBanner readConfig(@Nonnull JsonElement data) {
        this.getInt(data, "ClassId", this.reviveClass, 4, IntSingleValidator.greaterEqual0(), BuilderDescriptorState.Stable, "The Class Id", null);
        this.getString(data, "TargetSlot", this.targetSlot, "LockedTarget", StringNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The target slot to check", null);
        this.requireInstructionType(EnumSet.of(InstructionType.Interaction));
        return this;
    }

    public int getReviveClass(@Nonnull BuilderSupport support) {
        return this.reviveClass.get(support.getExecutionContext());
    }

    public int getTargetSlot(@Nonnull BuilderSupport builder) {
        return builder.getTargetSlot(this.targetSlot.get(builder.getExecutionContext()));
    }
}
