package me.nosta.cachecache.runnables;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JumeauRunnable extends BukkitRunnable {

    Player twin1, twin2;

    public JumeauRunnable(Player twin1, Player twin2) {
        this.twin1 = twin1;
        this.twin2 = twin2;
    }

    @Override
    public void run() {
        if (twin1.getLocation().distance(twin2.getLocation()) <= 20) {
            twin1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1*20,1,false,false));
            twin2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1*20,1,false,false));
        }

        int distance = (int)twin1.getLocation().distance(twin2.getLocation());
        String twin1msg = ChatColor.GREEN+twin2.getName()+getDirection(twin1,twin2)+ChatColor.AQUA+"("+distance+"m)";
        String twin2msg = ChatColor.GREEN+twin1.getName()+getDirection(twin2,twin1)+ChatColor.AQUA+"("+distance+"m)";
        twin1.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin1msg));
        twin2.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin2msg));

        /*twin1.setCompassTarget(twin2.getLocation());
        twin2.setCompassTarget(twin1.getLocation());*/
    }

    public String getDirection(Player origin, Player target) {
        Location direction = origin.getLocation();
        direction = direction.subtract(target.getLocation());
        float yaw = direction.getYaw();
        System.out.println(direction.getYaw());
        if (yaw >= 337.5f || yaw <= 22.5f) return " ↑ ";
        else if (yaw > 22.5f && yaw < 67.5f) return " ↗ ";
        else if (yaw >= 67.5f && yaw <= 112.5f) return " → ";
        else if (yaw > 112.5f && yaw < 157.5f) return " ↘ ";
        else if (yaw >= 157.5f && yaw < 202.5f) return " ↓ ";
        else if (yaw > 202.5f && yaw < 247.5f) return " ↙ ";
        else if (yaw >= 247.5f && yaw <= 292.5f) return " ← ";
        else return " ↖ ";
    }
}
