package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FeatureRenderer.class)
public class FeatureRendererMixin {

    @Redirect(
            method = "renderModel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/RenderLayer;getEntityCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"
            )
    )
    private static RenderLayer omniscience$useTranslucent(Identifier texture) {

        if (ConfigManager.getConfig().isEnabled()
                && texture.getPath().startsWith("textures/entity/villager/")) {
            return RenderLayer.getEntityTranslucent(texture);
        }

        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}