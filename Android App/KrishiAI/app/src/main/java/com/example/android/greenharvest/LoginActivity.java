package com.example.android.greenharvest;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName,etPassword;
    TextView tvsignup;
    Button loginButton;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvsignup = (TextView) findViewById(R.id.tvSignup);
        loginButton = (Button) findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }

            private void userLogin() {

                String UserName = etUserName.getText().toString().trim();
                String Password = etPassword.getText().toString().trim();

                if(UserName.isEmpty()) {
                    etUserName.setError("UserName Is Required");
                    etUserName.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(UserName).matches()){
                    etUserName.setError("Please Enter Proper Email");
                    etUserName.requestFocus();
                }

                if(Password.isEmpty()) {
                    etPassword.setError("Password Is Required");
                    etPassword.requestFocus();
                    return;
                }

                if (Password.length() <6 ){
                    etPassword.setError("Minimum Length Reqired Is 6");
                    etPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(UserName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            Intent intent =  new Intent(LoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
    protected void onStart(){
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

}
