/**
 *
 */
package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author tusm
 */
public class BackgroudService extends Service {
    private DownloadBinder IBinder;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        IBinder = new DownloadBinder();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return IBinder;
    }

    public class DownloadBinder extends Binder {
        public void start() {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //TODO

                }
            };
        }

    }
}
