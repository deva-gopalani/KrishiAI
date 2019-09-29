package com.example.android.greenharvest;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MarketPrediction extends AppCompatActivity {

    String URL ="https://enginx.pythonanywhere.com/price";

    private TextView mTextViewResult,mTextViewResultV;
    private RequestQueue mQueue;
    public Button buttonPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_prediction);
        mTextViewResult = findViewById(R.id.text_view_result);
        //mTextViewResultV = findViewById(R.id.text_view_result_variety);
        buttonPredict = findViewById(R.id.btn_market_predict);
        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jasonParse();
            }
        });
    }

    private void jasonParse(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        final String userid=user.getUid();
        final String uid = userid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MarketPrediction.this, response, Toast.LENGTH_LONG).show();
                parseData(response);
            }
            },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MarketPrediction.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void parseData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            //if (jsonObject.getString("status").equals("true")) {
            //  JSONArray dataArray = jsonObject.getJSONArray("prediction");
            //for (int i = 0; i < dataArray.length(); i++) {
            String output = jsonObject.getString("prediction");
            String[] values = output.split(",");

            for(int i=0;i<values.length;i++){
                values[i]=values[i].replaceAll("\\[","");
                values[i]=values[i].replaceAll("\\]","");
                values[i]=values[i].replaceAll("\"","");
                mTextViewResult.append((i+1)+")"+values[i]+"\n");
                buttonPredict.setEnabled(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
