package com.blcr.vj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blcr.vj.adapters.DrawerAdapter;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.VJs;
import com.blcr.vj.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;

public class MapsActivity2 extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static  final  float ZOOM_MAP = 16.f;

    String entregaLat="";
    TextView lblNombreD;
    TextView lblPrecioD;
    TextView lblConsolaD;
    TextView lblContactoD;
    TextView idCompra;
    TextView lblDescripcionD;
    Button btnComprar;
    Button btnBorrar;
    int idb;
    int un;
    int verborrar;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private String[] sectionsTitle;
    //geocoding: el proceso de convertir una direccion en coordenadas por ejemplo: tu calle #123 = 24.0, -100.9 lat lng
    //reverse geocofing, el proceso de convertir una lat lng en una direccion por ejemplo 24.0, 100.9 == tu calle #123

    //geocoder geocoding

    //para obtener nuestra ubicacion
    LocationManager locationManager;

    //para guardar las coordenadas del mapa y hacer una polylinea
    List<LatLng> markerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);


        /*
        * supportmapfragment map = (supportmapfragment) getsupportf
        * map.getmapasync(this);
        * */

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListView = (ListView) findViewById(R.id.drawer_listview);
        sectionsTitle = getResources().getStringArray(R.array.str_array_sectionsTitle);

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(sectionsTitle));
        drawerListView.setAdapter(adapter);

        un = getIntent().getExtras().getInt("idvj");
        verborrar = getIntent().getExtras().getInt("verborrar");

        VJDataSource vjs1 = new VJDataSource(this);

        VJs vjs =  vjs1.getVJ(un);

        lblNombreD = (TextView) findViewById(R.id.lblNombreD);
        lblPrecioD = (TextView) findViewById(R.id.lblPrecioD);
        lblConsolaD = (TextView) findViewById(R.id.lblConsolaD);
        lblContactoD = (TextView) findViewById(R.id.lblContactoD);
        idCompra = (TextView) findViewById(R.id.idCompra);
        lblDescripcionD = (TextView) findViewById(R.id.lblDescripcionD);
        btnComprar = (Button) findViewById(R.id.btnComprarD);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);

        entregaLat = vjs.getEntrega();
        lblNombreD.setText(vjs.getNombre());
        lblPrecioD.setText(vjs.getPrecio()+"");
        lblConsolaD.setText(vjs.getConsola());
        lblContactoD.setText(vjs.getUsuarioAlta().getCorreo());
        idCompra.setText(vjs.getId()+"");
        lblDescripcionD.setText(vjs.getDescipcion());
        idb = vjs.getId();

        if(verborrar==1)
        {
            btnComprar.setVisibility(View.INVISIBLE);
            btnBorrar.setVisibility(View.VISIBLE);
        }
        else
        {
            btnComprar.setVisibility(View.VISIBLE);
            btnBorrar.setVisibility(View.INVISIBLE);
        }

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                int idUsua = prefs.getInt("id", 0);

                VJDataSource vjds = new VJDataSource(getApplicationContext());
                vjds.updateStatus(idUsua,idb);

                Utils.showToast(getApplicationContext(), "Videojuego separado");

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemoryAlertDialog();
            }
        });



        Utils.showToast(this, un + "");
        setUpMapIfNeeded();
    }


    public void showMemoryAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.str_alert_memory_title);

        builder.setPositiveButton(R.string.str_alert_memory_btnInternal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = lblNombreD.getText().toString();
                VJDataSource vjs = new VJDataSource(getApplicationContext());
                vjs.deleteVJ(idb);
                Utils.showToast(getApplicationContext(), "Se borro el juego exitosamente " + title);

                //changeFragment(new misVJS(), "fgMyvjs");
            }
        });
        builder.setNegativeButton(R.string.str_alert_memory_btnExternal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //para asignarle la longitud y latitud

        //cameraupdatefactory hace los movimientos de la camara donde s eposiciona google maps
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(monterrey));
        //le agregamos un zoom para que se vea mas cerca

        //muestra el boton de obtener mi ubicacion en el google map fragmetn
        mMap.setMyLocationEnabled(true);

        LatLng monterrey;
        String sp[] = entregaLat.split(",");

        if(!sp[0].contains("(")) {
            float lat = Float.parseFloat(sp[1]);
            float lon = Float.parseFloat(sp[0]);


            monterrey = new LatLng(lon, lat);
        }
        else
        {
            monterrey = new LatLng(25.65, -100.29);
        }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monterrey, ZOOM_MAP));


                String title = "Lugar de entrega";
                addMarker(monterrey, title);
    }

    public void addMarker(LatLng position, String title)
    {

        MarkerOptions mo = new MarkerOptions();
        mo.position(position);
        mo.title(title);

        mMap.clear();
        mMap.addMarker(mo);
    }

}