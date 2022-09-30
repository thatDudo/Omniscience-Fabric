package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ModelPart.class)
public class ModelPartMixin {

    @ModifyVariable(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V", ordinal = 3, argsOnly=true)
    private float onRender(float alpha) {
        if (ConfigManager.getConfig().enabled) {
            if (alpha != 1.0f) {
                return ConfigManager.getConfig().alpha;
            }
        }
        return alpha;
    }
}
