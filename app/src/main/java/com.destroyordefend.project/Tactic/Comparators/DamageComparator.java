package com.destroyordefend.project.Tactic.Comparators;

import com.destroyordefend.project.Unit.Unit;

import java.util.Comparator;

public class DamageComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        if (o1.getValues().getDamage() == o2.getValues().getDamage())
            return 1;
        return o2.getValues().getDamage() - o1.getValues().getDamage();
    }
}
