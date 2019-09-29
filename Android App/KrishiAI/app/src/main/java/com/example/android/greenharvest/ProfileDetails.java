package com.example.android.greenharvest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileDetails extends AppCompatActivity {
    TextView personName, personEmail, saathBara, cropName, soilName;
    DatabaseReference firebase;
    Button editProfile;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        personName = findViewById(R.id.name_value);
        personEmail = findViewById(R.id.emailId_value);
        saathBara = findViewById(R.id.saathBaraNumber_value);
        cropName = findViewById(R.id.crop_type_value);
        soilName = findViewById(R.id.soil_type_value);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        firebase = FirebaseDatabase.getInstance().getReference("/0/users").child(userid);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String saathbara = dataSnapshot.child("saatBara").getValue().toString();
                String soilType = dataSnapshot.child("soil_type").getValue().toString();
                String cropType = dataSnapshot.child("crop_type").getValue().toString();

                personName.setText(name);
                personEmail.setText(email);
                saathBara.setText(saathbara);
                cropName.setText(cropType);
                soilName.setText(soilType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        editProfile = findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileDetails.this,EditProfile.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
}
