package com.example.android.greenharvest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NPKValues extends AppCompatActivity {

    Button nitrogenButton, phosphorusButton, potassiumButton, submitDetails;
    String[] phosphorusColour, potassiumColour, nitrogenColour;
    boolean[] nitrogenCheckedItems, phosphorusCheckedItems, potassiumCheckedItems;
    ArrayList<Integer> mNitrogenUserItems = new ArrayList<>();
    ArrayList<Integer> mPotassiumUserItems = new ArrayList<>();
    ArrayList<Integer> mPhosphorusUserItems = new ArrayList<>();
    String itemPotassium="", itemPhosphorus="", itemNitrogen = "";
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npkvalues);

        phosphorusColour = getResources().getStringArray(R.array.phosphorus_colour);
        phosphorusCheckedItems = new boolean[phosphorusColour.length];
        potassiumColour = getResources().getStringArray(R.array.potassium_colour);
        potassiumCheckedItems = new boolean[potassiumColour.length];
        nitrogenColour = getResources().getStringArray(R.array.nitrogen_colour);
        nitrogenCheckedItems = new boolean[nitrogenColour.length];
        nitrogenButton = findViewById(R.id.button_nitrogen);
        phosphorusButton = findViewById(R.id.button_phosphorus);
        potassiumButton = findViewById(R.id.button_potassium);
        submitDetails=findViewById(R.id.button_submit);

        nitrogenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mNitrogenBuilder = new AlertDialog.Builder(NPKValues.this);
                mNitrogenBuilder.setTitle("Choose Value");
                mNitrogenBuilder.setMultiChoiceItems(nitrogenColour, nitrogenCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mNitrogenUserItems.contains(position)){
                                mNitrogenUserItems.add(position);
                            }
                        }
                        else if(mNitrogenUserItems.contains(position)){
                            mNitrogenUserItems.remove(position);
                        }
                    }
                });
                mNitrogenBuilder.setCancelable(false);
                mNitrogenBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //String itemSoil = "";
                        for(int j = 0; j < mNitrogenUserItems.size(); j++){
                            itemNitrogen = itemNitrogen + nitrogenColour[mNitrogenUserItems.get(j)];
                            if(j!=mNitrogenUserItems.size()-1){
                                itemNitrogen = itemNitrogen + ", ";
                            }
                        }
                        Toast.makeText(NPKValues.this,"The selected value is " + itemNitrogen,Toast.LENGTH_LONG).show();
                    }
                });
                mNitrogenBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mNitrogenBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i < nitrogenCheckedItems.length;i++){
                            nitrogenCheckedItems[i] = false;
                            mNitrogenUserItems.clear();
                        }
                    }
                });
                AlertDialog mNitrogenDialog = mNitrogenBuilder.create();
                mNitrogenDialog.show();
            }
        });

        phosphorusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mPhosphorusBuilder = new AlertDialog.Builder(NPKValues.this);
                mPhosphorusBuilder.setTitle("Choose Value");
                mPhosphorusBuilder.setMultiChoiceItems(phosphorusColour, phosphorusCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mPhosphorusUserItems.contains(position)){
                                mPhosphorusUserItems.add(position);
                            }
                        }
                        else if(mPhosphorusUserItems.contains(position)){
                            mPhosphorusUserItems.remove(position);
                        }
                    }
                });
                mPhosphorusBuilder.setCancelable(false);
                mPhosphorusBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //String itemSoil = "";
                        for(int j = 0; j < mPhosphorusUserItems.size(); j++){
                            itemPhosphorus = itemPhosphorus + phosphorusColour[mPhosphorusUserItems.get(j)];
                            if(j!=mPhosphorusUserItems.size()-1){
                                itemPhosphorus = itemPhosphorus + ", ";
                            }
                        }
                        Toast.makeText(NPKValues.this,"The selected value is " + itemPhosphorus,Toast.LENGTH_LONG).show();
                    }
                });
                mPhosphorusBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mPhosphorusBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i < phosphorusCheckedItems.length;i++){
                            phosphorusCheckedItems[i] = false;
                            mPhosphorusUserItems.clear();
                        }
                    }
                });
                AlertDialog mPhosphorusDialog = mPhosphorusBuilder.create();
                mPhosphorusDialog.show();
            }
        });
        potassiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mPotassiumBuilder = new AlertDialog.Builder(NPKValues.this);
                mPotassiumBuilder.setTitle("Choose Value");
                mPotassiumBuilder.setMultiChoiceItems(potassiumColour, potassiumCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mPotassiumUserItems.contains(position)){
                                mPotassiumUserItems.add(position);
                            }
                        }
                        else if(mPotassiumUserItems.contains(position)){
                            mPotassiumUserItems.remove(position);
                        }
                    }
                });
                mPotassiumBuilder.setCancelable(false);
                mPotassiumBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //String itemSoil = "";
                        for(int j = 0; j < mPotassiumUserItems.size(); j++){
                            itemPotassium = itemPotassium + potassiumColour[mPotassiumUserItems.get(j)];
                            if(j!=mPotassiumUserItems.size()-1){
                                itemPotassium = itemPotassium + ", ";
                            }
                        }
                        Toast.makeText(NPKValues.this,"The selected value is " + itemPotassium,Toast.LENGTH_LONG).show();
                    }
                });
                mPotassiumBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mPotassiumBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        for(int i=0; i < potassiumCheckedItems.length;i++){
                            potassiumCheckedItems[i] = false;
                            mPotassiumUserItems.clear();
                        }
                    }
                });
                AlertDialog mPotassiumDialog = mPotassiumBuilder.create();
                mPotassiumDialog.show();
                submitDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        String uid=user.getUid();
                        /*Toast.makeText(EditProfile.this,"UID id"+uid,Toast.LENGTH_LONG).show();*/
                        database = FirebaseDatabase.getInstance().getReference("/0/users").child(uid);

                        database.child("nitrogenValues").setValue(itemNitrogen);
                        database.child("potassiumValues").setValue(itemPotassium);
                        database.child("phosphorusValues").setValue(itemPhosphorus);
                        Toast.makeText(NPKValues.this,"Values Sent Successfully",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
