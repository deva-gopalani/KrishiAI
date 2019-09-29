package com.example.android.greenharvest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class YieldPrediction extends AppCompatActivity {

    private TextView getYield;
    private RequestQueue mQueue;
    String URL ="https://yield.pythonanywhere.com/y";
    public Button buttonPredict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yield_prediction);

        getYield = findViewById(R.id.yieldPredict);
        buttonPredict = findViewById(R.id.yieldPredict_btn);
        mQueue = Volley.newRequestQueue(this);

        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jasonParse();
            }
        });

    }
    private void jasonParse(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userid=user.getUid();
        final String uid = userid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(YieldPrediction.this, response, Toast.LENGTH_LONG).show();
                parseData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YieldPrediction.this,error.toString(),Toast.LENGTH_LONG).show();
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
            String output = jsonObject.getString("prediction");
            output=output.replaceAll("\\[","");
            output=output.replaceAll("\\]","");
            getYield.append("Expected yield for your crop is "+output+" kg/hectares based on current area of farm");
            buttonPredict.setEnabled(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
