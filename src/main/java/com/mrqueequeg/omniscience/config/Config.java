package com.mrqueequeg.omniscience.config;

import com.google.gson.annotations.Expose;
import com.mrqueequeg.omniscience.EntityTargetGroup;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.ArrayUtils;

public class Config {

    @Expose public boolean enabled = true;
    @Expose public boolean excludeSelf = true;
    @Expose public float alpha = 0.40f;
    @Expose public int invisibleEntityGlow = 0;
    @Expose public int entityTargetGroup = EntityTargetGroup.PLAYER | EntityTargetGroup.MONSTER | EntityTargetGroup.VILLAGER | EntityTargetGroup.ANIMAL;
    @Expose public boolean forceRenderNameTags = false;
    @Expose public boolean removeSneakCover = false;

    @Expose public static final String[] InvisibleEntityGlowStrings = {
            new TranslatableText("config.generic.glow.option.none").getString(),
            new TranslatableText("config.generic.glow.option.player").getString(),
            new TranslatableText("config.generic.glow.option.match_targets").getString(),
    };

    public boolean isEnabled() {
        return enabled;
    }

    public void setAlphaPercent(int i) {
        alpha = ((float) i) / 100;
    }
    public int getAlphaPercent() {
        return (int) (alpha * 100);
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

    public boolean isTargeted(int group) {
        return (this.entityTargetGroup & group) > 0;
    }

    public boolean shouldGroupGlow(int group) {
        return group != 0 &&
                (group == EntityTargetGroup.PLAYER && (invisibleEntityGlow == 1 && isTargeted(EntityTargetGroup.PLAYER)) || (invisibleEntityGlow == 2 && isTargeted(group)));
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
