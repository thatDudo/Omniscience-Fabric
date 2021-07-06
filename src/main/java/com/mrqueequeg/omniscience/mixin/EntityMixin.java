package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.EntityTargetGroup;
import com.mrqueequeg.omniscience.access.EntityMixinAccess;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityMixinAccess {
    private int entityTargetGroup;

    public int getEntityTargetGroup() {
        return entityTargetGroup;
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void onInit(EntityType<?> type, World world, CallbackInfo info) {
        entityTargetGroup = EntityTargetGroup.getEntityGroup((Entity)(Object)this) | EntityTargetGroup.ALL;
    }

    @Inject(at = @At("HEAD"), method = "isInvisibleTo", cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().isTargeted(entityTargetGroup)) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "isSneaky", cancellable = true)
    private void onIsSneaky(CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().isEnabled()) {
            if (ConfigManager.getConfig().removeSneakCover) {
                info.setReturnValue(false);
            }
        }
    }
}
