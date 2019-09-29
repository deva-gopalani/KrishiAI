package com.example.android.greenharvest;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SignupActivity<lat> extends AppCompatActivity implements View.OnClickListener {

    Button soilType;
    Button cropType, currentLoc;
    String[] soilListItems;
    String[] cropListItems; //listItems
    boolean[] soilCheckedItems;
    boolean[] cropCheckedItems;
    ArrayList<Integer> mSoilUserItems = new ArrayList<>();
    ArrayList<Integer> mCropUserItems = new ArrayList<>();
    String itemSoil = "";
    String itemCrop = "";
    EditText etFullName, etUserName, etPassword, etsaathbara, landArea;
    TextView etlatitude, etlongitude;
    ProgressBar progressBar;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public String lat,longg;


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude  = location.getLongitude();
                double latitude  = location.getLatitude();
                lat = Double.toString(latitude);
                longg = Double.toString(longitude);
                etlatitude.setText(lat);
                etlongitude.setText(longg);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };*/


        etFullName = (EditText) findViewById(R.id.etFullName);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etsaathbara = (EditText) findViewById(R.id.etsaathbara);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        landArea =  findViewById(R.id.editTextFarmLand);
        etlatitude = findViewById(R.id.etLat);
        etlongitude = findViewById(R.id.etLong);

        mAuth = FirebaseAuth.getInstance();
        soilListItems = getResources().getStringArray(R.array.soil_type);
        soilCheckedItems = new boolean[soilListItems.length];
        cropListItems = getResources().getStringArray(R.array.crop_type);
        cropCheckedItems = new boolean[cropListItems.length];

        findViewById(R.id.btCreate).setOnClickListener(this);
        soilType = findViewById(R.id.soil_type_button);
        cropType = findViewById(R.id.crop_type_button);



        soilType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mSoilBuilder = new AlertDialog.Builder(SignupActivity.this);
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
                        Toast.makeText(SignupActivity.this,"The selected soil types are " + itemSoil,Toast.LENGTH_LONG).show();
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
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        cropType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mCropBuilder = new AlertDialog.Builder(SignupActivity.this);
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
                        Toast.makeText(SignupActivity.this,"The selected soil types are " + itemCrop,Toast.LENGTH_LONG).show();
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

        /*if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            }
        }*/
    }

    private void registerUser() {
        final String FullName = etFullName.getText().toString().trim();
        final String UserName = etUserName.getText().toString().trim();
        final String Password = etPassword.getText().toString().trim();
        final String et712no = etsaathbara.getText().toString().trim();
        final String area = landArea.getText().toString().trim();
        lat="19.0522";
        longg="72.9005";
        etlatitude.setText(lat);
        etlongitude.setText(longg);
        if(FullName.isEmpty()){
            etFullName.setError("Full Name Is Required");
            etFullName.requestFocus();
            return;
        }

        if(UserName.isEmpty()) {
            etUserName.setError("UserName Is Required");
            etUserName.requestFocus();
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
        if(area.isEmpty()){
            landArea.setError("Land area is required");
            landArea.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(UserName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    String pest = "0", pic="0";
                    User user = new User(UserName, et712no, itemCrop, itemSoil, FullName, lat, longg, area, pest, pic);
                    FirebaseDatabase.getInstance().getReference("/0/users")
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                               startActivity(new Intent(getBaseContext(), MainActivity.class));  //
                               finish();
                           } else {
                               //display a failure message
                           }
                        }
                    });
                    Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"User Registered Successfull",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btCreate:
                registerUser();
                break;
        }

    }
}