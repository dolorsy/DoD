package com.destroyordefend.project.Unit;

import java.util.HashMap;

public interface UnitSetHelper {
     void setNeighbourUnit(String side, Unit unit);
     Unit getNeighbourUnit(String side);
     void updateLeftAndRight();
     boolean needSwapWithLeft();
     boolean needSwapWithRight();
     void swapWithLeft();
     void swapWithRight();



}
