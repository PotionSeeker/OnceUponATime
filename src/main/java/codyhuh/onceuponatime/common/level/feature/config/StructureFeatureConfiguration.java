package codyhuh.onceuponatime.common.level.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class StructureFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<StructureFeatureConfiguration> CODEC = RecordCodecBuilder.create((configurationInstance) -> {
        return configurationInstance.group(ResourceLocation.CODEC.listOf().fieldOf("structures").forGetter((p_159830_) -> {
                    return p_159830_.structure;
                })
        ).apply(configurationInstance, StructureFeatureConfiguration::new);
    });
    public final List<ResourceLocation> structure;

    public StructureFeatureConfiguration(List<ResourceLocation> structures) {
        if (structures.isEmpty()) {
            throw new IllegalArgumentException("Structures list needs at least one entry");
        } else {
            this.structure = structures;
        }
    }
}