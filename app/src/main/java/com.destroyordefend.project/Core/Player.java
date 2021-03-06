package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.IdGenerator;

import java.util.TreeSet;


public class Player {
    private int Points;
    private boolean canBuy = true;
    private final int id;
    private TeamRole role;    //Is he attacker or Defender
    private String name;
    private TreeSet<Unit> army;

    public void setRole(TeamRole role) {
        this.role = role;
    }


    public Player() {
        id = IdGenerator.generate(this);
        army = new TreeSet<>((v1, v2) -> 1);

    }

    @Override
    public String toString() {
        return "Player{" + "Points=" + Points + ", canBuy=" + canBuy + ", id=" + id + ", role=" + role + ", name='" + name + '\'' + ", army=" + army + '}';
    }

    public Player(int points, TeamRole role, String name) {
        this();
        Points = points;
        this.role = role;
        this.name = name;
    }

    public void addArmy(Unit unit) {
        army.add(unit);

    }

    public boolean CanBuy() {
        return canBuy;
    }

    public void stopBuy() {
        canBuy = false;
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
        cutPrice(unit.getValues().getPrice());
        unit.setRole(this.role);
        army.add(unit);
    }

    public void updateArmyState() {
        for (Unit unit : army) {
            army.removeIf(unit1 -> !unit.isAlive());
        }
    }

    public int getId() {
        return id;
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