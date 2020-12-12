package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.util.TreeSet;

public interface Tactic {
    static TreeSet<Unit> updateRange(Unit t){

        TreeSet<Unit> temp = new TreeSet<>((o1,o2) -> 1);

        for(Unit unit: Game.getGame().getAllUnits()){
            if(isInRange(t,unit) && !t.equals(unit))
                temp.add(unit);
        }
        t.setTreeSetUnit(temp);
        /*
        Unit tempUnit = t.getNeighbourUnit("left");
        while( tempUnit != null && isInRange(t,tempUnit) ){
            if(t.getRole().equals(tempUnit.getRole())){
                tempUnit = tempUnit.getNeighbourUnit("left");
                continue;
            }
          //  System.out.println("in range of "  + t.getId() +  "\t" + t.getName()+"  "   +  t.getRole() + " unit: " + tempUnit.getName()  + "   "  + tempUnit.getRole());
            temp.add(tempUnit);
            System.out.println("not filtered ww : " + tempUnit.getName() + "  " + tempUnit.getId());

            tempUnit = tempUnit.getNeighbourUnit("left");
        }
        tempUnit = t.getNeighbourUnit("right");
        while( tempUnit != null && isInRange(t,tempUnit) ){
            if(t.getRole().equals(tempUnit.getRole())){
                tempUnit = tempUnit.getNeighbourUnit("right");
                continue;
            }
            temp.add(tempUnit);
            System.out.println("not filtered ww : " + tempUnit.getName() + "  " + tempUnit.getId());

            tempUnit = tempUnit.getNeighbourUnit("right");
        }

        /*if(t.getName().equalsIgnoreCase("Patriot Missile")){
            TreeSet<Unit> p = new TreeSet<>(new AriDefenceComparator());
            p.addAll(temp);
            temp = p;

        }else{
            TreeSet<Unit> p = new TreeSet<>(new PointComparator());
            p.addAll(temp);
            temp = p;
        }*/

        return temp;
        /*
        //Todo: the up change in total
        p("Update Range :" + t.getId() + " all : " + t.getTreeSetUnit().size());
        TreeSet<Unit> unitTreeSet = new TreeSet<>(game.getAllUnits());
        unitTreeSet.removeIf(
                unit -> unit.getLeft() > t.getRight()+t.getRange() ||
                        unit.getRight() < t.getLeft() - t.getRange() || t.getPosition().equals(unit.getPosition())
        );
        unitTreeSet.removeIf(
                unit -> unit.getDown() > t.getUp()+t.getRange() ||
                        unit.getUp() < t.getDown()-t.getRange()
        );
        t.setTreeSetUnit(unitTreeSet);
        */
    }
    static boolean isInRange(Unit radar,Unit target){

/*
        System.out.println("Bitch" + radar.getPosition().equals(target.getPosition()));
        System.out.println(target.getLeft());
        System.out.println(target.getRight());
        System.out.println(target.getDown());
        System.out.println(target.getUp());

        System.out.println(radar.getRight() + radar.getRange());
        System.out.println(radar.getLeft() - radar.getRange());
        System.out.println(radar.getUp() - radar.getRange());
        System.out.println(radar.getDown() + radar.getRange());
*/

        return !(target != null && ((target.getLeft() > radar.getRight() + radar.getRange() ||
                target.getRight() < radar.getLeft() - radar.getRange() || radar.getPosition().equals(target.getPosition())) ||
                target.getDown() < radar.getUp() - radar.getRange() ||
                        target.getUp() > radar.getDown() + radar.getRange()));
    }

    static Tactic getSuitableTactic(String tactic) {
    switch (tactic){
        case "LowestHealthAttack" :
            return new LowestHealthAttack();
        case "HighestDamageAttack":
            return new HighestDamageAttack();
        case"PriorityAttack":
            return new PriorityAttack();
        default:
            return new RandomAttack();
    }
    }

    void  SortMap (Unit unit);
}
