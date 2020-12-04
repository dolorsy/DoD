package com.destroyordefend.project.utility;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static final File logFile = new File("logFile.txt");


    private static void createFile (File file){
        if(!logFile.exists()){
            try {
                boolean newFile = file.createNewFile();
                if(newFile){
                    //  System.out.println("File created: " + myObj.getName());

                }else{
                    //System.out.println("File already exists.");

                }
            } catch (IOException e) {
                System.err.println("Cant create a log file!!");
                e.printStackTrace();
            }
        }

    }

    public static void writeFile(String text , File file){
        //todo: we send file as a parameter because maybe we ned to write in a specific file
        try {
            FileWriter myWriter = new FileWriter(file.getAbsoluteFile(),true);
            myWriter.write(text);
            myWriter.close();
            //   System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void m(Unit unit){
        writeFile("time :" + Game.getGame().getGameTimer().getCurrentSecond() +
                "  Unit_id :" + unit.getId() +
                "   X :" + unit.getPosition().getX() +" "+
                "   Y :" + unit.getPosition().getY() + "\n"  + "---------------" + "\n",logFile);
    }

    public static void d(Unit unit_One ,Unit unit_Two){
        File file = new File("logFile.txt");
        writeFile("Unit_id :" + unit_One.getId() +
                " Attack "  +
                "Unit_id :" + unit_Two.getId() + "\n" + "---------------" + "\n",file);
    }

    public static void h(Unit unit){
        File file = new File("logFile.txt");
        writeFile("time :" + Game.getGame().getGameTimer().getCurrentSecond()+
                "health :" + unit.getValues().getHealth() + "\n"  + "---------------" + "\n",file);
    }
    public static void four(){}

    public static void five(){}

    public static void six(){}

    public static void seven(){}

}

