package com.mrqueequeg.omniscience.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class TestMixin {

    @Inject(at = @At("HEAD"), method = "getBoundingBox", cancellable = true)
    private void onGetBoundingBox(CallbackInfoReturnable<Box> info) {
        if (((Object)this) instanceof HostileEntity) {
            info.setReturnValue(((TestAccess)this).getEntityBounds().contract(0.5));
        }
    }

//    @Inject(at = @At("RETURN"), method = "damage")
//    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable info) {
//        System.out.printf("%s received %.1f damage points", ((PlayerEntity)(Object)this).getGameProfile().getName(), amount);
//        //((PlayerEntity)(Object)this).setInvisible(true);
//        //((PlayerEntity)(Object)this).isInvisible()
//        System.out.println(((PlayerEntity)(Object)this));
//    }

//    @Inject(at = @At("HEAD"), method = "attack")
//    private void onSwingHand(Entity target, CallbackInfo info) {
//        System.out.println("Swinging hand");
//    }

//    @Inject(at = @At("HEAD"), method = "handleFallDamage")
//    private void onStopFalling(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info) {
//        System.out.println("touched ground!");
//
//        //System.out.println(((PlayerEntity)(Object)this).inventory.selectedSlot);
//        //((ClientPlayerEntity)(Object)this).move(MovementType.PLAYER, new Vec3d(500, 5, 5));
//    }


//    private static final MinecraftClient mc = MinecraftClient.getInstance();
//
//    @Inject(at = {@At("HEAD")}, method = {"sendChatMessage"}, cancellable = true)
//    public void onChatMessage(String message, CallbackInfo ci) {
//        if (message.equals(".d")) {
//            //System.out.println(((PlayerEntity)(Object)this).inventory.selectedSlot);
////            ItemStack itemStack = new ItemStack((ItemConvertible) Items.WRITABLE_BOOK, 1);
////            NbtList pages = new NbtList();
////            pages.addElement(0, (NbtElement) NbtString.of("DUPE"));
////            itemStack.putSubTag("pages", (NbtElement) pages);
////            itemStack.putSubTag("title", (NbtElement) NbtString.of("a"));
////            mc.getNetworkHandler().sendPacket((Packet) new BookUpdateC2SPacket(itemStack, true, mc.player.inventory.selectedSlot));
////            ci.cancel();
//        }
//    }
}
