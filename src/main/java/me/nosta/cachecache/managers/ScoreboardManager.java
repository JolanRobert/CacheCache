package me.nosta.cachecache.managers;

import me.nosta.cachecache.enums.TeamEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class ScoreboardManager {

    private static ScoreboardManager instance;

    public static ScoreboardManager getInstance() {
        if (instance == null) instance = new ScoreboardManager();
        return instance;
    }

    private Scoreboard customSB;

    private Team hunter, survivor;

    public ScoreboardManager() {
        customSB = Bukkit.getScoreboardManager().getNewScoreboard();
        createTeams();
    }

    public void createTeams() {
        if (customSB.getTeam("Hunter") == null) {
            hunter = customSB.registerNewTeam("Hunter");
            hunter.setColor(ChatColor.RED);
            setTeamOptions(hunter);
        }
        else hunter = customSB.getTeam("Hunter");

        if (customSB.getTeam("Survivor") == null) {
            survivor = customSB.registerNewTeam("Survivor");
            survivor.setColor(ChatColor.GREEN);
            setTeamOptions(survivor);
        }
        else survivor = customSB.getTeam("Survivor");

        //Remove everyone from teams
        for (Team team : customSB.getTeams()) {
            for (String player : team.getEntries()) {
                team.removeEntry(player);
            }
        }
    }

    public void setTeamOptions(Team team) {
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
    }

    public void joinTeam(TeamEnum team, Player player) {
        player.setScoreboard(customSB);
        if (team == TeamEnum.CHASSEUR) {
            survivor.removeEntry(player.getName());
            hunter.addEntry(player.getName());
        }
        else if (team == TeamEnum.SURVIVANT) {
            hunter.removeEntry(player.getName());
            survivor.addEntry(player.getName());
        }
    }

    public void createQuests() {

    }

    public void resetAll() {
        createTeams();
    }
}
