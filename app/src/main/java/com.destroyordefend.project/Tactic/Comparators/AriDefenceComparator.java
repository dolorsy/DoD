package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class AriDefenceComparator implements Comparator<Unit> {


    @Override
    public int compare(Unit o1, Unit o2) {

        int Range1 = o1.getRange();
        int Range2 = o1.getRange();

        if (Range1 > Range2 || Range1 < Range2)
            return -1;

        if (Range1 == Range2)
            return 1;

        return 0;
    }
}
