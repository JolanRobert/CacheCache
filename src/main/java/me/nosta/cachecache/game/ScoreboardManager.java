package me.nosta.cachecache.game;

import dev.jcsoftware.jscoreboards.JPerPlayerMethodBasedScoreboard;
import dev.jcsoftware.jscoreboards.JScoreboardTeam;
import me.nosta.cachecache.elements.TeamEnum;
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

    private Scoreboard bukkitSB;

    private JPerPlayerMethodBasedScoreboard scoreboard;
    private JScoreboardTeam hunter, survivor;



    public ScoreboardManager() {
        init();
    }

    public void init() {
        bukkitSB = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

        scoreboard = new JPerPlayerMethodBasedScoreboard();
        Bukkit.getOnlinePlayers().forEach(player -> scoreboard.addPlayer(player));

        hunter = scoreboard.createTeam("Hunter","",ChatColor.RED);
        survivor = scoreboard.createTeam("Survivor","",ChatColor.GREEN);
        setTeamOptions(hunter);
        setTeamOptions(survivor);
    }

    public void setTeamOptions(JScoreboardTeam team) {
        Team bTeam = team.toBukkitTeam(bukkitSB);
        bTeam.setAllowFriendlyFire(false);
        bTeam.setCanSeeFriendlyInvisibles(true);
        bTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
    }

    public void joinTeam(TeamEnum team, Player player) {
        if (team == TeamEnum.HUNTER) {
            survivor.removePlayer(player);
            hunter.addPlayer(player);
        }
        else if (team == TeamEnum.SURVIVOR) {
            hunter.removePlayer(player);
            survivor.addPlayer(player);
        }
    }

    public void resetAll() {
        init();
    }
}
