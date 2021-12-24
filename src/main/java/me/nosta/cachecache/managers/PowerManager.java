package me.nosta.cachecache.managers;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RunnableEnum;
import me.nosta.cachecache.enums.TeamEnum;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerManager {

    private static PowerManager instance;

    public static PowerManager getInstance() {
        if (instance == null) instance = new PowerManager();
        return instance;
    }

    public void triggerPowerCapitaine(PlayerRole capitaine) {
        capitaine.losePowerUse();
        if (capitaine.getPowerUse() == 0) capitaine.getPlayer().getInventory().remove(Material.NETHER_STAR);

        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.SURVIVANT) {
                Player player = pr.getPlayer();
                player.teleport(capitaine.getPlayer().getLocation());
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,5*20,0,false,false));
                player.setInvulnerable(true);
                player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN,Integer.MAX_VALUE,1);
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> RoleManager.getInstance().getPlayerRoles().forEach(pr -> pr.getPlayer().setInvulnerable(false)), 5*20);
        capitaine.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Capitaine) "+ChatColor.GREEN+"Activation du Ralliement !");
    }

    public void triggerPowerNinja(PlayerRole ninja) {
        if (ninja.getCooldown() > 0) {
            ninja.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Ninja) "+ChatColor.GREEN+"Camouflage rechargé dans "+ninja.getCooldown()+"s");
            return;
        }
        ninja.losePowerUse();
        if (ninja.getPowerUse() == 0) ninja.getPlayer().getInventory().remove(Material.NETHER_STAR);

        ninja.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10*20,1,false,false));
        ninja.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,10*20,0,false,false));

        ninja.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Ninja) "+ChatColor.GREEN+"Activation du Camouflage !"+" ("+(3-ninja.getPowerUse())+"/3)");
        ninja.getPlayer().playSound(ninja.getPlayer().getLocation(), Sound.ITEM_FIRECHARGE_USE, Integer.MAX_VALUE, 1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () ->
                ninja.getPlayer().playSound(ninja.getPlayer().getLocation(), Sound.ITEM_FIRECHARGE_USE, Integer.MAX_VALUE, 1), 10*20);

        if (ninja.getPowerUse() > 0) RunnableManager.getInstance().launchRunnable(RunnableEnum.NINJA);
    }

    public void triggerPowerVeteran(PlayerRole veteran, PlayerRole hunter) {
        veteran.losePowerUse();
        veteran.getPlayer().setInvulnerable(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> veteran.getPlayer().setInvulnerable(false), 5*20);

        hunter.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,5*20,127,false,false));
        hunter.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,5*20,127,false,false));
        hunter.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,5*20,0,false,false));
        hunter.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,5*20,0,false,false));

        veteran.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        if (veteran.getPowerUse() > 0) veteran.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,veteran.getPowerUse()-1,false,false));

        veteran.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
    }
}
