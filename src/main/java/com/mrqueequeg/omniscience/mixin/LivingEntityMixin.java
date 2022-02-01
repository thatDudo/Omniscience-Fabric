package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "hasStatusEffect", cancellable = true)
    private void onHasStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigManager.getConfig().enabled) {
            if (ConfigManager.getConfig().removeBlindnessEffect && effect == StatusEffects.BLINDNESS) {
                cir.setReturnValue(false);
            }
        }
    }
}
