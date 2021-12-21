package me.nosta.cachecache.game;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RoleManager {

    private static RoleManager instance;

    public static RoleManager getInstance() {
        if (instance == null) instance = new RoleManager();
        return instance;
    }

    private List<PlayerRole> playerRoles = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private int nbNonCivils;

    public RoleManager() {
        this.init();
    }

    public void init() {
        nbNonCivils = 1; //Chasseur

        for (Role r : roles) {
            if (r == Role.FRERE) nbNonCivils += 2;
            else nbNonCivils++;
        }

        for (Player p : Bukkit.getOnlinePlayers()) { addPlayerRole(p); }
    }

    public void addRole(Role role) {
        roles.add(role);

        if (role == Role.FRERE) nbNonCivils += 2;
        else nbNonCivils++;
    }

    public void removeRole(Role role) {
        roles.remove(role);

        if (role == Role.FRERE) nbNonCivils -= 2;
        else nbNonCivils--;
    }

    public void addPlayerRole(Player player) {
        PlayerRole pr = new PlayerRole(player);
        playerRoles.add(pr);
    }

    public void removePlayerRole(Player player) {
        for (PlayerRole pr : playerRoles) {
            if (pr.getPlayer() == player) {
                playerRoles.remove(pr);
                break;
            }
        }
    }

    public void resetAll() {
        playerRoles.clear();
        init();
    }

    public void showRoleInfo() {
        for (PlayerRole pr : playerRoles) {
            pr.getPlayer().sendMessage(pr.getRole().getDescription());
        }
    }

    public int getNbNonCivils() { return nbNonCivils; }

    public int getNbNullPlayerRoles() {
        int sum = 0;
        for (PlayerRole pr : playerRoles) {
            if (pr.getRole() == null) sum += 1;
        }
        return sum;
    }

    public List<Role> getRoles() {return roles;}
    public List<PlayerRole> getPlayerRoles() {return playerRoles;}
}
