package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.config.ConfigManager;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ModelPart.class)
public class ModelPartMixin {

    @ModifyArg(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelPart;renderCuboids(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumer;III)V"
            ),
            index = 4
    )
    private int modifyColor(int color) {
        if (!ConfigManager.getConfig().isEnabled()) {
            return color;
        }

        int alpha = (color >>> 24) & 0xFF;

        if (alpha == 255) {
            return color;
        }

        int configuredAlpha = Math.round(ConfigManager.getConfig().alpha * 255.0f);

        return (configuredAlpha << 24) | (color & 0x00FFFFFF);
    }
}