package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Unit.Unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Log extends ConsoleZoomingFilter{
    private static final File logFile = new File("logFile.txt");


    private static void createFile (File file){
        if(!logFile.exists()){
            try {
                boolean newFile = file.createNewFile();

            } catch (IOException e) {
                System.err.println("Cant create a log file!!");
                e.printStackTrace();
            }
        }

    }

    public static void writeFile(String text , File file){
        //todo: we send file as a parameter because maybe we ned to write in a specific file
        System.out.println(text);
        if(!System.getProperty("os.name").equals("Windows 10"))
            return;

        createFile(file);
        try {
            FileWriter myWriter = new FileWriter(file.getAbsoluteFile(),true);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void move(Unit unit){
        if(!inRange(unit.getPosition()))
            return;
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond() +
                "\tUnit_id :" + unit.getId() +
                " Moved to :"+
                "\tX :" + unit.getPosition().getX()+
                ",Y :" + unit.getPosition().getY() + "\n"  + "---------------" + "\n"+
                "target is"+unit.getMovement().getTarget()+"\n"
                +unit.getMovement().getTruck().size(),logFile);
    }

    public static void doDamage(Unit unit_One ,Unit unit_Two){
        if(!(inRange(unit_One.getPosition()) || inRange(unit_Two.getPosition())))
            return;

        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond() +
                "\tUnit_id: " + unit_One.getId() +
                "\t Attack "  +
                "\tUnit_id: " + unit_Two.getId() + " By Damage: " + unit_One.getDamaging().getDamage()+ "\n" + "---------------" + "\n",logFile);
    }

    public static void acceptDamage(Unit unit){
        if(!inRange(unit.getPosition()))
            return;
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond()+
                "\tUnit id: " + unit.getId() +
                "\tnew health :" + unit.getValues().getHealth() + "\n"  + "---------------" + "\n",logFile);
    }


    public static void onDestroy(Unit unit) {
        if(!inRange(unit.getPosition()))
            return;
        writeFile("time: " + Game.getGame().getGameTimer().getCurrentSecond()+
                "\tUnit id: " + unit.getId() +
                "\tDestroyed "+ "\n"  + "---------------" + "\n",logFile);
    }

    public static void GameOver(String gameStateName) {
        writeFile(gameStateName,logFile);
    }

    protected static  boolean inRange(Point p) {

        return !(p.getX() > ZoomCenter.getX()+ ZoomedArenaWidth/2 ||
                p.getX() < ZoomCenter.getX() -  ZoomedArenaWidth/2 ||
                p.getY() > ZoomCenter.getY()+ ZoomedArenaWidth/2 ||
                p.getY() < ZoomCenter.getY() -  ZoomedArenaWidth/2);
    }
    public static void z(){
        writeFile("Zoom Center: " + ZoomCenter + " Zoom Width: " + ZoomedArenaWidth ,logFile);
    }

    public static void initZoom(int x, int y, int zoomedArenaWidth) {
        ZoomCenter = new Point(x,y);
        ZoomedArenaWidth=zoomedArenaWidth;
        Log.z();
        System.out.println("Zoom Center ");
    }
}

