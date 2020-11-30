package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;


public class Shop {
    //Todo: Here static List<>Units
    ArrayList<Unit> ShopUnits = new ArrayList<>();
    HashMap<String, Integer> unitPrices = new HashMap<>();
    int lowestPrice = 0;

    public Unit getUnitByType(String type) {
        for (Unit unit : this.ShopUnits) {
            if (unit.getType().equals(type)) {
                return unit;
            }
        }
        return null;
    }

    public int getUnitPrice(String type) {
        return unitPrices.get(type);
    }

    public int getLowestPrice() {
        return this.lowestPrice;
    }

    public void InitShop() {
        //Todo: Here We Should read from DataBase Or JSON To fill ShopUnits;
        //Todo:Here We should init lowestPrice

    }

    public Unit sellItem(String type) {
        return new Unit(getUnitByType(type));
    }


}
