package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.level.feature.HippogryphNestFeature;
import codyhuh.onceuponatime.common.level.feature.config.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, OnceUponATime.MOD_ID);

    public static final RegistryObject<Feature<StructureFeatureConfiguration>> HIPPOGRYPH_NEST = FEATURES.register("hippogryph_nest", () -> new HippogryphNestFeature(StructureFeatureConfiguration.CODEC));
}