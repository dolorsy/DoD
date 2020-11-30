package com.destroyordefend.project.Core;


import com.destroyordefend.project.Unit.Unit;

import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;


public class Player {
    int Points;
    //Is he attacker or Defender
    TeamRole role;
    String id;
    TreeSet<Unit> army;

    public Player(int points, TeamRole role, String id) {
        army = new TreeSet<>(new PointComparator());

        Points = points;
        this.role = role;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public TreeSet<Unit> getArmy() {
        return army;
    }

    public TeamRole getRole() {
        return role;
    }

    public void addArmy(Unit u) {
        army.add(u);
    }

    void cutPrice(int price) throws NoEnoughPointsException {
        if (Points - price < 0) {
            throw new NoEnoughPointsException("No Enough Points to buy; Your Points:" + Points + "; Price: " + price);
        }
        Points -= price;
    }

    public void CreateArmy() {
        //Todo:Here we will Shopping
        while (this.Points > 0)
            try {
                BuyAnArmy(game.getShop().sellItem("SS"), game.shop.getUnitPrice("SS"));
            } catch (PointsCantByuException ex) {
                System.err.println(ex);
                break;
            }


    }

    public void BuyAnArmy(Unit unit, int price) throws PointsCantByuException {
        try {
            if (this.Points < game.getShop().getLowestPrice())
                throw new PointsCantByuException("You Have only " + this.Points + ", this cant buy anything!!");
            cutPrice(price);
            unit.setRole(this.role.name());
            army.add(unit);
        } catch (NoEnoughPointsException ex) {
            System.err.println(ex);
        }
    }

    public void updateArmyState() {
        for (Unit unit : army) {
            army.removeIf(unit1 -> unit.getHealth() == 0);
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


class PointsCantByuException extends Exception {
    PointsCantByuException(String message) {
        super(message);
    }
}
