package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Player;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Tactic;
import com.destroyordefend.project.utility.IdGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;

public class Unit implements TacticAble, MovementAble, Barrier, UnitSetHelper {

    private final int id;
    private Movement movement;
    private TreeSet<Unit> treeSetUnit = new TreeSet<>(new PointComparator());
    private Point point = new Point();
    private Player.TeamRole role;
    private UnitValues values;
    private Tactic tactic;
    private Damaging damaging;
    private List<String> SortMap;
    private HashMap<String, Unit> leftAndRight = new HashMap<>();


    public Unit() {
       id = IdGenerator.generate(this);
    }


    //Copy Constructor
    public Unit(Unit unit) {
        this();
        this.treeSetUnit.addAll(unit.treeSetUnit);
        this.movement = unit.movement;
        this.point = new Point(unit.point);
        this.role = unit.getRole();
        this.values = new UnitValues(unit.values);
    }

    public Unit(UnitValues unitValues) {
        this();
        this.values = new UnitValues(unitValues);
    }

    public List<String> getSortMap() {
        return SortMap;
    }

    public Damaging getDamaging() {
        if (damaging == null)
            damaging = new Damaging();
        return damaging;
    }

    public Tactic getTactic() {
        return tactic;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id + "\n" +
                ", movement=" + movement + "\n" +
                ", treeSetUnit=" + treeSetUnit + "\n" +
                ", point=" + point + "\n" +
                ", role=" + role + "\n" +
                ", values=" + values + "\n" +
                ", tactic=" + tactic + "\n" +
                ", damaging=" + damaging + "\n" +
                ", SortMap=" + SortMap + "\n" +
                '}';
    }

    public void setTreeSetUnit(TreeSet<Unit> treeSetUnit) {
        this.treeSetUnit = treeSetUnit;
    }

    public void setDamage(int damage){
        this.values.damage = damage;
    }

    public void setRole(Player.TeamRole role) {
        this.role = role;
    }

    public void setPosition(Point point) {
        this.point.setPoint(point);
    }

    public void setValues(UnitValues values) {
        this.values = new UnitValues(values);
    }

    public Movement getMovement() {
        return movement;
    }

    public void Move() {
        System.out.println("Current "  + getCurrentSpeed() );
        System.out.println("x,y : " + getPosition());
        for(int i =0 ;i<values.currentSpeed;i++) {
            Point p = this.movement.GetNextPoint(this);
            if(p.equals(getPosition())){
                continue;
            }
            boolean f = Movement.setNext(this,p);

           if(f){
               values.currentSpeed = values.speed / 2;

           }else{
               values.currentSpeed = values.speed ;

           }


           //Todo::The Following Code will used if we have many speed factors
  /*
            Barrier factor = Movement.canSetUnitPlace(p, this);
            if(factor != null)
            System.out.println("Terrain :" + factor.getName());
            if (factor != null && factor.getClass().getName().equals(Terrain.class.getName())) {
                Terrain terrain = (Terrain) factor;
                if (terrain.getSpeedFactory() != 0) {
                    //TODO: For loop like Current speed to push invokable method in UpdateMapAsyncTask
                    values.currentSpeed = values.speed / terrain.getSpeedFactory();
                }
            }else{
                values.currentSpeed = values.speed ;
                continue;
            }*/
            this.setPosition(p);
            this.updateLeftAndRight();
        }
        System.out.println("x,y : " + getPosition());

        System.out.println("Devider\n\n");
    }

    public TreeSet<Unit> getTreeSetUnit() {
        return treeSetUnit;
    }

    public Player.TeamRole getRole() {
        return role;
    }

