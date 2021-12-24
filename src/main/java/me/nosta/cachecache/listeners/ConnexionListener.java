package me.nosta.cachecache.listeners;

import me.nosta.cachecache.managers.InventoryManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("deprecation")
public class ConnexionListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();		
		event.setJoinMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"+"+ChatColor.DARK_GRAY+"] "+player.getName()+ChatColor.GRAY+" ("+
		ChatColor.YELLOW+Bukkit.getOnlinePlayers().size()+ChatColor.GRAY+"/"+ChatColor.YELLOW+Bukkit.getMaxPlayers()+ChatColor.GRAY+")");

		RoleManager.getInstance().addPlayerRole(player);
		InventoryManager.getInstance().UpdateNbCivil();
	}	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.DARK_GRAY+"["+ChatColor.RED+"-"+ChatColor.DARK_GRAY+"] "+player.getName()+ChatColor.GRAY+" ("+
		ChatColor.YELLOW+(Bukkit.getOnlinePlayers().size()-1)+ChatColor.GRAY+"/"+ChatColor.YELLOW+Bukkit.getMaxPlayers()+ChatColor.GRAY+")");

		RoleManager.getInstance().removePlayerRole(player);
		InventoryManager.getInstance().UpdateNbCivil();
	}
}
