package com.destroyordefend.project.Core;

import com.destroyordefend.project.Movement.FixedPatrol;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.destroyordefend.project.utility.UpdateMapAsyncTask;
import com.destroyordefend.project.utility.UpdateRangeAsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
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
    private Unit base;
    private TreeSet<Unit> allUnits = new TreeSet<>(new PointComparator());
    private TreeSet<Terrain> terrains = new TreeSet<>(new PointComparator());
    private States GameState = States.NotRunning;
    private Team attackers, defenders;
    int attackerNumber, defenderNumber;
    GameTimer gameTimer;

    private Game() {
        attackers = new Team();
        defenders = new Team();

        base = new Unit(Shop.getInstance().getBaseValues());
        settingUnit(new Point(200, 200), base, 1);
        base.setTreeSetUnit(new TreeSet<>(new PointComparator()));
        allUnits.add(base);
    }

    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public void StartAnewGame() {
/*
Todo:: terrain need to add terrains
        Terrain t = new Terrain(new Point(30,100),2,"river");
        terrains.add(t );*/
        gameTimer = new GameTimer(10);
        // CreateTeamsStage();
        // autoInitGame();
        UpdateUnits();
        this.StartBattle();
    }

    public void addPlayer(Player p) {
        (p.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders)
                .addPlayer(p);
    }

    public boolean settingUnit(Point p, Unit unit, int count) {
        Stack<Unit> addedUnits = new Stack<>();

        int l = 2 * unit.getRadius();

        List<Point> sides = new ArrayList<>(Arrays.asList(
                new Point(p.getX() - l, p.getY()), new Point(p.getX(), p.getY() - l),
                new Point(p.getX() + l, p.getY()), new Point(p.getX(), p.getY() + l)));
        List<Point> addedPoints = new ArrayList<>(Arrays.asList(
                new Point(-l, 0), new Point(0, -l),
                new Point(+l, 0), new Point(0, +l)
        ));

        unit.setPosition(p);
        if (noSharedPoints(unit)) {
            count--;
            addedUnits.add(unit);
            if (count > 0)
                unit = new Unit(unit);
        }
        boolean b = true;
        while (b && count > 0) {
            b = false;
            for (int i = 0; i < 4 && count > 0; i++) {
                unit.setPosition(sides.get(i));
                if (noSharedPoints(unit)) {
                    count--;
                    //added to stack of unit to all them all letter
                    addedUnits.add(unit);
                    //update sides by adding x,y values that stored in addedPoints list
                    Point side = sides.get(i);
                    Point add = addedPoints.get(i);
                    side.setX(side.getX() + add.getX());
                    side.setY(side.getY() + add.getY());
                    //if b becomes true when the loop is finished=>> a unit got its place , else cannot
                    b = true;
                    if (count > 0)//that mean we need another copy to place
                        unit = new Unit(unit);
                }
            }
        }
        if (b)//can add
            allUnits.addAll(addedUnits);
        return b;
    }

    boolean noSharedPoints(Unit u) {
        Point current = u.getPosition();
        boolean result = true;
        int left = u.getLeft();
        int right = u.getRight();
        int up = u.getUp();
        int down = u.getDown();
        if (left < 0 || up < 0)
            return false;
        for (int i = left; i <= right; i++) {
            for (int j = up; j <= down; j++) {
                u.setPosition(new Point(i, j));
                if (allUnits.contains(u))
                    result = false;
            }
        }
        u.setPosition(current);
        return result;
    }

    public TreeSet<Terrain> getTerrains() {
        return terrains;
    }

    public Unit getBase() {
        return base;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    private void StartBattle() {
        gameTimer.start();
        p("Start Battle");
        p(String.valueOf(allUnits.size()));
        for (Unit unit : allUnits) {
            p(unit.toString());
            UpdateMapAsyncTask.addMethod(unit::Move);
            UpdateRangeAsyncTask.addMethod(unit::UpdateRange);
            //Todo:Main method add to it Async Task
        }
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

    public void UpdateUnits() {
        //this method to Update AllUnits
        allUnits = new TreeSet<>(new PointComparator());

        for (Player player : attackers.getTeamPlayers()) {
            for (Unit unit : player.getArmy()) {
                unit.setHealth(10);//todo : I think this should be removed
                unit.setRole(player.getRole());
                allUnits.add(unit);
            }
        }
        for (Player player : defenders.getTeamPlayers()) {
            allUnits.addAll(player.getArmy());
        }
        p(String.valueOf(allUnits.size()));
        setNavigationForUnit();
    }

    public void setNavigationForUnit() {
        /*Unit left, right, curr;
        curr = allUnits.first();
        left = null;
        right = allUnits.iterator().next();
        //Todo:need to check
        for (int i = 1; i < allUnits.size(); i++) {
            curr.setRightUnit(right);
            right = allUnits.iterator().next();
            left = curr;
            curr = allUnits.iterator().next();
            curr.setLeftUnit(left);
        }*/
        //TOdo :need to check
        Unit left, right = null, cur;
        Iterator<Unit> unitIterator = allUnits.iterator();
        left = null;
        cur = unitIterator.next();
        if (unitIterator.hasNext())
            right = unitIterator.next();
        while (unitIterator.hasNext()) {
            cur.setNeighbourUnit("left", left);
            cur.setNeighbourUnit("right", right);
            left = cur;
            cur = right;
            right = unitIterator.next();
        }
    }

    public void setGameState(States gameState) {
        GameState = gameState;
    }

    public States getGameState() {
        return GameState;
    }

    public TreeSet<Unit> getAllUnits() {
        return this.allUnits;
    }

    public void setPlayersNumbers(int attackerNumber, int defenderNumber) {
        if (this.attackerNumber > 0 || this.defenderNumber > 0)
            return;
        if (attackerNumber <= 0 || defenderNumber <= 0)
            System.exit(10);
        this.attackerNumber = attackerNumber;
        this.defenderNumber = defenderNumber;
    }

    public void CreateTeamsStage() {
        String path = "src\\com\\destroyordefend\\project\\Teams.json";
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(path));
            JSONArray jsonArray = (JSONArray) obj.get("Players");
            for (Object jsonObject : jsonArray) {
                JSONObject player = (JSONObject) jsonObject;
                Player p = new Player((int) player.get("Points")
                        , Player.TeamRole.valueOf((String) player.get("role"))
                        , (String) player.get("id"));
                addPlayer(p);
                System.out.println(p.getId() + "  " + p.getRole());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PlayerIterator temp = null;

    public void UpdateState() {
        if (!attackers.isAlive()) {
            setGameState(States.DefenderWin);
        } else if (!base.isAlive()) {
            setGameState(States.AttackerWin);
        } else if (gameTimer.onEnd()) {
            setGameState(States.DefenderWin);
        }
    }

    public void DeleteUnit(Unit unit) {
        p("Removed id " + unit.getId());
        p(unit.getRole().name());
        (unit.getRole().equals(Player.TeamRole.Attacker) ?
                attackers :
                defenders).removeUnit(unit);
        allUnits.remove(unit);
    }

    private void autoInitGame() {
        Unit defndUnit = new Unit();
        defndUnit.setRole(Player.TeamRole.Defender);
        defndUnit.setPosition(new Point(200, 23));
        defndUnit.setHealth(200);
        defndUnit.getValues().setName("Main Base");
        defndUnit.setShot_speed(0);
        defndUnit.setSpeed(0);
        defndUnit.AcceptMovement(new FixedPosition());
        defndUnit.AcceptTactic(new RandomAttack());
        Unit attackUnit = new Unit();

        Unit.UnitValues values = Shop.getInstance().getUnitByName("TeslaTank");
        attackUnit.setRole(Player.TeamRole.Attacker);
        attackUnit.setPosition(new Point(20, 20));
        attackUnit.setValues(values);

        attackUnit.AcceptMovement(new /*ToTarget(defndUnit)*/ FixedPatrol(80));
        attackUnit.AcceptTactic(new RandomAttack());
        Player attacker = new Player();
        attacker.addArmy(attackUnit);

        attackers.addPlayer(attacker);
    }

    public PlayerIterator playerIterator() {
        if (temp == null)
            return temp = new PlayerIterator();
        return temp;
    }

    class PlayerIterator implements Iterator<Player> {

        Iterator<Player> attack = attackers.getTeamPlayers().iterator(),
                defend = defenders.getTeamPlayers().iterator();
        Player.TeamRole role = Player.TeamRole.Attacker;

        @Override
        public boolean hasNext() {
            return defend.hasNext() || attack.hasNext();
        }

        @Override
        public Player next() {
            if (role == Player.TeamRole.Attacker) {
                role = Player.TeamRole.Defender;
                if (!defend.hasNext())
                    defend = defenders.getTeamPlayers().iterator();
                return defend.next();
            } else {
                role = Player.TeamRole.Attacker;
                if (!attack.hasNext())
                    attack = attackers.getTeamPlayers().iterator();
                return attack.next();
            }
        }
    }
}
