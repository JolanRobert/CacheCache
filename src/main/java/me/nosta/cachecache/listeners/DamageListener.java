package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.TeamEnum;
import me.nosta.cachecache.game.RoleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setDamage(0);

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
        PlayerRole victim = RoleManager.getInstance().getPlayerRole((Player)event.getEntity());
        PlayerRole attacker = RoleManager.getInstance().getPlayerRole((Player)event.getDamager());

        if (attacker.getTeam() == TeamEnum.HUNTER) victim.setTeam(TeamEnum.HUNTER);
    }
}
