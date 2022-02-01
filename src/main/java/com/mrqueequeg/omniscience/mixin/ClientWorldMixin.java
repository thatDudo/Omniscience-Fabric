package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "getBlockParticle", cancellable = true)
    private void onGetBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().exposeBarrierBlocks && this.client.player != null && this.client.player.getMainHandStack().getItem() != Items.LIGHT) {
                cir.setReturnValue(Blocks.BARRIER);
            }
        }
    }
}
