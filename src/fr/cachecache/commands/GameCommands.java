package fr.cachecache.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cachecache.game.GameManager;
import fr.cachecache.game.GameState;

public class GameCommands implements CommandExecutor {
	
	public static GameCommands instance;
	
	public void OnEnable() {
		instance = this;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//CONSOLE
		if (!(sender instanceof Player)) return true;

		//PLAYER
		Player player = (Player) sender;

		if (!label.equalsIgnoreCase("cc") || args.length <= 0) {return true;}
		
		//!OP
		if (!player.isOp()) return this.cancelCommand(player, "Vous n'avez pas les droits nécessaires pour exécuter cette commande.");
		
		if (args[0].equalsIgnoreCase("start")) {
			if (GameManager.instance.getState() != GameState.WAITING) return this.cancelCommand(player, "Une partie est déjà en cours.");
			else CommandsExecutor.instance.startGame(player);
		}
		
		else if (args[0].equalsIgnoreCase("config")) {
			if (GameManager.instance.getState() != GameState.WAITING) return this.cancelCommand(player, "Une partie est déjà en cours.");
			else CommandsExecutor.instance.configGame(player);
		}

		else if (args[0].equalsIgnoreCase("forceend")) {
			if (GameManager.instance.getState() == GameState.WAITING) return this.cancelCommand(player, "Aucune partie n'est en cours.");
			else CommandsExecutor.instance.endGame(player);
		}
		
		else if (args[0].equalsIgnoreCase("confirmforceend")) {
			CommandsExecutor.instance.confirmEndGame(player);
		} 
		
		return true;
	}
	
	public boolean cancelCommand(Player player, String message) {
		player.sendMessage(ChatColor.DARK_RED+"[CC]"+ChatColor.RED+message);
		return true;
	}
}
