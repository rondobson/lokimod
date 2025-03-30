package net.ron.lokimod.entity.client;// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.TamableAnimal;
import net.ron.lokimod.entity.animations.ModAnimationDefinitions;
import net.ron.lokimod.entity.custom.LokiEntity;

public class LokiModel<T extends TamableAnimal> extends HierarchicalModel<T> {
	private final ModelPart Loki;
	private final ModelPart head;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart tail;
	private final ModelPart mane;
	private final ModelPart body;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;

	public LokiModel(ModelPart root) {
		this.Loki = root.getChild("Loki");
		this.head = this.Loki.getChild("head");
		this.bone = this.head.getChild("bone");
		this.bone2 = this.head.getChild("bone2");
		this.tail = this.Loki.getChild("tail");
		this.mane = this.Loki.getChild("mane");
		this.body = this.Loki.getChild("body");
		this.leg1 = this.Loki.getChild("leg1");
		this.leg2 = this.Loki.getChild("leg2");
		this.leg3 = this.Loki.getChild("leg3");
		this.leg4 = this.Loki.getChild("leg4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Loki = partdefinition.addOrReplaceChild("Loki", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = Loki.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 23).addBox(-0.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -10.5F, -7.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(16, 28).addBox(-2.0F, -15.5F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 10.5F, 7.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(16, 28).addBox(0.0F, -15.5F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 10.5F, 7.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition tail = Loki.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 10.0F));

		PartDefinition mane = Loki.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(2, 0).addBox(-2.0F, -2.0F, -4.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -10.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body = Loki.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition leg1 = Loki.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 28).addBox(-0.5F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 7.0F));

		PartDefinition leg2 = Loki.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -10.0F, 7.0F));

		PartDefinition leg3 = Loki.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -10.0F, -4.0F));

		PartDefinition leg4 = Loki.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -10.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose); // VERY IMPORTANT
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.LOKI_WALKING, limbSwing, limbSwingAmount, 2F, 3F);
		if (!((LokiEntity) entity).isInSittingPose()) {
			this.animate(((LokiEntity) entity).idleAnimationState, ModAnimationDefinitions.LOKI_IDLE, ageInTicks, 1F);
			this.animate(((LokiEntity) entity).idleAnimationState, ModAnimationDefinitions.LOKI_TAILWAG, ageInTicks, 1F);
		}

		if(((LokiEntity) entity).isInSittingPose()) {
			this.animate(((LokiEntity) entity).sitAnimationState, ModAnimationDefinitions.LOKI_SITTING, ageInTicks, 1F);
		}
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30F, 30F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25F, 45F);


		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Loki.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Loki;
	}
}