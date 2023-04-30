package me.nosta.cachecache.listeners;

import me.nosta.cachecache.managers.InventoryManager;
import me.nosta.cachecache.managers.RoleManager;
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
		event.setJoinMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"+"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY+player.getName());

		RoleManager.getInstance().addPlayerRole(player);
		InventoryManager.getInstance().UpdateNbCivil();
	}	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.DARK_GRAY+"["+ChatColor.RED+"-"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY+player.getName());

		RoleManager.getInstance().removePlayerRole(player);
		InventoryManager.getInstance().UpdateNbCivil();
	}
}
