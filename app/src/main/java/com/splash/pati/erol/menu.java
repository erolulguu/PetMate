package com.splash.pati.erol;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.facebook.login.LoginManager;

public class menu extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfilFragment profilFragment;
    private AramaFragment aramaFragment;
    private MesajFragment mesajFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        profilFragment = new ProfilFragment();
        aramaFragment = new AramaFragment();
        mesajFragment = new MesajFragment();


        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame,profilFragment).commit();





        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_profil :
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(profilFragment);
                        return true;

                    case R.id.nav_arama :
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(aramaFragment);
                        return true;
                    case R.id.nav_mesaj:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(mesajFragment);
                        return true;

                        default:
                            return false;


                }
            }

            private void setFragment(android.support.v4.app.Fragment fragment) {

                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame,fragment).commit();


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }


}
