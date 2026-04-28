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

public class PlayerClass implements Component<EntityStore> {
    private int playerClass;
    @Setter
    @Getter
    private int mainWeaponUpgrade;
    @Setter
    @Getter
    private int secWeaponUpgrade;
    @Setter
    @Getter
    private int armorUpgrade;

    public static final BuilderCodec<PlayerClass> CODEC = BuilderCodec.builder(PlayerClass.class, PlayerClass::new)
            .append(new KeyedCodec<>("PlayerClassData", Codec.INTEGER),(data, value) -> data.playerClass = value, data -> data.playerClass)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(5))
            .add()
            .append(new KeyedCodec<>("MainWeaponUpgrade", Codec.INTEGER),(data, value) -> data.mainWeaponUpgrade = value, data -> data.mainWeaponUpgrade)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(4))
            .add()
            .append(new KeyedCodec<>("SecondaryWeaponUpgrade", Codec.INTEGER),(data, value) -> data.secWeaponUpgrade = value, data -> data.secWeaponUpgrade)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(4))
            .add()
            .append(new KeyedCodec<>("ArmorUpgrade", Codec.INTEGER),(data, value) -> data.armorUpgrade = value, data -> data.armorUpgrade)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(4))
            .add()
            .build();

    public PlayerClass() {
        this.playerClass = 4;
        this.mainWeaponUpgrade = 0;
        this.secWeaponUpgrade = 0;
        this.armorUpgrade = 0;
    }

    public int getCurrentClass() {
        return this.playerClass;
    }

    public void setClass(int var) {
        this.playerClass = var;
    }

    public PlayerClass(PlayerClass clone) {
        this.playerClass = clone.playerClass;
        this.mainWeaponUpgrade = clone.mainWeaponUpgrade;
        this.secWeaponUpgrade = clone.secWeaponUpgrade;
        this.armorUpgrade = clone.armorUpgrade;
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new PlayerClass(this);
    }
}
