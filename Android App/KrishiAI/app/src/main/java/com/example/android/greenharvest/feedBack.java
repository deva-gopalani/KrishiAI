package com.example.android.greenharvest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class feedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Button startBtn = (Button) findViewById(R.id.btn_feedBack_submit);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        Log.i("Send email", "");

        EditText summary = (EditText) findViewById(R.id.feedBack_Response);
        String value = summary.getText().toString();
        RatingBar rateBar;
        rateBar = findViewById(R.id.ratingBar);
        String[] TO = {"2016.raghav.heda@ves.ac.in"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Float rate = rateBar.getRating();
        String rates = Float.toString(rate);
        value = "Your Feedback: "+value+"\n"+"Rating: "+rates+"/5";
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack For KrishiAI");
        emailIntent.putExtra(Intent.EXTRA_TEXT,value);
        /*emailIntent.putExtra(Intent.EXTRA_TEXT,rates);*/
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(feedBack.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
}
