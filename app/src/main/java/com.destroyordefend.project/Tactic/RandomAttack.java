package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.AriDefenceComparator;
import com.destroyordefend.project.Unit.Unit;

import java.util.List;
import java.util.TreeSet;

public class RandomAttack implements Tactic {

    @Override
    public void SortMap( Unit unit) {

        List<String> types = unit.getSortMap();

        AriDefenceComparator ariDefenceComparator = new AriDefenceComparator();

        TreeSet<Unit> temp = unit.getTreeSetUnit();
        TreeSet<Unit> filtered = new TreeSet<Unit>(ariDefenceComparator);
        for (String type : types) {
            for (Unit u : temp) {
                if (u.getName().equals(type))
                    filtered.add(u);
            }
        }
        unit.setTreeSetUnit(filtered);
    }
}
