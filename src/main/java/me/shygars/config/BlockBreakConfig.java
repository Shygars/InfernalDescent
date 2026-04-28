package me.shygars.config;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import lombok.Getter;

@Getter
public class BlockBreakConfig {
    public static final BuilderCodec<BlockBreakConfig> CODEC = BuilderCodec.builder(BlockBreakConfig.class, BlockBreakConfig::new)
            .append(new KeyedCodec<>("AllowedBlocks", BuilderCodec.STRING_ARRAY),
                    (blockBreakConfig, allowedBlocks) -> blockBreakConfig.allowedBlocks = allowedBlocks,
                    (BlockBreakConfig::getAllowedBlocks))
            .add()
            .append(new KeyedCodec<>("ParticleId", BuilderCodec.STRING),
                    (blockBreakConfig, particleId) -> blockBreakConfig.particleId = particleId,
                    (BlockBreakConfig::getParticleId))
            .add()
            .append(new KeyedCodec<>("SoundId", BuilderCodec.STRING),
                    (blockBreakConfig, soundId) -> blockBreakConfig.soundId = soundId,
                    (BlockBreakConfig::getSoundId))
            .add()
            .build();

    private String[] allowedBlocks = {"Soil_Dirt"};
    private String particleId = "Zolahva_Nostril_Fire";
    private String soundId = "SFX_Arrow_Fire_Hit";
}
