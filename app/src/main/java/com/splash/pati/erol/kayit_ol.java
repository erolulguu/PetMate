package com.splash.pati.erol;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class kayit_ol extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button btn;
    TextView txt2;
    TextView txt1;
    EditText AdSoyad;


    private RadioGroup radioGroup;
    private EditText emaill, sifree, namee;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        txt1 = (TextView) findViewById(R.id.textView2);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        AdSoyad = (EditText) findViewById(R.id.editTextname);
        Button button = (Button) findViewById(R.id.buttonn);
        btn = (Button) findViewById(R.id.button2);
        txt2 = (TextView) findViewById(R.id.textView5);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emaill.getText().toString();
                final String sifre = sifree.getText().toString();
                final TextView textView = (TextView) findViewById(R.id.textView);
                if (!email.isEmpty() && !sifre.isEmpty() &&
                        AdSoyad != null && txt2 != null && radioGroup.getCheckedRadioButtonId() != -1 && textView != null  ) {
                    Log.i("email sifre ekrana yaz",email + "-"+ sifre);
                    mAuth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(kayit_ol.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(kayit_ol.this, "Hatalı Email-Şifre", Toast.LENGTH_LONG).show();
                            } else {
                                String userId = mAuth.getCurrentUser().getUid();
                                Toast.makeText(kayit_ol.this, "kayit olundu", Toast.LENGTH_LONG).show();
                                FireBaseHelper fh = new FireBaseHelper();
                                Kullanicilar kullanici = new Kullanicilar(
                                        AdSoyad.getText().toString(),
                                        txt2.getText().toString(),
                                        ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(),
                                        textView.getText().toString()


                                );
                                fh.KullaniciEkle(kullanici, userId);
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);

                            }
                        }
                    });
                }
                else
                    Toast.makeText(kayit_ol.this, "Bilgilerinizi Eksiksiz Giriniz", Toast.LENGTH_LONG).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,}, 1000);
                } else {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String sehir = konumum(location.getLatitude(), location.getLongitude());
                        txt2.setText(sehir);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(kayit_ol.this, "Not Found!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    /*Intent intent = new Intent(kayit_ol.this,MainActivity.class);
                    startActivity(intent);
                    finish();*/
                    return;
                }
            }
        };

        emaill = (EditText) findViewById(R.id.editText);
        sifree = (EditText) findViewById(R.id.editText2);


    }

    public void kayitt(View view) {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String sehir = konumum(location.getLatitude(), location.getLongitude());
                        txt2.setText(sehir);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(kayit_ol.this, "Not Found!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ii, int ii1, int ii2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ii);
        c.set(Calendar.MONTH, ii1);
        c.set(Calendar.DAY_OF_MONTH, ii2);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(currentDateString);

    }

    private String konumum(double lat, double lon) {
        String Sehir = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0) {
                for (Address adr : addresses)
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        Sehir = adr.getLocality();
                        break;
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Sehir;

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

}

