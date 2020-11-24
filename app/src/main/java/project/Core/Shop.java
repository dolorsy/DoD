package project.Core;


import com.dolor.destroyordefense.UnitViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

import project.Unit.Unit;

public class Shop {
    //Todo: Here static List<>Units
    ArrayList<Unit> ShopUnits = new ArrayList<>();
    HashMap<String, Integer> unitPrices = new HashMap<>();
    int lowestPrice = 0;

    public Shop() {
        ShopUnits.add(new UnitViewHelper(new Unit(20, 34, 55, "tk", 130, 140, 440, 90)));
        ShopUnits.add(new UnitViewHelper(new Unit(21, 74, 75, "ks", 70, 120, 440, 95)));
        ShopUnits.add(new UnitViewHelper(new Unit(22, 84, 78, "tt", 50, 200, 460, 95)));
        ShopUnits.add(new UnitViewHelper(new Unit(53, 24, 65, "kt", 40, 100, 160, 0)));
        ShopUnits.add(new UnitViewHelper(new Unit(54, 2114, 765, "kt", 430, 1300, 1603, 932)));
        ShopUnits.add(new UnitViewHelper(new Unit(55, 274, 65, "tt", 450, 100, 760, 392)));
        ShopUnits.add(new UnitViewHelper(new Unit(56, 27, 6985, "ks", 4580, 1050, 1640, 923)));
        ShopUnits.add(new UnitViewHelper(new Unit(57, 247, 695, "tt", 4054, 10650, 1670, 92)));

    }

    private Unit getUnitByType(String type) {
        for (Unit unit : this.ShopUnits) {
            if (unit.getType().equals(type)) {
                return unit;
            }
        }
        return null;
    }

    public Unit getUnitByType2(String type) {
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
