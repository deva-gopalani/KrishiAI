package com.example.android.greenharvest;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.greenharvest.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView waterPlants;
    Button waterPlantButton;
    private RequestQueue mQueue;
    private TextView temp,humidity;
    public String humidityValue,cropType,doublehumidity;
    public static double moistureCutOff, currentMoisture;
    DatabaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        waterPlants = findViewById(R.id.card_notification_heading);
        waterPlantButton = findViewById(R.id.button);
        temp = (TextView) findViewById(R.id.textView7);

        humidity = (TextView) findViewById(R.id.textView12);
        notificationManager=NotificationManagerCompat.from(this);
        mQueue= Volley.newRequestQueue(this);
        jasonParseTemp();
        jasonParseHumidity();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        firebase = FirebaseDatabase.getInstance().getReference("/0/users").child(userid);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cropType = dataSnapshot.child("crop_type").getValue().toString();
                //Toast.makeText(MainActivity.this,cropType,Toast.LENGTH_LONG).show();
                changeCard();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //////////////////////////////////////////////////////////////////////////
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                CardView cardView_constant = findViewById(R.id.card_fixed);
                CardView cardView_notification = findViewById(R.id.card_notification);
                CardView cardView_prediction = findViewById(R.id.card_prediction);
                CardView cardView_pest = findViewById(R.id.card_pest);
                CardView cardView_npk = findViewById(R.id.card_npk);
                CardView cardView_cropSelection = findViewById(R.id.card_crop_selection);
                CardView cardView_yieldPrediction = findViewById(R.id.card_yield_prediction);
                switch (menuItem.getItemId())
                {
                    case R.id.menu_profile:
                        cardView_constant.setVisibility(View.GONE);
                        cardView_notification.setVisibility(View.GONE);
                        cardView_prediction.setVisibility(View.GONE);
                        cardView_pest.setVisibility(View.GONE);
                        cardView_npk.setVisibility(View.GONE);
                        cardView_cropSelection.setVisibility(View.GONE);
                        cardView_yieldPrediction.setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this,ProfileDetails.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_language:
                        cardView_constant.setVisibility(View.GONE);
                        cardView_notification.setVisibility(View.GONE);
                        cardView_prediction.setVisibility(View.GONE);
                        cardView_pest.setVisibility(View.GONE);
                        cardView_npk.setVisibility(View.GONE);
                        cardView_cropSelection.setVisibility(View.GONE);
                        cardView_yieldPrediction.setVisibility(View.GONE);
                        Intent intent1 = new Intent(MainActivity.this,ChangeLanguage.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_govtPolicies:
                        cardView_constant.setVisibility(View.GONE);
                        cardView_notification.setVisibility(View.GONE);
                        cardView_prediction.setVisibility(View.GONE);
                        cardView_pest.setVisibility(View.GONE);
                        cardView_npk.setVisibility(View.GONE);
                        cardView_cropSelection.setVisibility(View.GONE);
                        cardView_yieldPrediction.setVisibility(View.GONE);
                        Intent intent3 = new Intent(MainActivity.this,GovernmentPolicies.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_feedback:
                        cardView_constant.setVisibility(View.GONE);
                        cardView_notification.setVisibility(View.GONE);
                        cardView_prediction.setVisibility(View.GONE);
                        cardView_pest.setVisibility(View.GONE);
                        cardView_npk.setVisibility(View.GONE);
                        cardView_cropSelection.setVisibility(View.GONE);
                        cardView_yieldPrediction.setVisibility(View.GONE);
                        Intent intent4 = new Intent(MainActivity.this,feedBack.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_logout:
                        cardView_constant.setVisibility(View.GONE);
                        cardView_notification.setVisibility(View.GONE);
                        cardView_prediction.setVisibility(View.GONE);
                        cardView_pest.setVisibility(View.GONE);
                        cardView_npk.setVisibility(View.GONE);
                        cardView_cropSelection.setVisibility(View.GONE);
                        cardView_yieldPrediction.setVisibility(View.GONE);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void changeCard(){
        switch (cropType){
            case "Bajra":
                moistureCutOff=20;
                break;
            case "Wheat":
                moistureCutOff=60;
                break;
            case "Maize":
                moistureCutOff=50;
                break;
            case "Potato":
                moistureCutOff=80;
                break;
            case "Onion":
                moistureCutOff=21;
                break;
            case "Tomato":
                moistureCutOff=50;
                break;
            case "Brinjal":
                moistureCutOff=35;
                break;
            case "Sugarcane":
                moistureCutOff=60;
                break;
            case "Rice":
                moistureCutOff=65;
                break;
            case "Groundnut":
                moistureCutOff=25;
                break;
            case "Cauliflower":
                moistureCutOff=50;
                break;
            case "Cabbage":
                moistureCutOff=50;
                break;
            case "Turmeric":
                moistureCutOff=60;
                break;
            case "Sunflower":
                moistureCutOff=50;
                break;
            case "Barley":
                moistureCutOff=30;
                break;
        }
        //jasonParseHumidity();
        //currentMoisture = Double.valueOf(humidityValue);
        //currentMoisture = 50;
         if(currentMoisture>=moistureCutOff)
        {   //Toast.makeText(MainActivity.this,""+1,Toast.LENGTH_LONG).show();
            waterPlants.setText(getString(R.string.no_need_to_water));
            waterPlants.setTextColor(Color.BLACK);
            waterPlantButton.setEnabled(false);
        }
        else{
            //Toast.makeText(MainActivity.this,""+0,Toast.LENGTH_LONG).show();

            //String x = Double.toString(moistureCutOff);
            //Toast.makeText(MainActivity.this,x,Toast.LENGTH_LONG).show();
            waterPlants.setText(getString(R.string.water_the_plants));
            waterPlants.setTextColor(Color.RED);
            displayNotification();
            waterPlantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    waterPlants.setText(getString(R.string.done_watering));
                    waterPlants.setTextColor(Color.BLACK);
                }
            });
        }
    };
    public void displayNotification(){
        RemoteViews collapseView = new RemoteViews(getPackageName(),R.layout.custom_notification);
        collapseView.setTextViewText(R.id.notification_heading,getString(R.string.water_the_plants));
        collapseView.setTextViewText(R.id.notification_content,getString(R.string.click_here_to_view));
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID).setSmallIcon(R.drawable.logo_circle)
                .setCustomContentView(collapseView).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setContentIntent(contentIntent).setAutoCancel(true).build();
        notificationManager.notify(1,notification);
    };
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void pestDetails(View view) {
        Intent intent = new Intent(this,PestDetails.class);
        startActivity(intent);
    }

    public void marketPrediction(View view){
        Intent intent = new Intent(this,MarketPrediction.class);
        startActivity(intent);
    }
    public void npkValues(View view){
        Intent intent = new Intent(this,NPKValues.class);
        startActivity(intent);
    }
    public void cropSelection(View view){
        Intent intent = new Intent(this,CropSelection.class);
        startActivity(intent);
    }
    public void yieldPrediction(View view){
        Intent intent = new Intent(this,YieldPrediction.class);
        startActivity(intent);
    }

    private void jasonParseTemp(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://thingspeak.com/channels/851800/field/1/last";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        temp.setText(response.substring(0,5));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                temp.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void jasonParseHumidity(){

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://thingspeak.com/channels/853063/field/1/last";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        humidityValue = response.substring(0,5);
                        humidity.setText(humidityValue);
                        //Toast.makeText(MainActivity.this,humidityValue,Toast.LENGTH_LONG).show();
                        currentMoisture = Double.valueOf(humidityValue);
                       // Toast.makeText(MainActivity.this,""+humidityValue,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                humidity.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
