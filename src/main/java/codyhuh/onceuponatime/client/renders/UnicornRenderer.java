package codyhuh.onceuponatime.client.renders;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.client.models.UnicornModel;
import codyhuh.onceuponatime.client.renders.layers.HippogryphArmorLayer;
import codyhuh.onceuponatime.client.renders.layers.PassengerLayer;
import codyhuh.onceuponatime.client.renders.layers.UnicornHornPowerLayer;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.common.entities.Unicorn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class UnicornRenderer extends MobRenderer<Unicorn, UnicornModel<Unicorn>> {
    private static final ResourceLocation LOC = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/unicorn/unicorn.png");

    public UnicornRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new UnicornModel<>(cntxt.bakeLayer(UnicornModel.LAYER_LOCATION)), 0.55F);
        this.addLayer(new UnicornHornPowerLayer(this, cntxt.getModelSet()));
    }

    public ResourceLocation getTextureLocation(Unicorn entity) {
        return LOC;
    }

    @Override
    protected void setupRotations(Unicorn pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
    }
}