package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Tactic.Comparators.HealthComparator;
import com.destroyordefend.project.Unit.Unit;

import java.util.List;
import java.util.TreeSet;

public class LowestHealthAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {
        System.out.println(">>>>>>>>>>>>>>>>>>>\n\n");
        System.out.println("name: " + unit.getName()+ " ss: " + unit.getTreeSetUnit().size());
        TreeSet<Unit> temp = Tactic.updateRange(unit);
        List<String> types = unit.getSortMap();

        HealthComparator healthComparator = new HealthComparator();
        TreeSet<Unit> filtered = new TreeSet<Unit>(healthComparator);
        for (String type : types) {
            for (Unit u : temp) {
                if (u.getType().equals(type)) {
                    System.out.println("added in: " + u.getName()  + " " + u.getId());

                    filtered.add(u);
                }
            }
        }

        System.out.println("Not Filtered id:  " + unit.getId() + "  " + unit.getTreeSetUnit().size());
        System.out.println("Filtered " + filtered.size()+ " " + unit.getId());
        unit.setTreeSetUnit(filtered);
        System.out.println(">>>>>>>>>>>>>>>>>>>\n\n");

    }

    @Override
    public String toString() {
        return "LowestHealth";
    }
}
