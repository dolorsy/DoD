package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Shop {
    //Todo: Here static List<>Units
    private static Shop ins;
    ArrayList<Unit> ShopUnits;
    HashMap<String, Integer> unitPrices = new HashMap<>();
    int lowestPrice = 0;

    private Shop() {

        this.ShopUnits = new ArrayList<>();
        this.InitShop();
    }

    public static Shop getInstance() {
        if (ins == null)
            ins = new Shop();
        return ins;
    }

    public ArrayList<Unit> getShopUnits() {
        return ShopUnits;
    }

    public Unit getUnitByType(String type) {
        for (Unit unit : this.ShopUnits) {
            if (unit.getType().equals(type)) {
                return unit;
            }
        }
        return null;
    }

    public Unit getUnitByName(String type) {
        for (Unit unit : this.ShopUnits) {
            if (unit.getValues().getName().equals(type)) {
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

    public String getjson() {
        return "{\n" +
                "  \"shop\": [\n" +
                "    {\n" +
                "      \"type\": \"Tank\",\n" +
                "      \"name\": \"TeslaTank\",\n" +
                "      \"health\": \"1000\",\n" +
                "      \"armor\": \"0.50\",\n" +
                "      \"damage\": \"300\",\n" +
                "      \"range\": \"250\",\n" +
                "      \"shot_speed\": \"0.60\",\n" +
                "      \"radius\": \"20\",\n" +
                "      \"speed\": \"30\",\n" +
                "      \"SortMap\": [\"Tank\",\"Solider\"],\n" +
                "      \"price\": \"50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Solider\",\n" +
                "      \"name\": \"Sniper\",\n" +
                "      \"health\": \"250\",\n" +
                "      \"armor\": \"0.10\",\n" +
                "      \"damage\": \"75\",\n" +
                "      \"range\": \"700\",\n" +
                "      \"shot_speed\": \"0.4\",\n" +
                "      \"radius\": \"3\",\n" +
                "      \"speed\": \"10\",\n" +
                "      \"SortMap\": [\"Solider\"],\n" +
                "      \"price\": \"5\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Tank\",\n" +
                "      \"name\": \"Mirage tank\",\n" +
                "      \"health\": \"750\",\n" +
                "      \"armor\": \"0.10\",\n" +
                "      \"damage\": \"1000\",\n" +
                "      \"range\": \"10\",\n" +
                "      \"shot_speed\": \"1\",\n" +
                "      \"radius\": \"15\",\n" +
                "      \"speed\": \"60\",\n" +
                "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" +
                "      \"price\": \"70\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Solider\",\n" +
                "      \"name\": \"Infantry\",\n" +
                "      \"health\": \"250\",\n" +
                "      \"armor\": \"0.20\",\n" +
                "      \"damage\": \"50\",\n" +
                "      \"range\": \"50\",\n" +
                "      \"shot_speed\": \"1.5\",\n" +
                "      \"radius\": \"3\",\n" +
                "      \"speed\": \"10\",\n" +
                "      \"SortMap\": [\"Solider\"],\n" +
                "      \"price\": \"3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Solider\",\n" +
                "      \"name\": \"Grizzly Tank\",\n" +
                "      \"health\": \"1000\",\n" +
                "      \"armor\": \"0.40\",\n" +
                "      \"damage\": \"250\",\n" +
                "      \"range\": \"250\",\n" +
                "      \"shot_speed\": \"0.60\",\n" +
                "      \"radius\": \"20\",\n" +
                "      \"speed\": \"30\",\n" +
                "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" +
                "      \"price\": \"50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Solider\",\n" +
                "      \"name\": \"Navy SEAL\",\n" +
                "      \"health\": \"400\",\n" +
                "      \"armor\": \"0.20\",\n" +
                "      \"damage\": \"60\",\n" +
                "      \"range\": \"50\",\n" +
                "      \"shot_speed\": \"2\",\n" +
                "      \"radius\": \"3\",\n" +
                "      \"speed\": \"20\",\n" +
                "      \"SortMap\": [\"Tanks\",\"Solider\"],\n" +
                "      \"price\": \"10\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Tank\",\n" +
                "      \"name\": \"Tank destroyer\",\n" +
                "      \"health\": \"1000\",\n" +
                "      \"armor\": \"0.50\",\n" +
                "      \"damage\": \"400\",\n" +
                "      \"range\": \"150\",\n" +
                "      \"shot_speed\": \"0.60\",\n" +
                "      \"radius\": \"20\",\n" +
                "      \"speed\": \"20\",\n" +
                "      \"SortMap\": [\"Tank\"],\n" +
                "      \"price\": \"50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Tank\",\n" +
                "      \"name\": \"Prism tank\",\n" +
                "      \"health\": \"1000\",\n" +
                "      \"armor\": \"0.35\",\n" +
                "      \"damage\": \"300\",\n" +
                "      \"range\": \"150\",\n" +
                "      \"shot_speed\": \"0.60\",\n" +
                "      \"radius\": \"20\",\n" +
                "      \"speed\": \"20\",\n" +
                "      \"SortMap\": [\"Tank\",\"Solider\",\"Structure\"],\n" +
                "      \"price\": \"60\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Structure\",\n" +
                "      \"name\": \"Pillbox\",\n" +
                "      \"health\": \"4000\",\n" +
                "      \"armor\": \"0.70\",\n" +
                "      \"damage\": \"100\",\n" +
                "      \"range\": \"200\",\n" +
                "      \"shot_speed\": \"0.70\",\n" +
                "      \"radius\": \"40\",\n" +
                "      \"speed\": \"0\",\n" +
                "      \"SortMap\": [\"Solider\"],\n" +
                "      \"price\": \"150\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Structure\",\n" +
                "      \"name\": \"Prism Tower\",\n" +
                "      \"health\": \"4000\",\n" +
                "      \"armor\": \"0.70\",\n" +
                "      \"damage\": \"100\",\n" +
                "      \"range\": \"200\",\n" +
                "      \"shot_speed\": \"0.50\",\n" +
                "      \"radius\": \"30\",\n" +
                "      \"speed\": \"0\",\n" +
                "      \"SortMap\": [\"Solider\",\"Tank\"],\n" +
                "      \"price\": \"150\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Structure\",\n" +
                "      \"name\": \"Grand Cannon\",\n" +
                "      \"health\": \"6500\",\n" +
                "      \"armor\": \"0.30\",\n" +
                "      \"damage\": \"150\",\n" +
                "      \"range\": \"400\",\n" +
                "      \"shot_speed\": \"0.90\",\n" +
                "      \"radius\": \"40\",\n" +
                "      \"speed\": \"0\",\n" +
                "      \"SortMap\": [\"Solider\",\"Tank\"],\n" +
                "      \"price\": \"200\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"MAIN BASE\",\n" +
                "      \"name\": \"MAIN BASE\",\n" +
                "      \"health\": \"10000\",\n" +
                "      \"armor\": \"0.00\",\n" +
                "      \"damage\": \"0\",\n" +
                "      \"range\": \"0\",\n" +
                "      \"shot_speed\": \"0.00\",\n" +
                "      \"radius\": \"50\",\n" +
                "      \"speed\": \"0\",\n" +
                "      \"SortMap\": [],\n" +
                "      \"price\": \"0\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Airplane\",\n" +
                "      \"name\": \"Black Eagle\",\n" +
                "      \"health\": \"1500\",\n" +
                "      \"armor\": \"0.00\",\n" +
                "      \"damage\": \"400\",\n" +
                "      \"range\": \"30\",\n" +
                "      \"shot_speed\": \"0\",\n" +
                "      \"radius\": \"0\",\n" +
                "      \"speed\": \"100\",\n" +
                "      \"SortMap\": [\"MAIN BASE\"],\n" +
                "      \"price\": \"75\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Structure\",\n" +
                "      \"name\": \"Patriot Missile\\r\\nSystem\",\n" +
                "      \"health\": \"2500\",\n" +
                "      \"armor\": \"0.20\",\n" +
                "      \"damage\": \"50\",\n" +
                "      \"range\": \"400\",\n" +
                "      \"shot_speed\": \"0.90\",\n" +
                "      \"radius\": \"40\",\n" +
                "      \"speed\": \"0\",\n" +
                "      \"SortMap\": [\"Airplane\"],\n" +
                "      \"price\": \"175\"\n" +
                "    }\n" +
                "\n" +
                "  ]\n" +
                "}";
    }

    public void InitShop() {

        //Todo: Here We Should read from DataBase Or JSON To fill ShopUnits;
        //Todo:Here We should init lowestPrice

        String path = "res/raw/shop.json";


        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(getjson());
            JSONArray shop = (JSONArray) obj.get("shop");


            for (Object a : shop) {

                System.out.println(a);
                JSONObject unit1 = (JSONObject) a;
                String type = (String) unit1.get("type");

                String name = (String) unit1.get("name");

                String health = (String) unit1.get("health");

                String armor = (String) unit1.get("armor");

                String damage = (String) unit1.get("damage");

                String range = (String) unit1.get("range");

                String shot_speed = (String) unit1.get("shot_speed");

                String radius = (String) unit1.get("radius");

                String speed = (String) unit1.get("speed");

                List<String> SortMap = new ArrayList<>();

                JSONArray sMap = (JSONArray) unit1.get("SortMap");
                System.out.println(sMap);
                for (Object c : sMap) {
                    String s = c.toString();
                    SortMap.add(s);
                }
                String price = (String) unit1.get("price");
                lowestPrice = Math.min(lowestPrice, Integer.parseInt(price));
                Unit.UnitValues unitValues = new Unit.UnitValues(
                        type,
                        name,
                        Integer.parseInt(health),
                        Double.parseDouble(armor),
                        Integer.parseInt(damage),
                        Integer.parseInt(range),
                        Double.parseDouble(shot_speed),
                        Integer.parseInt(radius),
                        Integer.parseInt(speed),
                        (SortMap),
                        Integer.parseInt(price));
                Unit t = new Unit(4, unitValues);
                System.out.println(t);
                this.ShopUnits.add(t);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public Unit sellItem(String type) {
        return new Unit(Objects.requireNonNull(getUnitByType(type)));


    }


}
