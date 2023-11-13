package codyhuh.onceuponatime;

import codyhuh.onceuponatime.common.entities.Griffin;
import codyhuh.onceuponatime.registry.ModEntities;
import codyhuh.onceuponatime.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OnceUponATime.MOD_ID)
public class OnceUponATime {
    public static final String MOD_ID = "onceuponatime";

    public OnceUponATime() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);

        bus.addListener(this::createAttributes);
        bus.addListener(this::populateTabs);
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.GRIFFIN.get(), Griffin.createGriffinAttributes().build());
    }

    private void populateTabs(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            e.accept(ModItems.GRIFFIN_SPAWN_EGG.get());
        }
    }
}
