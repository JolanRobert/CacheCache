package fr.cachecache.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.cachecache.game.GameInventories;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandsExecutor {

	public static CommandsExecutor instance;
	
	public void OnEnable() {
		instance = this;
	}
	
	public void startGame(Player player) {
		player.sendMessage("Start Game");
	}
	
	public void endGame(Player player) {
		player.sendMessage("End Game");
		String question = ChatColor.DARK_RED+"[CC]"+ChatColor.RED+"Êtes-vous sûr de vouloir arrêter la partie ?";
		TextComponent yes = new TextComponent(ChatColor.GREEN+"[OUI]");
		yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirmforceend"));
		String no = ChatColor.RED+"[NON]";		
		player.sendMessage(question+yes+no);
	}
	
	public void confirmEndGame(Player player) {
		player.sendMessage("Confirm End Game");
	}
	
	public void configGame(Player player) {
		player.sendMessage("Config Game");
		player.openInventory(GameInventories.instance.getConfigInventory());
	}
}
