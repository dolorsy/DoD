package com.dolor.destroyordefense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DynamicActivity extends AppCompatActivity implements View.OnTouchListener {
    RecyclerView dynamicRecycler;
    ArrayList<String> myList = new ArrayList<>();
    DynamicList dynamicList;
    View emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        initList();
        emptyView = findViewById(R.id.emptyview);
        dynamicRecycler = findViewById(R.id.dynamicRecycler);
        dynamicList = new DynamicList(myList,getApplicationContext());

        dynamicRecycler.setAdapter(dynamicList);

MediaPlayer mp;
        dynamicRecycler.setLayoutManager(new LinearLayoutManager(this));

        startService(new Intent(this, music.class));


    }

    private void initList() {
        myList.add("Movment");
        myList.add("Terrain");
        myList.add("War");

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(getApplicationContext(),"Touched" , Toast.LENGTH_LONG).show();
        return false;
    }
}