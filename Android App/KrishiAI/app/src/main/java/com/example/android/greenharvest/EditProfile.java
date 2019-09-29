package com.example.android.greenharvest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    Button soilType, cropType, updateDetails;
    String[] soilListItems;
    String[] cropListItems; //listItems
    boolean[] soilCheckedItems;
    boolean[] cropCheckedItems;
    ArrayList<Integer> mSoilUserItems = new ArrayList<>();
    ArrayList<Integer> mCropUserItems = new ArrayList<>();
    String itemSoil = "";
    String itemCrop = "";
    EditText etFullName,etPassword,etsaathbara, landArea;
    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        updateDetails = findViewById(R.id.btUpdateDetails);
        soilListItems = getResources().getStringArray(R.array.soil_type);
        soilCheckedItems = new boolean[soilListItems.length];
        cropListItems = getResources().getStringArray(R.array.crop_type);
        cropCheckedItems = new boolean[cropListItems.length];
        soilType = findViewById(R.id.edit_soil_type_button);
        cropType = findViewById(R.id.edit_crop_type_button);
        landArea=findViewById(R.id.editTextArea);
        etFullName = (EditText) findViewById(R.id.etFullName);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etsaathbara = (EditText) findViewById(R.id.etsaathbara);
        soilType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mSoilBuilder = new AlertDialog.Builder(EditProfile.this);
                mSoilBuilder.setTitle("Choose Soil Type");
                mSoilBuilder.setMultiChoiceItems(soilListItems, soilCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mSoilUserItems.contains(position)){
                                mSoilUserItems.add(position);
                            }
                        }
                        else if(mSoilUserItems.contains(position)){
                            mSoilUserItems.remove(position);
                        }

                    }
                });
                mSoilBuilder.setCancelable(false);
                mSoilBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //String itemSoil = "";
                        for(int j = 0; j < mSoilUserItems.size(); j++){
                            itemSoil = itemSoil + soilListItems[mSoilUserItems.get(j)];
                            if(j!=mSoilUserItems.size()-1){
                                itemSoil = itemSoil + ", ";
                            }
                        }
                        Toast.makeText(EditProfile.this,"The selected soil types are " + itemSoil,Toast.LENGTH_LONG).show();
                    }

                });

                mSoilBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mSoilBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i < soilCheckedItems.length;i++){
                            soilCheckedItems[i] = false;
                            mSoilUserItems.clear();

                        }
                    }
                });
                AlertDialog mSoilDialog = mSoilBuilder.create();
                mSoilDialog.show();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        cropType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mCropBuilder = new AlertDialog.Builder(EditProfile.this);
                mCropBuilder.setTitle("Choose Crop Type");
                mCropBuilder.setMultiChoiceItems(cropListItems, cropCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mCropUserItems.contains(position)){
                                mCropUserItems.add(position);
                            }
                        }
                        else if(mCropUserItems.contains(position)){
                            mCropUserItems.remove(position);
                        }

                    }
                });
                mCropBuilder.setCancelable(false);
                mCropBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //String itemCrop = "";
                        for(int j = 0; j < mCropUserItems.size(); j++){
                            itemCrop = itemCrop + cropListItems[mCropUserItems.get(j)];
                            if(j!=mSoilUserItems.size()-1){
                                itemCrop = itemCrop + ", ";
                            }
                        }
                        Toast.makeText(EditProfile.this,"The selected soil types are " + itemCrop,Toast.LENGTH_LONG).show();
                    }
                });

                mCropBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mCropBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i < cropCheckedItems.length;i++){
                            cropCheckedItems[i] = false;
                            mCropUserItems.clear();
                        }
                    }
                });
                AlertDialog mSoilDialog = mCropBuilder.create();
                mSoilDialog.show();
            }
        });

        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String FullName = etFullName.getText().toString().trim();
                final String Password = etPassword.getText().toString().trim();
                final String et712no = etsaathbara.getText().toString().trim();
                final String area = landArea.getText().toString().trim();
                if(FullName.isEmpty()){
                    etFullName.setError("Full Name Is Required");
                    etFullName.requestFocus();
                    return;
                }


                if(Password.isEmpty()) {
                    etPassword.setError("Password Is Required");
                    etPassword.requestFocus();
                    return;
                }


                if(et712no.isEmpty()) {
                    etsaathbara.setError("Saathbara Number Is Required");
                    etsaathbara.requestFocus();
                    return;
                }
                if(area.isEmpty()) {
                    landArea.setError("Land Area Is Required");
                    landArea.requestFocus();
                    return;
                }
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();
                /*Toast.makeText(EditProfile.this,"UID id"+uid,Toast.LENGTH_LONG).show();*/
                databaseUsers = FirebaseDatabase.getInstance().getReference("/0/users").child(uid);

                user.updatePassword(Password);
                databaseUsers.child("name").setValue(FullName);
                databaseUsers.child("saatBara").setValue(et712no);
                databaseUsers.child("crop_type").setValue(itemCrop);
                databaseUsers.child("soil_type").setValue(itemSoil);
                databaseUsers.child("area").setValue(area);
                Toast.makeText(EditProfile.this,"Details Updated Successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ProfileDetails.class));
        finish();

    }
}
