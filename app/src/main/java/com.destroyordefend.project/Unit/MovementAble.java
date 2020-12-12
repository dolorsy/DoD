package com.destroyordefend.project.Unit;

import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Tactic.Plan;


public interface MovementAble {
    MovementAble AcceptMovement(Movement movement);
    void addTarget(Point point);
    MovementAble AcceptPlan(Plan plan);

}
