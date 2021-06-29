package com.mrqueequeg.omniscience.mixin;

import com.mrqueequeg.omniscience.config.ConfigManager;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Team.class)
public class TeamMixin {

    @Inject(at = @At("HEAD"), method="getNameTagVisibilityRule", cancellable = true)
    private void onShouldRenderName(CallbackInfoReturnable<AbstractTeam.VisibilityRule> info) {
        if (ConfigManager.getConfig().enabled) {
            int i = ConfigManager.getConfig().showNameTagCondition;
            if (i == 1) {
                info.setReturnValue(AbstractTeam.VisibilityRule.ALWAYS);
            }
            else if (i == 2) {
                info.setReturnValue(AbstractTeam.VisibilityRule.NEVER);
            }
        }
    }
}
