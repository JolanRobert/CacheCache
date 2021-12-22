package me.nosta.cachecache.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandsCompleter implements TabCompleter {
	
	private static CommandsCompleter instance;

	public static CommandsCompleter getInstance() {
		if (instance == null) instance = new CommandsCompleter();
		return instance;
	}
	
	private List<String> result = new ArrayList<String>();
	private String s;

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {				
		result.clear();
		
		if (label.equalsIgnoreCase("cc")) {
			
			if (args.length == 1) {
				s = "config"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
				s = "help"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
				s = "start"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
				s = "stop"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
			}
		}
		
		return result;
	}
}
