package codyhuh.onceuponatime.client.models;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Collections;

public class HippogryphModel<T extends Hippogryph> extends AgeableListModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "griffin"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart l_wing_1;
	private final ModelPart l_wing_2;
	private final ModelPart r_wing_1;
	private final ModelPart r_wing_2;
	private final ModelPart ears;
	private final ModelPart l_leg_1;
	private final ModelPart r_leg_1;
	private final ModelPart l_leg_2a;
	private final ModelPart r_leg_2a;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart l_wing_1_feathers;
	private final ModelPart r_wing_1_feathers;

	public HippogryphModel(ModelPart base) {
		this.root = base.getChild("root");
		this.l_leg_1 = root.getChild("l_leg_1");
		this.r_leg_1 = root.getChild("r_leg_1");
		this.l_leg_2a = root.getChild("l_tight");
		this.r_leg_2a = root.getChild("r_tight");
		this.body = root.getChild("body");
		this.tail = body.getChild("tail");
		this.head = root.getChild("head");
		this.ears = head.getChild("ears");
		this.l_wing_1 = root.getChild("l_wing_1");
		this.l_wing_2 = l_wing_1.getChild("l_wing_2");
		this.l_wing_1_feathers = l_wing_1.getChild("l_wing_1_feathers");
		this.r_wing_1 = root.getChild("r_wing_1");
		this.r_wing_2 = r_wing_1.getChild("r_wing_2");
		this.r_wing_1_feathers = r_wing_1.getChild("r_wing_1_feathers");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.5F, 5.0F, 1.5F));

		PartDefinition l_leg_1 = root.addOrReplaceChild("l_leg_1", CubeListBuilder.create().texOffs(16, 61).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 36).addBox(-2.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.0F, -6.0F));

		PartDefinition r_leg_1 = root.addOrReplaceChild("r_leg_1", CubeListBuilder.create().texOffs(16, 61).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 36).addBox(-3.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 6.0F, -6.0F));

		PartDefinition l_wing_1 = root.addOrReplaceChild("l_wing_1", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -10.0F, -1.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -5.0F, -1.25F));

		PartDefinition l_wing_1_feathers = l_wing_1.addOrReplaceChild("l_wing_1_feathers", CubeListBuilder.create().texOffs(58, 36).addBox(4.0F, -34.0F, 0.25F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, 1.1276F));

		PartDefinition l_wing_2 = l_wing_1.addOrReplaceChild("l_wing_2", CubeListBuilder.create().texOffs(0, 79).addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 0).addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -1.5F));

		PartDefinition r_wing_1 = root.addOrReplaceChild("r_wing_1", CubeListBuilder.create().texOffs(62, 61).mirror().addBox(-1.0F, -10.0F, -1.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -5.0F, -1.25F));

		PartDefinition r_wing_1_feathers = r_wing_1.addOrReplaceChild("r_wing_1_feathers", CubeListBuilder.create().texOffs(58, 36).mirror().addBox(0.0F, -10.0F, 1.3776F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition r_wing_2 = r_wing_1.addOrReplaceChild("r_wing_2", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 79).mirror().addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -10.0F, -1.75F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-3.5F, -14.5343F, -3.75F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(100, 72).addBox(-3.5F, -14.5343F, -3.75F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.1F))
				.texOffs(105, 68).addBox(-3.5F, -14.5343F, -5.95F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(8, 79).addBox(-3.5F, -14.5343F, -5.75F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 61).addBox(-1.5F, -12.5343F, -8.75F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(8, 83).addBox(-1.5F, -8.5343F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.4657F, -8.25F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(3.5F, -13.5343F, 0.25F));

		PartDefinition cube_r1 = ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(78, 36).addBox(-5.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r2 = ears.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 36).mirror().addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -9.8724F, 11.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(66, 97).addBox(-6.0F, -4.0F, -9.8724F, 11.0F, 11.0F, 20.0F, new CubeDeformation(0.1F))
				.texOffs(0, 87).addBox(-6.0F, -4.0F, -4.8724F, 11.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 61).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.0F, 8.3776F, 0.3927F, 0.0F, 0.0F));

		PartDefinition l_tight = root.addOrReplaceChild("l_tight", CubeListBuilder.create().texOffs(0, 61).mirror().addBox(-2.0F, 0.0F, -1.75F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 6.0F, 7.8776F));

		PartDefinition r_tight = root.addOrReplaceChild("r_tight", CubeListBuilder.create().texOffs(0, 61).addBox(-2.0F, 0.0F, -1.75F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 6.0F, 7.8776F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		ears.visible = !entity.isWearingArmor();

		if (!entity.onGround() && (entity.isFlying() || entity.isLanding())) {
			limbSwing = ageInTicks * 0.75F;
			limbSwingAmount = 0.3F;

			this.root.xRot = headPitch * ((float)Math.PI / 180F);

			this.l_wing_1_feathers.visible = true;
			this.r_wing_1_feathers.visible = true;

			this.head.xRot = 0.435F;
			this.head.yRot = 0.0F;

			this.tail.xRot = 1.13446F;

			this.l_wing_1.xRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 0.5F;
			this.l_wing_1.yRot = 0.0F;
			this.l_wing_1.zRot = 1.3F + Mth.sin(limbSwing * 0.5F) * limbSwingAmount * 2.5F;
			this.l_wing_1.x = 4.0F;
			this.l_wing_1.y = -4.5F;
			this.l_wing_1.z = -1.0F;

			this.l_wing_2.xRot = -0.0872665F;
			this.l_wing_2.yRot = 0.0F;
			this.l_wing_2.zRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 1.25F;
			this.l_wing_2.z = -1.5F;

			this.r_wing_1.xRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 0.5F;
			this.r_wing_1.yRot = 0.0F;
			this.r_wing_1.zRot = -(1.3F + Mth.sin(limbSwing * 0.5F) * limbSwingAmount * 2.5F);
			this.r_wing_1.x = -4.5F;
			this.r_wing_1.y = -4.5F;
			this.r_wing_1.z = -1.0F;

			this.r_wing_2.xRot = -0.0872665F;
			this.r_wing_2.yRot = 0.0F;
			this.r_wing_2.zRot = -(-Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 1.25F);
			this.r_wing_2.z = -1.5F;

			this.l_leg_1.xRot = -1.143F;
			this.l_leg_1.yRot = -0.179F;
			this.l_leg_1.zRot = 0.0872F;
			this.l_leg_1.y = 5.0F;
			this.l_leg_1.z = -3.0F;

			this.r_leg_1.xRot = -1.143F;
			this.r_leg_1.yRot = 0.179F;
			this.r_leg_1.zRot = -0.0872F;
			this.r_leg_1.y = 5.0F;
			this.r_leg_1.z = -3.0F;

			this.r_leg_2a.xRot = 0.953F;
			this.r_leg_2a.yRot = 0.0617F;
			this.r_leg_2a.zRot = 0.0617F;

			//this.r_leg_2b.xRot = 0.9167F;
			//this.r_leg_2b.yRot = 0.02655F;
			//this.r_leg_2b.zRot = 0.02655F;

			this.l_leg_2a.xRot = 0.953F;
			this.l_leg_2a.yRot = -0.0617F;
			this.l_leg_2a.zRot = -0.0617F;

			//this.l_leg_2b.xRot = 0.9167F;
			//this.l_leg_2b.yRot = -0.02655F;
			//this.l_leg_2b.zRot = -0.02655F;
		}
		else {
			this.root.xRot = 0.0F;

			this.tail.xRot = 0.35F;

			this.l_wing_1.xRot = -0.22F;
			this.l_wing_1.yRot = -0.35F;
			this.l_wing_1.zRot = 2.35F;
			this.l_wing_1.x = 4.0F;
			this.l_wing_1.y = -4.5F;
			this.l_wing_1.z = -1.0F;

			this.l_wing_2.xRot = -1.5708F;
			this.l_wing_2.yRot = 0.0F;
			this.l_wing_2.zRot = 0.0F;
			this.l_wing_2.z = 1.5F;

			this.r_wing_1.xRot = -0.22F;
			this.r_wing_1.yRot = 0.35F;
			this.r_wing_1.zRot = -2.35F;
			this.r_wing_1.x = -5.0F;
			this.r_wing_1.y = -4.5F;
			this.r_wing_1.z = -1.0F;

			this.r_wing_2.xRot = -1.5708F;
			this.r_wing_2.yRot = 0.0F;
			this.r_wing_2.zRot = 0.0F;
			this.r_wing_2.z = 1.5F;

			this.l_wing_1_feathers.visible = false;
			this.r_wing_1_feathers.visible = false;

			this.l_leg_1.xRot = Mth.cos(limbSwing + 2.0F) * limbSwingAmount * 0.7F;
			this.l_leg_1.yRot = -0.025F;
			this.l_leg_1.zRot = 0.0F;
			this.l_leg_1.y = 6.0F;
			this.l_leg_1.z = -6.0F;

			this.r_leg_1.xRot = -Mth.cos(limbSwing + 2.0F) * limbSwingAmount * 0.7F;
			this.r_leg_1.yRot = 0.025F;
			this.r_leg_1.zRot = 0.0F;
			this.r_leg_1.y = 6.0F;
			this.r_leg_1.z = -6.0F;

			this.r_leg_2a.xRot = Mth.cos(limbSwing + 2.0F) * limbSwingAmount * 0.7F;
			this.r_leg_2a.yRot = 0.0F;
			this.r_leg_2a.zRot = 0.0F;

			this.l_leg_2a.xRot = -Mth.cos(limbSwing + 2.0F) * limbSwingAmount * 0.7F;
			this.l_leg_2a.yRot = 0.0F;
			this.l_leg_2a.zRot = 0.0F;

			this.head.xRot = headPitch * ((float)Math.PI / 180F);
			this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return Collections.emptyList();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(root);
	}
}