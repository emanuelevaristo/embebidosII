package com.blcr.vj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blcr.vj.fragments.addUser;
import com.blcr.vj.fragments.login;
//import com.blcr.vj.networking.userNet;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
        int lang = prefs.getInt("lang", 0);
        // Cambia el idioma de la app
        String lenguaje = "es";
        if( lang == 1){
            lenguaje = "en";
        }
        if(lang == 2)
        {
            lenguaje = "fr";
        }

        Locale loc = new Locale(lenguaje);
        Locale.setDefault(loc);

        Configuration config = new Configuration();
        config.locale = loc;


        FrameLayout container = (FrameLayout) findViewById(R.id.content_frame);

        changeFragment(new login(), "fgLogin");

    }

    private void changeFragment(Fragment newFragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);

        if (currentFragment != null && currentFragment.isVisible())
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, newFragment, tag);
        ft.commit();

    }

    public void onBackPressed() {
        //super.onBackPressed();
        //aqui mando a llamar al fragment anterior
        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
