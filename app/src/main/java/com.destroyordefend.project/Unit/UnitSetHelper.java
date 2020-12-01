package com.destroyordefend.project.Unit;

import java.util.HashMap;

public interface UnitSetHelper {
    HashMap<String, Unit> leftAndRight = new HashMap<>();

    public Unit getLeftUnit();

    public void setLeftUnit(Unit unit);

    public Unit getRightUnit();

    public void setRightUnit(Unit unit);

    public void updateLeftAndRight();

    public boolean needSwapWithLeft();

    public boolean needSwapWithRight();

    public void swapWithLeft();

    public void swapWithRight();


}
