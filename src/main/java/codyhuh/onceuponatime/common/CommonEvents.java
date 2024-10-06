package codyhuh.onceuponatime.common;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.common.entities.Unicorn;
import codyhuh.onceuponatime.common.level.biome.ModRegionProvider;
import codyhuh.onceuponatime.registry.ModEntities;
import codyhuh.onceuponatime.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import terrablender.api.Regions;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        Regions.register(new ModRegionProvider(new ResourceLocation(OnceUponATime.MOD_ID, "once_upon_a_time"), 1));
    }

    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.HIPPOGRYPH.get(), Hippogryph.createHippogryphAttributes().build());
        e.put(ModEntities.UNICORN.get(), Unicorn.createUnicornAttributes().build());
    }
}