package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.AndroidManger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Shop {
    private static Shop ins;
    private Unit.UnitValues baseUnitValues;
    private ArrayList<Unit.UnitValues> shopUnits;
    private int lowestPrice = 1 << 30;

    private Shop() {
        this.shopUnits = new ArrayList<>();
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

    public Unit.UnitValues getUnitByName(String name) {
        System.out.println();
        if(name.equalsIgnoreCase("Main Base"))
            return getBaseValues();
        for (Unit.UnitValues unitValues : this.shopUnits) {
            if (unitValues.is(name)) {
                return unitValues;
            }
        }
        return null;
    }

    public int getLowestPrice() {
        return this.lowestPrice;
    }

    public void InitShop() {

        String path = "F:\\Collage\\3erd Year\\programming Languages\\FirstRepo\\src\\com\\destroyordefend\\project\\UnitslnShop.json";

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(json);
            JSONArray shop = (JSONArray) obj.get("shop");

            for (Object a : shop) {
                JSONObject unit1 = (JSONObject) a;
                Unit.UnitValues unitValues = new Unit.UnitValues(unit1);
                if (unitValues.is("main base")) {
                    baseUnitValues = unitValues;
            }
                else {
                    this.shopUnits.add(unitValues);
                    lowestPrice = Math.min(lowestPrice, Integer.parseInt((String) unit1.get("price")));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String json = "{\n" + "  \"shop\": [\n" + "    {\n" + "      \"type\": \"Tank\",\n" + "      \"name\": \"TeslaTank\",\n" + "      \"health\": \"1000\",\n" + "      \"armor\": \"0.50\",\n" + "      \"damage\": \"300\",\n" + "      \"range\": \"250\",\n" + "      \"shot_speed\": \"0.60\",\n" + "      \"radius\": \"20\",\n" + "      \"speed\": \"30\",\n" + "      \"SortMap\": [\"Tank\",\"Solider\"],\n" + "      \"price\": \"50\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Solider\",\n" + "      \"name\": \"Sniper\",\n" + "      \"health\": \"250\",\n" + "      \"armor\": \"0.10\",\n" + "      \"damage\": \"75\",\n" + "      \"range\": \"700\",\n" + "      \"shot_speed\": \"0.4\",\n" + "      \"radius\": \"3\",\n" + "      \"speed\": \"10\",\n" + "      \"SortMap\": [\"Solider\"],\n" + "      \"price\": \"5\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Tank\",\n" + "      \"name\": \"Mirage tank\",\n" + "      \"health\": \"750\",\n" + "      \"armor\": \"0.10\",\n" + "      \"damage\": \"1000\",\n" + "      \"range\": \"10\",\n" + "      \"shot_speed\": \"1\",\n" + "      \"radius\": \"15\",\n" + "      \"speed\": \"60\",\n" + "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" + "      \"price\": \"70\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Solider\",\n" + "      \"name\": \"Infantry\",\n" + "      \"health\": \"250\",\n" + "      \"armor\": \"0.20\",\n" + "      \"damage\": \"50\",\n" + "      \"range\": \"50\",\n" + "      \"shot_speed\": \"1.5\",\n" + "      \"radius\": \"3\",\n" + "      \"speed\": \"10\",\n" + "      \"SortMap\": [\"Solider\"],\n" + "      \"price\": \"3\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Solider\",\n" + "      \"name\": \"Grizzly Tank\",\n" + "      \"health\": \"1000\",\n" + "      \"armor\": \"0.40\",\n" + "      \"damage\": \"250\",\n" + "      \"range\": \"250\",\n" + "      \"shot_speed\": \"0.60\",\n" + "      \"radius\": \"20\",\n" + "      \"speed\": \"30\",\n" + "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" + "      \"price\": \"50\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Solider\",\n" + "      \"name\": \"Navy SEAL\",\n" + "      \"health\": \"400\",\n" + "      \"armor\": \"0.20\",\n" + "      \"damage\": \"60\",\n" + "      \"range\": \"50\",\n" + "      \"shot_speed\": \"2\",\n" + "      \"radius\": \"3\",\n" + "      \"speed\": \"20\",\n" + "      \"SortMap\": [\"Tanks\",\"Solider\"],\n" + "      \"price\": \"10\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Tank\",\n" + "      \"name\": \"Tank destroyer\",\n" + "      \"health\": \"1000\",\n" + "      \"armor\": \"0.50\",\n" + "      \"damage\": \"400\",\n" + "      \"range\": \"150\",\n" + "      \"shot_speed\": \"0.60\",\n" + "      \"radius\": \"20\",\n" + "      \"speed\": \"20\",\n" + "      \"SortMap\": [\"Tank\"],\n" + "      \"price\": \"50\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Tank\",\n" + "      \"name\": \"Prism tank\",\n" + "      \"health\": \"1000\",\n" + "      \"armor\": \"0.35\",\n" + "      \"damage\": \"300\",\n" + "      \"range\": \"150\",\n" + "      \"shot_speed\": \"0.60\",\n" + "      \"radius\": \"20\",\n" + "      \"speed\": \"20\",\n" + "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" + "      \"price\": \"60\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Structure\",\n" + "      \"name\": \"Pillbox\",\n" + "      \"health\": \"4000\",\n" + "      \"armor\": \"0.70\",\n" + "      \"damage\": \"100\",\n" + "      \"range\": \"200\",\n" + "      \"shot_speed\": \"0.70\",\n" + "      \"radius\": \"40\",\n" + "      \"speed\": \"0\",\n" + "      \"SortMap\": [\"Solider\"],\n" + "      \"price\": \"150\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Structure\",\n" + "      \"name\": \"Prism Tower\",\n" + "      \"health\": \"4000\",\n" + "      \"armor\": \"0.70\",\n" + "      \"damage\": \"100\",\n" + "      \"range\": \"200\",\n" + "      \"shot_speed\": \"0.50\",\n" + "      \"radius\": \"30\",\n" + "      \"speed\": \"0\",\n" + "      \"SortMap\": [\"Solider\",\"Tank\"],\n" + "      \"price\": \"150\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Structure\",\n" + "      \"name\": \"Grand Cannon\",\n" + "      \"health\": \"6500\",\n" + "      \"armor\": \"0.30\",\n" + "      \"damage\": \"150\",\n" + "      \"range\": \"400\",\n" + "      \"shot_speed\": \"0.90\",\n" + "      \"radius\": \"40\",\n" + "      \"speed\": \"0\",\n" + "      \"SortMap\": [\"Solider\",\"Tank\"],\n" + "      \"price\": \"200\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Structure\",\n" + "      \"name\": \"MAIN BASE\",\n" + "      \"health\": \"10000\",\n" + "      \"armor\": \"0.00\",\n" + "      \"damage\": \"0\",\n" + "      \"range\": \"0\",\n" + "      \"shot_speed\": \"0.00\",\n" + "      \"radius\": \"50\",\n" + "      \"speed\": \"0\",\n" + "      \"SortMap\": [],\n" + "      \"price\": \"0\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Airplane\",\n" + "      \"name\": \"Black Eagle\",\n" + "      \"health\": \"1500\",\n" + "      \"armor\": \"0.00\",\n" + "      \"damage\": \"400\",\n" + "      \"range\": \"30\",\n" + "      \"shot_speed\": \"0\",\n" + "      \"radius\": \"0\",\n" + "      \"speed\": \"100\",\n" + "      \"SortMap\": [\"MAIN BASE\"],\n" + "      \"price\": \"75\"\n" + "    },\n" + "    {\n" + "      \"type\": \"Structure\",\n" + "      \"name\": \"Patriot Missile\",\n" + "      \"health\": \"2500\",\n" + "      \"armor\": \"0.20\",\n" + "      \"damage\": \"50\",\n" + "      \"range\": \"400\",\n" + "      \"shot_speed\": \"0.90\",\n" + "      \"radius\": \"40\",\n" + "      \"speed\": \"0\",\n" + "      \"SortMap\": [\"Airplane\"],\n" + "      \"price\": \"175\"\n" + "    }\n" + "\n" + "  ]\n" + "}";
}