package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Tactic;

import java.util.List;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;


public class Unit implements TacticAble, MovementAble, Barrier, UnitSetHelper {

    public final int id;
    Movement movement;
    TreeSet<Unit> treeSetUnit = new TreeSet<>(new PointComparator());
    Point point = new Point();
    String role;
    UnitValues values;
    Tactic tactic;
    Damaging damaging;
    String playerId;
    List<String> SortMap;

    //Copy Constructor
    public Unit(Unit unit) {
        this.treeSetUnit.addAll(unit.treeSetUnit);
        this.id = unit.id;
        this.movement = unit.movement;
        this.point = new Point(unit.point);
        this.role = unit.getRole();
        this.values = new UnitValues(unit.values);
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

    public Unit(int id, UnitValues values) {
        this.id = id;
        this.values = new UnitValues(values);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void print() {
        System.out.println(
                "id: " + id
//                +"\nrad: "  + radius
//                +"\nrang: " + range
//                +"\ntype: " + type
                        + "\nMovement: " + movement.toString()
                        + "\ninRange: " + treeSetUnit.toString()
                        + "\npoint: " + point.asString()
                        + "\nRole: " + role
                        + "\nvaleus: " + values.asString());
    }

    //Set

//    public void setRadius(int radius) {
//        this.radius = radius;
//    }
//
//    public void setRange(int range) {
//        this.range = range;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void setTreeSetUnit(TreeSet<Unit> treeSetUnit) {
        this.treeSetUnit = treeSetUnit;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setValues(UnitValues values) {
        this.values = values;
    }

    //Get
    public int geUnitId() {
        return id;
    }

//    @Override
//    public int getRadius() {
//        return radius;
//    }

    public int getRange() {
        return values.range;
    }

//    public String getType() {
//        return type;
//    }

    public void Move() {
        p("Move id: " + id + " x,y ");

        Point p = this.movement.GetNextPoint(this);

        p("Move id: " + id + " x,y " + p.asString());
        Barrier factor = Movement.canSetUnitPlace(p, this);
        if (factor.getClass().getName().equals(Terrain.class.getName())) {
            Terrain terrain = (Terrain) factor;
            if (terrain.getSpeedFactory() != 0) {
                //TODO: For loop like Current speed to push invokable method in UpdateMapAsyncTask
                values.currentSpeed = values.speed / terrain.getSpeedFactory();

            }
        }
        this.setPoint(p);
        this.updateLeftAndRight();
    }

    public TreeSet<Unit> getTreeSetUnit() {
        return treeSetUnit;
    }


    public String getRole() {
        return role;
    }

    public UnitValues getValues() {
        return values;
    }

    //Get Position Unit
    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getRadius() {
        return 0;
    }

    @Override
    public String getType() {
        return null;
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

    public int getDamage() {
        return this.values.damage;
    }

    public void setDamage(int damage) {


        this.values.damage = damage;
    }

    public int getHealth() {
        return this.values.health;
    }

    @Override
    public boolean isAlive() {
        return getHealth() > 0;
    }

    public void setSpeed(int speed) {

        this.values.speed = speed;
    }

    public Unit giveValues(UnitValues unitValues) {
        this.values = unitValues;
        return this;
    }

    public void setHealth(int health) {

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
    public Unit getLeftUnit() {
        return leftAndRight.get("left");
    }

    @Override
    public void setLeftUnit(Unit unit) {
        leftAndRight.put("left", unit);
    }

    @Override
    public Unit getRightUnit() {
        return leftAndRight.get("right");
    }

    @Override
    public void setRightUnit(Unit unit) {
        leftAndRight.put("right", unit);
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

        return (getLeft() == getLeftUnit().getRight() && getDown() < getLeftUnit().getUp()) ||
                this.getLeft() < getLeftUnit().getRight();
    }

    @Override
    public boolean needSwapWithRight() {
        //Todo: need to check;

        return (getRight() == getRightUnit().getLeft() && getUp() < getRightUnit().getDown()) ||
                getRight() > getRightUnit().getLeft();
    }

    @Override
    public void swapWithLeft() {
        //Todo: need to check;

        Unit temp = getLeftUnit();
        setLeftUnit(getLeftUnit().getLeftUnit());
        temp.setRightUnit(getRightUnit());
        setRightUnit(temp);
        temp.setLeftUnit(this);
    }

    @Override
    public void swapWithRight() {
        //Todo: need to check;

        Unit temp = getRightUnit();
        setRightUnit(getRightUnit().getRightUnit());
        temp.setLeftUnit(getLeftUnit());
        setLeftUnit(temp);
        temp.setRightUnit(this);
    }

    public void UpdateRange() {
        p("Update Range id: " + id);
        Tactic.updateRange(this);
        this.tactic.SortMap(this);
        //Todo::Make sure the call by referance
    }

    public static class UnitValues {
        String type;
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
        int currentSpeed;//12


        public UnitValues(String type, String name, int health, double armor, int damage, int range, double shot_speed, int radius, int speed, List<String> SortMap, int price) {
            this.type = type;
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

        public UnitValues(UnitValues unitValues) {
            this(unitValues.type,
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

        /* public UnitValues(JSONObject unit){
             org.json.simple.JSONObject unit1 = (org.json.simple.JSONObject) a;
              type = (String) unit1.get("type");

              name = (String) unit1.get("name");

              health = (String) unit1.get("health");

              armor = (String) unit1.get("armor");

              damage = (String) unit1.get("damage");

              range = (String) unit1.get("range");

              shot_speed = (String) unit1.get("shot_speed");

              radius = (String) unit1.get("radius");

              speed = (String) unit1.get("speed");

              sortMap = new ArrayList<>();

             JSONArray sMap = (JSONArray) unit1.get("SortMap");
             System.out.println(sMap);
             for (Object c : sMap) {
                 String s = c.toString();
                 ortMap.add(s);
             }
             String price = (String) unit1.get("price");

         }
 */
        public String asString() {
            return String.valueOf(
                    "Speed: " + speed
                            + "health" + health);

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public String getType() {
            return type;
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

        public double getShot_speed() {
            return shot_speed;
        }

        public int getRadius() {
            return radius;
        }

        public int getSpeed() {
            return speed;
        }

        public int getCurrentSpeed() {
            return currentSpeed;
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

            if ((values.health - damage) <= 0) {
                values.health = 0;
            } else {
                values.health -= damage;
            }
            p("Accept Damage id: " + id + "new Helth: " + values.health);
        }


        @Override
        public void decrease() {
            canShot -= 1;
        }

    }

}

//Inner Class Unit Values


