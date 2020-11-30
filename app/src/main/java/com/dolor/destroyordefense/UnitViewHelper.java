package com.dolor.destroyordefense;


import com.destroyordefend.project.Unit.Unit;

import java.util.TreeSet;


public class UnitViewHelper extends Unit {
    private int counter = 0;

    public UnitViewHelper(int id, int radius, int range, String type, int speed, int shot_speed, int damage, int health) {
        super(id, radius, range, type, speed, shot_speed, damage, health);
    }

    public UnitViewHelper(int id, int radius, int range, String type, Unit.UnitValues values, TreeSet<Unit> treeSetUnit) {
        super(id, radius, range, type, values, treeSetUnit);
    }

    public UnitViewHelper(Unit unit) {
        super(unit);
    }

    public int plusOne() {
        return ++counter;
    }

    public int minusOne() {
        return --counter;
    }

    public int getCounter() {
        return counter;
    }

}