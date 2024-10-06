package codyhuh.onceuponatime.common.level.biome;

import codyhuh.onceuponatime.registry.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils.*;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModRegionProvider extends Region {
public static final ResourceLocation LOCATION = new ResourceLocation("minecraft:overworld");
	
	public ModRegionProvider(ResourceLocation name, int weight) {
		super(name, RegionType.OVERWORLD, weight);
	}
	
	@Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
		Temperature temperature = Temperature.WARM;
		Humidity humidity = Humidity.NEUTRAL;
		Continentalness continentalness = Continentalness.DEEP_OCEAN;
		Erosion erosion = Erosion.FULL_RANGE;
		Depth depth = Depth.UNDERGROUND;
		Weirdness weirdness = Weirdness.FULL_RANGE;
		this.addBiome(mapper, temperature, humidity, continentalness, erosion, weirdness, depth, 0.4F, ModBiomes.SERPENTS_COVE);
	}
}
