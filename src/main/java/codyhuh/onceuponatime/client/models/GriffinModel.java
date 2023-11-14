package codyhuh.onceuponatime.client.models;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Griffin;
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

public class GriffinModel<T extends Griffin> extends AgeableListModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "griffin"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart l_wing_1;
	private final ModelPart l_wing_2;
	private final ModelPart r_wing_1;
	private final ModelPart r_wing_2;
	private final ModelPart l_leg_1;
	private final ModelPart r_leg_1;
	private final ModelPart l_leg_2a;
	private final ModelPart r_leg_2a;
	private final ModelPart l_leg_2b;
	private final ModelPart r_leg_2b;
	private final ModelPart tail;
	private final ModelPart head;

	public GriffinModel(ModelPart base) {
		this.root = base.getChild("root");
		this.l_leg_1 = root.getChild("l_leg_1");
		this.r_leg_1 = root.getChild("r_leg_1");
		this.l_leg_2a = root.getChild("l_tight");
		this.r_leg_2a = root.getChild("r_tight");
		this.l_leg_2b = l_leg_2a.getChild("l_leg_2");
		this.r_leg_2b = r_leg_2a.getChild("r_leg_2");
		this.body = root.getChild("body");
		this.tail = body.getChild("tail");
		this.head = root.getChild("head");
		this.l_wing_1 = root.getChild("l_wing_1");
		this.l_wing_2 = l_wing_1.getChild("l_wing_2");
		this.r_wing_1 = root.getChild("r_wing_1");
		this.r_wing_2 = r_wing_1.getChild("r_wing_2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.5F, 5.0F, 1.6224F));

		PartDefinition l_leg_1 = root.addOrReplaceChild("l_leg_1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(25, 82).addBox(-2.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.0F, -6.0F));

		PartDefinition r_leg_1 = root.addOrReplaceChild("r_leg_1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(true)
				.texOffs(25, 82).addBox(-3.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(true), PartPose.offset(-4.0F, 6.0F, -6.0F));

		PartDefinition l_tight = root.addOrReplaceChild("l_tight", CubeListBuilder.create().texOffs(0, 69).addBox(-2.0F, -4.5F, -3.5F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.5F, 7.5F));

		PartDefinition l_leg_2 = l_tight.addOrReplaceChild("l_leg_2", CubeListBuilder.create().texOffs(79, 51).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, -0.5F));

		PartDefinition r_tight = root.addOrReplaceChild("r_tight", CubeListBuilder.create().texOffs(0, 69).mirror().addBox(-2.0F, -4.5F, -3.5F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 3.5F, 7.5F));

		PartDefinition r_leg_2 = r_tight.addOrReplaceChild("r_leg_2", CubeListBuilder.create().texOffs(79, 51).mirror().addBox(-2.0F, 0.0F, 0.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.5F, -0.5F));

		PartDefinition l_wing_1 = root.addOrReplaceChild("l_wing_1", CubeListBuilder.create().texOffs(44, 32).addBox(-1.0F, -10.0F, -1.75F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(58, 51).addBox(0.0F, -10.0F, 1.25F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -5.0F, -1.25F));

		PartDefinition l_wing_2 = l_wing_1.addOrReplaceChild("l_wing_2", CubeListBuilder.create().texOffs(84, 0).addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -1.75F));

		PartDefinition r_wing_1 = root.addOrReplaceChild("r_wing_1", CubeListBuilder.create().texOffs(58, 51).mirror().addBox(0.0F, -10.0F, 1.25F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 32).mirror().addBox(-1.0F, -10.0F, -1.75F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -5.0F, -1.25F));

		PartDefinition r_wing_2 = r_wing_1.addOrReplaceChild("r_wing_2", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(84, 0).mirror().addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -10.0F, -1.75F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 25).addBox(-3.5F, -14.5343F, -3.75F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(15, 41).addBox(-3.5F, -14.5343F, -5.75F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(43, 9).addBox(-1.5F, -12.5343F, -8.75F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(16, 69).addBox(-1.5F, -8.5343F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.4657F, -8.25F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(15, 32).addBox(-5.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -13.5343F, 0.25F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(15, 32).mirror().addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, -13.5343F, 0.25F, 0.0F, 0.0F, -0.2618F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -10.0F, 11.0F, 11.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.5F, -4.0F, 10.0F));

		PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 67).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.onGround() && (entity.isFlying() || entity.isLanding())) {
			limbSwing = ageInTicks * 0.75F;
			limbSwingAmount = 0.3F;

			this.head.xRot = 0.435F;

			this.root.xRot = -0.08726F;

			this.tail.xRot = 1.13446F;

			this.l_wing_1.xRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 0.5F;
			this.l_wing_1.yRot = 0.0F;
			this.l_wing_1.zRot = 1.3F + Mth.sin(limbSwing * 0.5F) * limbSwingAmount * 2.5F;

			this.l_wing_2.xRot = -0.0872665F;
			this.l_wing_2.yRot = 0.0F;
			this.l_wing_2.zRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 1.25F;

			this.r_wing_1.xRot = -Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 0.5F;
			this.r_wing_1.yRot = 0.0F;
			this.r_wing_1.zRot = -(1.3F + Mth.sin(limbSwing * 0.5F) * limbSwingAmount * 2.5F);

			this.r_wing_2.xRot = -0.0872665F;
			this.r_wing_2.yRot = 0.0F;
			this.r_wing_2.zRot = -(-Mth.cos(limbSwing * 0.5F) * limbSwingAmount * 1.25F);

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

			this.r_leg_2a.xRot = 0.7853F;
			this.r_leg_2a.yRot = 0.0617F;
			this.r_leg_2a.zRot = -0.0617F;

			this.r_leg_2b.xRot = 0.9167F;
			this.r_leg_2b.yRot = 0.02655F;
			this.r_leg_2b.zRot = 0.02655F;

			this.l_leg_2a.xRot = 0.7853F;
			this.l_leg_2a.yRot = -0.0617F;
			this.l_leg_2a.zRot = 0.0617F;

			this.l_leg_2b.xRot = 0.9167F;
			this.l_leg_2b.yRot = -0.02655F;
			this.l_leg_2b.zRot = -0.02655F;
		}
		else {
			this.head.xRot = 0.0F;

			this.root.xRot = 0.0F;

			this.tail.xRot = 0.0F;

			this.l_wing_1.xRot = -0.22F;
			this.l_wing_1.yRot = -0.22F;
			this.l_wing_1.zRot = 2.75F;

			this.l_wing_2.xRot = -1.25F;
			this.l_wing_2.yRot = -0.045F;
			this.l_wing_2.zRot = 0.073F;

			this.r_wing_1.xRot = -0.22F;
			this.r_wing_1.yRot = 0.22F;
			this.r_wing_1.zRot = -2.75F;

			this.r_wing_2.xRot = -1.25F;
			this.r_wing_2.yRot = 0.045F;
			this.r_wing_2.zRot = -0.073F;

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

			this.r_leg_2b.xRot = 0.0F;
			this.r_leg_2b.yRot = 0.0F;
			this.r_leg_2b.zRot = 0.0F;

			this.l_leg_2a.xRot = -Mth.cos(limbSwing + 2.0F) * limbSwingAmount * 0.7F;
			this.l_leg_2a.yRot = 0.0F;
			this.l_leg_2a.zRot = 0.0F;

			this.l_leg_2b.xRot = 0.0F;
			this.l_leg_2b.yRot = 0.0F;
			this.l_leg_2b.zRot = 0.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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