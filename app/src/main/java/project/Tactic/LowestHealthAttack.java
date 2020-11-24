package project.Tactic;


import java.util.TreeSet;

import project.Tactic.Comparators.HealthComparator;
import project.Unit.Unit;

public class LowestHealthAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {

        HealthComparator healthComparator = new HealthComparator();
        TreeSet<Unit> temp = new TreeSet<>(healthComparator);
        temp = unit.getTreeSetUnit();
        unit.setTreeSetUnit(temp);

    }
}
