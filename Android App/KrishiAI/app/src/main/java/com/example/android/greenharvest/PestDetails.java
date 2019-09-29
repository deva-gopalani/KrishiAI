package com.example.android.greenharvest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.greenharvest.App.CHANNEL_1_ID;

public class PestDetails extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;

    TextView pestUpdate;
    DatabaseReference firebase;
    public String pestBinaryValue;
    public int flag =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_details);
        pestUpdate = findViewById(R.id.textViewPest);
        notificationManager=NotificationManagerCompat.from(this);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        firebase = FirebaseDatabase.getInstance().getReference("/0/users").child(userid);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pestBinaryValue = dataSnapshot.child("pest").getValue().toString();
                //Toast.makeText(PestDetails.this,pestBinaryValue,Toast.LENGTH_LONG).show();

                if(pestBinaryValue.equals("1")){
                    pestUpdate.setText(getString(R.string.pest_detected));
                    pestUpdate.setTextColor(Color.RED);
                    displayNotification();
                }
                if(pestBinaryValue.equals("0")){
                    pestUpdate.setText(getString(R.string.no_pest));
                    pestUpdate.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void displayNotification(){
        RemoteViews collapseView = new RemoteViews(getPackageName(),R.layout.custom_notification);
        collapseView.setTextViewText(R.id.notification_heading,getString(R.string.pest_detected));
        collapseView.setTextViewText(R.id.notification_content,getString(R.string.click_here_to_view));
        Intent intent = new Intent(this,PestDetails.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID).setSmallIcon(R.drawable.logo_circle)
                .setCustomContentView(collapseView).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setContentIntent(contentIntent).setAutoCancel(true).build();
        notificationManager.notify(1,notification);
    };
}
