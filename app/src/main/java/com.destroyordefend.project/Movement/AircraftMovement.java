package com.destroyordefend.project.Movement;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.Log;

import java.util.Stack;

import static com.destroyordefend.project.Core.Game.getGame;

public class AircraftMovement implements Movement {
    private Stack<Point> track = new Stack<>();
    final Point airport;



    public AircraftMovement(Point airport){
        track.add(airport);
        track.add(Game.getGame().getBase().getPosition());
        this.airport = airport;
    }

    public void updateTrack(){
        track.add(airport);
        track.add(getGame().getBase().getPosition());
    }

    @Override
    public void StartMove(Unit unit) {
        Point p = this.GetNextPoint(unit);
        unit.setPosition(p);


        Log.move(unit);
    }

    @Override
    public void addTarget(Point p, Unit u) {

    }
    public void GoToAirPort(){
        track.pop();
    }

    @Override
    public Point GetNextPoint(Unit unit) {
        unit.getTactic().SortMap(unit);
        if(track.peek().equals(unit.getPosition()))
            track.pop();
        if( track.empty())
            updateTrack();
        Point p = Movement.straightMove(unit.getPosition(),track.peek());
        return p;
    }

    @Override
    public Stack<Point> getTruck() {
        return track;
    }


    @Override
    public Point getTarget() {
        return track.peek();
    }

    @Override
    public String toString() {
        return "AircraftMovement{" +
                "airport=" + airport +
                '}';
    }
}
