package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> SERPENTS_COVE = register("serpents_cove");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(OnceUponATime.MOD_ID, name));
    }
}
