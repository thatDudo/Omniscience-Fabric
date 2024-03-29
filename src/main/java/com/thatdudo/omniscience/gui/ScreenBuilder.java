package com.thatdudo.omniscience.gui;

import com.thatdudo.omniscience.util.EntityTargetGroup;
import com.thatdudo.omniscience.Omniscience;
import com.thatdudo.omniscience.config.Config;
import com.thatdudo.omniscience.config.ConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.SelectorBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ScreenBuilder {
    private static boolean configScreenOpened = false;
    private static SubCategoryListEntry subCatTargeted;
    private static SubCategoryListEntry subCatMisc;

    public static boolean isConfigScreenOpened() {
        return configScreenOpened;
    }

    public static Screen buildConfigScreen(Screen parent) {

        configScreenOpened = true;

        Config config = ConfigManager.getConfig();
        Config defaults = ConfigManager.getDefaults();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of(Omniscience.MOD_NAME))
                .transparentBackground()
                //.setDoesConfirmSave(true)
                .setSavingRunnable(() -> {
                    configScreenOpened = false;

                    ConfigManager.writeConfig(true);
                });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory catGeneric = builder.getOrCreateCategory(Text.translatable("config.generic.title"));

        // enable
        BooleanToggleBuilder toggleEnabled = entryBuilder.startBooleanToggle(Text.translatable("config.generic.enabled.title"), config.enabled)
                .setDefaultValue(defaults.enabled)
                .setTooltip(Text.translatable("config.generic.enabled.tooltip"))
                .setSaveConsumer(n -> config.enabled = n);

        // alpha
        IntSliderBuilder sliderAlpha = entryBuilder.startIntSlider(Text.translatable("config.generic.alpha.title"), config.getAlphaPercent(), 0, 100)
                .setDefaultValue(defaults.getAlphaPercent())
                //.setTooltip(Text.translatable("config.generic.alpha.tooltip"))
                .setSaveConsumer(config::setAlphaPercent);

        // glow
        SelectorBuilder<String> selectorInvisibleEntityGlow = entryBuilder.startSelector(Text.translatable("config.generic.glow.title"), Config.InvisibleEntityGlowStrings, config.getInvisibleEntityGlowString())
                .setDefaultValue(defaults.getInvisibleEntityGlowString())
                .setTooltip(Text.translatable("config.generic.glow.tooltip"))
                .setSaveConsumer(config::setInvisibleEntityGlow);

        // all
        BooleanToggleBuilder toggleTargetAll = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.all.title"), config.isGroupTargeted(EntityTargetGroup.ALL))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.ALL))
//                .setTooltip(Text.translatable("config.generic.targeted.all.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ALL, n));

        // players
        BooleanToggleBuilder toggleTargetPlayers = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.players.title"), config.isGroupTargeted(EntityTargetGroup.PLAYER))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.PLAYER))
                //.setTooltip(Text.translatable("config.generic.targeted.players.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.PLAYER, n));

        // monster
        BooleanToggleBuilder toggleTargetMonster = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.monster.title"), config.isGroupTargeted(EntityTargetGroup.MONSTER))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.MONSTER))
                //.setTooltip(Text.translatable("config.generic.targeted.monster.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.MONSTER, n));

        // villager
        BooleanToggleBuilder toggleTargetVillager = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.villager.title"), config.isGroupTargeted(EntityTargetGroup.VILLAGER))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.VILLAGER))
                //.setTooltip(Text.translatable("config.generic.targeted.villager.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.VILLAGER, n));

        // animals
        BooleanToggleBuilder toggleTargetAnimals = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.animals.title"), config.isGroupTargeted(EntityTargetGroup.ANIMAL))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.ANIMAL))
                //.setTooltip(Text.translatable("config.generic.targeted.animals.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ANIMAL, n));

        // objects
        BooleanToggleBuilder toggleTargetObjects = entryBuilder.startBooleanToggle(Text.translatable("config.generic.targeted.objects.title"), config.isGroupTargeted(EntityTargetGroup.OBJECT))
                .setDefaultValue(defaults.isGroupTargeted(EntityTargetGroup.OBJECT))
                //.setTooltip(Text.translatable("config.generic.targeted.objects.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.OBJECT, n));

        // force render name tags
        SelectorBuilder<String> toggleForceRenderNameTags = entryBuilder.startSelector(Text.translatable("config.generic.misc.name_tags.title"), Config.ForceRenderNameTagsStrings, config.getForceRenderNameTagsString())
                .setDefaultValue(defaults.getForceRenderNameTagsString())
                .setTooltip(Text.translatable("config.generic.misc.name_tags.tooltip"))
                .setSaveConsumer(config::setForceRenderNameTags);

        // remove blindness effect
        BooleanToggleBuilder toggleRemoveBlindness = entryBuilder.startBooleanToggle(Text.translatable("config.generic.misc.remove_blindness.title"), config.removeBlindnessEffect)
                .setDefaultValue(defaults.removeBlindnessEffect)
                .setTooltip(Text.translatable("config.generic.misc.remove_blindness.tooltip"))
                .setSaveConsumer(n -> config.removeBlindnessEffect = n);

        // expose barrier blocks
        BooleanToggleBuilder toggleExposeBarriers = entryBuilder.startBooleanToggle(Text.translatable("config.generic.misc.expose_barriers.title"), config.exposeBarrierBlocks)
                .setDefaultValue(defaults.exposeBarrierBlocks)
                .setTooltip(Text.translatable("config.generic.misc.expose_barriers.tooltip"))
                .setSaveConsumer(n -> config.exposeBarrierBlocks = n);

        // only enable in creative
        BooleanToggleBuilder toggleOnlyEnableInCreative = entryBuilder.startBooleanToggle(Text.translatable("config.generic.misc.only_creative.title"), config.onlyEnableInCreative)
                .setDefaultValue(defaults.onlyEnableInCreative)
                .setTooltip(Text.translatable("config.generic.misc.only_creative.tooltip"))
                .setSaveConsumer(n -> config.onlyEnableInCreative = n);


        SubCategoryBuilder subCatTargetedBuilder = entryBuilder.startSubCategory(Text.translatable("config.generic.targeted.title"));
        subCatTargetedBuilder.setTooltip(Text.translatable("config.generic.targeted.tooltip"));
        subCatTargetedBuilder.add(toggleTargetAll.build());
        subCatTargetedBuilder.add(toggleTargetPlayers.build());
        subCatTargetedBuilder.add(toggleTargetMonster.build());
        subCatTargetedBuilder.add(toggleTargetVillager.build());
        subCatTargetedBuilder.add(toggleTargetAnimals.build());
        subCatTargetedBuilder.add(toggleTargetObjects.build());
        subCatTargetedBuilder.setExpanded(subCatTargeted != null && subCatTargeted.isExpanded());
        subCatTargeted = subCatTargetedBuilder.build();

        SubCategoryBuilder subCatMiscBuilder = entryBuilder.startSubCategory(Text.translatable("config.generic.misc.title"));
        subCatMiscBuilder.add(toggleForceRenderNameTags.build());
        subCatMiscBuilder.add(toggleRemoveBlindness.build());
        subCatMiscBuilder.add(toggleExposeBarriers.build());
        subCatMiscBuilder.add(toggleOnlyEnableInCreative.build());
        subCatMiscBuilder.setExpanded(subCatMisc != null && subCatMisc.isExpanded());
        subCatMisc = subCatMiscBuilder.build();


        catGeneric.addEntry(toggleEnabled.build());
        catGeneric.addEntry(sliderAlpha.build());
        catGeneric.addEntry(selectorInvisibleEntityGlow.build());
        catGeneric.addEntry(subCatTargeted);
        catGeneric.addEntry(subCatMisc);

        return builder.build();
    }

    public static void openConfigScreen(MinecraftClient client) {
        client.setScreenAndRender(buildConfigScreen(client.currentScreen));
    }
}
