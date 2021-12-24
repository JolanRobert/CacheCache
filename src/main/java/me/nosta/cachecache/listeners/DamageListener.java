package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.TeamEnum;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import me.nosta.cachecache.enums.RunnableEnum;
import me.nosta.cachecache.managers.RunnableManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
                switch (victim.getRole()) {
                    case ANGE:
                        break;
                    case CAPITAINE:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.CAPITAINE);
                        transformToHunter(victim);
                        break;
                    case JUMEAU:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.JUMEAU);
                        transformToHunter(victim.getTwin());
                        transformToHunter(victim);
                        break;
                    case SNIPER:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.SNIPER);
                        transformToHunter(victim);
                        break;
                    case VETERAN:
                        if (victim.getPowerUse() > 0) PowerManager.getInstance().triggerPowerVeteran(victim,attacker);
                        else transformToHunter(victim);
                        break;
                    default:
                        transformToHunter(victim);
                        break;
                }
            }

            //Rebelle hits Hunter
            else if (attacker.getRole() == RoleEnum.REBELLE && victim.getRole() == RoleEnum.CHASSEUR) {

            }
        }

        //Bow damage handling
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player) {
            PlayerRole victim = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)event.getEntity());
            PlayerRole attacker = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)((Arrow) event.getDamager()).getShooter());

            if (attacker.getRole() == RoleEnum.SNIPER && victim.getRole() == RoleEnum.CHASSEUR) {
                victim.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,5*20,0,false,false));
            }
        }
    }

    public void transformToHunter(PlayerRole pr) {
        //Expert handling
        if (pr.getRole() != RoleEnum.VETERAN) {
            PlayerRole veteran = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.VETERAN);
            if (veteran != null && veteran.getPowerUse() < 2) {
                veteran.gainPowerUse();
                veteran.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                veteran.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,veteran.getPowerUse()-1,false,false));
            }
        }

        pr.setTeam(TeamEnum.CHASSEUR);
        pr.setRole(RoleEnum.CHASSEUR);
        pr.clearAll();
        pr.giveHunterKnife();
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        Entity arrow = event.getEntity();
        arrow.remove();
    }
}
