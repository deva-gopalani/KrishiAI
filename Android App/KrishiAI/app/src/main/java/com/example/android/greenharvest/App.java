package com.example.android.greenharvest;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "pest_detection";
    public static final String CHANNEL_2_ID = "water_plants";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Pest Detection",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager1 = getSystemService(NotificationManager.class);
            manager1.createNotificationChannel(channel1);

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Water The Plants",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager2 = getSystemService(NotificationManager.class);
            manager2.createNotificationChannel(channel2);
        }
    }
}
