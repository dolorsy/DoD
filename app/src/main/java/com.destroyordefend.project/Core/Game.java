package com.destroyordefend.project.Core;

import android.util.Log;

import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Movement.ToTarget;
import com.destroyordefend.project.Tactic.LowestHealthAttack;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import com.destroyordefend.project.utility.UpdateRangeAsyncTask;

import java.util.TreeSet;

import static com.destroyordefend.project.Main.p;


enum States {
    NotRunning,
    Running,
    Paused,
    AttackerWin,
    DefenderWin,
}

public class Game {
    public static Game game;
    static Unit unit;
    TreeSet<Unit> allUnits;
    TreeSet<Terrain> terrains;
    States GameState = States.NotRunning;
    Shop shop = new Shop();
    Team attackers;
    Team defenders;
    int attackerNumber, defenderNumber;
    int initPoints = 10000;
    GameTimer gameTimer;

    private Game() {
        terrains = new TreeSet<>(new Terrain.TerrainComparator());
        allUnits = new TreeSet<>(new PointComparator());
        attackers = new Team();
        defenders = new Team();
    }

    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public void StartAnewGame() {
        //Todo:: terrain need to add terrains
        gameTimer = new GameTimer(30);
        //Todo:Here We Should get the number of Players
        attackers.addPlayer(new Player(initPoints, Player.TeamRole.Attacker, "attacker"));
        defenders.addPlayer(new Player(initPoints, Player.TeamRole.Defender, "defender"));
        unit = new Unit(5, 5, 2, "TT", 5, 5, 5, 50);
        unit.setTreeSetUnit(new TreeSet<>(new PointComparator()));
        p(unit.getTreeSetUnit().toString());
        attackers.getTeamPlayers().get(0).addArmy(new Unit(unit)
                .AcceptTactic(new LowestHealthAttack()).AcceptMovement(new FixedPosition()));
        defenders.getTeamPlayers().get(0).addArmy(new Unit(unit).AcceptTactic(new RandomAttack())
                .AcceptMovement(new ToTarget(attackers.getTeamPlayers().get(0).getArmy().first())));

        UpdateUnits();
        //this.StartShoppingStage();
        this.StartPlacementStage();

        this.StartBattle();
        //  UpdateUnits();
        /**
         * The Following Code We Will Use Later
         *
         * this.CreatePlayersStage();
         * this.StartShoppingStage();
         * **/

    }

    public void addPlayer(Player p) {
        Log.i("TAG", "addPlayer: " + p.getRole());
        (p.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders)
                .addPlayer(p);
    }

    public TreeSet<Terrain> getTerrains() {
        return terrains;
    }

    public Unit getBase() {
        //Todo: return base
        return null;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public boolean oneMore() {
        return attackerNumber + defenderNumber - 1 ==
                attackers.getTeamPlayers().size() +
                        defenders.getTeamPlayers().size();
    }

    public boolean fullAttackers() {
        return attackerNumber == attackers.getTeamPlayers().size();
    }

    public boolean fullDefender() {
        return defenders.getTeamPlayers().size() == defenderNumber;
    }

    private void StartBattle() {
        gameTimer.start();

        p("StartBattel");
        p(String.valueOf(allUnits.size()));
        for (Unit unit : allUnits) {
            unit.print();
            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(unit::UpdateRange);
            //Todo:Main method add to it Async Task

        }
    }


    public void UpdateUnits() {
        //this method to Update AllUnits
        allUnits = new TreeSet<>(new PointComparator());

        for (Player player : attackers.getTeamPlayers()) {
            for (Unit unit : player.getArmy()) {
                unit.setId(7);
                unit.setHealth(10);
                unit.setRole(player.role.name());
                allUnits.add(unit);
            }
        }
        for (Player player : defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        p(String.valueOf(allUnits.size()));
    }

    public States getGameState() {
        return GameState;
    }

    public void setGameState(States gameState) {
        GameState = gameState;
    }

    public void setPlayersNumbers(int attackerNumber, int defenderNumber) {
        if (this.attackerNumber > 0 || this.defenderNumber > 0)
            return;
        if (attackerNumber <= 0 || defenderNumber <= 0)
            System.exit(10);
        this.attackerNumber = attackerNumber;
        this.defenderNumber = defenderNumber;
    }

    public TreeSet<Unit> getAllUnits() {
        return this.allUnits;
    }

    public Shop getShop() {
        return shop;
    }

    protected void AddAnewPlayer() {
        //Todo:  we should Get The Name of Player and the Role (Attacker / Defender) and set In the following constructor

    }

    protected void StartPlacementStage() {
        //Todo: x and y is Temporary Here
        int x = 10, y = 10, r = 5;
        for (Player p : defenders.getTeamPlayers()) {
            for (Unit u : p.getArmy()) {
                p("PS " + u.getId());
                Movement.canSetUnitPlace(new Point(x, y), u);
                x += 100;
                y += 100;
            }
        }
        for (Player p : attackers.getTeamPlayers()) {
            for (Unit u : p.getArmy()) {
                Movement.canSetUnitPlace(new Point(x, y), u);
                x += 10;
                y += 10;
            }
        }

    }

    protected void CreateTeamsStage() {

        /**
         * for the number of players we will call AddPlayer
         * inside the loop the Statement will be
         * AddAnewPlayer();
         */


    }

    protected void StartShoppingStage() {

        for (Player player : attackers.getTeamPlayers()) {
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }
        for (Player player : defenders.getTeamPlayers()) {
            player.CreateArmy();
            allUnits.addAll(player.getArmy());
        }

    }

    public void UpdateState() {
        boolean stillInGame = false;

        //Todo: should be a variable in Team count how many unit in all team players
        for (Player player : attackers.getTeamPlayers()) {
            if (player.getArmy().size() != 0) {
                stillInGame = true;
            }
            if (!stillInGame) {
                setGameState(States.DefenderWin);
                return;
            }
        }
        for (Unit unit : allUnits) {
            if (unit.getType().equals("Base") && unit.getHealth() == 0) {
                setGameState(States.AttackerWin);
                return;
            }
        }
        if (gameTimer.onEnd()) {
            setGameState(States.DefenderWin);
        }
    }

    public void DeleteUnit(Unit unit) {
        p("Removed id " + unit.getId());
        p(unit.getRole());
        if (unit.getRole().equals("Attacker")) {
            for (Player player : attackers.getTeamPlayers()) {
                if (player.getId().equals(unit.getPlayerId())) {
                    player.getArmy().remove(unit);
                }
            }
        } else {
            for (Player player : defenders.getTeamPlayers()) {
                if (player.getId().equals(unit.getPlayerId())) {
                    player.getArmy().remove(unit);
                }
            }
        }
        this.allUnits.remove(unit);

    }


}
