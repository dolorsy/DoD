package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Map;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;

public interface Tactic {
    static void updateRange(Unit t){

        TreeSet<Unit> temp = new TreeSet<>(new PointComparator());
        Unit tempUnit = t.getNeighbourUnit("left");
        while(isInRange(t,tempUnit)){
            temp.add(tempUnit);
            tempUnit = tempUnit.getNeighbourUnit("left");
        }
        tempUnit = t.getNeighbourUnit("right");
        while(isInRange(t,tempUnit)){
            temp.add(tempUnit);
            tempUnit = tempUnit.getNeighbourUnit("right");
        }
        t.setTreeSetUnit(temp);
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
        return !(target.getLeft() > radar.getRight() + radar.getValues().getRange() ||
                target.getRight() < radar.getLeft() - radar.getValues().getRange() || radar.getPosition().equals(target.getPosition())) ||
                !(target.getDown() > radar.getUp() + radar.getValues().getRange() ||
                        target.getUp() < radar.getDown() - radar.getValues().getRange());
    }
    void  SortMap (Unit unit);
}
