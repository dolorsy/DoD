package com.dolor.destroyordefense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import project.Core.Shop;

public class UnitDetailsActivity extends GeneralActivity {
    TextView allDet;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);
        Shop s = new Shop();
        allDet = findViewById(R.id.alldet);
        next = findViewById(R.id.next);
        allDet.setText(s.getUnitByType2(getIntent().getStringExtra("type")).toString());
        //TODO get player and add if for check player points
        next.setOnClickListener(v -> startActivity(new Intent(this, ArenaActivity.class)));


    }
}