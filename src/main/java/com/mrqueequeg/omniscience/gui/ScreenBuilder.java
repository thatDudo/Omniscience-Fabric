package com.mrqueequeg.omniscience.gui;

import com.mrqueequeg.omniscience.Omniscience;
import com.mrqueequeg.omniscience.config.Config;
import com.mrqueequeg.omniscience.config.ConfigManager;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ScreenBuilder {
    public static boolean configScreenOpened = false;

    public static Screen buildConfigScreen(Screen parent) {
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
        AbstractConfigListEntry<Boolean> toggleEnabled = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.enabled.title"), config.enabled)
                .setDefaultValue(defaults.enabled)
                .setTooltip(new TranslatableText("config.generic.enabled.tooltip"))
                .setSaveConsumer(n -> config.enabled = n)
                .build();

        // remove sneak cover
        AbstractConfigListEntry<Boolean> toggleRemoveSneakCover = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.sneak_cover.title"), config.removeSneakCover)
                .setDefaultValue(defaults.removeSneakCover)
                .setTooltip(new TranslatableText("config.generic.sneak_cover.tooltip"))
                .setSaveConsumer(n -> config.removeSneakCover = n)
                .build();

        // let all players glow

        // exclude self
//        AbstractConfigListEntry<Boolean> toggleExcludeSelf = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.exclude_self.title"), config.excludeSelf)
//                .setDefaultValue(defaults.excludeSelf)
//                .setTooltip(new TranslatableText("config.generic.exclude_self.tooltip"))
//                .setSaveConsumer(n -> config.excludeSelf = n)
//                .build();

        // alpha
        IntegerSliderEntry sliderAlpha = entryBuilder.startIntSlider(new TranslatableText("config.generic.alpha.title"), config.getAlphaPercent(), 0, 100)
                .setDefaultValue(defaults.getAlphaPercent())
                .setTooltip(new TranslatableText("config.generic.alpha.tooltip"))
                .setSaveConsumer(config::setAlphaPercent)
                .build();

        // glow
        SelectionListEntry<String> selectorInvisibleEntityGlow = entryBuilder.startSelector(new TranslatableText("config.generic.glow.title"), Config.InvisibleEntityGlowStrings, config.getInvisibleEntityGlowString())
                .setDefaultValue(defaults.getInvisibleEntityGlowString())
                .setTooltip(new TranslatableText("config.generic.glow.tooltip"))
                .setSaveConsumer(config::setInvisibleEntityGlow)
                .build();

        // name tag
        SelectionListEntry<String> selectorNameTag = entryBuilder.startSelector(new TranslatableText("config.generic.name_tag.title"), Config.ShowNameTagConditionStrings, config.getShowNameTagConditionString())
                .setDefaultValue(defaults.getShowNameTagConditionString())
                .setTooltip(new TranslatableText("config.generic.name_tag.tooltip"))
                .setSaveConsumer(config::setShowNameTagCondition)
                .build();

        // all
        AbstractConfigListEntry<Boolean> toggleTargetAll = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.all.title"), config.targeted.all)
                .setDefaultValue(defaults.targeted.all)
                .setTooltip(new TranslatableText("config.generic.targeted.all.tooltip"))
                .setSaveConsumer(n -> config.targeted.all = n)
                .build();

        // players
        AbstractConfigListEntry<Boolean> toggleTargetPlayers = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.players.title"), config.targeted.players)
                .setDefaultValue(defaults.targeted.players)
                //.setTooltip(new TranslatableText("config.generic.targeted.players.tooltip"))
                .setSaveConsumer(n -> config.targeted.players = n)
                .build();

        // monster
        AbstractConfigListEntry<Boolean> toggleTargetMonster = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.monster.title"), config.targeted.monster)
                .setDefaultValue(defaults.targeted.monster)
                //.setTooltip(new TranslatableText("config.generic.targeted.monster.tooltip"))
                .setSaveConsumer(n -> config.targeted.monster = n)
                .build();

        // villager
        AbstractConfigListEntry<Boolean> toggleTargetVillager = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.villager.title"), config.targeted.villager)
                .setDefaultValue(defaults.targeted.villager)
                //.setTooltip(new TranslatableText("config.generic.targeted.villager.tooltip"))
                .setSaveConsumer(n -> config.targeted.villager = n)
                .build();

        // animals
        AbstractConfigListEntry<Boolean> toggleTargetAnimals = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.animals.title"), config.targeted.animals)
                .setDefaultValue(defaults.targeted.animals)
                //.setTooltip(new TranslatableText("config.generic.targeted.animals.tooltip"))
                .setSaveConsumer(n -> config.targeted.animals = n)
                .build();

        // objects
        AbstractConfigListEntry<Boolean> toggleTargetObjects = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.objects.title"), config.targeted.objects)
                .setDefaultValue(defaults.targeted.objects)
                //.setTooltip(new TranslatableText("config.generic.targeted.objects.tooltip"))
                .setSaveConsumer(n -> config.targeted.objects = n)
                .build();


        SubCategoryBuilder subCatTargeted = entryBuilder.startSubCategory(new TranslatableText("config.generic.targeted.title"));
        subCatTargeted.add(toggleTargetAll);
        subCatTargeted.add(toggleTargetPlayers);
        subCatTargeted.add(toggleTargetMonster);
        subCatTargeted.add(toggleTargetVillager);
        subCatTargeted.add(toggleTargetAnimals);
        subCatTargeted.add(toggleTargetObjects);
        subCatTargeted.setExpanded(true);

        catGeneric.addEntry(toggleEnabled);
        //catGeneric.addEntry(toggleExcludeSelf);
        catGeneric.addEntry(toggleRemoveSneakCover);
        catGeneric.addEntry(sliderAlpha);
        catGeneric.addEntry(selectorInvisibleEntityGlow);
        //catGeneric.addEntry(selectorNameTag);
        catGeneric.addEntry(subCatTargeted.build());

        return builder.build();
    }

    public static void openConfigScreen() {
        openConfigScreen(MinecraftClient.getInstance());
    }

    public static void openConfigScreen(MinecraftClient client) {
        client.openScreen(buildConfigScreen(client.currentScreen));
    }
}
