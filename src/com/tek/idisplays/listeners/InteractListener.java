package com.tek.idisplays.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import com.tek.idisplays.Main;
import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;
import com.tek.idisplays.map.DisplayManager;
import com.tek.idisplays.tasks.SelectionHighlightTask;

public class InteractListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				final Player player = event.getPlayer();
				if(Main.getInstance().getSelections().containsKey(player.getUniqueId())) {
					if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Reference.wandIdentifier, PersistentDataType.BYTE)) {
						Selection selection = Main.getInstance().getSelections().get(player.getUniqueId());
						if(selection.getFrom() == null) {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							
							if(faceLooking == BlockFace.UP || faceLooking == BlockFace.DOWN) {
								player.sendMessage(Reference.PREFIX + Reference.color("&c您无法在地板或天花板上放置显示器"));
							} else {
								selection.setFrom(blockLook.getRelative(faceLooking));
								selection.setFace(faceLooking);
								player.sendMessage(Reference.PREFIX + Reference.color("&a首先显示角落位置设置"));
							}
						} else {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							if(faceLooking == null || blockLook == null) return;
							Block fromBlock = selection.getFrom();
							Block toBlock = blockLook.getRelative(faceLooking);
							
							boolean works = true;
							if(selection.getFace() == BlockFace.WEST || selection.getFace() == BlockFace.EAST) if(Math.abs(toBlock.getX() - fromBlock.getX()) != 0) works = false;
							if(selection.getFace() == BlockFace.SOUTH || selection.getFace() == BlockFace.NORTH) if(Math.abs(toBlock.getZ() - fromBlock.getZ()) != 0) works = false;
						
							if(works) {
								selection.setTo(toBlock);
								player.getInventory().setItemInMainHand(null);
								player.sendMessage(Reference.PREFIX + Reference.color("&a选择完成！ 创建显示..."));
								
								DisplayManager.attemptCreateDisplay(selection, player);
								Main.getInstance().getSelections().remove(player.getUniqueId());
							} else {
								player.sendMessage(Reference.PREFIX + Reference.color("&c您无法在此位置放置显示器（必须厚度为1块）"));
							}
						}
					}
				}
			}
		}
	}
	
}
