package com.home.reviewatm;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class HelloService extends IntentService {
    private static final String TAG = HelloService.class.getSimpleName();
    public static final String ACTION_HELLO_DONE = "action_hello_done";

    public HelloService() {
        super("HelloService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(5000);
            Log.d(TAG, "onHandleIntent: " + intent.getStringExtra("NAME") );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent done = new Intent();
        done.setAction(ACTION_HELLO_DONE);
        sendBroadcast(done);


    }

}
