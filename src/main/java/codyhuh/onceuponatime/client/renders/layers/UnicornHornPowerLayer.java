package codyhuh.onceuponatime.client.renders.layers;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.models.UnicornModel;
import codyhuh.onceuponatime.common.entities.Unicorn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class UnicornHornPowerLayer<T extends Unicorn, M extends UnicornModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation POWER_LOCATION = new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/unicorn/unicorn_power.png");
   private final UnicornModel<T> model;

   public UnicornHornPowerLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
      super(pRenderer);
      this.model = new UnicornModel<>(pModelSet.bakeLayer(UnicornModel.POWER_LAYER_LOCATION));
   }

   @Override
   public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (pLivingEntity.isPowered()) {
         float f = (float)pLivingEntity.tickCount + pPartialTicks;

         VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.energySwirl(POWER_LOCATION, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
         pPoseStack.pushPose();
         model.horn_overlay.translateAndRotate(pPoseStack);
         model.horn_overlay.render(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
         pPoseStack.popPose();

         model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
         this.getParentModel().copyPropertiesTo(model);
         model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

         //model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
      }
   }

   protected float xOffset(float pTickCount) {
      return pTickCount * -0.00001F;
   }
}