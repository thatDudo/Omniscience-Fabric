package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"), method = "render")
    private static boolean onRender(LivingEntity instance, StatusEffect effect) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().removeBlindnessEffect) {
                return false;
            }
        }
        return instance.hasStatusEffect(effect);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"), method = "applyFog")
    private static boolean onApplyFog(LivingEntity instance, StatusEffect effect) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().removeBlindnessEffect) {
                return false;
            }
        }
        return instance.hasStatusEffect(effect);
    }
}
