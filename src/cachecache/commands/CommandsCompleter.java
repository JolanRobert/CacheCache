package cachecache.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

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
				s = "start"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
				s = "forceend"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
				s = "config"; if (s.startsWith(args[0].toLowerCase())) result.add(s);
			}
		}
		
		return result;
	}
}
