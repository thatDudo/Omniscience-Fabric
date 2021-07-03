package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.Omniscience;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(at = @At("HEAD"), method = "setGameMode")
    private void onSetGameMode(GameMode gameMode, CallbackInfo info) {
        Omniscience.isCreative = gameMode.isCreative();
    }
}
