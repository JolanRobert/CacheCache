package me.nosta.cachecache.game;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
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
    private List<RoleEnum> roles = new ArrayList<>();
    private int nbNonCivils;

    public RoleManager() {
        this.init();
    }

    public void init() {
        nbNonCivils = 1; //Chasseur

        for (RoleEnum r : roles) {
            if (r == RoleEnum.JUMEAU) nbNonCivils += 2;
            else nbNonCivils++;
        }

        Bukkit.getOnlinePlayers().forEach(this::addPlayerRole);
    }

    public void addRole(RoleEnum role) {
        roles.add(role);

        if (role == RoleEnum.JUMEAU) roles.add(role);
        else nbNonCivils++;
    }

    public void removeRole(RoleEnum role) {
        roles.remove(role);

        if (role == RoleEnum.JUMEAU) nbNonCivils -= 2;
        else nbNonCivils--;
    }

    public void addPlayerRole(Player player) {
        PlayerRole pr = new PlayerRole(player);
        playerRoles.add(pr);
        pr.clearAll();
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

    public PlayerRole getPlayerRole(Player player) {
        for (PlayerRole pr : playerRoles) {
            if (pr.getPlayer() == player) return pr;
        }
        return null;
    }

    public PlayerRole getEspion() {
        for (PlayerRole pr : playerRoles) {
            if (pr.getRole() == RoleEnum.ESPION) return pr;
        }
        return null;
    }

    public int getNbNonCivils() {return nbNonCivils;}
    public List<RoleEnum> getRoles() {return roles;}
    public List<PlayerRole> getPlayerRoles() {return playerRoles;}
}
