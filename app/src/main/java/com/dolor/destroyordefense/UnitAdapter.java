package com.dolor.destroyordefense;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitHolder> {
    List<Unit.UnitValues> unitList;
    Context context;

    public UnitAdapter(List<Unit.UnitValues> unitList, Context context, int playerPoint) {
        this.unitList = new ArrayList<>(unitList);
        this.unitList.removeIf(unit -> unit.getPrice() > playerPoint);
        this.context = context;
    }

    @NonNull
    @Override
    public UnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UnitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitHolder holder, int position) {
        Unit.UnitValues unit = unitList.get(position);
        holder.bind(unit);
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public void setUnitList(List<Unit.UnitValues> list, int playerPoints) {
        this.unitList = new ArrayList<>(list);
        unitList.removeIf(unit -> unit.getPrice() > playerPoints);
    }

    public class UnitHolder extends RecyclerView.ViewHolder {
        TextView unitName, unitPrice, unitDamage, unitHealth;

        public UnitHolder(@NonNull View itemView) {
            super(itemView);
            unitName = itemView.findViewById(R.id.unit_name);
            unitPrice = itemView.findViewById(R.id.unit_price);
            unitDamage = itemView.findViewById(R.id.unit_damage);
            unitHealth = itemView.findViewById(R.id.unit_health);
        }

        public void bind(Unit.UnitValues unit) {
            unitName.setText(unit.getName());
            unitPrice.setText("Price:" + unit.getPrice());
            unitHealth.setText("Health:" + unit.getHealth());
            unitDamage.setText("Damage:" + unit.getDamage());
            itemView.setOnClickListener(v -> {
                Log.d(TAG, "bind: " + unit);
                AndroidManger.lastBoughtUnit = new Unit(unit);
                ((ShopActivity) context).show(unit);
            });
        }
    }


}
