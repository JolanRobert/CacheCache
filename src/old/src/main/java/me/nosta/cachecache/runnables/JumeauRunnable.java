package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class JumeauRunnable extends BukkitRunnable {

    PlayerRole twin1, twin2;

    public JumeauRunnable(PlayerRole twin1, PlayerRole twin2) {
        this.twin1 = twin1;
        this.twin2 = twin2;
        this.runTaskTimer(Main.getInstance(),0,1*20);
    }

    @Override
    public void run() {
        if (twin1.getRole() != RoleEnum.JUMEAU) this.cancel();

        Player twin1P = twin1.getPlayer();
        Player twin2P = twin2.getPlayer();

        if (twin1P.getLocation().distance(twin2P.getLocation()) <= 20) {
            twin1P.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,25,1,false,false));
            twin1P.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,25,1,false,false));
        }

        int distance = (int)twin1P.getLocation().distance(twin2P.getLocation());
        String twin1msg = ChatColor.GREEN+twin2P.getName()+getDirection(twin1P.getLocation(),twin2P.getLocation())+ChatColor.AQUA+"("+distance+"m)";
        String twin2msg = ChatColor.GREEN+twin1P.getName()+getDirection(twin2P.getLocation(),twin1P.getLocation())+ChatColor.AQUA+"("+distance+"m)";
        twin1P.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin1msg));
        twin1P.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin2msg));
    }

    public String getDirection(Location playerPos, Location targetPos) {
        Vector difference = targetPos.subtract(playerPos).toVector().normalize();
        double alpha = (Math.atan2(difference.getZ(), difference.getX()) - (Math.PI / 2));
        double yaw = playerPos.getYaw()-Math.toDegrees(alpha);
        if (yaw >= -45 && yaw <= 45) return " ↑ ";
        else if (yaw >= 45 && yaw <= 135) return " ← ";
        else if (yaw >= -135 && yaw <= -45) return " → ";
        else return " ↓ ";
    }
}
