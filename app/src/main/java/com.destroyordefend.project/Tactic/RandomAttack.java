package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.PointComparator;
import com.destroyordefend.project.Tactic.Comparators.AriDefenceComparator;
import com.destroyordefend.project.Unit.Unit;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class RandomAttack implements Tactic {

    @Override
    public void SortMap( Unit unit) {
        System.out.println(">>>>>>>>>>>>>>>>>>>\n\n");
        System.out.println("name: " + unit.getName()+ " ss: " + unit.getTreeSetUnit().size());

        TreeSet<Unit> temp = Tactic.updateRange(unit);
        ArrayList<String> types  ;
        types = unit.getSortMap();

        TreeSet<Unit> filtered = new TreeSet<>((v1,v2)->1);
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
        return "RandomAttack";
    }
}
