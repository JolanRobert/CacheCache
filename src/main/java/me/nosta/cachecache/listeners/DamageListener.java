package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.RunnableEnum;
import me.nosta.cachecache.enums.TeamEnum;
import me.nosta.cachecache.managers.DeathManager;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import me.nosta.cachecache.managers.RunnableManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
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

        PlayerRole victim = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)event.getEntity());
        PlayerRole attacker = RoleManager.getInstance().getPlayerRoleWithPlayer((Player)event.getDamager());

        if (attacker == null || victim == null) return;
        if (attacker.isStunned()) return;
        if (victim.isInvincible()) event.setCancelled(true);

        RoleEnum role = victim.getRole() == RoleEnum.ESPION ? victim.getCover() : victim.getRole();

        //Melee damage handling
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            //Hunter hits Survivor
            if (attacker.getTeam() == TeamEnum.CHASSEUR && victim.getTeam() == TeamEnum.SURVIVANT) {
                if (!DeathManager.getInstance().hasCorrectItem(attacker.getPlayer(),Material.GOLDEN_SWORD,"Poignard")) return;

                switch (role) {
                    case ANGE:
                        if (victim.isInvincible()) PowerManager.getInstance().triggerPowerAnge(victim,attacker);
                        else DeathManager.getInstance().transformToHunter(attacker,victim);
                        break;
                    case CAPITAINE:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.CAPITAINE);
                        DeathManager.getInstance().transformToHunter(attacker,victim);
                        break;
                    case JUMEAU:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.JUMEAU);
                        victim.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Jumeau) "+ChatColor.GREEN+"Vous emportez votre Jumeau dans la mort !");
                        victim.getTwin().getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Jumeau) "+ChatColor.GREEN+"Votre Jumeau vous emporte dans la mort !");
                        DeathManager.getInstance().transformToHunter(attacker,victim,victim.getTwin());
                        break;
                    case SNIPER:
                        RunnableManager.getInstance().stopRunnable(RunnableEnum.SNIPER);
                        DeathManager.getInstance().transformToHunter(attacker,victim);
                        break;
                    case VETERAN:
                        if (victim.getPowerUse() > 0) PowerManager.getInstance().triggerPowerVeteran(victim,attacker);
                        else DeathManager.getInstance().transformToHunter(attacker,victim);
                        break;
                    default:
                        DeathManager.getInstance().transformToHunter(attacker,victim);
                        break;
                }
            }

            //Rebelle hits Hunter
            else if (attacker.getRole() == RoleEnum.REBELLE && victim.getRole() == RoleEnum.CHASSEUR) {
                if (role == RoleEnum.REBELLE && attacker.getPowerUse() > 0) {
                    if (DeathManager.getInstance().hasCorrectItem(attacker.getPlayer(), Material.IRON_SWORD,"Dague")) PowerManager.getInstance().triggerPowerRebelle(attacker,victim);
                }
            }
        }

        //Bow damage handling
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player) {
            if (attacker.getRole() == RoleEnum.SNIPER && victim.getRole() == RoleEnum.CHASSEUR) {
                victim.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,5*20,0,false,false));
            }
        }
    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        event.getEntity().remove();
    }
}
