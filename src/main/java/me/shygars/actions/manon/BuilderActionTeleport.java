package me.shygars.actions.manon;

import com.google.gson.JsonElement;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.npc.asset.builder.Builder;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.FloatHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BuilderActionTeleport extends BuilderActionBase {
    @Nonnull
    protected final DoubleHolder xPos = new DoubleHolder();
    @Nonnull
    protected final DoubleHolder yPos = new DoubleHolder();
    @Nonnull
    protected final DoubleHolder zPos = new DoubleHolder();
    @Nonnull
    protected final FloatHolder xRot = new FloatHolder();
    @Nonnull
    protected final FloatHolder yRot = new FloatHolder();
    @Nonnull
    protected final FloatHolder zRot = new FloatHolder();
    @Nonnull
    protected final StringHolder targetWorld = new StringHolder();
    @Nonnull
    protected final BooleanHolder selfTeleport = new BooleanHolder();

    @NullableDecl
    @Override
    public String getShortDescription() {
        return "Teleport the entity or the target";
    }

    @NullableDecl
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @NullableDecl
    @Override
    public Action build(BuilderSupport support) {
        return new ActionTeleport(this, support);
    }

    @NullableDecl
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    public BuilderActionTeleport readConfig(@Nonnull JsonElement data) {
        this.getDouble(data, "X", this.xPos, 0, null, BuilderDescriptorState.Stable, "X coordinate", null);
        this.getDouble(data, "Y", this.yPos, 0, null, BuilderDescriptorState.Stable, "Y coordinate", null);
        this.getDouble(data, "Z", this.zPos, 0, null, BuilderDescriptorState.Stable, "Z coordinate", null);
        this.getFloat(data, "Pitch", this.xRot, 0, null, BuilderDescriptorState.Stable, "Yaw", null);
        this.getFloat(data, "Yaw", this.yRot, 0, null, BuilderDescriptorState.Stable, "Pitch", null);
        this.getFloat(data, "Roll", this.zRot, 0, null, BuilderDescriptorState.Stable, "Roll", null);
        this.getString(data, "World", this.targetWorld, null, null, BuilderDescriptorState.Stable, "Target world to teleport to.", null);
        this.getBoolean(data, "SelfTeleport", this.selfTeleport, false, BuilderDescriptorState.Stable, "Teleport self or the interactive target.", null);
        this.requireInstructionType(EnumSet.of(InstructionType.Interaction));
        return this;
    }

    public Teleport getTeleportComponent(@Nonnull BuilderSupport support) {
        World world;
        if (this.targetWorld.get(support.getExecutionContext()) != null) {
            world = Universe.get().getWorld(this.targetWorld.get(support.getExecutionContext()));
        }
        else world = support.getEntity().getWorld();
        return Teleport.createForPlayer(
                world,
                new Transform(
                        new Vector3d(
                                this.xPos.get(support.getExecutionContext()),
                                this.yPos.get(support.getExecutionContext()),
                                this.zPos.get(support.getExecutionContext())
                        ),
                        new Vector3f(
                                this.xRot.get(support.getExecutionContext()),
                                this.yRot.get(support.getExecutionContext()),
                                this.zRot.get(support.getExecutionContext())
                        )
                )
        );
    }

    public boolean selfTeleport(@Nonnull BuilderSupport support) {
        return this.selfTeleport.get(support.getExecutionContext());
    }
}
