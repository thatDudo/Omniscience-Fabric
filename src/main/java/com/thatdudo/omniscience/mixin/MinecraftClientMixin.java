package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
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
                if (ConfigManager.getConfig().shouldEntityGlow(entity)) {
                    info.setReturnValue(true);
                }
            }
        }
    }
}
