package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.AndroidManger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;


public class Shop {
    private static Shop ins;
    private Unit.UnitValues baseUnitValues;
    private ArrayList<Unit.UnitValues> shopUnits;
    private int lowestPrice = 1 << 30;

    private Shop() {
        this.shopUnits = new ArrayList<>();
        shopUnits.add(null);
        this.InitShop();
    }

    public static Shop getInstance() {
        if (ins == null) {
            synchronized (Shop.class) {
                if (ins == null)
                    ins = new Shop();
            }
        }
        return ins;
    }

    public ArrayList<Unit.UnitValues> getShopUnits() {
        return shopUnits;
    }

    public Unit.UnitValues getBaseValues() {
        return baseUnitValues;
    }

    public Unit.UnitValues getUnitByName(String type) {
        for (Unit.UnitValues unitValues : this.shopUnits) {
            if (unitValues.is(type)) {
                return unitValues;
            }
        }
        return null;
    }

    public int getLowestPrice() {
        return this.lowestPrice;
    }

    public void InitShop() {

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(AndroidManger.jsonFile);
            JSONArray shop = (JSONArray) obj.get("shop");

            for (Object a : shop) {
                JSONObject unit1 = (JSONObject) a;
                Unit.UnitValues unitValues = new Unit.UnitValues(unit1);
                if (unitValues.is("main base"))
                    baseUnitValues = unitValues;
                else {
                    this.shopUnits.add(unitValues);
                    lowestPrice = Math.min(lowestPrice, Integer.parseInt((String) unit1.get("price")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}