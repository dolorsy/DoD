package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.IdGenerator;

import java.util.TreeSet;


public class Player {
    public final int id;
    private int Points;
    private TeamRole role;    //Is he attacker or Defender
    private String name;
    private TreeSet<Unit> army;

    public Player() {
        id = IdGenerator.generate(this);
    }

    public Player(int points, TeamRole role, String name) {
        this();
        army = new TreeSet<>(new PointComparator());//todo:need to check if it is work for point comparator
        Points = points;
        this.role = role;
        this.name = name;
    }

    public TeamRole getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public TreeSet<Unit> getArmy() {
        return army;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return Points;
    }

    public void cutPrice(int price) throws NoEnoughPointsException {
        if (Points - price < 0) {
            throw new NoEnoughPointsException("No Enough Points to buy; Your Points:" + Points + "; Price: " + price);
        }
        Points -= price;
    }

    public void BuyAnArmy(Unit unit) throws NoEnoughPointsException {
        cutPrice(unit.getPrice());
        unit.setRole(this.role);
        army.add(unit);
    }

    public void updateArmyState() {
        for (Unit unit : army) {
            army.removeIf(unit1 -> !unit.isAlive());
        }
    }

    public enum TeamRole {
        Attacker, Defender
    }
}

class NoEnoughPointsException extends Exception {
    NoEnoughPointsException(String message) {
        super(message);
    }
}