package me.shygars.actions;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BuilderActionMessage extends BuilderActionBase {
    @Nonnull
    protected final StringHolder messageString = new StringHolder();
    protected final BooleanHolder toEveryone = new BooleanHolder();

    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Send a Message to the player";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionMessage(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionMessage readConfig(@Nonnull JsonElement data) {
        this.getString(data, "Message", this.messageString, null, null, BuilderDescriptorState.Stable, "The message to send", null);
        this.getBoolean(data, "ToEveryone", this.toEveryone, false, BuilderDescriptorState.Stable, "Send to everyone or the target only", null);
        this.requireInstructionType(EnumSet.of(InstructionType.Interaction));
        return this;
    }

    public String getMessageString(@Nonnull BuilderSupport support) {
        return this.messageString.get(support.getExecutionContext());
    }

    public Boolean getIsToEveryone(@Nonnull BuilderSupport support) {
        return this.toEveryone.get(support.getExecutionContext());
    }
}
