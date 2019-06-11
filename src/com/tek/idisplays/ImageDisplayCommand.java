package com.tek.idisplays;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ImageDisplayCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Reference.PREFIX + Reference.color("&c你必须是一个使用它的玩家"));
			return false;
		}
		
		Player player = (Player) sender;
		
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 2) {
					if(sender.hasPermission(Reference.PERMISSION_CREATE)) {
						if(!Main.getInstance().getSelections().containsKey(player.getUniqueId())) {
							Selection selection = new Selection(args[1]);
							if(selection.imageExists()) {
								Main.getInstance().getSelections().put(player.getUniqueId(), selection);
								player.getInventory().addItem(Reference.creationWand);
								sender.sendMessage(Reference.PREFIX + Reference.color("&a给你显示创建魔杖"));
								sender.sendMessage(Reference.color("&7- &6右键单击第一个块的面"));
								sender.sendMessage(Reference.color("&7- &6右键单击另一个块"));
								sender.sendMessage(Reference.color("&7- &6您可以通过删除项目来取消显示创建"));
							} else {
								sender.sendMessage(Reference.PREFIX + Reference.color("&c那个图像不存在"));
							}
						} else {
							sender.sendMessage(Reference.PREFIX + Reference.color("&c你已经在创建一个显示器了"));
						}
					} else {
						sender.sendMessage(Reference.PREFIX + Reference.color("&c您没有足够的权限来使用它"));
					}
				} else {
					sender.sendMessage(Reference.PREFIX + Reference.color("&c无效的语法"));
				}
			} 
			
			else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
				if(args.length == 1) {
					if(sender.hasPermission(Reference.PERMISSION_REMOVE)) {
						if(!Main.getInstance().getDeletions().contains(player.getUniqueId())) {
							Main.getInstance().getDeletions().add(player.getUniqueId());
							sender.sendMessage(Reference.PREFIX + Reference.color("&a点击一个显示，它将被完全删除"));
						} else {
							sender.sendMessage(Reference.PREFIX + Reference.color("&c你已经删除了一个显示器"));
						}
					} else {
						sender.sendMessage(Reference.PREFIX + Reference.color("&c您没有足够的权限来使用它"));
					}
				} else {
					sender.sendMessage(Reference.PREFIX + Reference.color("&c无效的语法"));
				}
			} 
			
			else if(args[0].equalsIgnoreCase("help")) {
				if(args.length == 1) {
					sender.sendMessage(Reference.color("&a&l图片&2显示 &6帮助菜单"));
					sender.sendMessage(Reference.color("&8- &a/tupian create <文件/url> &8- &a根据您的选择创建显示"));
					sender.sendMessage(Reference.color("&8- &a/tupian remove &8- &a让你进入删除模式（点击显示将其删除）"));
					sender.sendMessage(Reference.color("&8- &a/tupian help &8- &a提供插件的帮助菜单"));
				} else {
					sender.sendMessage(Reference.PREFIX + Reference.color("&c无效的语法"));
				}
			} 
			
			else {
				sender.sendMessage(Reference.PREFIX + Reference.color("&c无效的语法"));
			}
		} else {
			sender.sendMessage(Reference.PREFIX + Reference.color("&c无效的语法"));
		}
		
		return false;
	}
	
	public static class ImageDisplayCompleter implements TabCompleter {
		@Override
		public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
			if(!(sender instanceof Player)) {
				return Arrays.asList();
			}
			
			if(args.length == 1) return Arrays.asList("help", "create", "remove", "delete").stream().filter(cmd -> cmd.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
			if(args.length == 2 && args[0].equalsIgnoreCase("create")) return Arrays.stream(Reference.bannerFolder.listFiles()).map(File::getName)
					.filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).collect(Collectors.toList());
			return Arrays.asList();
		}
	}

}
