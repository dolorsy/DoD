package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class DamageComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        if (o1.getDamage() == o2.getDamage())
            return 1;
        return o2.getDamage() - o1.getDamage();
    }
}
