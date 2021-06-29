package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {


    @Inject(at = @At("RETURN"), method = "getEquippedStack")
    private void onEquip(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> info) {
        if (ConfigManager.getConfig().enabled) {
            Item item = info.getReturnValue().getItem();
            if (!(((Object)this) instanceof ClientPlayerEntity) && item == Items.IRON_SWORD) {
                System.out.println(((PlayerEntity)(Object)this).getGameProfile().getName()+" is the murder!!");
            }
        }
    }
}
