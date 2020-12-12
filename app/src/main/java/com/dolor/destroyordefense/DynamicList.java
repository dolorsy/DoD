package com.dolor.destroyordefense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Core.Player;
import com.destroyordefend.project.Core.Point;
import com.destroyordefend.project.Core.Shop;
import com.destroyordefend.project.Movement.AircraftMovement;
import com.destroyordefend.project.Movement.FixedPatrol;
import com.destroyordefend.project.Movement.FixedPosition;
import com.destroyordefend.project.Movement.Movement;
import com.destroyordefend.project.Movement.ToTarget;
import com.destroyordefend.project.Tactic.AirAttack;
import com.destroyordefend.project.Tactic.RandomAttack;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.dolor.destroyordefense.ArenaUtilities.ArenaActivity;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.content.ContextCompat.startActivity;

public class DynamicList extends RecyclerView.Adapter<DynamicList.DynamicViewHolder> {
    //Declare the ArrayList You Want
    private ArrayList<String> myList = new ArrayList<>();

    public void setMyList(ArrayList<String> myList) {
        this.myList = myList;
    }
    Context context;
    public DynamicList(ArrayList<String> myList,Context context) {
        this.myList = myList;
    this.context = context;
    }

    @NonNull
    @Override
    public DynamicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //You should Change The Item_Layout for each Adapter You made (R.layout.Item_Layout)
            return new DynamicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false));
        }

    @Override
    public void onBindViewHolder(@NonNull DynamicViewHolder holder, int position) {
        //Here you Have to Set The Values of The Views Layout
        //This is The Final Step of Adapter Working
        //You should take a Look at DynamicViewHolderClass at the bottom
        // holder.textView.setText(myList.get(position));

        holder.itemText.setOnClickListener(v -> {


        switch ( myList.get(position)){
            case "Movment":
                Movment();
                break;
            case "Terrain":
                Terrain();
                break;
            case "War":
                War();
                break;



        }
            context.startActivity(new Intent(context, ArenaActivity.class).putExtra("treeSet", 0).setFlags(FLAG_ACTIVITY_NEW_TASK));

        });

        holder.itemText.setText(myList.get(position));

    }

    private void War(){


        Terrain t;

        t = new Terrain(new Point(1200,550),2,100,"vally");
        Game.getGame().getTerrains().add(t);
        t = new Terrain(new Point(1000,550),2,100,"river");
        Game.getGame().getTerrains().add(t);


        Player Defender = new Player();
        Player Attacker = new Player();
        Defender.setRole(Player.TeamRole.Defender);
        Attacker.setRole(Player.TeamRole.Attacker);
        Game.getGame().getDefenders().addPlayer(Defender);
        Game.getGame().getAttackers().addPlayer(Attacker);


        Unit u = new Unit();
        Unit.UnitValues values;

        values = Shop.getInstance().getBaseValues();
        Game.getGame().getBase().setRole(Player.TeamRole.Defender);
        Game.getGame().getBase().setValues(values);
        Game.getGame().getBase().AcceptMovement(new FixedPosition());
        Game.getGame().getBase().setPosition(new Point(1200,1100));
        Game.getGame().getBase().setRole(Player.TeamRole.Defender);
        Defender.addArmy(Game.getGame().getBase());

        u = new Unit();
        values = Shop.getInstance().getUnitByName("Prism Tower");
        u.setValues(values);
        u.setPosition(new Point(1000,1000));
        u.AcceptMovement(new FixedPosition());
        u.AcceptTactic(new RandomAttack());
        u.setRole(Player.TeamRole.Defender);
        Defender.addArmy(u);
        Game.getGame().getAllUnits().add(u);

/*
         u = new Unit();
        values = Shop.getInstance().getUnitByName("Black Eagle");
        u.setValues(values);
        u.setPosition(new Point(1200,300));
        u.AcceptMovement(new AircraftMovement(new Point(1200,300)));
        u.AcceptTactic(new RandomAttack());
        u.setRole(Player.TeamRole.Attacker);
        Attacker.addArmy(u);
        Game.getGame().getAllUnits().add(u);

*/
        u = new Unit();
        values = Shop.getInstance().getUnitByName("Mirage tank");
        u.setValues(values);
        u.setPosition(new Point(1200,400));
        u.AcceptMovement(new ToTarget(Game.getGame().getBase()));
        u.AcceptTactic(new RandomAttack());
        u.setRole(Player.TeamRole.Attacker);
        Attacker.addArmy(u);
        Game.getGame().getAllUnits().add(u);
        Game.getGame().getAttackers().addPlayer(Attacker);

    }
    private void Movment(){



        Game.getGame().getAllUnits().clear();
        Unit u = new Unit();
        Player Defender = new Player();
        Unit.UnitValues values;
        values = Shop.getInstance().getBaseValues();
        u.setValues(values);
        u.setPosition(new Point(1000,1000));
        u.AcceptMovement(new FixedPosition());
        u.setRole(Player.TeamRole.Defender);
        Defender.addArmy(u);

        u = new Unit();
         values = Shop.getInstance().getUnitByName("Mirage tank");
        u.setValues(values);
        u.setPosition(new Point(900,900));
        u.AcceptMovement(new FixedPatrol(200));
        u.AcceptTactic(new RandomAttack());
        Defender.addArmy(u);
        Game.getGame().getAllUnits().add(u);
        Game.getGame().getDefenders().addPlayer(Defender);

    }

    private void Terrain(){
        Terrain t;

        t = new Terrain(new Point(1200,550),2,100,"vally");
        Game.getGame().getTerrains().add(t);
        t = new Terrain(new Point(1000,550),2,100,"river");
        Game.getGame().getTerrains().add(t);


        Game.getGame().getAllUnits().clear();
        Player Defender = new Player();
        Unit.UnitValues values;
        values = Shop.getInstance().getBaseValues();
        Game.getGame().getBase().setValues(values);
        Game.getGame().getBase().AcceptMovement(new FixedPosition());
        Game.getGame().getBase().setPosition(new Point(1200,1100));
        Game.getGame().getBase().setRole(Player.TeamRole.Defender);
        Defender.addArmy(Game.getGame().getBase());

        Unit u = new Unit();
        values = Shop.getInstance().getUnitByName("Mirage tank");
        u.setValues(values);
        u.setPosition(new Point(1200,400));
        u.AcceptMovement(new ToTarget(Game.getGame().getBase()));
        u.AcceptTactic(new RandomAttack());
        u.setRole(Player.TeamRole.Attacker);
        Defender.addArmy(u);
        Game.getGame().getAllUnits().add(u);
        Game.getGame().getAttackers().addPlayer(Defender);


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void setList(ArrayList<String> myList) {
        this.myList = myList;
    }

    public static class DynamicViewHolder extends RecyclerView.ViewHolder {
        //This Class is a Model of your Item View(Item_Layout)
        //  TextView textView;
        TextView itemText;
        public DynamicViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemText = itemView.findViewById(R.id.testTitle);
            //Here The initialization of layout Views
            //textView = itemView.findViewById(R.id.textView);
            itemText.setOnClickListener(v -> {
                System.out.println("Clicked" + "  " + itemText.getText());
            });
        }
    }
}
