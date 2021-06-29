package com.mrqueequeg.omniscience.config;

import com.google.gson.annotations.Expose;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.ArrayUtils;

public class Config {

    @Expose public boolean enabled = true;
    @Expose public boolean excludeSelf = true;
    @Expose public boolean removeSneakCover = false;
    @Expose public float alpha = 0.40f;
    @Expose public int invisibleEntityGlow = 0;
    @Expose public int showNameTagCondition = 0;
    @Expose public TargetList targeted = new TargetList();

    @Expose public static final String[] InvisibleEntityGlowStrings = {
            new TranslatableText("config.generic.glow.option.none").getString(),
            new TranslatableText("config.generic.glow.option.player").getString(),
            new TranslatableText("config.generic.glow.option.all").getString(),
    };

    @Expose public static final String[] ShowNameTagConditionStrings = {
            new TranslatableText("config.generic.name_tag.option.default").getString(),
            new TranslatableText("config.generic.name_tag.option.always").getString(),
            new TranslatableText("config.generic.name_tag.option.never").getString(),
            new TranslatableText("config.generic.name_tag.option.if_invisible").getString(),
    };

    public static final class TargetList {
        @Expose public boolean all = false;
        @Expose public boolean players = true;
        @Expose public boolean monster = true;
        @Expose public boolean villager = true;
        @Expose public boolean animals = true;
        @Expose public boolean objects = false;
    }

    public void setAlphaPercent(int i) {
        alpha = ((float) i) / 100;
    }
    public int getAlphaPercent() {
        return (int) (alpha * 100);
    }

    public void setInvisibleEntityGlow(int i) {
        if (i >= 0 && i < ShowNameTagConditionStrings.length) {
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

    public void setShowNameTagCondition(int i) {
        if (i >= 0 && i < ShowNameTagConditionStrings.length) {
            showNameTagCondition = i;
        }
    }
    public int getShowNameTagCondition() {
        return showNameTagCondition;
    }
    public void setShowNameTagCondition(String t) {
        setShowNameTagCondition(ArrayUtils.indexOf(ShowNameTagConditionStrings, t));
    }
    public String getShowNameTagConditionString() {
        return ShowNameTagConditionStrings[getShowNameTagCondition()];
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
        if (showNameTagCondition < 0) {
            showNameTagCondition = 0;
            valid = false;
        }
        else if (showNameTagCondition > ShowNameTagConditionStrings.length) {
            showNameTagCondition = ShowNameTagConditionStrings.length - 1;
            valid = false;
        }

        return valid;
    }
}
