package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.client.models.UnicornModel;
import codyhuh.onceuponatime.client.renders.HippogryphRenderer;
import codyhuh.onceuponatime.client.renders.UnicornRenderer;
import codyhuh.onceuponatime.common.items.DyeableHippogryphArmorItem;
import codyhuh.onceuponatime.registry.ModEntities;
import codyhuh.onceuponatime.registry.ModItems;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;
import java.io.IOException;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    public static ShaderInstance unicornHorn;
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_UNICORN_HORN = new RenderStateShard.ShaderStateShard(() -> unicornHorn);

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.HIPPOGRYPH.get(), HippogryphRenderer::new);
        e.registerEntityRenderer(ModEntities.UNICORN.get(), UnicornRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(HippogryphModel.LAYER_LOCATION, HippogryphModel::createBodyLayer);
        e.registerLayerDefinition(UnicornModel.LAYER_LOCATION, UnicornModel::createBodyLayer);
        e.registerLayerDefinition(UnicornModel.POWER_LAYER_LOCATION, UnicornModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        ItemColor armorColor = (stack, tintIndex) -> ((DyeableHippogryphArmorItem) stack.getItem()).getColor(stack);
        event.register(armorColor, ModItems.LEATHER_HIPPOGRYPH_ARMOR.get());
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
                new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(OnceUponATime.MOD_ID, "rendertype_unicorn_horn"),
                        DefaultVertexFormat.NEW_ENTITY),
                shaderInstance -> {
                    unicornHorn = shaderInstance;
                }

        );
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
}
