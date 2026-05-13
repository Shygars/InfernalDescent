package me.shygars.actions;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BuilderActionEventTitle extends BuilderActionBase {
    @Nonnull
    protected final BooleanHolder toEveryone = new BooleanHolder();
    protected final StringHolder messageString = new StringHolder();
    protected final StringHolder secondaryMessageString = new StringHolder();
    protected final BooleanHolder isMajor = new BooleanHolder();
    protected final FloatHolder duration = new FloatHolder();
    protected final FloatHolder fadeInDuration = new FloatHolder();
    protected final FloatHolder fadeOutDuration = new FloatHolder();
    protected final StringHolder eventSound = new StringHolder();

    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Show an Event Title";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionEventTitle(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionEventTitle readConfig(@Nonnull JsonElement data) {
        this.getBoolean(data, "ToEveryone", this.toEveryone, false, BuilderDescriptorState.Stable, "Style of the event title", null);
        this.getString(data, "Message", this.messageString, null, null, BuilderDescriptorState.Stable, "The title", null);
        this.getString(data, "SecondaryMessage", this.secondaryMessageString, null, null, BuilderDescriptorState.Stable, "The sub title", null);
        this.getBoolean(data, "IsMajor", this.isMajor, false, BuilderDescriptorState.Stable, "Style of the event title", null);
        this.getFloat(data, "Duration", this.duration, 1, null, BuilderDescriptorState.Stable, "Duration the event title stays on screen", null);
        this.getFloat(data, "FadeInDuration", this.fadeInDuration, 1, null, BuilderDescriptorState.Stable, "Fade in", null);
        this.getFloat(data, "FadeOutDuration", this.fadeOutDuration, 1, null, BuilderDescriptorState.Stable, "Fade out", null);
        this.getString(data, "EventSound", this.eventSound, null, null, BuilderDescriptorState.Stable, "Event sound to play when the title shows up", null);
        this.requireInstructionType(EnumSet.of(InstructionType.Interaction));
        return this;
    }

    public Boolean getIsToEveryone(@Nonnull BuilderSupport support) {
        return this.toEveryone.get(support.getExecutionContext());
    }

    public String getMessageString(@Nonnull BuilderSupport support) {
        return this.messageString.get(support.getExecutionContext());
    }

    public String getSecondaryMessageString(@Nonnull BuilderSupport support) {
        return this.secondaryMessageString.get(support.getExecutionContext());
    }

    public Boolean getIsMajor(@Nonnull BuilderSupport support) {
        return this.isMajor.get(support.getExecutionContext());
    }

    public float getDuration(@Nonnull BuilderSupport support) {
        return this.duration.get(support.getExecutionContext());
    }

    public float getFadeInDuration(@Nonnull BuilderSupport support) {
        return this.fadeInDuration.get(support.getExecutionContext());
    }

    public float getFadeOutDuration(@Nonnull BuilderSupport support) {
        return this.fadeOutDuration.get(support.getExecutionContext());
    }

    public String getEventSound(@Nonnull BuilderSupport support) {
        return this.eventSound.get(support.getExecutionContext());
    }
}
