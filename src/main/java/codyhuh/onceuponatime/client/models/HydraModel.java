package codyhuh.onceuponatime.client.models;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.animations.HydraAnimation;
import codyhuh.onceuponatime.client.animations.HydraIdleAnimation;
import codyhuh.onceuponatime.common.entities.Hydra;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HydraModel<T extends Hydra> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "hydra"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail_base;
	private final ModelPart tail_end;
	private final ModelPart neck_base;
	private final ModelPart neck_middle;
	private final ModelPart neck_end;
	private final ModelPart head;
	private final ModelPart top_head;
	private final ModelPart bottom_head;
	private final ModelPart tongue;
	private final ModelPart neck_base2;
	private final ModelPart neck_middle2;
	private final ModelPart neck_end2;
	private final ModelPart head2;
	private final ModelPart top_head2;
	private final ModelPart bottom_head2;
	private final ModelPart tongue2;
	private final ModelPart neck_base3;
	private final ModelPart neck_middle3;
	private final ModelPart neck_end3;
	private final ModelPart head3;
	private final ModelPart top_head3;
	private final ModelPart bottom_head3;
	private final ModelPart tongue3;
	private final ModelPart l_arm;
	private final ModelPart l_hand;
	private final ModelPart r_arm;
	private final ModelPart r_hand;

	public HydraModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.tail_base = this.body.getChild("tail_base");
		this.tail_end = this.tail_base.getChild("tail_end");
		this.neck_base = this.body.getChild("neck_base");
		this.neck_middle = this.neck_base.getChild("neck_middle");
		this.neck_end = this.neck_middle.getChild("neck_end");
		this.head = this.neck_end.getChild("head");
		this.top_head = this.head.getChild("top_head");
		this.bottom_head = this.head.getChild("bottom_head");
		this.tongue = this.bottom_head.getChild("tongue");
		this.neck_base2 = this.body.getChild("neck_base2");
		this.neck_middle2 = this.neck_base2.getChild("neck_middle2");
		this.neck_end2 = this.neck_middle2.getChild("neck_end2");
		this.head2 = this.neck_end2.getChild("head2");
		this.top_head2 = this.head2.getChild("top_head2");
		this.bottom_head2 = this.head2.getChild("bottom_head2");
		this.tongue2 = this.bottom_head2.getChild("tongue2");
		this.neck_base3 = this.body.getChild("neck_base3");
		this.neck_middle3 = this.neck_base3.getChild("neck_middle3");
		this.neck_end3 = this.neck_middle3.getChild("neck_end3");
		this.head3 = this.neck_end3.getChild("head3");
		this.top_head3 = this.head3.getChild("top_head3");
		this.bottom_head3 = this.head3.getChild("bottom_head3");
		this.tongue3 = this.bottom_head3.getChild("tongue3");
		this.l_arm = this.root.getChild("l_arm");
		this.l_hand = this.l_arm.getChild("l_hand");
		this.r_arm = this.root.getChild("r_arm");
		this.r_hand = this.r_arm.getChild("r_hand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -9.0F, 16.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -2.0F));

		PartDefinition tail_base = body.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 9.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(0.0F, -7.0F, 1.0F, 0.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 13.0F));

		PartDefinition tail_end = tail_base.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(40, 32).addBox(0.0F, -5.0F, 1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(52, 50).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 18.0F));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create().texOffs(94, 0).addBox(-3.0F, -10.0F, -5.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.0F));

		PartDefinition neck_middle = neck_base.addOrReplaceChild("neck_middle", CubeListBuilder.create().texOffs(96, 48).addBox(-5.0F, -14.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, -4.0F));

		PartDefinition neck_end = neck_middle.addOrReplaceChild("neck_end", CubeListBuilder.create().texOffs(0, 9).addBox(-2.4F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -14.0F, 2.5F));

		PartDefinition head = neck_end.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.1F, -8.0F, 0.5F));

		PartDefinition top_head = head.addOrReplaceChild("top_head", CubeListBuilder.create().texOffs(54, 0).addBox(-4.0F, -3.0F, -11.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 49).addBox(-1.0F, -6.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 100).addBox(-3.0F, -4.0F, -11.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 31).addBox(-3.5F, 0.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition l_fin_r1 = top_head.addOrReplaceChild("l_fin_r1", CubeListBuilder.create().texOffs(52, 44).mirror().addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -2.0F, 1.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition l_fin_r2 = top_head.addOrReplaceChild("l_fin_r2", CubeListBuilder.create().texOffs(52, 44).addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition bottom_head = head.addOrReplaceChild("bottom_head", CubeListBuilder.create().texOffs(24, 65).addBox(-4.0F, 0.0F, -11.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(96, 107).addBox(-3.5F, -2.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tongue = bottom_head.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(2, 0).addBox(-1.5F, 0.0F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck_base2 = body.addOrReplaceChild("neck_base2", CubeListBuilder.create().texOffs(94, 0).addBox(-3.0F, -10.0F, -5.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -2.0F, -8.0F, 0.0F, 0.5236F, -0.2618F));

		PartDefinition neck_middle2 = neck_base2.addOrReplaceChild("neck_middle2", CubeListBuilder.create().texOffs(96, 48).addBox(-5.0F, -14.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, -4.0F));

		PartDefinition neck_end2 = neck_middle2.addOrReplaceChild("neck_end2", CubeListBuilder.create().texOffs(0, 9).addBox(-2.4F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -14.0F, 2.5F));

		PartDefinition head2 = neck_end2.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offset(0.1F, -8.0F, 0.5F));

		PartDefinition top_head2 = head2.addOrReplaceChild("top_head2", CubeListBuilder.create().texOffs(54, 0).addBox(-4.0F, -3.0F, -11.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 49).addBox(-1.0F, -6.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 100).addBox(-3.0F, -4.0F, -11.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 31).addBox(-3.5F, 0.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition l_fin_r3 = top_head2.addOrReplaceChild("l_fin_r3", CubeListBuilder.create().texOffs(52, 44).mirror().addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -2.0F, 1.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition l_fin_r4 = top_head2.addOrReplaceChild("l_fin_r4", CubeListBuilder.create().texOffs(52, 44).addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition bottom_head2 = head2.addOrReplaceChild("bottom_head2", CubeListBuilder.create().texOffs(24, 65).addBox(-4.0F, 0.0F, -11.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(96, 107).addBox(-3.5F, -2.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tongue2 = bottom_head2.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(2, 0).addBox(-1.5F, 0.0F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck_base3 = body.addOrReplaceChild("neck_base3", CubeListBuilder.create().texOffs(94, 0).addBox(-3.0F, -10.0F, -5.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -2.0F, -8.0F, 0.0F, -0.5236F, 0.2618F));

		PartDefinition neck_middle3 = neck_base3.addOrReplaceChild("neck_middle3", CubeListBuilder.create().texOffs(96, 48).addBox(-5.0F, -14.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 3.0F, -4.0F));

		PartDefinition neck_end3 = neck_middle3.addOrReplaceChild("neck_end3", CubeListBuilder.create().texOffs(0, 9).addBox(-2.4F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -14.0F, 2.5F));

		PartDefinition head3 = neck_end3.addOrReplaceChild("head3", CubeListBuilder.create(), PartPose.offset(0.1F, -8.0F, 0.5F));

		PartDefinition top_head3 = head3.addOrReplaceChild("top_head3", CubeListBuilder.create().texOffs(54, 0).addBox(-4.0F, -3.0F, -11.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-4.0F, -5.0F, -5.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 49).addBox(-1.0F, -6.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 100).addBox(-3.0F, -4.0F, -11.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(70, 31).addBox(-3.5F, 0.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition l_fin_r5 = top_head3.addOrReplaceChild("l_fin_r5", CubeListBuilder.create().texOffs(52, 44).mirror().addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -2.0F, 1.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition l_fin_r6 = top_head3.addOrReplaceChild("l_fin_r6", CubeListBuilder.create().texOffs(52, 44).addBox(0.0F, -5.0F, -1.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition bottom_head3 = head3.addOrReplaceChild("bottom_head3", CubeListBuilder.create().texOffs(24, 65).addBox(-4.0F, 0.0F, -11.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(96, 107).addBox(-3.5F, -2.0F, -10.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tongue3 = bottom_head3.addOrReplaceChild("tongue3", CubeListBuilder.create().texOffs(2, 0).addBox(-1.5F, 0.0F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition l_arm = root.addOrReplaceChild("l_arm", CubeListBuilder.create().texOffs(64, 67).addBox(0.0F, -2.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -3.0F, -7.0F));

		PartDefinition l_hand = l_arm.addOrReplaceChild("l_hand", CubeListBuilder.create().texOffs(1, 38).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offset(12.0F, 3.0F, 0.0F));

		PartDefinition r_arm = root.addOrReplaceChild("r_arm", CubeListBuilder.create().texOffs(64, 67).mirror().addBox(-12.0F, -2.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -3.0F, -7.0F));

		PartDefinition r_hand = r_arm.addOrReplaceChild("r_hand", CubeListBuilder.create().texOffs(1, 38).mirror().addBox(-1.0F, -1.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-12.0F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

			this.neck_middle2.visible = !entity.isLeftHeadKilled();
			this.neck_middle3.visible = !entity.isMiddleHeadKilled();
			this.neck_middle.visible = !entity.isRightHeadKilled();


		this.animateWalk(HydraAnimation.WALK, limbSwing, limbSwingAmount, 3.0F, 100.0F);
		this.animate(entity.idleAnimationState, HydraIdleAnimation.IDLE, ageInTicks);

		float neckX = headPitch * 0.004F;
		float neckY = netHeadYaw * 0.004F;

		this.neck_base.xRot = neckX;
		this.neck_base.yRot = neckY;
		this.neck_end.xRot = neckX;
		this.neck_end.yRot = neckY;
		this.head.xRot = neckX;
		this.head.yRot = neckY;

		this.neck_base2.xRot = neckX;
		this.neck_base2.yRot = neckY;
		this.neck_end2.xRot = neckX;
		this.neck_end2.yRot = neckY;
		this.head2.xRot = neckX;
		this.head2.yRot = neckY;

		this.neck_base3.xRot = neckX;
		this.neck_base3.yRot = neckY;
		this.neck_end3.xRot = neckX;
		this.neck_end3.yRot = neckY;
		this.head3.xRot = neckX;
		this.head3.yRot = neckY;
	}

	@Override
	public ModelPart root() {
		return root;
	}
}