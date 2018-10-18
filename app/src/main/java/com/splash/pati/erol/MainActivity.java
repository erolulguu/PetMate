package com.splash.pati.erol;

import android.*;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    TextView txt1;

    EditText emaill;
    EditText sifree;


    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private FirebaseAuth mAuth;

    private static final String TAG = "FACELOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.Theme_AppCompat);

        printKeyHash();

        emaill = (EditText)findViewById(R.id.editText);
        sifree = (EditText)findViewById(R.id.editText2);
        txt = (TextView) findViewById(R.id.textView2);
        txt1 = (TextView) findViewById(R.id.textView3);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), kayit_ol.class);
                startActivity(intent);
            }

        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emaill.getText().toString();
                final String sifre = sifree.getText().toString();
                mAuth.signInWithEmailAndPassword(email, sifre).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "giris hatasi", Toast.LENGTH_LONG).show();
                        }else {

                            Intent intent = new Intent(getApplication(), menu.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

        mAuth = FirebaseAuth.getInstance();


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Intent intent=new Intent(getBaseContext(),menu.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        //is already logged with facebook
        if(Profile.getCurrentProfile() != null)
        {
            Intent intent = new Intent(getApplication(), menu.class);
            startActivity(intent);
        }

    }





    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    private void updateUI() {
       // Toast.makeText(MainActivity.this,"Giriş Yaptın",Toast.LENGTH_LONG).show();
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("OnComplete","oldu");
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }




    void printKeyHash()
    {
        try{
            PackageInfo info=getPackageManager().getPackageInfo("com.splash.pati.erol", PackageManager.GET_SIGNATURES);
            for(Signature s:info.signatures)
            {
                MessageDigest md= MessageDigest.getInstance("SHA");
                md.update(s.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



}
