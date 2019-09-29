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

public class CropSelection extends AppCompatActivity {

    private TextView cropSelect;
    private RequestQueue mQueue;
    String URL ="https://clustering.pythonanywhere.com/clustering";
    public Button buttonPredict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_selection);

        cropSelect = findViewById(R.id.cropSelect);
        buttonPredict = findViewById(R.id.cropSelect_btn);
        mQueue = Volley.newRequestQueue(this);

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
        //String URL ="https://enginx.pythonanywhere.com/price";

        //mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(CropSelection.this, response, Toast.LENGTH_LONG).show();
                parseData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CropSelection.this,error.toString(),Toast.LENGTH_LONG).show();
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
            String[] values = output.split("//");

            for(int i=0;i<values.length;i++){
                values[i]=values[i].replaceAll("\\[","");
                values[i]=values[i].replaceAll("\\]","");
                values[i]=values[i].replaceAll("\"","");
                cropSelect.append(values[i]+"\n");
                buttonPredict.setEnabled(false);
            }
            //JSONObject crop = dataArray.getJSONObject(i);
            //String market = crop.getString("For market");

            //mTextViewResultV.append(crop.getString("variety"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
