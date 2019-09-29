package com.example.android.greenharvest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PushCode extends AppCompatActivity {

    Button pushHumidity, pushTemp;
    private FirebaseAuth mAuth;
    DatabaseReference databaseHum, databaseTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_code);

        pushHumidity = findViewById(R.id.push_humidity);
        pushTemp = findViewById(R.id.push_temp);

        databaseHum = FirebaseDatabase.getInstance().getReference("/0/humidity");
        databaseTemp = FirebaseDatabase.getInstance().getReference("/0/temperature");
        pushHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] intArray = new int[]{ 88,65,75,40,60,90,67 };
                for(int i=0; i<=6; i++)
                {
                    String arr = Integer.toString(intArray[i]);

                    if(!TextUtils.isEmpty(arr)){
                        String id = databaseHum.push().getKey();
                        Humidity humidity = new Humidity(arr);
                        databaseHum.child(id).setValue(humidity);
                    }

                }
            }
        });

        pushTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] intArray = new int[]{ 22,29,31,32,35,20,19 };
                for(int i=0; i<=6; i++)
                {
                    String arr = Integer.toString(intArray[i]);

                    if(!TextUtils.isEmpty(arr)){
                        String id = databaseTemp.push().getKey();
                        Temperature humidity = new Temperature(arr);
                        databaseTemp.child(id).setValue(humidity);
                    }

                }

            }
        });
    }

}
