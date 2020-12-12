package com.dolor.destroyordefense.ArenaUtilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Terrain;
import com.destroyordefend.project.Unit.Unit;
import com.destroyordefend.project.utility.GameTimer;
import com.dolor.destroyordefense.GameOverWinActivity;
import com.dolor.destroyordefense.R;
import com.dolor.destroyordefense.ShopActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TreeSet;

import static com.destroyordefend.project.Core.Game.game;
import static com.destroyordefend.project.Core.Game.getGame;
import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;

public class ArenaActivity extends AppCompatActivity
implements
        GestureDetector.OnGestureListener, View.OnTouchListener {
    //right down corner
    TypeConverter maxX, maxY;

    View gestureView;

    static MutableLiveData<String> gameState = new MutableLiveData<>();



    private GestureDetector mGestureDetector;
  //  private ScaleGestureDetector detector;
    int zoom = 1;
    float s = 0;
     float scale  = 1f;
    //current corner

    static
    TypeConverter seekX = new TypeConverter(Type.point),
            seekY = new TypeConverter(Type.point);
    RelativeLayout relativeLayout;
    SeekBar xSeekBar, ySeekBar;
    List<MyImage> inRange = new ArrayList<>();
    List<MyImage> tinRange = new ArrayList<>();
    MutableLiveData<TreeSet<Unit>> liveData = new MutableLiveData<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle("X : " + seekX.toPoint() + " Y : " + seekY.toPoint());

        mGestureDetector = new GestureDetector(this,this);
        gestureView = findViewById(R.id.gestureView);
        gestureView.setOnTouchListener(this);
      //  detector = new ScaleGestureDetector(this, new MyScaleListener());

        gameState.observe(this, s -> {
            startActivity(new Intent(this, GameOverWinActivity.class));
            finish();
        });

        xSeekBar = findViewById(R.id.XseekBar);
        ySeekBar = findViewById(R.id.YseekBar);
        xSeekBar.requestApplyInsets();
        relativeLayout = findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        TypeConverter.square = 1;

        maxX = new TypeConverter(mdispSize.x , Type.pixel);
        maxY = new TypeConverter(mdispSize.y , Type.pixel);

        relativeLayout.setX(0);
        relativeLayout.setY(0);
        updateScreen(Game.getGame().getAllUnits(),getGame().getTerrains());




        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekX.setValue(progress, Type.point);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekY.setValue(progress, Type.point);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());
            }
        });


        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,20);
                updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());
            }
        };
        handler.postDelayed(r, 0);
        System.out.println(Game.getGame().getAllUnits().size());

        Game.getGame().StartAnewGame();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customization_action_bar, menu);
        MenuItem item = menu.findItem(R.id.start_game);
        //TODO: start game
        item.setOnMenuItemClickListener(v -> {
            boolean done = Game.getGame().settingUnit(
                    new com.destroyordefend.project.Core.Point(seekX.toInteger(), seekY.toInteger())
                    , lastBoughtUnit
                    , getIntent().getIntExtra("counter", -1));
            if (done) {
                Toast.makeText(this, "unit is bought", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ShopActivity.class));
            } else {
                Toast.makeText(this, "Can not set units here", Toast.LENGTH_SHORT).show();
            }

            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    void updateScreen(TreeSet<Unit> allUnits, TreeSet<Terrain> terrains) {
        relativeLayout.removeAllViews();
        inRange = new ArrayList<>();
        tinRange = new ArrayList<>();
        relativeLayout.requestLayout();
        for (Unit unit : allUnits) {
            inRange.add(new MyImage(unit, this));
        }
        for (Terrain terrain:terrains)
            tinRange.add(new MyImage(terrain,this));
        startUpdate();

    }


    void startUpdate() {
        for (MyImage myImage : tinRange) {
            ImageView iv = myImage.imageView;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(myImage.width.toPixel(), myImage.width.toPixel());
            params.leftMargin = myImage.center.getX();
            params.topMargin = myImage.center.getY();
            relativeLayout.addView(iv, params);
        }
        for (MyImage myImage : inRange) {
            ImageView iv = myImage.imageView;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(myImage.width.toPixel(), myImage.width.toPixel());
            params.leftMargin = myImage.center.getX();
            params.topMargin = myImage.center.getY();
            relativeLayout.addView(iv, params);
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    TypeConverter.square += 1;
                    updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN && TypeConverter.square > 1) {
                    TypeConverter.square -= 1;
                    updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    TypeConverter down() {
        return seekY.plus(maxY);
    }

    TypeConverter left() {
        return seekX;
    }

    TypeConverter right() {
        return seekX.plus(maxX);
    }

    TypeConverter up() {
        return seekY;
    }



    @Override
    public boolean onDown(MotionEvent e) {
       // xSeekBar.setProgress();
      //  Toast.makeText(getApplicationContext(),"OnDown",Toast.LENGTH_LONG).show();
        Log.d("Event","Event : " + e + " Down");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
     //   Toast.makeText(getApplicationContext(),"OnUp",Toast.LENGTH_LONG).show();
        Log.d("Event","Event : " + e + " ShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Toast.makeText(getApplicationContext(),"onSingleTapUp",Toast.LENGTH_LONG).show();
        Log.d("Event","Event : " + e + " SingleTapUp");

        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();
        float factor = 15;



        if(Math.abs(deltaX) > 100){
        //Scrolling Horizontal
        if (deltaX > 0) {

            xSeekBar.setProgress(xSeekBar.getProgress() - (int) (deltaX / factor));

            //     touchTypListener.onScroll(SCROLL_DIR_RIGHT);
        } else {
            xSeekBar.setProgress(xSeekBar.getProgress() - (int) (deltaX / factor));

            //    touchTypListener.onScroll(SCROLL_DIR_LEFT);
        }

    }

        if(Math.abs(deltaY)  > 100) {
            //Scrolling Vertical
            if (Math.abs(deltaY) > 1) {
                if (deltaY > 0) {
                    ySeekBar.setProgress(ySeekBar.getProgress() - (int) (deltaY / factor));
                    //    touchTypListener.onScroll(SCROLL_DIR_DOWN);
                } else {
                    ySeekBar.setProgress(ySeekBar.getProgress() - (int) (deltaY / factor));

                    //     touchTypListener.onScroll(SCROLL_DIR_UP);
                }
            }

        }
      //  xSeekBar.setProgress((int)distanceX);
      //  ySeekBar.setProgress((int)distanceY);
        Log.d("Event","Event1 : " + e1 + "Event2 : " + e2 +
                "  Down" + " dx : " + distanceX + " dy: "  + distanceY);

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
  //      detector.onTouchEvent(event);
        onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        return true;
    }





    private class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        float onScaleBegin = 0;
        float onScaleEnd = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //Here we zoom;
            s =detector.getCurrentSpan() - detector.getPreviousSpan();

            if(s > 20){
                //zoom in
                TypeConverter.square -= (int)s/3;

            }else if(s<-20) {
                TypeConverter.square += (int)s/3;

            }
            scale = detector.getScaleFactor();
            Toast.makeText(getApplicationContext(), String.valueOf(s),Toast.LENGTH_SHORT).show();
            if(s>1100) {
                //TypeConverter.square -= (int)s/1100;
            }
            updateScreen(Game.getGame().getAllUnits(),Game.getGame().getTerrains());

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            // Toast.makeText(getApplicationContext(), "Scale Begin", Toast.LENGTH_SHORT).show();
            Log.i("scale_tag", "Scale Begin");
            onScaleBegin = scale;

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

            // Toast.makeText(getApplicationContext(), "Scale Ended", Toast.LENGTH_SHORT).show();
            Log.i("scale_tag", "Scale End");
            onScaleEnd = scale;

            if (onScaleEnd > onScaleBegin) {
                // Toast.makeText(getApplicationContext(), "Scaled Up by a factor of  " + String.valueOf(onScaleEnd / onScaleBegin), Toast.LENGTH_SHORT).show();

                Log.i("scale_tag", "Scaled Up by a factor of  " + String.valueOf(onScaleEnd / onScaleBegin));
            }

            if (onScaleEnd < onScaleBegin) {
                // Toast.makeText(getApplicationContext(), "Scaled Down by a factor of  " + String.valueOf(onScaleBegin / onScaleEnd), Toast.LENGTH_SHORT).show();
                Log.i("scale_tag", "Scaled Down by a factor of  " + String.valueOf(onScaleBegin / onScaleEnd));
            }

            super.onScaleEnd(detector);
        }
    }

    public static void updateUiState(String state){
//        gameState.setValue(state);
    }

}
