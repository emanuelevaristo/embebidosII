package com.blcr.vj.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.blcr.vj.Main2Activity;
import com.blcr.vj.MainActivity;
import com.blcr.vj.R;
import com.blcr.vj.utils.Utils;

/**
 * Created by ErickAlejandro on 06/11/2015.
 */
public class configuraciones  extends Fragment {

    Button btnSave;
    Button btnCerrarSesion;
    Spinner spLang;
    Spinner spColors;
    TextView lblLang;
    TextView lblColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.congifuraciones, container, false);

        btnSave = (Button) rootView.findViewById(R.id.btnConfigSave);
        spLang = (Spinner) rootView.findViewById(R.id.spConfigLang);
        spColors = (Spinner) rootView.findViewById(R.id.spConfigColors);
        btnCerrarSesion = (Button) rootView.findViewById(R.id.btnCerrarSesion);
        lblLang = (TextView) rootView.findViewById(R.id.lblConfigLang);
        lblColor = (TextView) rootView.findViewById(R.id.lblConfigColors);


        SharedPreferences prefs = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int color =  prefs.getInt("colors", 0);


        spLang.setBackgroundColor(Color.WHITE);
        spColors.setBackgroundColor(Color.WHITE);
        lblColor.setTextColor(Color.BLACK);
        lblLang.setTextColor(Color.BLACK);
        if (color == 1) {
            spLang.setBackgroundColor(Color.GRAY);
            spColors.setBackgroundColor(Color.GRAY);
            btnSave.setBackgroundColor(0x6db1e2);
            btnCerrarSesion.setBackgroundColor(0x6db1e2);
            lblColor.setTextColor(Color.WHITE);
            lblLang.setTextColor(Color.WHITE);
            btnSave.setTextColor(Color.WHITE);
            btnCerrarSesion.setTextColor(Color.WHITE);
        }else if (color == 2) {
            spLang.setBackgroundColor(Color.CYAN);
            spColors.setBackgroundColor(Color.CYAN);
            btnSave.setBackgroundColor(Color.GREEN);
            btnCerrarSesion.setBackgroundColor(Color.GREEN);
            btnSave.setTextColor(Color.WHITE);
            btnCerrarSesion.setTextColor(Color.WHITE);
            lblColor.setTextColor(Color.CYAN);
            lblLang.setTextColor(Color.CYAN);

        }else if (color==3)
        {
            spLang.setBackgroundColor(Color.CYAN);
            spColors.setBackgroundColor(Color.CYAN);
            btnSave.setBackgroundColor(Color.YELLOW);
            btnCerrarSesion.setBackgroundColor(Color.YELLOW);
            lblColor.setTextColor(Color.CYAN);
            lblLang.setTextColor(Color.CYAN);
            btnSave.setTextColor(Color.CYAN);
            btnCerrarSesion.setTextColor(Color.CYAN);
        }
        else if(color==4)
        {
            spLang.setBackgroundColor(Color.YELLOW);
            spColors.setBackgroundColor(Color.YELLOW);
            btnSave.setBackgroundColor(Color.BLACK);
            btnCerrarSesion.setBackgroundColor(Color.BLACK);
            lblColor.setTextColor(Color.YELLOW);
            lblLang.setTextColor(Color.YELLOW);
            btnSave.setTextColor(Color.YELLOW);
            btnCerrarSesion.setTextColor(Color.YELLOW);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfig();

                // Reiniciar la app
                getActivity().finish();
                startActivity(new Intent(getActivity().getIntent()));
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("usuario");
                editor.remove("contra");
                editor.remove("id");
                editor.remove("check");
                editor.commit();

                Intent i = new Intent(getActivity(), Main2Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        loadConfig();

        return rootView;
    }

    private void saveConfig() {
        SharedPreferences prefs = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int lang = spLang.getSelectedItemPosition();
        int colors = spColors.getSelectedItemPosition();

        editor.putInt("lang", lang);
        editor.putInt("colors", colors);
        editor.commit();
    }

    private  void loadConfig() {
        SharedPreferences prefs = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int lang = prefs.getInt("lang", 0);
        int colors = prefs.getInt("colors", 0);

        spLang.setSelection(lang);
        spColors.setSelection(colors);
    }
}
