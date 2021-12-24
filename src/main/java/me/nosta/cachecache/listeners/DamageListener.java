package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.elements.RoleEnum;
import me.nosta.cachecache.elements.TeamEnum;
import me.nosta.cachecache.game.RoleManager;
import me.nosta.cachecache.game.RunnableEnum;
import me.nosta.cachecache.game.RunnableManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setDamage(0);

        //Melee damage handling
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            PlayerRole victim = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)event.getEntity());
            PlayerRole attacker = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)event.getDamager());

            //Hunter hits Survivor
            if (attacker.getTeam() == TeamEnum.CHASSEUR && victim.getTeam() == TeamEnum.SURVIVANT) {
                victim.setTeam(TeamEnum.CHASSEUR);

                //Twin Kill
                if (victim.getRole() == RoleEnum.JUMEAU) {
                    PlayerRole twin = victim.getTwin();
                    twin.setRole(RoleEnum.CHASSEUR);
                    twin.clearAll();
                    twin.giveKnife();
                    RunnableManager.getInstance().stopRunnable(RunnableEnum.TWIN);
                }

                victim.setRole(RoleEnum.CHASSEUR);
                victim.clearAll();
                victim.giveKnife();
            }

            //Survivor hits Hunter
            else if (attacker.getTeam() == TeamEnum.SURVIVANT && victim.getTeam() == TeamEnum.CHASSEUR) {

            }
        }

        //Bow damage handling
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player) {

        }

        else return;

    }
}
