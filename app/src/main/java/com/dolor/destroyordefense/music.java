package com.dolor.destroyordefense;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class music extends Service {
    MediaPlayer mp;
    public music() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this,R.raw.csgo);
        mp.setLooping(false);
        mp.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

}