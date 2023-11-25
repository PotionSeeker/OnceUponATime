package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.client.renders.HippogryphRenderer;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.HIPPOGRYPH.get(), HippogryphRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(HippogryphModel.LAYER_LOCATION, HippogryphModel::createBodyLayer);
    }

    public static KeyMapping descendKey;

    @SubscribeEvent
    public static void register(final RegisterKeyMappingsEvent event) {
        descendKey = create("dragonDescend", KeyEvent.VK_X);

        event.register(descendKey);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + OnceUponATime.MOD_ID + "." + name, key, "key.category." + OnceUponATime.MOD_ID);
    }
}
