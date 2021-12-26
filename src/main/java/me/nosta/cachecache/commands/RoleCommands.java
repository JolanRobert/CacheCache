package me.nosta.cachecache.commands;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.GameState;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.GameManager;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoleCommands implements CommandExecutor {

    private static RoleCommands instance;

    public static RoleCommands getInstance() {
        if (instance == null) instance = new RoleCommands();
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //CONSOLE
        if (!(sender instanceof Player)) return true;

        //PLAYER
        Player player = (Player) sender;

        if (!label.equalsIgnoreCase("role") || args.length <= 0) {return true;}

        if (args[0].equalsIgnoreCase("respawn") && args[1] != null) {
            if (GameManager.getInstance().getState() != GameState.PLAYING) return this.cancelCommand(player, "Impossible d'effectuer cette commande.");
            PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
            if (pr.getRole() != RoleEnum.PRETRE) return this.cancelCommand(player, "Vous n'êtes pas Prêtre.");
            PlayerRole dead = RoleManager.getInstance().getPlayerRoleWithPlayer(Bukkit.getPlayer(args[1]));
            if (dead.isRespawnable()) {
                if (pr.getPowerUse() > 0) PowerManager.getInstance().triggerPowerPretre(pr,dead);
            }
            else pr.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Prêtre) "+ChatColor.GREEN+dead.getPlayer().getName()+" ne peut plus être ressuscité.");
        }

        return true;
    }

    public boolean cancelCommand(Player player, String message) {
        player.sendMessage(ChatColor.DARK_RED+"[CC] "+ChatColor.RED+message);
        return true;
    }
}
