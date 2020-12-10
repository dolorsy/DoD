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
import com.destroyordefend.project.Movement.FixedPatrol;
import com.destroyordefend.project.Movement.ToTarget;
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

            Unit u = new Unit();
            Unit.UnitValues values = Shop.getInstance().getUnitByName("Mirage tank");
            u.setValues(values);
            u.setPosition(new Point(500,500));
            u.AcceptMovement(new FixedPatrol(2000));
           // Game.getGame().getAllUnits().add(u);
/*

            u = new Unit();
            values = Shop.getInstance().getUnitByName("Mirage tank");
            u.setPosition(new Point(500,500));
            u.AcceptMovement(new FixedPatrol(1000));
            Game.getGame().getAllUnits().add(u);


*/
            context.startActivity(new Intent(context, ArenaActivity.class).putExtra("treeSet", 0).setFlags(FLAG_ACTIVITY_NEW_TASK));
        });

        holder.itemText.setText(myList.get(position));
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
