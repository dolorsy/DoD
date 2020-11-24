package project.Tactic;


import java.util.TreeSet;

import project.Tactic.Comparators.DamageComparator;
import project.Unit.Unit;

public class HighestDamageAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {

        DamageComparator damageComparator = new DamageComparator();
        TreeSet<Unit> temp = new TreeSet<Unit>(damageComparator);
        temp = unit.getTreeSetUnit();
        unit.setTreeSetUnit(temp);


    }
}
