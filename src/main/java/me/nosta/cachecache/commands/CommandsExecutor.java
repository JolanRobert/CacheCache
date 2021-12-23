package me.nosta.cachecache.commands;

import me.nosta.cachecache.game.InventoryManager;
import me.nosta.cachecache.game.GameManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandsExecutor {

	public void configGame(Player player) {
		player.openInventory(InventoryManager.getInstance().getConfigInventory());
	}

	public void helpInfo(Player player) {
		String helpMsg = ChatColor.GOLD+"[CC] "+ChatColor.GREEN+"Aide >>";
		helpMsg += ChatColor.GOLD+"\n/cc config : "+ChatColor.GREEN+"Permet de configurer la composition des rôles de la partie.";
		helpMsg += ChatColor.GOLD+"\n/cc start : "+ChatColor.GREEN+"Lance la partie.";
		helpMsg += ChatColor.GOLD+"\n/cc stop : "+ChatColor.GREEN+"Arrête la partie en cours.";
		player.sendMessage(helpMsg);
	}

	public void startGame(Player player) {
		GameManager.getInstance().startGame();
	}
	
	public void stopGame(Player player) {
		TextComponent msg = new TextComponent(ChatColor.DARK_RED+"[CC]"+ChatColor.RED+" Cliquer pour confirmer l'arrêt de la partie.");
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/cc stop confirm"));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED+"Cliquer pour arrêter la partie en cours.")));
		
		player.spigot().sendMessage(msg);
	}
	
	public void endGame(Player player) {
		Bukkit.broadcastMessage(ChatColor.DARK_RED+"[CC] "+ChatColor.RED+player.getName()+" a forcé l'arrêt de la partie.");
		GameManager.getInstance().endGame();
	}
}
