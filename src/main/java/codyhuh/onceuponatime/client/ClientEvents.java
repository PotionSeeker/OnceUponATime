package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.GriffinModel;
import codyhuh.onceuponatime.client.renders.GriffinRenderer;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.GRIFFIN.get(), GriffinRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(GriffinModel.LAYER_LOCATION, GriffinModel::createBodyLayer);
    }
}
