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
    @Setter
    @Getter
    private int healthStatsUp;
    @Setter
    @Getter
    private int strengthStatsUp;
    @Setter
    @Getter
    private int resistanceStatsUp;
    @Setter
    @Getter
    public int staminaStatsUp;
    public final int HEALTH_STAT_UP = 0;
    public final int STRENGTH_STAT_UP = 1;
    public final int RESISTANCE_STAT_UP = 2;
    public final int STAMINA_STAT_UP = 3;

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
            .append(new KeyedCodec<>("HealthStatsUp", Codec.INTEGER),(data, value) -> data.healthStatsUp = value, data -> data.healthStatsUp)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(201))
            .add()
            .append(new KeyedCodec<>("StrengthStatsUp", Codec.INTEGER),(data, value) -> data.strengthStatsUp = value, data -> data.strengthStatsUp)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(11))
            .addValidator(Validators.lessThan(76))
            .add()
            .append(new KeyedCodec<>("ResistanceStatsUp", Codec.INTEGER),(data, value) -> data.resistanceStatsUp = value, data -> data.resistanceStatsUp)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-49))
            .addValidator(Validators.lessThan(151))
            .add()
            .append(new KeyedCodec<>("StaminaStatsUp", Codec.INTEGER),(data, value) -> data.staminaStatsUp = value, data -> data.staminaStatsUp)
            .addValidator(Validators.nonNull())
            .addValidator(Validators.greaterThan(-1))
            .addValidator(Validators.lessThan(73))
            .add()
            .build();

    public PlayerClass() {
        this.playerClass = 4;
        this.mainWeaponUpgrade = 0;
        this.secWeaponUpgrade = 0;
        this.armorUpgrade = 0;
        this.healthStatsUp = 50;
        this.strengthStatsUp = 25;
        this.resistanceStatsUp = 100;
        this.staminaStatsUp = 16;
    }

    public PlayerClass(int playerClass) {
        this.playerClass = playerClass;
        this.mainWeaponUpgrade = 0;
        this.secWeaponUpgrade = 0;
        this.armorUpgrade = 0;
        this.healthStatsUp = 50;
        this.strengthStatsUp = 25;
        this.resistanceStatsUp = 100;
        this.staminaStatsUp = 16;
    }

    public PlayerClass(int playerClass, int mainWeaponUpgrade, int secWeaponUpgrade, int armorUpgrade) {
        this.playerClass = playerClass;
        this.mainWeaponUpgrade = mainWeaponUpgrade;
        this.secWeaponUpgrade = secWeaponUpgrade;
        this.armorUpgrade = armorUpgrade;
        this.healthStatsUp = 50;
        this.strengthStatsUp = 25;
        this.resistanceStatsUp = 100;
        this.staminaStatsUp = 16;
    }

    public PlayerClass(int playerClass, int mainWeaponUpgrade, int secWeaponUpgrade, int armorUpgrade, int healthStatsUp, int strengthStatsUp, int resistanceStatsUp, int staminaStatsUp) {
        this.playerClass = playerClass;
        this.mainWeaponUpgrade = mainWeaponUpgrade;
        this.secWeaponUpgrade = secWeaponUpgrade;
        this.armorUpgrade = armorUpgrade;
        this.healthStatsUp = healthStatsUp;
        this.strengthStatsUp = strengthStatsUp;
        this.resistanceStatsUp = resistanceStatsUp;
        this.staminaStatsUp = staminaStatsUp;
    }

    public int getCurrentClass() {
        return this.playerClass;
    }

    public void setClass(int var) {
        this.playerClass = var;
    }

    public float getStatsUpValue(int stat) {
        if (stat == 0) {
            return this.healthStatsUp * 2;
        }
        else if (stat == 1) {
            return (float) (this.strengthStatsUp * 0.04);
        }
        else if (stat == 2) {
            return (float) (this.resistanceStatsUp * 0.01);
        }
        else if (stat == 3) {
            return (float) (this.staminaStatsUp * 0.25);
        }
        else return 0;
    }

    public PlayerClass(PlayerClass clone) {
        this.playerClass = clone.playerClass;
        this.mainWeaponUpgrade = clone.mainWeaponUpgrade;
        this.secWeaponUpgrade = clone.secWeaponUpgrade;
        this.armorUpgrade = clone.armorUpgrade;
        this.healthStatsUp = clone.healthStatsUp;
        this.strengthStatsUp = clone.strengthStatsUp;
        this.resistanceStatsUp = clone.resistanceStatsUp;
        this.staminaStatsUp = clone.staminaStatsUp;
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new PlayerClass(this);
    }
}