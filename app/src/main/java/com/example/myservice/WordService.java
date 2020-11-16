package com.example.myservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WordService extends Service {
    private Timer timer = new Timer();
    private static final long UPDATE_INTERVAL = 5000;
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();
    private String[] fixedList = { "Ubuntu", "Android", "iOX", "Windows 10",
            "Mac OS", "VX Works" };
    private int index = 0;

    public void onCreate() {
        super.onCreate();
        pollForUpdates();
    }

    private void pollForUpdates() {
        //start a task which runs asynchronuously
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Change list, first fill it then delete first element
                // and add it at the end of the list
                if (list.size() >= 6) {
                    list.remove(0);
                }
				list.add(fixedList[index++]);
				if (index >= fixedList.length){
                index = 0;
            }
        }
    }, 0, UPDATE_INTERVAL);
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    // We return the binder class upon a call of bindService
    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }
public class MyBinder extends Binder {
    WordService getService() {
        return WordService.this;
    }
}
    public List<String> getWordList() {
        return list;
    }  }

