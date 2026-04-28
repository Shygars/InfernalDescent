package me.shygars.components;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class SoulForm implements Component<EntityStore> {

    public static final BuilderCodec<SoulForm> CODEC = BuilderCodec.builder(SoulForm.class, SoulForm::new)
            .build();

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return null;
    }
}
