package com.example.android.greenharvest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_change_language);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English","हिंदी","मराठी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChangeLanguage.this);
        mBuilder.setTitle("Choose Language..");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLocale("en");
                    recreate();
                }
                else if (i==1){
                    setLocale("hi");
                    recreate();
                }
                else if (i==2){
                    setLocale("mr");
                    recreate();
                }

                dialogInterface.dismiss();

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext( ).getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
}
