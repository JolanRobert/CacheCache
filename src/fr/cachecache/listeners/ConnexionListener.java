package fr.cachecache.listeners;

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
	}	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		event.setQuitMessage(ChatColor.DARK_GRAY+"["+ChatColor.RED+"-"+ChatColor.DARK_GRAY+"] "+player.getName()+ChatColor.GRAY+" ("+
		ChatColor.YELLOW+(Bukkit.getOnlinePlayers().size()-1)+ChatColor.GRAY+"/"+ChatColor.YELLOW+Bukkit.getMaxPlayers()+ChatColor.GRAY+")");
	}
}
