package codyhuh.onceuponatime.client.renders;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HippogryphRenderer extends MobRenderer<Hippogryph, HippogryphModel<Hippogryph>> {
    private static final ResourceLocation LOC = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/hippogryph.png");
    private static final ResourceLocation SADDLE = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/hippogryph_saddle.png");

    public HippogryphRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new HippogryphModel<>(cntxt.bakeLayer(HippogryphModel.LAYER_LOCATION)), 0.7F);
        addLayer(new SaddleLayer<>(this, new HippogryphModel<>(cntxt.bakeLayer(HippogryphModel.LAYER_LOCATION)), SADDLE));
    }

    public ResourceLocation getTextureLocation(Hippogryph entity) {
        return LOC;
    }

    @Override
    protected void setupRotations(Hippogryph pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntityLiving.prevTilt, pEntityLiving.tilt)));

    }
}