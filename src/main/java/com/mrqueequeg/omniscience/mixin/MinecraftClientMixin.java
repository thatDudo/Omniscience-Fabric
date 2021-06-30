package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.access.EntityMixinAccess;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "hasOutline", cancellable = true)
    private void onHasOutline(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (entity.isInvisible()) {
                if (ConfigManager.getConfig().shouldGroupGlow(((EntityMixinAccess)entity).getEntityTargetGroup())) {
                    info.setReturnValue(true);
                }
            }
        }
    }
}
