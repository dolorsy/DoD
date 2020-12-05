package com.dolor.destroyordefense;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.destroyordefend.project.Core.Game;
import com.destroyordefend.project.Unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static com.dolor.destroyordefense.AndroidManger.lastBoughtUnit;

public class ArenaActivity extends GeneralActivity {
    TypeConverter maxX, maxY;
    TypeConverter seekX = new TypeConverter(Type.point),
            seekY = new TypeConverter(Type.point);
    RelativeLayout relativeLayout;
    SeekBar xSeekBar, ySeekBar;
    List<MyImage> inRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle("X : " + seekX + " Y : " + seekY);

        xSeekBar = findViewById(R.id.XseekBar);
        ySeekBar = findViewById(R.id.YseekBar);

        relativeLayout = findViewById(R.id.my_relative_layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        TypeConverter.square = 1000;

        maxX = new TypeConverter(mdispSize.x, Type.pixel);
        maxY = new TypeConverter(mdispSize.y, Type.pixel);
        Log.i("", "onCreate: maxX/Y" + maxX + " " + maxY);
        relativeLayout.setX(0);
        relativeLayout.setY(0);
        Log.d("TAG", "onCreate: allUnit.size()" + Game.getGame().getAllUnits().size());
        updateScreen(Game.getGame().getAllUnits());
        relativeLayout.setOnLongClickListener(v -> {
            lastBoughtUnit.setPosition(new com.destroyordefend.project.Core.Point((int) seekX.toPoint(), (int) seekY.toPoint()));
            System.out.println(lastBoughtUnit.getValues().getName());
            Game.getGame().getAllUnits().add(lastBoughtUnit);
            Log.i("TAG", "onClick: lastBoughtUnit.pos " + lastBoughtUnit.getPosition());
            Toast.makeText(this, "unit is bought", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ShopActivity.class));
            return true;
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

    /*void updateScreen(TreeSet<Unit> allUnit) {
        Log.i("", "updateScreen: allUnit.size()" + allUnit.size());
        relativeLayout.removeAllViews();
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
*/
    void updateScreen(TreeSet<Unit> allUnits) {
        relativeLayout.removeAllViews();
        relativeLayout.requestLayout();
        for (Unit unit : allUnits) {
            inRange.add(new MyImage(unit));
        }
        startUpdate();
    }

    void startUpdate() {
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
                    TypeConverter.square+=10;
                    updateScreen(Game.getGame().getAllUnits());

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN && TypeConverter.square > 10) {
                    TypeConverter.square -= 10;
                    updateScreen(Game.getGame().getAllUnits());

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

    enum Type {
        point, pixel;
    }

    static class TypeConverter {
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
            return "(" + value + "," + type + ")";
        }
    }

    class MyImage {
        ImageView imageView;
        GeneralActivity context;
        TypeConverter width;
        com.destroyordefend.project.Core.Point center;

        public MyImage(Unit unit) {

            this.imageView = new ImageView(ArenaActivity.this.relativeLayout.getContext());
            this.imageView.setImageResource(getSuitableImage(unit.getName()));
            this.width = new TypeConverter(unit.getRadius() * 2, Type.point);
            this.center = new com.destroyordefend.project.Core.Point(unit.getPosition());
            TypeConverter centerX = new TypeConverter(center.getX(), Type.point);
            TypeConverter centerY = new TypeConverter(center.getY(), Type.point);
            centerX = centerX.minus(left());
            centerY = centerY.minus(up());
            center.setX(centerX.toPixel());
            center.setY(centerY.toPixel());
            Log.d("", "MyImage: center = " + center);
        }

        public ImageView getImageView() {

            return imageView;
        }

        public int getWidth() {
            return width.toInteger();
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
}
