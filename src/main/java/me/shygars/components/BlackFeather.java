package me.shygars.components;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class BlackFeather implements Component<EntityStore> {

    public static final BuilderCodec<BlackFeather> CODEC = BuilderCodec.builder(BlackFeather.class, BlackFeather::new)
            .build();

    public BlackFeather() {
    }

    public BlackFeather(BlackFeather clone) {
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new BlackFeather(this);
    }
}
