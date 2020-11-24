package project.Unit;

import java.util.TreeSet;

import project.Core.Point;
import project.Movement.Movement;
import project.Tactic.Tactic;

import static project.Core.Game.game;


public class Unit extends Thread implements TacticAble, MovementAble {

    private final int attackSpeed = 4;
    private int id;
    private int radius;
    private int range;
    private String type;//type unit
    private Movement movement;
    private TreeSet<Unit> treeSetUnit;
    private Point point;
    private String role;
    private UnitValues values;
    private Tactic tactic;

    //Constructor 1
    public Unit(int id, int radius, int range, String type, int speed, int shot_speed, int damage, int health) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        values = new UnitValues(speed, shot_speed, damage, health);
        this.point = new Point(0, 0);

    }

    //Constructor 2
    public Unit(int id, int radius, int range, String type, UnitValues values, TreeSet<Unit> treeSetUnit) {
        this.id = id;
        this.radius = radius;
        this.range = range;
        this.type = type;
        this.values = values;
        this.treeSetUnit = treeSetUnit;
        this.point = new Point(0, 0);
    }


    //Copy Constructor
    public Unit(Unit unit) {
        this.id = unit.id;
        this.radius = unit.radius;
        this.range = unit.range;
        this.type = unit.type;
        this.movement = unit.movement;
        this.treeSetUnit = unit.treeSetUnit;
        this.point = unit.point;
        this.role = unit.getRole();
        this.values = unit.values;
    }

    public Tactic getTactic() {
        return tactic;
    }

    public void Run() {
        //Todo: Here We Should Implement Thread Behaviour if each Unit on Thread
    }

    //Set
    public void setId(int id) {
        this.id = id;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    //Get
    public int getUnitId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void Move() {
        Point p = this.movement.GetNextPoint(getPosition());
        int factor = Movement.SetUnitPlace(p, this);
        if (factor != 0) {
            //TODO: For loop like Current speed to push invokable method in UpdateMapAsyncTask
            values.currentSpeed = values.speed / factor;
            this.setPoint(p);
        }

    }

    public TreeSet<Unit> getTreeSetUnit() {
        return treeSetUnit;
    }

    public void setTreeSetUnit(TreeSet<Unit> treeSetUnit) {
        this.treeSetUnit = treeSetUnit;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UnitValues getValues() {
        return values;
    }

    public void setValues(UnitValues values) {
        this.values = values;
    }

    //Get Position Unit
    public Point getPosition() {
        return point;
    }

    //Method TacticAble Class
    @Override
    public TacticAble AcceptTactic(Tactic tactic) {

        this.tactic = tactic;
        return this;
    }

    //Method MovementAble Class
    @Override
    public MovementAble AcceptMovement(Movement movement) {
        return this;
    }

    public int getDamage() {
        return this.values.damage;
    }

    public void setDamage(int damage) {

        this.values.damage = damage;
    }

    public int getHealth() {
        return this.values.health;
    }

    public void setHealth(int health) {

        this.values.health = health;
    }

    //Get
    public int getSpeed() {

        return this.values.speed;
    }

    public void setSpeed(int speed) {

        this.values.speed = speed;
    }

    public int getCurrentSpeed() {
        return this.values.currentSpeed;
    }

    public int getShot_speed() {
        return this.values.shot_speed;
    }

    public void setShot_speed(int shot_speed) {

        this.values.shot_speed = shot_speed;
    }

    void onDestroy() {

        game.UpdateState();


    }

    public int getLeft() {
        return point.getX() - this.radius;
    }

    public int getRight() {
        return point.getX() + this.radius;
    }

    public int getUp() {
        return point.getY() + this.radius;
    }

    public int getDown() {
        return point.getY() - this.radius;
    }

    public void UpdateRange() {
        Tactic.updateRange(this);
        this.tactic.SortMap(this);
        //Todo::Make sure the call by referance
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", radius=" + radius +
                ", range=" + range +
                ", attackSpeed=" + attackSpeed +
                ", type='" + type + '\'' +
                ", movement=" + movement +
                ", treeSetUnit=" + treeSetUnit +
                ", point=" + point +
                ", role='" + role + '\'' +
                ", values=" + values.toString() +
                ", tactic=" + tactic +
                '}';
    }

    protected class UnitValues {

//Object for Singleton

        int speed;
        int shot_speed;
        int damage;
        int health;
        int currentSpeed;

        //constructor Empty
        private UnitValues() {
        }

        //Constructor UnitValues Class
        public UnitValues(int speed, int shot_speed, int damage, int health) {
            this.speed = speed;
            this.shot_speed = shot_speed;
            this.damage = damage;
            this.health = health;
            this.currentSpeed = speed;
        }

        @Override
        public String toString() {
            return "UnitValues{" +
                    "speed=" + speed +
                    ", shot_speed=" + shot_speed +
                    ", damage=" + damage +
                    ", health=" + health +
                    ", currentSpeed=" + currentSpeed +
                    '}';
        }
    }

    public class Damaging implements Damage {
        int canShot = 0;

        public int getCanShot() {
            return canShot;
        }

        @Override
        public int DoDamage() {
            if (canShot == 0) {
                canShot = 4;
                return values.damage;

            } else {
                decrease();
                return 0;
            }
        }

        @Override
        public void AcceptDamage(int damage) {

            if ((values.health - damage) <= 0) {
                values.health = 0;
            } else {
                values.health -= damage;
            }
        }

        @Override
        public void decrease() {
            canShot -= 1;
        }

    }
}

//Inner Class Unit Values


