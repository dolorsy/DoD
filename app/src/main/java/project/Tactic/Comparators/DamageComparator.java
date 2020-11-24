package project.Tactic.Comparators;


import java.util.Comparator;

import project.Unit.Unit;

public class DamageComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        if (o1.getDamage() == o2.getDamage())
            return 1;
        return o2.getDamage() - o1.getDamage();
    }
}
