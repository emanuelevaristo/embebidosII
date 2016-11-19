package com.blcr.vj;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blcr.vj.adapters.DrawerAdapter;
import com.blcr.vj.fragments.addVj;
import com.blcr.vj.fragments.configuraciones;
import com.blcr.vj.fragments.listavj;
import com.blcr.vj.fragments.misVJS;
import com.blcr.vj.fragments.verVJ;
import com.blcr.vj.utils.Utils;

import java.util.Arrays;

public class detalle extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private String[] sectionsTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListView = (ListView) findViewById(R.id.drawer_listview);
        sectionsTitle = getResources().getStringArray(R.array.str_array_sectionsTitle);

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(sectionsTitle));
        drawerListView.setAdapter(adapter);

        int idvj = getIntent().getExtras().getInt("idvj");
        int verborrar = getIntent().getExtras().getInt("verborrar");

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Se dio click en Crear Titulo
                    Bundle arguments = new Bundle();
                    arguments.putInt("id", 1);
                    listavj frg = listavj.newInstance(arguments);
                    changeFragment(frg, "fgListaVj");
                } else if (position == 1) {
                    // Se dio click en Crear Nota
                    changeFragment(new misVJS(), "fgMyvjs");
                } else if (position == 2) {
                    // Se dio click en Mis Notas
                    changeFragment(new addVj(), "fgAddVJ");
                } else if (position == 3) {
                    // Se dio click en Configuracion
                    changeFragment(new configuraciones(), "fgConfiguration");
                }
                drawerLayout.closeDrawers();
            }
        });

        Bundle arguments = new Bundle();
        arguments.putInt("idvj", idvj);
        arguments.putInt("verborrar", verborrar);
        verVJ frg = verVJ.newInstance(arguments);
        Utils.showToast(this, idvj + "");
        changeFragment(frg, "fgverVJ");

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


}
