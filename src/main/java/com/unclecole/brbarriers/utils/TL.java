package com.unclecole.brbarriers.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.logging.Level;

public enum TL {
	NO_PERMISSION("messages.no-permission", "&c&lERROR! &fYou don't have the permission to do that."),
	INVALID_ARGUMENT_NUMBER("messages.invalid-number", "&c&lERROR: &c'<argument>' has to be a number"),
	INVALID_COMMAND_USAGE("messages.invalid-command-usage", "&cIncorrect Usage: &7<command>"),
	PLAYER_ONLY("messages.player-only", "&cThis command is for players only!"),
	SUCCESSFULLY_SET_CORNER("messages.successfully-set-corner", "&a&lSUCCESS! &fYou successfully set corner %corner%!"),
	EDITING_MODE("messages.editing-mode", "&fYou have &e&l%toggle% edit mode!"),
	EDITING_ERROR("messages.editing-error", "&c&lERROR: &fYou didn't complete the setup!"),
	NOT_PERMITTED_TO_ENTER("messages.not-permitted-to-enter", "&c&lERROR: &fYou are not permitted to enter %barrier%! Unlock with %amount% %key%&f Keys!"),
	BARRIER_LIST("messages.barrier-list", "&e&l%barrier% &7► &7(%status%&7)"),
	BARRIER_DELETED("messages.barrier-deleted", "&a&lSUCCESS! &fYou successfully deleted %barrier% barrier!"),
	KEY_DELETED("messages.barrier-deleted", "&a&lSUCCESS! &fYou successfully deleted %barrier% Key!"),
	BARRIER_HELP_CMD("messages.barrier-help-cmd", "&e%command% &8&l- &a%description% &7(&c%permission%&7)"),
	SUCCESSFULLY_MADE_KEY("messages.successfully-made-key", "&a&lSUCCESS! &fYou successfully made &a%key% &fkey!"),
	MUST_HOLD_ITEM_TO_CREATE_KEY("messages.must-hold-item-to-create-key", "&c&lERROR! &fYou must be holding an item to create a key!");

	private String path, def;
	private static ConfigFile config;

	TL(String path, String start) {
		this.path = path;
		this.def = start;
	}

	public String getDefault() {
		return this.def;
	}

	public String getPath() {
		return this.path;
	}

	public void setDefault(String message) {
		this.def = message;
	}

	public static void loadMessages(ConfigFile configFile) {
		config = configFile;
		FileConfiguration data = configFile.getConfig();
		for (TL message : values()) {
			if (!data.contains(message.getPath())) {
				data.set(message.getPath(), message.getDefault());
			}
		}
		configFile.save();
	}

	public void send(CommandSender sender) {
		if (sender instanceof org.bukkit.entity.Player) {
			sender.sendMessage(C.color(getDefault()));
		} else {
			sender.sendMessage(C.strip(getDefault()));
		}
	}

	public void send(CommandSender sender, PlaceHolder... placeHolders) {
		if (sender instanceof org.bukkit.entity.Player) {
			sender.sendMessage(C.color(getDefault(), placeHolders));
		} else {
			sender.sendMessage(C.strip(getDefault(), placeHolders));
		}
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(C.color(message));
	}

	public static void message(CommandSender sender, String message, PlaceHolder... placeHolders) {
		sender.sendMessage(C.color(message, placeHolders));
	}

	public static void message(CommandSender sender, List<String> message) {
		message.forEach(m -> sender.sendMessage(C.color(m)));
	}

	public static void message(CommandSender sender, List<String> message, PlaceHolder... placeHolders) {
		message.forEach(m -> sender.sendMessage(C.color(m, placeHolders)));
	}

	public static void log(Level lvl, String message) {
		Bukkit.getLogger().log(lvl, message);
	}
}
