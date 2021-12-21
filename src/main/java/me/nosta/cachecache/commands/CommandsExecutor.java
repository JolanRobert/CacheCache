package me.nosta.cachecache.commands;

import me.nosta.cachecache.game.GameInventories;
import me.nosta.cachecache.game.GameManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandsExecutor {

	public void startGame(Player player) {
		player.sendMessage("Start Game");
		GameManager.getInstance().startGame();
	}
	
	public void endGame(Player player) {
		player.sendMessage("End Game");
		TextComponent msg = new TextComponent(ChatColor.DARK_RED+"[CC]"+ChatColor.RED+" Cliquer pour confirmer l'arrêt de la partie.");
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/cc confirmforceend"));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED+"Cliquer pour arrêter la partie en cours.")));
		
		player.spigot().sendMessage(msg);
	}
	
	public void confirmEndGame(Player player) {
		player.sendMessage("Confirm End Game");
		GameManager.getInstance().endGame();
	}
	
	public void configGame(Player player) {
		player.sendMessage("Config Game");
		player.openInventory(GameInventories.getInstance().getConfigInventory());
	}
}
