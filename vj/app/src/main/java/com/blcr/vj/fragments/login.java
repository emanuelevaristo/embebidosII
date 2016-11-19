package com.blcr.vj.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blcr.vj.MainActivity;
import com.blcr.vj.R;
import com.blcr.vj.data.UserDataSource;
//import com.blcr.vj.networking.userNet;
import com.blcr.vj.utils.Utils;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class login extends Fragment {
    EditText txtContrasenia;
    EditText txtUsername;
    Button btnEntrar;
    Button btnRegistrarse;
    CheckBox chkbncs;


    public login() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fgg_login, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtContrasenia = (EditText) rootView.findViewById(R.id.txtContraLogin);
        txtUsername = (EditText) rootView.findViewById(R.id.txtUsuarioLogin);
        btnEntrar = (Button) rootView.findViewById(R.id.btnEntrar);
        btnRegistrarse = (Button) rootView.findViewById(R.id.btnRegistrarse);
        final UserDataSource usa = new UserDataSource(getActivity());
        chkbncs = (CheckBox) rootView.findViewById(R.id.chkbncs);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = txtUsername.getText().toString();
                final String contra = txtContrasenia.getText().toString();
                final int idu = usa.login(nombre, contra);
                int check = 1;
                if(idu>0) {

                    if(chkbncs.isChecked())
                {
                    saveConfig(idu);
                    check = 1;
                }
                    else
                {
                    SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("id", idu);
                    editor.putInt("check", check);
                    editor.commit();
                }

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("id", idu);
                    i.putExtra("check", check);
                    startActivity(i);
                    getActivity().finish();
                }
                else
                {
                    Utils.showToast(getActivity(), "Usuario y/o contraseña incorrecta");
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new addUser(), "fgAddUser");
            }
        });

        loadConfig();

        return rootView;
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

    private void saveConfig(int id) {
        SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String usuario = txtUsername.getText().toString();
        String contra = txtContrasenia.getText().toString();

        editor.putString("usuario", usuario);
        editor.putString("contra", contra);
        editor.putInt("id", id);
        editor.commit();
    }

    private void loadConfig() {
        SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String usuario = prefs.getString("usuario", "");
        String contra  = prefs.getString("contra", "");
        final UserDataSource usa = new UserDataSource(getActivity());
        int id = usa.login(usuario, contra);

        if(id>0) {
           Intent i = new Intent(getActivity(), MainActivity.class);
           i.putExtra("id", id);
           startActivity(i);
           getActivity().finish();
        }
        else
        {
          //  new userNet(getActivity()).execute("get");
        }

    }

}
