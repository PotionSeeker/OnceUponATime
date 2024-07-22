package codyhuh.onceuponatime.client.listeners;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.ClientShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = OnceUponATime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterShadersEventListener {

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
                new ShaderInstance(event.getResourceProvider(),
                        new ResourceLocation(OnceUponATime.MOD_ID, "rendertype_unicorn_horn"),
                        DefaultVertexFormat.NEW_ENTITY),
                shaderInstance -> {
                    ClientShaders.unicornHorn = shaderInstance;
                }

        );
    }


}
