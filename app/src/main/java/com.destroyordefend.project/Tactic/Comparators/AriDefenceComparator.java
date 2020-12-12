package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class AriDefenceComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {
        if(o1.getName().equalsIgnoreCase("AirPlane") || o2.getName().equalsIgnoreCase("AirPlane"))
            return 0;
        int o1Range = o1.getValues().getRange();
        int o2Range = o2.getValues().getRange();
        return o1Range == o2Range ? 1 : -1;
    }
}
