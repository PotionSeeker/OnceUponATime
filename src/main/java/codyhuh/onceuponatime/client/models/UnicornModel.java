package codyhuh.onceuponatime.client.models;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Unicorn;
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

public class UnicornModel<T extends Unicorn> extends AgeableListModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "unicorn"), "main");
	public static final ModelLayerLocation POWER_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "unicorn"), "armor");
	public final ModelPart horn_overlay;
	public final ModelPart horn;
	private final ModelPart root;
	private final ModelPart back_body;
	private final ModelPart l_leg2;
	private final ModelPart r_leg2;
	private final ModelPart tail;
	private final ModelPart tail_hair;
	private final ModelPart body;
	private final ModelPart r_leg1;
	private final ModelPart l_leg1;
	private final ModelPart head;
	private final ModelPart mane;
	private final ModelPart l_ear;
	private final ModelPart r_ear;

	public UnicornModel(ModelPart base) {
		this.root = base.getChild("root");
		this.back_body = root.getChild("back_body");
		this.l_leg2 = back_body.getChild("l_leg2");
		this.r_leg2 = back_body.getChild("r_leg2");
		this.tail = back_body.getChild("tail");
		this.tail_hair = tail.getChild("tail_hair");
		this.body = root.getChild("body");
		this.r_leg1 = body.getChild("r_leg1");
		this.l_leg1 = body.getChild("l_leg1");
		this.head = body.getChild("head");
		this.horn = head.getChild("horn");
		this.horn_overlay = horn.getChild("horn_overlay");
		this.mane = head.getChild("mane");
		this.l_ear = head.getChild("l_ear");
		this.r_ear = head.getChild("r_ear");
	}

	public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition back_body = root.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition l_leg2 = back_body.addOrReplaceChild("l_leg2", CubeListBuilder.create().texOffs(42, 26).addBox(-1.5F, 7.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(28, 46).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 4.0F, 6.5F));

		PartDefinition r_leg2 = back_body.addOrReplaceChild("r_leg2", CubeListBuilder.create().texOffs(41, 26).mirror().addBox(-2.25F, 7.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(28, 46).mirror().addBox(-1.25F, 0.0F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.75F, 4.0F, 6.5F));

		PartDefinition tail = back_body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(48, 10).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 8.0F));

		PartDefinition tail_hair = tail.addOrReplaceChild("tail_hair", CubeListBuilder.create().texOffs(16, 37).addBox(0.0F, 1.0F, -2.0F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 27).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -7.0F, 8.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -1.0F));

		PartDefinition r_leg1 = body.addOrReplaceChild("r_leg1", CubeListBuilder.create().texOffs(41, 26).mirror().addBox(-2.5F, 5.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 47).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 5.0F, -4.5F));

		PartDefinition l_leg1 = body.addOrReplaceChild("l_leg1", CubeListBuilder.create().texOffs(41, 26).addBox(-2.5F, 5.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 47).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, -4.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 11).addBox(-2.5F, -7.0F, -3.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(30, 0).addBox(-3.0F, -11.0F, -4.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(40, 46).addBox(-2.5F, -11.0F, -8.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -6.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(43, 55).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -6.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(43, 55).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -6.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -2.0F));

		PartDefinition cube_r3 = horn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 53).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition horn_overlay = horn.addOrReplaceChild("horn_overlay", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = horn_overlay.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition mane = head.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -10.0F, -5.0F, 2.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 4.0F));

		PartDefinition l_ear = head.addOrReplaceChild("l_ear", CubeListBuilder.create().texOffs(5, 70).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -11.0F, -1.0F));

		PartDefinition r_ear = head.addOrReplaceChild("r_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -11.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Unicorn entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//limbSwing = ageInTicks * 0.75F;
		//limbSwingAmount = 0.3F;

		this.head.xRot = headPitch * 0.0175F;
		this.head.yRot = netHeadYaw * 0.0175F;

		this.head.xRot += 0.435F + Mth.cos(limbSwing * 0.6F) * 0.5F * limbSwingAmount;
		this.head.y = -1.0F + Mth.cos(1.0F - limbSwing * 0.6F) * 1.0F * limbSwingAmount;
		this.head.z = -6.0F + Mth.cos(1.0F - limbSwing * 0.6F) * 1.0F * limbSwingAmount;

		this.tail.xRot = 0.25F + Mth.cos(limbSwing * 0.6F) * 0.2F * limbSwingAmount;
		this.tail.zRot = Mth.cos(limbSwing * 0.3F) * 0.5F * limbSwingAmount;

		this.r_leg2.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
		this.l_leg2.xRot = Mth.cos(limbSwing * 0.6F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.r_leg1.xRot = Mth.cos(limbSwing * 0.6F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.l_leg1.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
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