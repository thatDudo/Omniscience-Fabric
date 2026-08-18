package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.Config;
import com.thatdudo.omniscience.config.ConfigManager;
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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper.Argb;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepWoolFeatureRenderer.class)
public class SheepWoolFeatureRendererMixin {

    @Shadow @Final
    private static Identifier SKIN;

    @Shadow @Final
    private SheepWoolEntityModel<SheepEntity> model;

    private FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> _context;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void onInit(
            FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context,
            EntityModelLoader loader,
            CallbackInfo ci
    ) {
        this._context = context;
    }

    @Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V"
    )
    private void onRender(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light,
            SheepEntity sheepEntity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float customAngle,
            float headYaw,
            float headPitch,
            CallbackInfo ci
    ) {
        Config config = ConfigManager.getConfig();

        if (!config.isEnabled()
                || !sheepEntity.isInvisible()
                || sheepEntity.isSheared()
                || !config.isEntityTargeted(sheepEntity)) {
            return;
        }

        /*
         * Calculate the wool color exactly like vanilla.
         */
        int woolColor;

        if (sheepEntity.hasCustomName()
                && "jeb_".equals(sheepEntity.getName().getString())) {

            int n = sheepEntity.age / 25 + sheepEntity.getId();
            int o = DyeColor.values().length;

            int p = n % o;
            int q = (n + 1) % o;

            float r = ((float) (sheepEntity.age % 25) + tickDelta) / 25.0F;

            int s = SheepEntity.getRgbColor(DyeColor.byId(p));
            int t = SheepEntity.getRgbColor(DyeColor.byId(q));

            woolColor = Argb.lerp(r, s, t);

        } else {
            woolColor = SheepEntity.getRgbColor(sheepEntity.getColor());
        }

        /*
         * Apply Omniscience transparency.
         */
        int alpha = (int) (config.alpha * 255.0F);
        alpha = Math.max(0, Math.min(255, alpha));

        int color = (alpha << 24) | (woolColor & 0x00FFFFFF);

        /*
         * Copy the normal sheep model state to the wool model.
         */
        _context.getModel().copyStateTo(this.model);

        this.model.animateModel(
                sheepEntity,
                limbAngle,
                limbDistance,
                tickDelta
        );

        this.model.setAngles(
                sheepEntity,
                limbAngle,
                limbDistance,
                customAngle,
                headYaw,
                headPitch
        );

        VertexConsumer vertexConsumer =
                vertexConsumerProvider.getBuffer(
                        RenderLayer.getItemEntityTranslucentCull(SKIN)
                );

        this.model.render(
                matrixStack,
                vertexConsumer,
                light,
                LivingEntityRenderer.getOverlay(sheepEntity, 0.0F),
                color
        );
    }
}