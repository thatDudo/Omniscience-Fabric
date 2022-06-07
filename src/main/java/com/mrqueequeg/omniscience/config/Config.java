package com.mrqueequeg.omniscience.config;

import com.google.gson.annotations.Expose;
import com.mrqueequeg.omniscience.EntityTargetGroup;
import com.mrqueequeg.omniscience.Omniscience;
import com.mrqueequeg.omniscience.access.EntityMixinAccess;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class Config {

    @Expose public boolean enabled = true;
    @Expose public float alpha = 0.40f;
    @Expose public int invisibleEntityGlow = 0;
    @Expose public int entityTargetGroup = EntityTargetGroup.PLAYER | EntityTargetGroup.MONSTER | EntityTargetGroup.VILLAGER | EntityTargetGroup.ANIMAL;
    @Expose public int forceRenderNameTags = 0;
    @Expose public boolean exposeBarrierBlocks = false;
    @Expose public boolean removeBlindnessEffect = false;
    @Expose public boolean onlyEnableInCreative = false;
    public String ff = "";

    @Expose public static final String[] ForceRenderNameTagsStrings = {
            Text.translatable("config.generic.misc.name_tags.option.never").getString(),
            Text.translatable("config.generic.misc.name_tags.option.when_sneaking").getString(),
            Text.translatable("config.generic.misc.name_tags.option.always").getString(),
    };

    @Expose public static final String[] InvisibleEntityGlowStrings = {
            Text.translatable("config.generic.glow.option.none").getString(),
            Text.translatable("config.generic.glow.option.player").getString(),
            Text.translatable("config.generic.glow.option.match_targets").getString(),
    };

    public boolean isEnabled() {
        return enabled && (!onlyEnableInCreative || Omniscience.isCreative);
    }

    public void setAlphaPercent(int i) {
        alpha = ((float) i) / 100;
    }
    public int getAlphaPercent() {
        return (int) (alpha * 100);
    }

    public void setForceRenderNameTags(int i) {
        if (i >= 0 && i < ForceRenderNameTagsStrings.length) {
            forceRenderNameTags = i;
        }
    }
    public int getForceRenderNameTags() {
        return forceRenderNameTags;
    }
    public void setForceRenderNameTags(String t) {
        setForceRenderNameTags(ArrayUtils.indexOf(ForceRenderNameTagsStrings, t));
    }
    public String getForceRenderNameTagsString() {
        return ForceRenderNameTagsStrings[forceRenderNameTags];
    }

    public void setInvisibleEntityGlow(int i) {
        if (i >= 0 && i < InvisibleEntityGlowStrings.length) {
            invisibleEntityGlow = i;
        }
    }
    public int getInvisibleEntityGlow() {
        return invisibleEntityGlow;
    }
    public void setInvisibleEntityGlow(String t) {
        setInvisibleEntityGlow(ArrayUtils.indexOf(InvisibleEntityGlowStrings, t));
    }
    public String getInvisibleEntityGlowString() {
        return InvisibleEntityGlowStrings[getInvisibleEntityGlow()];
    }

    public void setEntityTargetGroup(int group, boolean active) {
        this.entityTargetGroup = active ? this.entityTargetGroup | group : this.entityTargetGroup & ~group;
    }

    public boolean isGroupTargeted(int group) {
        return (this.entityTargetGroup & group) > 0;
    }

    public boolean isEntityTypeTargeted(Entity entity) {
        return isGroupTargeted(((EntityMixinAccess)entity).getEntityTargetGroup());
    }

    public boolean shouldGroupGlow(int group) {
        return group != 0 &&
                (group == EntityTargetGroup.PLAYER && (invisibleEntityGlow == 1 && isGroupTargeted(EntityTargetGroup.PLAYER)) || (invisibleEntityGlow == 2 && isGroupTargeted(group)));
    }

    public boolean shouldEntityTypeGlow(Entity entity) {
        return shouldGroupGlow(((EntityMixinAccess)entity).getEntityTargetGroup());
    }

    /**
     * @return true if nothing was changed
     */
    public boolean validate() {
        boolean valid = true;

        if (alpha < 0) {
            alpha = 0;
            valid = false;
        }
        else if (alpha > 1) {
            alpha = 1;
            valid = false;
        }
        if (invisibleEntityGlow < 0) {
            invisibleEntityGlow = 0;
            valid = false;
        }
        else if (invisibleEntityGlow > InvisibleEntityGlowStrings.length) {
            invisibleEntityGlow = InvisibleEntityGlowStrings.length-1;
            valid = false;
        }
        if (entityTargetGroup < 0 || entityTargetGroup >= 64) {
            entityTargetGroup = 0;
            valid = false;
        }

        return valid;
    }
}
