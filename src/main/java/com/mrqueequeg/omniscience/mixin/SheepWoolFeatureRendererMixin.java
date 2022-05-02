package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.access.EntityMixinAccess;
import com.mrqueequeg.omniscience.config.Config;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepWoolFeatureRenderer.class)
public class SheepWoolFeatureRendererMixin {

    @Shadow @Final private static Identifier SKIN;
    @Shadow @Final private SheepWoolEntityModel<SheepEntity> model;

    private FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> _context;


    @Inject(at = @At("RETURN"), method = "<init>")
    private void onInit(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context, EntityModelLoader loader, CallbackInfo ci) {
        this._context = context;
    }

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V")
    private void onRenderHead(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        Config config = ConfigManager.getConfig();

        if (config.isEnabled() && config.isEntityTargeted(sheepEntity)) {

            _context.getModel().copyStateTo(this.model);
            this.model.animateModel(sheepEntity, f, g, h);
            this.model.setAngles(sheepEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getItemEntityTranslucentCull(SKIN));
            this.model.render(matrixStack, vertexConsumer2, i, LivingEntityRenderer.getOverlay(sheepEntity, 0.0f), 1.0f, 1.0f, 1.0f, 0.15f);
        }
    }
}
