package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class AriDefenceComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {
        int o1Range = o1.getValues().getRange();
        int o2Range = o1.getValues().getRange();
        return o1Range == o2Range ? 1 : -1;
    }
}
