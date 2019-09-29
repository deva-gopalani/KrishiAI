package com.example.android.greenharvest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GovernmentPolicies extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_policies);

        webView=findViewById(R.id.govt_policies_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://kisansamadhan.com/government-scheme-for-farmers/central-government-schemes/");
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }


}
