package me.shygars.actions.manon;

import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
import com.hypixel.hytale.server.npc.instructions.Sensor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class BuilderSensorQuestStarted extends BuilderSensorBase {
    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Detect if the quest has started";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Sensor build(BuilderSupport support) {
        return new SensorQuestStarted(this);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }
}
