package me.shygars.actions.manon;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import me.shygars.actions.zolahva.ActionSetForcedWeather;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BuilderActionSetForcedWeather extends BuilderActionBase {
    @Nonnull
    protected final StringHolder weatherId = new StringHolder();

    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Set the weather in the current world";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionSetForcedWeather(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionSetForcedWeather readConfig(@Nonnull JsonElement data) {
        this.getString(data, "Weather Id", this.weatherId, null, null, BuilderDescriptorState.Stable, "The Id of the weather to set", null);
        return this;
    }

    public String getWeatherId(@Nonnull BuilderSupport support) {
        return this.weatherId.get(support.getExecutionContext());
    }
}
