package project.Tactic.Comparators;


import java.util.Comparator;

import project.Unit.Unit;

public class HealthComparator implements Comparator<Unit> {

    @Override
    public int compare(Unit o1, Unit o2) {

        if (o1.getHealth() == o2.getHealth())
            return 1;
        return o1.getHealth() - o2.getHealth();
    }
}
