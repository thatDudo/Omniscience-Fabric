package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.Omniscience;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(at = @At("HEAD"), method = "setGameModes")
    private void onSetGameModes(GameMode gameMode, GameMode previousGameMode, CallbackInfo info) {
        Omniscience.isCreative = gameMode.isCreative();
    }

    @Inject(at = @At("HEAD"), method = "setGameMode")
    private void onSetGameMode(GameMode gameMode, CallbackInfo info) {
        Omniscience.isCreative = gameMode.isCreative();
    }
}
