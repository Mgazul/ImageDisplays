package com.tek.idisplays;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Reference {
	
	public static final String PREFIX            = color("&8[&a图片&2显示&8] &f");
	public static final String PERMISSION_CREATE = "tupain.create";
	public static final String PERMISSION_REMOVE = "tupain.remove";
	public static File bannerFolder;
	public static NamespacedKey wandIdentifier;
	public static ItemStack creationWand;
	
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	public static void init() {
		bannerFolder = new File("plugins/" + Main.getInstance().getName() + "/displays");
		bannerFolder.mkdirs();
		
		wandIdentifier = new NamespacedKey(Main.getInstance(), "isCreationWand");
		
		creationWand = new ItemStack(Material.STICK);
		ItemMeta meta = creationWand.getItemMeta();
		meta.setDisplayName(color("&a&l显示创建工具"));
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		meta.getPersistentDataContainer().set(wandIdentifier, PersistentDataType.BYTE, (byte)0x01);
		creationWand.setItemMeta(meta);
	}
	
}
