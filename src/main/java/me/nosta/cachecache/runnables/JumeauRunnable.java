package me.nosta.cachecache.runnables;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class JumeauRunnable extends BukkitRunnable {

    Player twin1, twin2;

    public JumeauRunnable(PlayerRole twin1, PlayerRole twin2) {
        this.twin1 = twin1.getPlayer();
        this.twin2 = twin2.getPlayer();
        this.runTaskTimer(Main.getInstance(),0,1*20);
    }

    @Override
    public void run() {
        if (twin1.getLocation().distance(twin2.getLocation()) <= 20) {
            twin1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,25,1,false,false));
            twin2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,25,1,false,false));
        }

        int distance = (int)twin1.getLocation().distance(twin2.getLocation());
        String twin1msg = ChatColor.GREEN+twin2.getName()+getDirection(twin1,twin2)+ChatColor.AQUA+"("+distance+"m)";
        String twin2msg = ChatColor.GREEN+twin1.getName()+getDirection(twin2,twin1)+ChatColor.AQUA+"("+distance+"m)";
        twin1.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin1msg));
        twin2.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(twin2msg));

        /*twin1.setCompassTarget(twin2.getLocation());
        twin2.setCompassTarget(twin1.getLocation());*/
    }

    public String getDirection(Player twin1, Player twin2) {
        Vector originPos = twin1.getLocation().toVector();
        Vector targetPos = twin2.getLocation().toVector();
        Vector direction = (targetPos.subtract(originPos)).normalize();

        System.out.println("direction");
        return " ↑ ";
    }
}
