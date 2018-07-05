package com.example.kim.deomver1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    String stEmail;
    String stPassWord;
    EditText etEmail;
    EditText etPassWord;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etEmail = (EditText)findViewById(R.id.etLogin);
        etPassWord = (EditText)findViewById(R.id.etPassword);


        pbLogin = (ProgressBar)findViewById(R.id.pbLogin);
        Button btnRegister = (Button)findViewById(R.id.btnJoin);

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    SharedPreferences sharedPreferences = getSharedPreferences("email", MODE_PRIVATE);
                    /*
                    editor.putString("email",user.getEmail());
                    editor.apply();*/

                }else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               stEmail = etEmail.getText().toString();
               stPassWord = etPassWord.getText().toString();

                if(stEmail.isEmpty() || stEmail.equals("") || stPassWord.isEmpty() || stPassWord.equals("")){
                    Toast.makeText(MainActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, stEmail+" "+stPassWord, Toast.LENGTH_SHORT).show();
                    regiserUser(stEmail,stPassWord);
                }

            }
        });

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stEmail = etEmail.getText().toString();
                stPassWord = etPassWord.getText().toString();
                if(stEmail.isEmpty() || stEmail.equals("") || stPassWord.isEmpty() || stPassWord.equals("")){
                    Toast.makeText(MainActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    userLogin(stEmail,stPassWord);
                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }


    public void regiserUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void userLogin(String email, String password){
        pbLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signinWithEmail:onComplete:"+ task.isSuccessful());

                        pbLogin.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success"+task.getException());
                            Toast.makeText(MainActivity.this, "Authentification failed", Toast.LENGTH_SHORT).show();

                        }else {
                            Intent in = new Intent(MainActivity.this, ChatAtivity.class);
                            startActivity(in);
                            finish();

                        }
                    }
                });
    }
}
