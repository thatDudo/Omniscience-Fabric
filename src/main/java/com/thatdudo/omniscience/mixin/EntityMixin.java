package com.thatdudo.omniscience.mixin;

import com.thatdudo.omniscience.access.EntityMixinAccess;
import com.thatdudo.omniscience.config.ConfigManager;
import com.thatdudo.omniscience.util.EntityTargetGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityMixinAccess {
    private int entityTargetGroup;

    @Override
    public int getEntityTargetGroup() {
        return this.entityTargetGroup;
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void onInit(EntityType<?> type, World world, CallbackInfo info) {
        this.entityTargetGroup = EntityTargetGroup.getEntityGroup((Entity)(Object)this);
    }

    @Inject(at = @At("HEAD"), method = "isInvisibleTo", cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().isGroupTargeted(this.entityTargetGroup)) {
                info.setReturnValue(false);
            }
        }
        else if (player.isSpectator()) { // FIXME: Find another way to hide invisible entities in replay of ReplayMod
            info.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "isSneaky", cancellable = true)
    private void onIsSneaky(CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().getForceRenderNameTags() == 1) {
                info.setReturnValue(false);
            }
        }
    }
}
