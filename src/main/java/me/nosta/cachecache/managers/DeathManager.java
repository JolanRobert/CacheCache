package me.nosta.cachecache.managers;

import me.nosta.cachecache.Main;
import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.enums.TeamEnum;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeathManager {

    private static DeathManager instance;

    public static DeathManager getInstance() {
        if (instance == null) instance = new DeathManager();
        return instance;
    }

    public void transformToHunter(PlayerRole hunter, PlayerRole survivor) {
        angeHandling(survivor);
        pretreHandling(survivor);

        for (PlayerRole pr : RoleManager.getInstance().getPlayerRoles()) {
            if (pr.getTeam() == TeamEnum.CHASSEUR) {
                pr.getPlayer().sendMessage(ChatColor.DARK_RED+"(Chasseur) "+ChatColor.RED+pr.getPlayer().getName()+" a tué "+ChatColor.GREEN+survivor.getPlayer().getName()+" !");
            }
        }

        survivor.getPlayer().sendMessage(ChatColor.RED+"Vous êtes mort ! Vous rejoignez l'équipe des Chasseurs.");

        survivor.setTeam(TeamEnum.CHASSEUR);
        survivor.setRole(RoleEnum.CHASSEUR);
        survivor.clearAll();
        survivor.giveHunterKnife();

        survivor.setRespawnable(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> survivor.setRespawnable(false),10*20);
    }

    public void transformToHunter(PlayerRole hunter, PlayerRole twin1, PlayerRole twin2) {
        transformToHunter(hunter,twin1);
        transformToHunter(hunter,twin2);
    }

    public void angeHandling(PlayerRole dead) {
        PlayerRole ange = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.ANGE);
        if (ange == null) return;
        if (ange.getAdmirer() != dead) return;
        ange.setInvincible(false);
        ange.getPlayer().sendMessage(ChatColor.DARK_GREEN+"(Ange) "+ChatColor.GREEN+"Votre adorateur est mort, vous perdez votre invincibilité et possédez désormais l'effet Glowing permanent !");
        ange.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,Integer.MAX_VALUE,0,false,false));
    }

    public void pretreHandling(PlayerRole dead) {
        PlayerRole pretre = RoleManager.getInstance().getPlayerRoleWithRole(RoleEnum.PRETRE);
        if (pretre == null) return;
        if (pretre.getPowerUse() == 0) return;

        TextComponent msg = new TextComponent(ChatColor.DARK_GREEN+"(Prêtre) "+ChatColor.GREEN+dead.getPlayer().getName()+" est mort ! Cliquer sur ce message pour le ressusciter.");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/role respawn "+dead.getPlayer().getName()));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN+"Cliquer pour ressusciter "+dead.getPlayer().getName())));

        pretre.getPlayer().spigot().sendMessage(msg);
    }

    //Check if attack has correct item to perform actions
    public boolean hasCorrectItem(Player player, Material material, String itemName) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType() == material) {
            if (mainHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        if (offHand.getType() == material) {
            if (offHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        return false;
    }
}
