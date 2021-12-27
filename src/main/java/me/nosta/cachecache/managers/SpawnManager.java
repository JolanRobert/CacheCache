package me.nosta.cachecache.managers;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.GameState;
import me.nosta.cachecache.enums.RoleEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Random;

public class SpawnManager {

    private static SpawnManager instance;

    public static SpawnManager getInstance() {
        if (instance == null) instance = new SpawnManager();
        return instance;
    }

    private Location hunterSpawn;
    private List<Location> survivorSpawns;

    public SpawnManager() {
        initSpawns();
    }

    public void initSpawns() {
        hunterSpawn = new Location(hunterSpawn.getWorld(), 266,70,207,0,0);

        survivorSpawns.add(new Location(Bukkit.getWorld("world"),180,65,96.5,90,0)); //Abris Bus
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),167,85,276,180,0)); //Apiculteur
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),143,65,214,0,0)); //Bac à sable
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),151,71,251,-90,0)); //Bar clandestin
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),217,77,169,90,0)); //Bibliothèque
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),132,66,256,180,0)); //Biocoop
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),197,71,83,180,0)); //Breton
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),182,72,230,90,0)); //Cinéma
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),162,73.5,74,180,0)); //Discothèque
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),83,65,176,0,0)); //École
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),198,66,344,-90,0)); //Église
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),266,75,155,180,0)); //Garage
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),257,66,105,0,0)); //Gare
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),81,79,237,0,0)); //Hélicoptère
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),200,66,95,-90,0)); //Hôtel
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),206,72,43,0,0)); //Italien
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),187.25,78,154,0,0)); //Japonais Top
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),104,66,226,-90,0)); //Kiosque
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),224,82,267,180,0)); //Mairie
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),187.5,66,180,0,0)); //Marché
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),155,76,106,0,0)); //Muscu Top
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),255.5,73,52,180,0)); //Parking
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),270,67,316,90,0)); //Petit parc en bas
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),95,68,149,180,0)); //Piscine
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),90,68,110,0,0)); //Piscine 2
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),334,69,161,180,0)); //Quick Market
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),236,73,136,0,0)); //Skatepark
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),220,79,139,0,0)); //Thaïlandais
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),182,65,252,-90,0)); //Tour
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),368,77,101,-90,0)); //Train 1
        survivorSpawns.add(new Location(Bukkit.getWorld("world"),241,77,109,90,0)); //Train 2
    }

    public void teleportPlayer(PlayerRole playerRole) {
        if (playerRole.getRole() == RoleEnum.CHASSEUR) playerRole.getPlayer().teleport(hunterSpawn);
        else {
            Random rdm = new Random();
            Location rdmLocation = survivorSpawns.get(rdm.nextInt(survivorSpawns.size()));
            playerRole.getPlayer().teleport(rdmLocation);
        }
    }
}
