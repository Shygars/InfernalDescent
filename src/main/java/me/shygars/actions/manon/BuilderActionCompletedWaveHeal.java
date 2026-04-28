package me.shygars.actions.manon;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.AssetHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.asset.EntityStatExistsValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionSetStat;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class BuilderActionCompletedWaveHeal extends BuilderActionBase {
    protected final AssetHolder stat = new AssetHolder();
        @NullableDecl
    @Override
    public String getShortDescription() {
        return "Give back 25% health";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionCompletedWaveHeal(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionCompletedWaveHeal readConfig(@Nonnull JsonElement data) {
        this.requireAsset(data, "Stat", this.stat, EntityStatExistsValidator.required(), BuilderDescriptorState.Stable, "The entity stat to affect.", null);
        return this;
    }

    public int getStat(@Nonnull BuilderSupport support) {
        return EntityStatType.getAssetMap().getIndex(this.stat.get(support.getExecutionContext()));
    }
}
