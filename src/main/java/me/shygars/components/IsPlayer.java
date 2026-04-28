package me.shygars.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

public class IsPlayer implements Component<EntityStore> {

    public static final BuilderCodec<IsPlayer> CODEC = BuilderCodec.builder(IsPlayer.class, IsPlayer::new)
            .build();

    public IsPlayer() {
    }

    public IsPlayer(IsPlayer clone) {
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new IsPlayer(this);
    }
}
