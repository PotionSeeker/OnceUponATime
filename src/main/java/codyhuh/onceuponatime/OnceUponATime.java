package codyhuh.onceuponatime;

import codyhuh.onceuponatime.client.ClientProxy;
import codyhuh.onceuponatime.common.CommonProxy;
import codyhuh.onceuponatime.registry.ModEntities;
import codyhuh.onceuponatime.registry.ModFeatures;
import codyhuh.onceuponatime.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OnceUponATime.MOD_ID)
public class OnceUponATime {
    public static final String MOD_ID = "onceuponatime";
    public static CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new); // Proxy code from Alex's Caves

    public OnceUponATime() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);
        ModFeatures.FEATURES.register(bus);

        PROXY.commonInit();
    }
}
