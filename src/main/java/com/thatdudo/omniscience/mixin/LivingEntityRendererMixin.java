package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.Config;
import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(at = @At("HEAD"), method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
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

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private boolean onIsInvisibleTo(LivingEntity instance, PlayerEntity playerEntity) { // Overwrites Redirect of ReplayMod
        return instance.isInvisibleTo(playerEntity);
    }
}
