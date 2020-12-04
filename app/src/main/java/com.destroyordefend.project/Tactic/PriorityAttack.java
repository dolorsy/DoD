package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.Core.Point;

import java.util.Map;
import java.util.TreeSet;

public class PriorityAttack implements Tactic{

    @Override
    public void SortMap(Unit unit) {
        Tactic.updateRange(unit);
        //todo:??
    }
}
