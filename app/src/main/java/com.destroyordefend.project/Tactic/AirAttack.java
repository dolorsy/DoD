package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

public class AirAttack implements Tactic{
    @Override
    public void SortMap(Unit unit) {
        System.out.println("Condition "  + Tactic.isInRange(unit,Game.getGame().getBase()));
        if(Tactic.isInRange(unit,Game.getGame().getBase())){
            unit.getTreeSetUnit().add(Game.getGame().getBase());
            Game.getGame().getBase().getDamaging().AcceptDamage(unit.getDamage());
            unit.getMovement().getTruck().pop();
        }
        else{
            unit.getTreeSetUnit().clear();
        }
    }
}
