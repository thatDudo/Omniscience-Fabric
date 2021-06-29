package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.config.Config;
import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    private boolean isPlayerEntity(Object obj) {
        return (obj instanceof PlayerEntity);
    }

    private boolean isMonsterEntity(Object obj) {
        return (obj instanceof HostileEntity
                || obj instanceof SlimeEntity
                || obj instanceof GhastEntity
                || obj instanceof PhantomEntity
                || obj instanceof EnderDragonEntity);
    }

    private boolean isAnimalEntity(Object obj) {
        return (obj instanceof AnimalEntity
                || obj instanceof WaterCreatureEntity
                || obj instanceof GolemEntity
                || obj instanceof AmbientEntity);
    }

    private boolean isVillagerEntity(Object obj) {
        return (obj instanceof WanderingTraderEntity
                || obj instanceof VillagerEntity);
    }

    private boolean isObjectEntity(Object obj) {
        return (obj instanceof ArmorStandEntity);
    }

    private void printEntity(Object obj) {
        if (isPlayerEntity(obj)) {
            System.out.println("Looking at player: "+((PlayerEntity)(Object)obj).getGameProfile().getName());
        }
        if (isMonsterEntity(obj)) {
            System.out.println("Looking at monster");
        }
        if (isAnimalEntity(obj)) {
            System.out.println("Looking at animal");
        }
        if (isVillagerEntity(obj)) {
            System.out.println("Looking at villager");
        }
        if (isObjectEntity(obj)) {
            System.out.println("Looking at object");
        }
    }

    @Inject(at = @At("HEAD"), method = "isInvisibleTo", cancellable = true)
    private void onEvent(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().enabled) {
            Config config = ConfigManager.getConfig();
            Object t = (Object) this;

            if (config.targeted.all
                    || (config.targeted.players && isPlayerEntity(t))
                    || (config.targeted.monster && isMonsterEntity(t))
                    || (config.targeted.villager && isVillagerEntity(t))
                    || (config.targeted.animals && isAnimalEntity(t))
                    || (config.targeted.objects && isObjectEntity(t))) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    private void onIsGlowing(CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().enabled) {
            if (((Entity)(Object)this).isInvisible()) {
                if ((ConfigManager.getConfig().getInvisibleEntityGlow() == 2 || (ConfigManager.getConfig().getInvisibleEntityGlow() == 1 && isPlayerEntity((Object)this)))) {
                    info.setReturnValue(true);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "isSneaky", cancellable = true)
    private void onIsSneaky(CallbackInfoReturnable<Boolean> info) {
        if (ConfigManager.getConfig().enabled) {
            if (ConfigManager.getConfig().removeSneakCover) {
                info.setReturnValue(false);
            }
        }
    }

//    @Inject(at = @At("HEAD"), method = "shouldRenderName", cancellable = true)
//    public void onShouldRenderName(CallbackInfoReturnable<Boolean> info) {
//        if (ConfigManager.getConfig().enabled) {
//            int i = ConfigManager.getConfig().showNameTagCondition;
//            if (i == 1 || (i == 3 && ((Entity)(Object)this).isInvisible())) {
//                info.setReturnValue(true);
//            }
//            else if (i == 2) {
//                info.setReturnValue(false);
//            }
//        }
//    }

//    @Inject(at = @At("RETURN"), method = "getItemsEquipped")
//    private void onGetItemsHand(CallbackInfoReturnable<Iterable<ItemStack>> info) {
//        if (ConfigManager.getConfig().enabled && isPlayerEntity((Object)this) && !(((Object)this) instanceof ClientPlayerEntity)) {
//            info.getReturnValue().forEach(stack -> {
//                if (stack.getItem() == Items.IRON_SWORD) {
//                    System.out.println("-|- "+((PlayerEntity)(Object)this).getGameProfile().getName()+" is murder");
//                }
//            });
//        }
//    }
}
