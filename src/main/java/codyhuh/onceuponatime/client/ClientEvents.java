package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.client.renders.HippogryphRenderer;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.registry.ModEntities;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
    public static void registerKeyMappings(final RegisterKeyMappingsEvent event) {
        descendKey = create("flightDescend", KeyEvent.VK_X);

        event.register(descendKey);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + OnceUponATime.MOD_ID + "." + name, key, "key.category." + OnceUponATime.MOD_ID);
    }

    @SubscribeEvent
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> OnceUponATime.PROXY.clientInit());
    }

    @SubscribeEvent
    public void preRenderLiving(RenderLivingEvent.Pre<Player, HumanoidModel<Player>> event) {
        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!isFirstPersonPlayer(event.getEntity())) {
                MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<>(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    public boolean isFirstPersonPlayer(LivingEntity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    @Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void cameraOverlay(ViewportEvent.ComputeCameraAngles e) {
            Minecraft mc = Minecraft.getInstance();
            var player = mc.player;

            if (player != null && player.isPassenger() && player.getVehicle() instanceof Hippogryph hippogryph) {

                if (mc.options.getCameraType().isFirstPerson()) {
                    float factor = 0.25F;

                    float pitch = e.getPitch() + (float) (Mth.lerp(e.getPartialTick(), hippogryph.xRotO, hippogryph.xRot) * factor);
                    float roll = e.getRoll() - (float) (Mth.lerp(e.getPartialTick(), hippogryph.prevTilt, hippogryph.tilt));

                    Camera camera = e.getCamera();

                    camera.move(0.0D, 0.0D, (Mth.lerp(e.getPartialTick(), hippogryph.prevTilt, hippogryph.tilt)) * 0.025D);

                    e.setPitch(pitch);
                    e.setRoll(roll);
                }
                else {
                    Camera camera = e.getCamera();

                    camera.move(-0.75D, 0.0D, 0.0D);
                }
            }
        }
    }
}
