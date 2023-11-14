package codyhuh.onceuponatime.client.renders;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.GriffinModel;
import codyhuh.onceuponatime.common.entities.Griffin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GriffinRenderer extends MobRenderer<Griffin, GriffinModel<Griffin>> {
    private static final ResourceLocation LOC = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/griffin.png");
    private static final ResourceLocation SADDLE = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/griffin_saddle.png");

    public GriffinRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new GriffinModel<>(cntxt.bakeLayer(GriffinModel.LAYER_LOCATION)), 0.7F);
    }

    public ResourceLocation getTextureLocation(Griffin entity) {
        return entity.isSaddled() ? SADDLE : LOC;
    }
}