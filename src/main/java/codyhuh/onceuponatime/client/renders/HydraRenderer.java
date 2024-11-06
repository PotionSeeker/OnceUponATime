package codyhuh.onceuponatime.client.renders;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HydraModel;
import codyhuh.onceuponatime.common.entities.Hydra;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HydraRenderer extends MobRenderer<Hydra, HydraModel<Hydra>> {
    private static final ResourceLocation LOC = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/hydra/hydra.png");

    public HydraRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new HydraModel<>(cntxt.bakeLayer(HydraModel.LAYER_LOCATION)), 1.0F);
    }

    public ResourceLocation getTextureLocation(Hydra entity) {
        return LOC;
    }
}