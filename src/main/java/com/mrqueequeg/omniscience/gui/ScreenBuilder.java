package com.mrqueequeg.omniscience.gui;

import com.mrqueequeg.omniscience.EntityTargetGroup;
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

        // all
        AbstractConfigListEntry<Boolean> toggleTargetAll = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.all.title"), config.isTargeted(EntityTargetGroup.ALL))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.ALL))
                .setTooltip(new TranslatableText("config.generic.targeted.all.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ALL, n))
                .build();

        // players
        AbstractConfigListEntry<Boolean> toggleTargetPlayers = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.players.title"), config.isTargeted(EntityTargetGroup.PLAYER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.PLAYER))
                //.setTooltip(new TranslatableText("config.generic.targeted.players.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.PLAYER, n))
                .build();

        // monster
        AbstractConfigListEntry<Boolean> toggleTargetMonster = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.monster.title"), config.isTargeted(EntityTargetGroup.MONSTER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.MONSTER))
                //.setTooltip(new TranslatableText("config.generic.targeted.monster.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.MONSTER, n))
                .build();

        // villager
        AbstractConfigListEntry<Boolean> toggleTargetVillager = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.villager.title"), config.isTargeted(EntityTargetGroup.VILLAGER))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.VILLAGER))
                //.setTooltip(new TranslatableText("config.generic.targeted.villager.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.VILLAGER, n))
                .build();

        // animals
        AbstractConfigListEntry<Boolean> toggleTargetAnimals = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.animals.title"), config.isTargeted(EntityTargetGroup.ANIMAL))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.ANIMAL))
                //.setTooltip(new TranslatableText("config.generic.targeted.animals.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.ANIMAL, n))
                .build();

        // objects
        AbstractConfigListEntry<Boolean> toggleTargetObjects = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.targeted.objects.title"), config.isTargeted(EntityTargetGroup.OBJECT))
                .setDefaultValue(defaults.isTargeted(EntityTargetGroup.OBJECT))
                //.setTooltip(new TranslatableText("config.generic.targeted.objects.tooltip"))
                .setSaveConsumer(n -> config.setEntityTargetGroup(EntityTargetGroup.OBJECT, n))
                .build();

        // force render name tags
        AbstractConfigListEntry<Boolean> toggleForceRenderNameTags = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.name_tags.force_render.title"), config.forceRenderNameTags)
                .setDefaultValue(defaults.forceRenderNameTags)
                .setTooltip(new TranslatableText("config.generic.name_tags.force_render.tooltip"))
                .setSaveConsumer(n -> config.forceRenderNameTags = n)
                .build();

        // remove sneak cover
        AbstractConfigListEntry<Boolean> toggleRemoveSneakCover = entryBuilder.startBooleanToggle(new TranslatableText("config.generic.name_tags.sneak_cover.title"), config.removeSneakCover)
                .setDefaultValue(defaults.removeSneakCover)
                .setTooltip(new TranslatableText("config.generic.name_tags.sneak_cover.tooltip"))
                .setSaveConsumer(n -> config.removeSneakCover = n)
                .build();


        SubCategoryBuilder subCatTargeted = entryBuilder.startSubCategory(new TranslatableText("config.generic.targeted.title"));
        subCatTargeted.add(toggleTargetAll);
        subCatTargeted.add(toggleTargetPlayers);
        subCatTargeted.add(toggleTargetMonster);
        subCatTargeted.add(toggleTargetVillager);
        subCatTargeted.add(toggleTargetAnimals);
        subCatTargeted.add(toggleTargetObjects);
        subCatTargeted.setExpanded(true);

        SubCategoryBuilder subCatNameTag = entryBuilder.startSubCategory(new TranslatableText("config.generic.name_tags.title"));
        subCatNameTag.add(toggleForceRenderNameTags);
        subCatNameTag.add(toggleRemoveSneakCover);
        subCatNameTag.setExpanded(true);

        catGeneric.addEntry(toggleEnabled);
        //catGeneric.addEntry(toggleExcludeSelf);
        catGeneric.addEntry(sliderAlpha);
        catGeneric.addEntry(selectorInvisibleEntityGlow);
        catGeneric.addEntry(subCatTargeted.build());
        catGeneric.addEntry(subCatNameTag.build());

        return builder.build();
    }

    public static void openConfigScreen() {
        openConfigScreen(MinecraftClient.getInstance());
    }

    public static void openConfigScreen(MinecraftClient client) {
        client.openScreen(buildConfigScreen(client.currentScreen));
    }
}
