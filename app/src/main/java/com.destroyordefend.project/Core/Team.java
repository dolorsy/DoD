package com.destroyordefend.project.Core;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Player> teamPlayers;
    public final int id ;
    public Team() {
        id = IdGenerator.generate(this);
        teamPlayers = new ArrayList<>();
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public void addPlayer(Player player) {
        teamPlayers.add(player);
    }

    public void removeUnit(Unit unit) {
        for (Player p : teamPlayers) {
            p.getArmy().removeIf(unit1 -> unit.getId() == unit.getId());
        }
    }

    public boolean isAlive() {
        for (Player player : teamPlayers)
            if (!player.getArmy().isEmpty())
                return true;
        return false;

    }
}
