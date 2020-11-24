package com.dolor.destroyordefense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitHolder> {
    List<UnitViewHelper> unitList;
    Context context;

    public UnitAdapter(List<UnitViewHelper> unitList, Context context) {
        this.unitList = unitList;
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
        UnitViewHelper unit = unitList.get(position);
        holder.bind(unit);
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public void setUnitList(List<UnitViewHelper> list) {
        this.unitList = list;
    }

    public class UnitHolder extends RecyclerView.ViewHolder {
        TextView unitName, unitPrice;

        public UnitHolder(@NonNull View itemView) {
            super(itemView);
            unitName = itemView.findViewById(R.id.unit_name);
            unitPrice = itemView.findViewById(R.id.unit_price);

        }

        public void bind(UnitViewHelper unit) {
            unitName.setText(unit.getType() + "");
            unitPrice.setText(unit.getHealth() + "");
            itemView.setOnClickListener(v -> context.startActivity(new Intent(context, UnitDetailsActivity.class).putExtra("type", unit.getType())));
        }
    }


}
