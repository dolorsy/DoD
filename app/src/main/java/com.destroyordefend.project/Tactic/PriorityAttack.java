package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;

public class PriorityAttack implements Tactic {

    @Override
    public void SortMap(Unit unit) {
        Tactic.updateRange(unit);
        //todo:??
    }
}
