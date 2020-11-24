package com.dolor.destroyordefense;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.Unit.Unit;

public class MainActivity extends GeneralActivity {
    RecyclerView recyclerView;
    UnitAdapter unitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View view = getSupportActionBar().getCustomView();
        recyclerView = findViewById(R.id.shop_recycler_view);

        List<UnitViewHelper> list = new ArrayList<>();
        list.add(new UnitViewHelper(new Unit(20, 34, 55, "tk", 130, 140, 440, 90)));
        list.add(new UnitViewHelper(new Unit(21, 74, 75, "ks", 70, 120, 440, 95)));
        list.add(new UnitViewHelper(new Unit(22, 84, 78, "tt", 50, 200, 460, 95)));
        list.add(new UnitViewHelper(new Unit(53, 24, 65, "kt", 40, 100, 160, 0)));
        list.add(new UnitViewHelper(new Unit(54, 2114, 765, "kt", 430, 1300, 1603, 932)));
        list.add(new UnitViewHelper(new Unit(55, 274, 65, "tt", 450, 100, 760, 392)));
        list.add(new UnitViewHelper(new Unit(56, 27, 6985, "ks", 4580, 1050, 1640, 923)));
        list.add(new UnitViewHelper(new Unit(57, 247, 695, "tt", 4054, 10650, 1670, 92)));
        unitAdapter = new UnitAdapter(list, this);
        recyclerView.setAdapter(unitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}