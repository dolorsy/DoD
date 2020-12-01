package com.destroyordefend.project.Tactic;

import com.destroyordefend.project.Unit.Unit;

import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Main.p;

public interface Tactic {
    static void updateRange(Unit t) {
        p("Update Range :" + t.id + " all : " + t.getTreeSetUnit().size());
        TreeSet<Unit> unitTreeSet = new TreeSet<>(game.getAllUnits());
        unitTreeSet.removeIf(
                unit -> unit.getLeft() > t.getRight() + t.getRange() ||
                        unit.getRight() < t.getLeft() - t.getRange() || t.getPosition().equals(unit.getPosition())
        );
        unitTreeSet.removeIf(
                unit -> unit.getDown() > t.getUp() + t.getRange() ||
                        unit.getUp() < t.getDown() - t.getRange()
        );
        t.setTreeSetUnit(unitTreeSet);
    }

    void SortMap(Unit unit);
}
