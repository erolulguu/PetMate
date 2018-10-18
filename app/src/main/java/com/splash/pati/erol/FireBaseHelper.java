package com.splash.pati.erol;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Aceer on 20.04.2018.
 */

public class FireBaseHelper {
    FirebaseDatabase database;
    DatabaseReference ref;

    public FireBaseHelper() {
        database = FirebaseDatabase.getInstance();

    }
    public void KullaniciEkle(Kullanicilar kullanicilar,String UId){
        if (kullanicilar != null){
            ref = database.getReference("Kullanicilar").child(UId);
            ref.push().setValue(kullanicilar);
        }
    }
}
