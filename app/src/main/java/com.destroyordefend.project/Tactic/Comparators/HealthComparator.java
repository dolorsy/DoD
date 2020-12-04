package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class HealthComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        if (o1.getValues().getHealth() == o2.getValues().getHealth())
            return 1;
        return o1.getValues().getHealth() - o2.getValues().getHealth();
    }
}
