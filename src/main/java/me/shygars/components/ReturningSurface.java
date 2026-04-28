package me.shygars.components;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ReturningSurface implements Component<EntityStore> {

    public static final BuilderCodec<ReturningSurface> CODEC = BuilderCodec.builder(ReturningSurface.class, ReturningSurface::new)
            .build();

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return null;
    }
}
