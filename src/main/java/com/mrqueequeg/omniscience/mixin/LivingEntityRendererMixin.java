package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.access.EntityMixinAccess;
import com.mrqueequeg.omniscience.config.Config;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(at = @At("HEAD"), method = "hasLabel", cancellable = true)
    public void onShouldRenderName(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            Config config = ConfigManager.getConfig();
            if (config.isEnabled() && config.getForceRenderNameTags() == 2) {
                if (config.shouldEntityGlow(livingEntity)) {
                    info.setReturnValue(true);
                }
            }
        }
    }
}
