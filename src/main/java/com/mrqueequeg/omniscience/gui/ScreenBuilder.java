package com.mrqueequeg.omniscience.gui;

import com.mrqueequeg.omniscience.EntityTargetGroup;
import com.mrqueequeg.omniscience.Omniscience;
import com.mrqueequeg.omniscience.config.Config;
import com.mrqueequeg.omniscience.config.ConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.SelectorBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ScreenBuilder {
    public static boolean configScreenOpened = false;

    public static Screen buildConfigScreen(Screen parent) {
        // TODO: better config screen
        /*
            - enabled
            + invisibility revealer
                - transparency (0...100)
                //- mark with glow (None, Player, All)
                (
                + target list (mark | target)
                    - All
                    - Players
                    - Monsters
                    - Animals
                    - Objects
            + effects
                - remove blindness effect
            + highlighting
                - (un)mark players with chat command
                + highlight
                    - All
                    - Players
                    - Monster
                    - Animals
                    - Objects
                + name tags
                    - force render
                    - remove sneak coverage
            + misc
                - disallow inventory to be hidden by server
                -
         */

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
        ConfigCategory catGeneric = builder.getOrCreateCategory(new TranslatableText("config.generic.title"));

        // enable
        BooleanToggleBuilder toggleEnabled = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.enabled.title"), config.enabled)
                .setDefaultValue(defaults.enabled)
                .setTooltip(new TranslatableText("config.generic.enabled.tooltip"))
                .setSaveConsumer(n -> config.enabled = n);

        // exclude self
//        BooleanToggleBuilder toggleExcludeSelf = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.exclude_self.title"), config.excludeSelf)
//                .setDefaultValue(defaults.excludeSelf)
//                .setTooltip(new TranslatableText("config.generic.exclude_self.tooltip"))
//                .setSaveConsumer(n -> config.excludeSelf = n);

        // alpha
        IntSliderBuilder sliderAlpha = entryBuilder.startIntSlider(new TranslatableText("config.generic.alpha.title"), config.getAlphaPercent(), 0, 100)
                .setDefaultValue(defaults.getAlphaPercent())
                //.setTooltip(new TranslatableText("config.generic.alpha.tooltip"))
                .setSaveConsumer(config::setAlphaPercent);

        // glow
        SelectorBuilder<String> selectorInvisibleEntityGlow = entryBuilder.startSelector(new TranslatableText("config.generic.glow.title"), Config.InvisibleEntityGlowStrings, config.getInvisibleEntityGlowString())
                .setDefaultValue(defaults.getInvisibleEntityGlowString())
                .setTooltip(new TranslatableText("config.generic.glow.tooltip"))
                .setSaveConsumer(config::setInvisibleEntityGlow);

        // all
        BooleanToggleBuilder toggleTargetAll = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.all.title"), config.isTargeted(EntityTargetGroup.ALL))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.ALL))
                .setTooltip(new TranslatableText("config.generic.targeted.all.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ALL, n));

        // players
        BooleanToggleBuilder toggleTargetPlayers = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.players.title"), config.isTargeted(EntityTargetGroup.PLAYER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.PLAYER))
                //.setTooltip(new TranslatableText("config.generic.targeted.players.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.PLAYER, n));

        // monster
        BooleanToggleBuilder toggleTargetMonster = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.monster.title"), config.isTargeted(EntityTargetGroup.MONSTER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.MONSTER))
                //.setTooltip(new TranslatableText("config.generic.targeted.monster.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.MONSTER, n));

        // villager
        BooleanToggleBuilder toggleTargetVillager = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.villager.title"), config.isTargeted(EntityTargetGroup.VILLAGER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.VILLAGER))
                //.setTooltip(new TranslatableText("config.generic.targeted.villager.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.VILLAGER, n));

        // animals
        BooleanToggleBuilder toggleTargetAnimals = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.animals.title"), config.isTargeted(EntityTargetGroup.ANIMAL))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.ANIMAL))
                //.setTooltip(new TranslatableText("config.generic.targeted.animals.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ANIMAL, n));

        // objects
        BooleanToggleBuilder toggleTargetObjects = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.objects.title"), config.isTargeted(EntityTargetGroup.OBJECT))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.OBJECT))
                //.setTooltip(new TranslatableText("config.generic.targeted.objects.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.OBJECT, n));

        // force render name tags
        BooleanToggleBuilder toggleForceRenderNameTags = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.name_tags.force_render.title"), config.forceRenderNameTags)
                .setDefaultValue(defaults.forceRenderNameTags)
                .setTooltip(new TranslatableText("config.generic.name_tags.force_render.tooltip"))
                .setSaveConsumer(n -> config.forceRenderNameTags = n);

        // remove sneak cover
        BooleanToggleBuilder toggleRemoveSneakCover = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.name_tags.sneak_cover.title"), config.removeSneakCover)
                .setDefaultValue(defaults.removeSneakCover)
                .setTooltip(new TranslatableText("config.generic.name_tags.sneak_cover.tooltip"))
                .setSaveConsumer(n -> config.removeSneakCover = n);

        // only enable in creative
        BooleanToggleBuilder toggleOnlyEnableInCreative = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.misc.only_creative.title"), config.onlyEnableInCreative)
                .setDefaultValue(defaults.onlyEnableInCreative)
                .setTooltip(new TranslatableText("config.generic.misc.only_creative.tooltip"))
                .setSaveConsumer(n -> config.onlyEnableInCreative = n);


        SubCategoryBuilder subCatTargeted = entryBuilder.startSubCategory(new TranslatableText("config.generic.targeted.title"));
        subCatTargeted.setTooltip(new TranslatableText("config.generic.targeted.tooltip"));
        subCatTargeted.add(toggleTargetAll.build());
        subCatTargeted.add(toggleTargetPlayers.build());
        subCatTargeted.add(toggleTargetMonster.build());
        subCatTargeted.add(toggleTargetVillager.build());
        subCatTargeted.add(toggleTargetAnimals.build());
        subCatTargeted.add(toggleTargetObjects.build());
        subCatTargeted.setExpanded(false);

        SubCategoryBuilder subCatNameTag = entryBuilder.startSubCategory(new TranslatableText("config.generic.name_tags.title"));
        subCatNameTag.add(toggleForceRenderNameTags.build());
        subCatNameTag.add(toggleRemoveSneakCover.build());
        subCatNameTag.setExpanded(false);

        SubCategoryBuilder subCatMisc = entryBuilder.startSubCategory(new TranslatableText("config.generic.misc.title"));
        subCatMisc.add(toggleOnlyEnableInCreative.build());
        subCatMisc.setExpanded(false);

        catGeneric.addEntry(toggleEnabled.build());
        //catGeneric.addEntry(toggleExcludeSelf.build());
        catGeneric.addEntry(sliderAlpha.build());
        catGeneric.addEntry(selectorInvisibleEntityGlow.build());
        catGeneric.addEntry(subCatTargeted.build());
        catGeneric.addEntry(subCatNameTag.build());
        catGeneric.addEntry(subCatMisc.build());

        return builder.build();
    }

    public static void openConfigScreen() {
        openConfigScreen(MinecraftClient.getInstance());
    }

    public static void openConfigScreen(MinecraftClient client) {
        client.openScreen(buildConfigScreen(client.currentScreen));
    }
}
