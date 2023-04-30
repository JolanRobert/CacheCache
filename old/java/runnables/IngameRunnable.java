package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.TeamEnum;
import me.nosta.cachecache.managers.GameManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class IngameRunnable extends BukkitRunnable {

    List<PlayerRole> playerRoles;
    boolean hunterWin, survivorWin;

    public IngameRunnable() {
        this.playerRoles = RoleManager.getInstance().getPlayerRoles();
        this.runTaskTimer(Main.getInstance(),0, 20);
    }

    @Override
    public void run() {
        hunterWin = true;
        for (PlayerRole pr : playerRoles) {
            if (pr.getTeam() != TeamEnum.CHASSEUR && pr.getRole() != RoleEnum.ESPION) {
                hunterWin = false;
                break;
            }
        }

        if (hunterWin) {
            Bukkit.broadcastMessage(ChatColor.RED+"Victoire des Chasseurs !");
            GameManager.getInstance().endGame();
        }
    }
}