    public UnitValues getValues() {
        return values;
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRadius() {
        return values.radius;
    }

    //Method TacticAble Class
    @Override
    public Unit AcceptTactic(Tactic tactic) {
        p("Accept Tactic");
        this.tactic = tactic;
        return this;
    }

    //Method MovementAble Class
    @Override
    public Unit AcceptMovement(Movement movement) {
        this.movement = movement;
        return this;
    }

    @Override
    public boolean isAlive() {
        return values.health > 0;
    }

    public void setSpeed(int speed) {
        this.values.speed = speed;
    }

    public Unit giveValues(UnitValues unitValues) {
        this.values = new UnitValues(unitValues);
        return this;
    }

    public void setHealth(int health) {
        if(values == null)
            values = new UnitValues();
        this.values.health = health;
    }

    public double getShot_speed() {
        return this.values.shot_speed;
    }

    //Get
    public int getSpeed() {
        return this.values.speed;
    }

    public int getCurrentSpeed() {
        return this.values.currentSpeed;
    }

    public void setShot_speed(int shot_speed) {
        this.values.shot_speed = shot_speed;
    }

    void onDestroy() {
        game.DeleteUnit(this);
        game.UpdateState();
    }

    @Override
    public Unit getNeighbourUnit(String side){return leftAndRight.get(side);}
    @Override
    public void setNeighbourUnit(String side,Unit unit) {
        leftAndRight.put(side, unit);
    }

    @Override
    public void updateLeftAndRight() {
        //Todo: need to check;
        if (needSwapWithLeft()) {
            swapWithLeft();
        } else if (needSwapWithRight()) {
            swapWithRight();
        }
    }

    @Override
    public boolean needSwapWithLeft() {
        //Todo: need to check;

        return getNeighbourUnit("left") != null &&(( getLeft() == getNeighbourUnit("left").getRight() && getDown() < getNeighbourUnit("left").getUp()) ||
                this.getLeft() < getNeighbourUnit("left").getRight());
    }

    @Override
    public boolean needSwapWithRight() {
        //Todo: need to check;

        return getNeighbourUnit("right") != null &&( (getRight() == getNeighbourUnit("right").getLeft() && getUp() < getNeighbourUnit("right").getDown()) ||
                getRight() > getNeighbourUnit("right").getLeft());
    }

    @Override
    public void swapWithLeft() {
        //Todo: need to check;

        Unit temp = getNeighbourUnit("left");
        setNeighbourUnit("left",getNeighbourUnit("left").getNeighbourUnit("left"));
        temp.setNeighbourUnit("right",getNeighbourUnit("right"));
        setNeighbourUnit("right",temp);
        temp.setNeighbourUnit("left",this);
    }

    @Override
    public void swapWithRight() {
        //Todo: need to check;
        Unit temp = getNeighbourUnit("right");
        setNeighbourUnit("right",getNeighbourUnit("right").getNeighbourUnit("right"));
        temp.setNeighbourUnit("left",getNeighbourUnit("left"));
        setNeighbourUnit("left",temp);
        temp.setNeighbourUnit("right",this);
    }

    public void UpdateRange() {
        Tactic.updateRange(this);
        this.tactic.SortMap(this);
        p("Update Range id: " + id + "new Range " + treeSetUnit);
        //Todo::Make sure the call by referance
    }
    @Override
    public String getName() {
        return values.name;
    }

    public static class UnitValues {
        String name;
        int health;
        double armor;
        int damage;
        int range;
        double shot_speed;
        int radius;
        int speed;
        List<String> sortMap;
        int price;
        int currentSpeed;//11

        public UnitValues(String name, int health, double armor, int damage, int range, double shot_speed, int radius, int speed, List<String> SortMap, int price) {
            this.name = name;
            this.health = health;
            this.armor = armor;
            this.damage = damage;
            this.range = range;
            this.shot_speed = shot_speed;
            this.radius = radius;
            this.speed = speed;
            this.sortMap = SortMap;
            this.price = price;
            currentSpeed = speed;
        }

        public  UnitValues(){

        }
        public UnitValues(UnitValues unitValues) {
            this(
                    unitValues.name,
                    unitValues.health,
                    unitValues.armor,
                    unitValues.damage,
                    unitValues.range,
                    unitValues.shot_speed,
                    unitValues.radius,
                    unitValues.speed,
                    unitValues.sortMap,
                    unitValues.price);
        }

        public UnitValues(JSONObject unit) {
            name = (String) unit.get("name");
            health = Integer.parseInt((String) unit.get("health"));
            armor = Double.parseDouble((String) unit.get("armor"));
            damage =Integer.parseInt ((String) unit.get("damage"));
            range = Integer.parseInt((String) unit.get("range"));
            shot_speed = Double.parseDouble((String) unit.get("shot_speed"));
            radius = Integer.parseInt((String) unit.get("radius"));
            speed = Integer.parseInt((String) unit.get("speed"));
            price = Integer.parseInt((String) unit.get("price"));
            sortMap = new ArrayList<>();
            JSONArray sMap = (JSONArray) unit.get("SortMap");
            for (Object c : sMap) {
                String s = c.toString();
                sortMap.add(s);
            }
        }
        public boolean is(String name){
            return this.name.equalsIgnoreCase(name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
        public int getPrice() {
            return price;
        }

        public int getHealth() {
            return health;
        }

        public double getArmor() {
            return armor;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }

        @Override
        public String toString() {
            return "UnitValues{" +
                    "name='" + name + '\'' +
                    ", health=" + health +
                    ", armor=" + armor +
                    ", damage=" + damage +
                    ", range=" + range +
                    ", shot_speed=" + shot_speed +
                    ", radius=" + radius +
                    ", speed=" + speed +
                    ", sortMap=" + sortMap +
                    ", price=" + price +
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
        public void DoDamage() {
            treeSetUnit.first().getDamaging().AcceptDamage(this.getDamage());
        }
        @Override
        public int getDamage() {
            if (canShot == 0) {
                p("Do damage id: " + id);
                canShot = 4;
                return values.damage;
            } else {
                decrease();
                return 0;
            }
        }

        @Override
        public void AcceptDamage(int damage) {
            //Todo:: use Armore Here
            int valueresulte = values.health - (int) (damage* getValues().armor);
            if ((valueresulte) <= 0) {
                values.health = 0;
            } else {
                values.health -= valueresulte;
            }
            p("Accept Damage id: " + id + "new Health: " + values.health);
        }
        @Override
        public void decrease() {
            canShot -= 1;
        }
    }
}