package com.mrqueequeg.omniscience.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface TestAccess {
    @Accessor("entityBounds")
    Box getEntityBounds();
}
