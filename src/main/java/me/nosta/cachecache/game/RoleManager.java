package me.nosta.cachecache.game;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoleManager {

    private static RoleManager instance;

    public static RoleManager getInstance() {
        if (instance == null) instance = new RoleManager();
        return instance;
    }

    private HashMap<Role,Integer> roles = new HashMap<>();
    private List<PlayerRole> playerRoles = new ArrayList<>();

    public RoleManager() {
        this.initRoles();
    }

    public void initRoles() {
        roles.put(Role.CHASSEUR,1);
        roles.put(Role.CIVIL,Bukkit.getOnlinePlayers().size()-1);
    }

    public void addRole(Role role) {
        if (role == Role.FRERE) roles.put(role,2);
        else roles.put(role,1);

        //Remove one civil
        if (roles.get(Role.CIVIL) > 0) roles.replace(Role.CIVIL, roles.get(Role.CIVIL)-1);

        PrintRoles();
    }

    public void removeRole(Role role) {
        roles.remove(role);

        //Add one civil
        if (this.getNbNonCivil() < Bukkit.getOnlinePlayers().size()-1) roles.replace(Role.CIVIL, roles.get(Role.CIVIL)+1);

        PrintRoles();
    }

    public void resetRoles() {
        roles.clear();
        playerRoles.clear();
    }

    public void assignRoles(Player player, Role role) {
        PlayerRole pr = new PlayerRole(player,role);
        playerRoles.add(pr);
    }

    public void showRoleInfo() {
        for (PlayerRole pr : playerRoles) {
            pr.getPlayer().sendMessage(pr.getRole().getDescription());
        }
    }

    public void PrintRoles() {
        roles.forEach((key, value) -> {
            System.out.println(key.getName()+" : "+value);
        });
    }

    public int getNbRoles() {
        return this.getNbNonCivil()+this.getNbCivil();
    }

    public int getNbCivil() {
        return roles.get(Role.CIVIL);
    }

    public int getNbNonCivil() {
        int sum = 0;
        for (int value : roles.values()) { sum += value; }
        return sum-this.getNbCivil();
    }

    public HashMap<Role,Integer> getRoles() {
        return roles;
    }

    public List<PlayerRole> getPlayerRoles() {
        return playerRoles;
    }
}
