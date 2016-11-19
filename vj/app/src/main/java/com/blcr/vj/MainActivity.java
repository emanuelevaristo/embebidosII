package com.blcr.vj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.blcr.vj.R;
import com.blcr.vj.adapters.DrawerAdapter;
import com.blcr.vj.adapters.vjAdapter;
import com.blcr.vj.fragments.addUser;
import com.blcr.vj.fragments.addVj;
import com.blcr.vj.fragments.configuraciones;
import com.blcr.vj.fragments.listavj;
import com.blcr.vj.fragments.misVJS;
import com.blcr.vj.model.VJs;
//import com.blcr.vj.networking.userNet;
import com.blcr.vj.utils.Utils;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private String[] sectionsTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        vista();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListView = (ListView) findViewById(R.id.drawer_listview);
        sectionsTitle = getResources().getStringArray(R.array.str_array_sectionsTitle);

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(sectionsTitle));
        drawerListView.setAdapter(adapter);

        final int idu;
        int iddd= 0;
        int check = 0;
        try {
            iddd = getIntent().getExtras().getInt("id");
            check = getIntent().getExtras().getInt("check");
        }catch (Exception ex)
        {
            iddd = 0;
        }

        idu = iddd;

        if(idu == 0)
        {

           // new userNet(this).execute("get");
        }

        //Utils.showToast(MainActivity.this, idu + "");

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Se dio click en Crear Titulo
                    Bundle arguments = new Bundle();
                    arguments.putInt("id", idu);
                    listavj frg = listavj.newInstance(arguments);
                    changeFragment(frg, "fgListaVj");
                } else if (position == 1) {
                    // Se dio click en Crear Nota
                    changeFragment(new misVJS(), "fgMyvjs");
                } else if (position == 2) {
                    // Se dio click en Mis Notas
                    changeFragment(new addVj(), "fgAddVJ");
                    //Intent i = new Intent(parent.getContext(), MapsActivity.class);
                    //parent.getContext().startActivity(i);
                } else if (position == 3) {
                    // Se dio click en Configuracion
                    changeFragment(new configuraciones(), "fgConfiguration");
                }
                drawerLayout.closeDrawers();
            }
        });


        //Bundle arguments = new Bundle();
        //arguments.putInt("id", idu);
       // listavj frg = listavj.newInstance(arguments);
       // changeFragment(frg, "fgListaVj");

    }


    private void changeFragment(Fragment newFragment, String tag) {

        FragmentManager fragmentManager = getFragmentManager();

        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);

        if (currentFragment != null && currentFragment.isVisible())
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, newFragment, tag).addToBackStack(null);
        ft.commit();

    }

    public void updateMyGamesFromServer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Fragment currentFragment = getFragmentManager().findFragmentByTag("fgMyNotes");
                if (currentFragment != null)
                    ((misVJS) currentFragment).updatevjsListView();
            }
        });
    }

    private void   vista()
    {
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

        DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
        getBaseContext().getResources().updateConfiguration(config, metrics);

        FrameLayout container = (FrameLayout) findViewById(R.id.content_frame);

        prefs = getSharedPreferences("config", MODE_PRIVATE);
        int color =  prefs.getInt("colors", 0);

        container.setBackgroundColor(0);
        BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.fondonormal);
       // bg.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        container.setBackgroundDrawable(bg);

        if (color == 1) {
            container.setBackgroundColor(0);
            bg = (BitmapDrawable)getResources().getDrawable(R.drawable.fondoceleste);
         //   bg.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            container.setBackgroundDrawable(bg);
        }else if (color == 2) {
            container.setBackgroundColor(0);
            bg = (BitmapDrawable)getResources().getDrawable(R.drawable.fondo);
           // bg.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            container.setBackgroundDrawable(bg);
        }else if (color==3)
        {
            container.setBackgroundColor(0);
            bg = (BitmapDrawable)getResources().getDrawable(R.drawable.fondotigre);
           // bg.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            container.setBackgroundDrawable(bg);
        }
        else if(color==4)
        {
            container.setBackgroundColor(0);
            bg = (BitmapDrawable)getResources().getDrawable(R.drawable.pacman);
            // bg.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            container.setBackgroundDrawable(bg);
        }


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
