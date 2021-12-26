package me.nosta.cachecache.commands;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.GameState;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.GameManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {
	
	private static GameCommands instance;
	private CommandsExecutor ce;

	public static GameCommands getInstance() {
		if (instance == null) instance = new GameCommands();
		return instance;
	}
	
	public GameCommands() {
		ce = new CommandsExecutor();
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

		if (args[0].equalsIgnoreCase("config")) {
			if (GameManager.getInstance().getState() != GameState.WAITING) return this.cancelCommand(player, "Une partie est déjà en cours.");
			else ce.configGame(player);
		}

		else if (args[0].equalsIgnoreCase("help")) {
			ce.helpInfo(player);
		}

		else if (args[0].equalsIgnoreCase("start")) {
			if (GameManager.getInstance().getState() != GameState.WAITING) return this.cancelCommand(player, "Une partie est déjà en cours.");
			else ce.startGame(player);
		}

		else if (args[0].equalsIgnoreCase("stop") && args.length == 1) {
			if (GameManager.getInstance().getState() == GameState.WAITING) return this.cancelCommand(player, "Aucune partie n'est en cours.");
			else ce.stopGame(player);
		}

		else if (args[0].equalsIgnoreCase("stop") && args[1].equalsIgnoreCase("confirm")) {
			if (GameManager.getInstance().getState() == GameState.WAITING) return this.cancelCommand(player, "Aucune partie n'est en cours.");
			else ce.endGame(player);
		}

		else if (args[0].equalsIgnoreCase("respawn") && args[1] != null) {
			if (GameManager.getInstance().getState() != GameState.PLAYING) return true;
			PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
			if (pr.getRole() != RoleEnum.PRETRE) return true;
			PlayerRole dead = RoleManager.getInstance().getPlayerRoleWithPlayer(Bukkit.getPlayer(args[1]));


			else ce.endGame(player);
		}
		
		return true;
	}
	
	public boolean cancelCommand(Player player, String message) {
		player.sendMessage(ChatColor.DARK_RED+"[CC] "+ChatColor.RED+message);
		return true;
	}
}
