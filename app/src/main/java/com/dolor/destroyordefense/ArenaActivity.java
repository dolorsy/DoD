package com.dolor.destroyordefense;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;

public class ArenaActivity extends GeneralActivity {
    TypeConverter maxX, maxY;
    TypeConverter seekX = new TypeConverter(TypeConverter.Type.point),
            seekY = new TypeConverter(TypeConverter.Type.point);
    ConstraintLayout constraintLayout;
    SeekBar xSeekBar, ySeekBar;
    List<MyImage> inRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle("X : " + seekX + " Y : " + seekY);

        xSeekBar = findViewById(R.id.XseekBar);
        ySeekBar = findViewById(R.id.YseekBar);

        constraintLayout = findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        TypeConverter.square = 10;

        maxX = new TypeConverter(mdispSize.x, TypeConverter.Type.pixel);
        maxY = new TypeConverter(mdispSize.y, TypeConverter.Type.pixel);
        Log.i("", "onCreate: maxX/Y" + maxX + " " + maxY);
        constraintLayout.setX(0);
        constraintLayout.setY(0);
        Log.d("TAG", "onCreate: allUnit.size()" + Game.getGame().getAllUnits().size());
        updateScreen(Game.getGame().getAllUnits());
        constraintLayout.setOnClickListener(v -> {
            lastBoughtUnit.setPosition(new com.destroyordefend.project.Core.Point((int)seekX.toPoint(),(int) seekY.toPoint()));
            System.out.println(lastBoughtUnit.getValues().getName());
            Game.getGame().getAllUnits().add(lastBoughtUnit);
            Log.i("TAG", "onClick: lastBoughtUnit.pos " + lastBoughtUnit.getPosition());
            Toast.makeText(this, "unit is bought", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ShopActivity.class));
        });

        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekX .setValue( progress);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits());

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
                seekY.setValue( progress);
                setTitle("X : " + seekX.toInteger() + " Y : " + seekY.toInteger());
                updateScreen(Game.getGame().getAllUnits());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateScreen(Game.getGame().getAllUnits());
            }
        });
    }

    void updateScreen(TreeSet<Unit> allUnit) {
        Log.i("", "updateScreen: allUnit.size()" + allUnit.size());
        constraintLayout.removeAllViews();
        inRange = new ArrayList<>();
        TypeConverter left = left();
        TypeConverter right = right();
        TypeConverter up = up();
        TypeConverter down = down();
        Log.d("Yaaa", "updateScreen: " + left + " " + right + " " + up + " " + " " + down);
        for (Unit unit : allUnit) {
            System.out.println(unit.getPosition());
            if ((unit.getPosition().getX() >= left.toPoint() && unit.getPosition().getX() <= right.toPoint())
                    && (unit.getPosition().getY() <= down.toPoint() && unit.getPosition().getY() >= up.toPoint())) {
                inRange.add(new MyImage(unit));
            }
        }
        startUpdate();
    }

    void startUpdate() {
        ConstraintLayout.LayoutParams params;
        for (MyImage myImage : inRange) {
            System.out.println("startUpdate: img width" + myImage.getImageView().getLayoutParams().width);
            params = new ConstraintLayout.LayoutParams(myImage.getImageView().getLayoutParams().width, myImage.getImageView().getLayoutParams().width);
            constraintLayout.addView(myImage.getImageView(), params);
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
                    TypeConverter.square+=10;
                    updateScreen(Game.getGame().getAllUnits());

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    TypeConverter.square-=10;
                    updateScreen(Game.getGame().getAllUnits());

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    class MyImage {
        ImageView imageView;
        TypeConverter width;
        com.destroyordefend.project.Core.Point center;

        public MyImage(Unit unit) {
            this.imageView = new ImageView(ArenaActivity.this);
            this.imageView.setImageResource(getSuitableImage(unit.getName()));
            this.width =new TypeConverter( unit.getRadius() * 2, TypeConverter.Type.point);
            imageView.setLayoutParams(new ConstraintLayout.LayoutParams(width.toPixel(),width.toPixel()));
            // imageView.getLayoutParams().height = width*square;
            this.center = new com.destroyordefend.project.Core.Point(unit.getPosition());
           /* this.center.setX(center.getX() - (seekX / 11));
            this.center.setY(center.getY() + (seekY / 11));*/
            TypeConverter centerX = new TypeConverter(center.getX(), TypeConverter.Type.point);
            TypeConverter centerY = new TypeConverter(center.getY(), TypeConverter.Type.point);
            centerX = centerX.minus(left());
            centerY = centerY.minus(up());
            center.setX((int)centerX.toPixel());
            center.setY((int)centerY.toPixel());
            Log.d("", "MyImage: center = " + center);
        }

        public ImageView getImageView() {

            return imageView;
        }

        public int getWidth() {
            return (int)width.toInteger();
        }

        public com.destroyordefend.project.Core.Point getCenter() {
            return center;
        }

        private int getSuitableImage(String type) {
            switch (type) {
                case "TeslaTank":
                    break;
                case "Sniper":
                    break;
                case "Mirage Tank":
                    break;
                case "Infantry":
                    break;
                case "Grizzly Tank":
                    break;
                case "Navy SEAL":
                    break;
                case "Tank Destroyer":
                    break;
                case "Prism Tank":
                    break;
                case "pillbox":
                    break;
                case "Prism Tower":
                    break;
                case "Grand Canon":
                    break;
                case "MainBase":
                    break;
                case "Black Eagle":
                    break;
                case "Pateriot Missile System":
                    break;
            }
            return R.mipmap.test_icon;
        }
    }

    TypeConverter left() {
        return seekX;
    }

    TypeConverter right() {
        return seekX .plus(maxX);
    }

    TypeConverter up() {
        return seekY ;
    }

    TypeConverter down() {
        return seekY .plus(maxY);
    }


}

class TypeConverter {
    public enum Type {
        point, pixel;
    }

    public static int square;
    Type type;
    int value;

    public TypeConverter(Type type) {
        this.type = type;
    }

    public TypeConverter(int value, Type type) {
        this(type);
        this.value = value;
    }

    public TypeConverter(TypeConverter t2) {
        this(t2.value, t2.type);
    }

    int toPixel() {
        if (type == Type.point) {
            type = Type.pixel;
            value *= square;
        }
        return value;
    }

    int toPoint() {
        if (type == Type.pixel) {
            type = Type.point;
            value /= square;
        }
        return value;
    }

    public TypeConverter setValue(int value) {
        this.value = value;
        return this;
    }

    public TypeConverter setValue(int value, Type type) {
        setValue(value);
        this.type = type;
        return this;
    }

    public int toInteger() {
        return value;
    }

    public TypeConverter minus(TypeConverter t2) {
        TypeConverter temp = new TypeConverter(t2);
        temp.setValue(-temp.value);
        return plus(temp);
    }

    public TypeConverter plus(TypeConverter t2) {
        if (type != t2.type) {
            TypeConverter temp = new TypeConverter(t2);
            if (type == Type.pixel)
                temp.toPixel();
            else
                temp.toPoint();
        }
        return new TypeConverter(value + t2.value, type);
    }

    @Override
    public String toString() {
        return "(" + value +","+ type+")";
    }
}