package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.Config;
import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer.class)
public class VillagerClothingFeatureRendererMixin {

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;isInvisible()Z"
            )
    )
    private boolean omniscience$shouldRenderClothing(LivingEntity entity) {
        Config config = ConfigManager.getConfig();

        if (config.isEnabled()
                && entity.isInvisible()
                && config.isEntityTargeted(entity)) {
            return false;
        }

        return entity.isInvisible();
    }

    @ModifyConstant(
            method = "render",
            constant = @Constant(intValue = -1)
    )
    private int omniscience$modifyClothingColor(int color) {
        Config config = ConfigManager.getConfig();

        if (!config.isEnabled()) {
            return color;
        }

        int alpha = Math.round(config.alpha * 255.0f);
        alpha = Math.max(0, Math.min(255, alpha));

        return (alpha << 24) | 0x00FFFFFF;
    }

}