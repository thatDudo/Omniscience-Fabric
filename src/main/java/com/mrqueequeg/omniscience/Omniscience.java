package com.mrqueequeg.omniscience;

import com.mrqueequeg.omniscience.config.ConfigManager;
import com.mrqueequeg.omniscience.gui.ScreenBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

public class Omniscience implements ClientModInitializer {
	public static final String MOD_ID = "omniscience";
	public static final String MOD_NAME = "Omniscience";
	public static final String CONFIG_FILE_NAME = Omniscience.MOD_ID+".json";

	private KeyBinding keyBindingOpenSettings;
	private KeyBinding keyToggleEnabled;

	@Override
	public void onInitializeClient() {

		ConfigManager.init();

		// adding keybinding to settings
		keyBindingOpenSettings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.omniscience.settings", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
				"key.category.omniscience" // The translation key of the keybinding's category.
		));
		keyToggleEnabled = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.omniscience.enable", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
				"key.category.omniscience" // The translation key of the keybinding's category.
		));
		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
	}

	public void tick(MinecraftClient client) {
		if (keyBindingOpenSettings.wasPressed()) {
			ScreenBuilder.openConfigScreen(client);
		}
		else if (keyToggleEnabled.wasPressed()) {
			if (client.player != null) {
				if (ConfigManager.getConfig().enabled) {
					ConfigManager.getConfig().enabled = false;
					client.player.sendMessage(new TranslatableText("message.disabled").formatted(Formatting.RED), true);
				}
				else {
					ConfigManager.getConfig().enabled = true;
					client.player.sendMessage(new TranslatableText("message.enabled").formatted(Formatting.GREEN), true);
				}
			}
		}
	}

	public static boolean isCreative = true;

//	public static GameMode getGameMode() {
//		if (MinecraftClient.getInstance().interactionManager == null) {
//			return null;
//		}
//		return MinecraftClient.getInstance().interactionManager.getCurrentGameMode();
//	}
}
