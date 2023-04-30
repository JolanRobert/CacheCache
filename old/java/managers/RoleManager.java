package me.nosta.cachecache.managers;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
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

    public RoleManager() {
        init();
    }

    public void init() {
        Bukkit.getOnlinePlayers().forEach(this::addPlayerRole);
    }

    public void addRole(RoleEnum role) {roles.add(role);}
    public void removeRole(RoleEnum role) {roles.remove(role);}

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

    public PlayerRole getPlayerRoleWithPlayer(Player player) {
        for (PlayerRole pr : playerRoles) {
            if (pr.getPlayer() == player) return pr;
        }
        return null;
    }

    public PlayerRole getPlayerRoleWithRole(RoleEnum roleEnum) {
        for (PlayerRole pr : playerRoles) {
            if (pr.getRole() == roleEnum) return pr;
        }
        return null;
    }

    public List<RoleEnum> getRoles() {return roles;}
    public List<PlayerRole> getPlayerRoles() {return playerRoles;}
}
