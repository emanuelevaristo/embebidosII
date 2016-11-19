package com.blcr.vj.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blcr.vj.Main2Activity;
import com.blcr.vj.MapsActivity2;
import com.blcr.vj.R;
import com.blcr.vj.detalle;
import com.blcr.vj.fragments.addVj;
import com.blcr.vj.fragments.configuraciones;
import com.blcr.vj.fragments.verVJ;
import com.blcr.vj.model.VJs;

import java.util.List;

/**
 * Created by ErickAlejandro on 02/11/2015.
 */
public class vjAdapter extends BaseAdapter {
    List<VJs> vjList;

    public vjAdapter(List<VJs> vjList) {
        this.vjList = vjList;
    }

    @Override
    public int getCount() {
        return vjList.size();
    }

    @Override
    public Object getItem(int position) {
        return vjList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewGroup item; //representa la interfaz grafica

        if(convertView != null) //verifica si ya se inflo la vista
        {
            item = (ViewGroup) convertView;//si ya lo inflo, solo le pasa la vista de nuevo al item
        }
        else
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //infla por primera vez el xml
            item = (ViewGroup) inflater.inflate(R.layout.vj, null);
        }

        TextView lblNombre = (TextView) item.findViewById(R.id.lblNombre); //inicializa la variable con el label
        TextView lblPrecio = (TextView) item.findViewById(R.id.lblPrecio);
        TextView lblConsola = (TextView) item.findViewById(R.id.lblConsola);
        TextView lblID = (TextView) item.findViewById(R.id.idID);
        Button btnVer = (Button) item.findViewById(R.id.btnVer);
        TextView lblStatus = (TextView) item.findViewById(R.id.lblStatus);
        ImageView imgVJS = (ImageView) item.findViewById(R.id.imgVJS);
        Button btnComparir = (Button) item.findViewById(R.id.idCompartir);

        btnComparir = (Button) item.findViewById(R.id.idCompartir);




        final VJs vjs = (VJs) getItem(position); //obtiene los elementos

        lblNombre.setText(vjs.getNombre()); //escribe el nombre
        lblPrecio.setText("$" + vjs.getPrecio());
        lblConsola.setText(vjs.getConsola());
        lblID.setText(vjs.getId()+"");
        lblStatus.setText(vjs.getStatus());


        btnComparir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Entra a VG Store y mira mis articulos en venta: Juego: " + vjs.getNombre() +" Precio: $" + vjs.getPrecio() + " Consola: " + vjs.getConsola());
                intent.setPackage("com.whatsapp");
                parent.getContext().startActivity(Intent.createChooser(intent, "Compartir con"));
            }
        });


        SharedPreferences prefs = parent.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        int color =  prefs.getInt("colors", 0);


        lblNombre.setTextColor(Color.RED);
        lblPrecio.setTextColor(Color.RED);
        lblConsola.setTextColor(Color.RED);
        lblStatus.setTextColor(Color.RED);
        btnVer.setTextColor(Color.BLACK);
        if (color == 1) {
            lblNombre.setTextColor(Color.WHITE);
            lblPrecio.setTextColor(Color.WHITE);
            lblConsola.setTextColor(Color.WHITE);
            lblStatus.setTextColor(Color.WHITE);
            btnVer.setTextColor(Color.WHITE);
            btnVer.setBackgroundColor(0x6db1e2);
        }else if (color == 2) {
            lblNombre.setTextColor(Color.CYAN);
            lblPrecio.setTextColor(Color.CYAN);
            lblConsola.setTextColor(Color.CYAN);
            lblStatus.setTextColor(Color.CYAN);
            btnVer.setTextColor(Color.WHITE);
            btnVer.setBackgroundColor(Color.GREEN);

        }else if (color==3)
        {
            lblNombre.setTextColor(Color.RED);
            lblPrecio.setTextColor(Color.RED);
            lblConsola.setTextColor(Color.RED);
            lblStatus.setTextColor(Color.RED);
            btnVer.setTextColor(Color.CYAN);
            btnVer.setBackgroundColor(Color.YELLOW);
        }
        else if(color==4)
        {
            lblNombre.setTextColor(Color.YELLOW);
            lblPrecio.setTextColor(Color.YELLOW);
            lblConsola.setTextColor(Color.YELLOW);
            lblStatus.setTextColor(Color.YELLOW);
            btnVer.setTextColor(Color.YELLOW);
            btnVer.setBackgroundColor(Color.BLACK);
        }




        final int idvj = vjs.getId();
        final int misjuegos = vjs.getMisvjs();

        byte[] image = vjs.getImage();
        if(image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgVJS.setImageBitmap(bmp);
        }else {
            imgVJS.setImageResource(R.drawable.add);
        }


        if(misjuegos==1)
        {
            lblStatus.setVisibility(View.VISIBLE);

        }
        else
        {
            lblStatus.setVisibility(View.INVISIBLE);
        }


        //setonclickListener();
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verbor = 0;
                if(misjuegos==1)
                {
                    verbor = 1;
                }
                else
                {
                    verbor = 0;
                }

                //Intent i = new Intent(parent.getContext(), MapsActivity2.class);
                //i.putExtra("idvj", idvj);
                //i.putExtra("verborrar", verbor);
                //parent.getContext().startActivity(i);

                String tag = "fgvervj";
                Bundle arguments = new Bundle();
                arguments.putInt("idvj", idvj);
                arguments.putInt("verborrar", verbor);
                verVJ frg = verVJ.newInstance(arguments);

                FragmentManager fragmentManager = ((Activity) parent.getContext()).getFragmentManager();

                Fragment currentFragment = fragmentManager.findFragmentByTag(tag);

                if (currentFragment != null && currentFragment.isVisible())
                    return;

                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, frg, tag).addToBackStack(null);
                ft.commit();


            }
        });

        return item;
    }


}
