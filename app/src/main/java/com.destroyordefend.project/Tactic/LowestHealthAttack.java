package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.HealthComparator;
import com.destroyordefend.project.Unit.Unit;

import java.util.List;
import java.util.TreeSet;

public class LowestHealthAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {

        List<String> types = unit.getSortMap();

        HealthComparator healthComparator = new HealthComparator();

        TreeSet<Unit> temp = new TreeSet<>(healthComparator);

        temp = unit.getTreeSetUnit();

        TreeSet<Unit> filtered = new TreeSet<Unit>(healthComparator);
        for (String type : types) {
            for (Unit u : temp) {
                if (u.getType().equals(type))
                    filtered.add(u);
            }
        }

        unit.setTreeSetUnit(filtered);


    }
}
