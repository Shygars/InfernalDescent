package me.shygars.game.hud;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import me.shygars.components.PlayerClass;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class StatsUpHUD extends CustomUIHud {
    private final PlayerClass playerClass;

    public StatsUpHUD(@NonNullDecl PlayerRef playerRef, @NonNullDecl PlayerClass playerClass) {
        super(playerRef, "StatsUpHUD");
        this.playerClass = playerClass;
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        if (this.playerClass != null) {
            uiCommandBuilder.append("StatsUp.ui");
            uiCommandBuilder.set("#HealthBar.Value", percentage(0, 200, playerClass.getHealthStatsUp()));
            if (playerClass.getStrengthStatsUp() < 25) {
                uiCommandBuilder.set("#Strength.Visible", false);
                uiCommandBuilder.set("#NegativeStrength.Visible", true);
            }
            else {
                uiCommandBuilder.set("#Strength.Visible", true);
                uiCommandBuilder.set("#NegativeStrength.Visible", false);
            }
            uiCommandBuilder.set("#StrengthBar.Value", strengthPercentage(playerClass.getStrengthStatsUp()));
            uiCommandBuilder.set("#NegativeStrengthBar.Value", strengthPercentage(playerClass.getStrengthStatsUp()));
            if (playerClass.getStrengthStatsUp() > 100) {
                uiCommandBuilder.set("#Resistance.Visible", false);
                uiCommandBuilder.set("#NegativeResistance.Visible", true);
            }
            else {
                uiCommandBuilder.set("#Resistance.Visible", true);
                uiCommandBuilder.set("#NegativeResistance.Visible", false);
            }
            uiCommandBuilder.set("#ResistanceBar.Value", resistancePercentage(playerClass.getResistanceStatsUp()));
            uiCommandBuilder.set("#NegativeResistanceBar.Value", resistancePercentage(playerClass.getResistanceStatsUp()));
            uiCommandBuilder.set("#StaminaBar.Value", percentage(0, 72, playerClass.getStaminaStatsUp()));
        }
    }

    public float percentage(float min, float max, float value) {
        float range = max - min;
        float calculatedValue = value - min;
        return calculatedValue / range;
    }

    public float strengthPercentage(float value) {
        float calculatedValue;
        if (value > 25) {
            calculatedValue = value - 25;
            return calculatedValue / 50;
        }
        else if (value > 12 && value < 25) {
            calculatedValue = value - 12;
            return calculatedValue / -13 + 1;
        }
        else if (value == 12) {
            return 1;
        }
        else return 0;
    }

    public float resistancePercentage(float value) {
        float calculatedValue;
        if (value > 100) {
            calculatedValue = value - 100;
            return calculatedValue / 50;
        }
        else if (value > 50 && value < 100) {
            calculatedValue = value - 50;
            return calculatedValue / -50 + 1;
        }
        else if (value == 50) {
            return 1;
        }
        else return 0;
    }
}