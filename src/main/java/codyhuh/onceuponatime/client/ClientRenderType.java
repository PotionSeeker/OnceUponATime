package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.OnceUponATime;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ClientRenderType extends RenderType {

    public ClientRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static RenderType unicornHorn(ResourceLocation pLocation, float pU, float pV) {
        return RenderType.create(
                "unicorn_horn",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder().setShaderState(ClientShaders.RENDERTYPE_UNICORN_HORN)
                        .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                                .add(pLocation, false, false)
                                .add(new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/unicorn/unicorn_mask.png"), false, false)
                                .build()
                        )
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(pU, pV))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(false));
    }

}
