package net.ron.lokimod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.ron.lokimod.LokiMod;
import net.ron.lokimod.entity.custom.LokiEntity;

public class LokiRenderer extends MobRenderer<LokiEntity, LokiModel<LokiEntity>> {
    public LokiRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LokiModel<>(pContext.bakeLayer(ModModelLayers.LOKI_LAYER)), 0.5f);
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getTextureLocation(LokiEntity lokiEntity) {
        return new ResourceLocation(LokiMod.MODID, "textures/entity/loki.png");
    }

    @Override
    public void render(LokiEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

        pPoseStack.scale(0.6F, 0.6F, 0.6F);

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
