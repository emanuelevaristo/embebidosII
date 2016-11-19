package com.blcr.vj.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blcr.vj.Main2Activity;
import com.blcr.vj.R;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.VJs;
import com.blcr.vj.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class verVJ extends Fragment {

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
    ImageView imgMisVJ;
    CallbackManager callbackManager;
    String entregaLat="";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static  final  float ZOOM_MAP = 16.f;

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageLocaction = "";
    //para obtener nuestra ubicacion
    LocationManager locationManager;
    public static final int NOTIFICACION_ID =1;

    //para guardar las coordenadas del mapa y hacer una polylinea
    List<LatLng> markerPosition;

    public static verVJ newInstance(Bundle arguments)
    {
        verVJ vervj = new verVJ();
        if(arguments != null)
        {
            vervj.setArguments(arguments);
        }
        return vervj;
    }

    public verVJ() {
    }

    ShareDialog shareDialog;

    private ShareButton shareButton;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());


        View rootView = inflater.inflate(R.layout.fg_vj, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getArguments();
         un = bundle.getInt("idvj");
         verborrar = bundle.getInt("verborrar");

        VJDataSource vjs1 = new VJDataSource(getActivity());

        final VJs vjs =  vjs1.getVJ(un);

        lblNombreD = (TextView) rootView.findViewById(R.id.lblNombreD);
        lblPrecioD = (TextView) rootView.findViewById(R.id.lblPrecioD);
        lblConsolaD = (TextView) rootView.findViewById(R.id.lblConsolaD);
        lblContactoD = (TextView) rootView.findViewById(R.id.lblContactoD);
        idCompra = (TextView) rootView.findViewById(R.id.idCompra);
        lblDescripcionD = (TextView) rootView.findViewById(R.id.lblDescripcionD);
        btnComprar = (Button) rootView.findViewById(R.id.btnComprarD);
        btnBorrar = (Button) rootView.findViewById(R.id.btnBorrar);
        entregaLat = vjs.getEntrega();
        imgMisVJ = (ImageView) rootView.findViewById(R.id.imgMisVJ);

        byte[] image = vjs.getImage();
        final Bitmap bmmp;
        if(image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgMisVJ.setImageBitmap(bmp);


             bmmp = bmp;
        }
        else
        {
            imgMisVJ.setImageResource(R.drawable.add);
            bmmp = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        }


        callbackManager = CallbackManager.Factory.create();

        Button b = (Button) rootView.findViewById(R.id.btnComp);
        Button b2 = (Button) rootView.findViewById(R.id.btnCompimg);

        shareDialog = new ShareDialog(getActivity());

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = bmmp;
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .setCaption("Juego: " + vjs.getNombre() + " Precio: $" + vjs.getPrecio() + " Consola: " + vjs.getConsola())
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {

                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Juego: " + vjs.getNombre())
                            .setContentDescription(
                                    "Precio: $" + vjs.getPrecio() + " Consola: " + vjs.getConsola())
                            .setContentUrl(Uri.parse("https://www.facebook.com/VG-Store-1097085457054223/?skip_nax_wizard=true"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });



        SharedPreferences prefs = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int color =  prefs.getInt("colors", 0);


        lblNombreD.setTextColor(Color.RED);
        lblPrecioD.setTextColor(Color.RED);
        lblConsolaD.setTextColor(Color.RED);
        lblContactoD.setTextColor(Color.RED);
        lblDescripcionD.setTextColor(Color.RED);
        btnComprar.setTextColor(Color.BLACK);
        btnBorrar.setTextColor(Color.BLACK);
        if (color == 1) {
            lblNombreD.setTextColor(Color.WHITE);
            lblPrecioD.setTextColor(Color.WHITE);
            lblConsolaD.setTextColor(Color.WHITE);
            lblContactoD.setTextColor(Color.WHITE);
            lblDescripcionD.setTextColor(Color.WHITE);
            btnComprar.setTextColor(Color.WHITE);
            btnBorrar.setHintTextColor(Color.WHITE);
            btnComprar.setBackgroundColor(0x6db1e2);
            btnBorrar.setBackgroundColor(0x6db1e2);
        }else if (color == 2) {
            lblNombreD.setTextColor(Color.CYAN);
            lblPrecioD.setTextColor(Color.CYAN);
            lblConsolaD.setTextColor(Color.CYAN);
            lblContactoD.setTextColor(Color.CYAN);
            lblDescripcionD.setTextColor(Color.CYAN);
            btnComprar.setTextColor(Color.WHITE);
            btnBorrar.setTextColor(Color.WHITE);
            btnComprar.setBackgroundColor(Color.GREEN);
            btnBorrar.setBackgroundColor(Color.GREEN);

        }else if (color==3)
        {
            lblNombreD.setTextColor(Color.RED);
            lblPrecioD.setTextColor(Color.RED);
            lblConsolaD.setTextColor(Color.RED);
            lblContactoD.setTextColor(Color.RED);
            lblDescripcionD.setTextColor(Color.RED);
            btnComprar.setTextColor(Color.CYAN);
            btnBorrar.setTextColor(Color.CYAN);
            btnComprar.setBackgroundColor(Color.YELLOW);
            btnBorrar.setBackgroundColor(Color.YELLOW);
        }
        else if(color==4)
        {
            lblNombreD.setTextColor(Color.YELLOW);
            lblPrecioD.setTextColor(Color.YELLOW);
            lblConsolaD.setTextColor(Color.YELLOW);
            lblContactoD.setTextColor(Color.YELLOW);
            lblDescripcionD.setTextColor(Color.YELLOW);
            btnComprar.setTextColor(Color.BLACK);
            btnBorrar.setTextColor(Color.BLACK);
            btnComprar.setBackgroundColor(Color.YELLOW);
            btnBorrar.setBackgroundColor(Color.YELLOW);
        }



        setUpMapIfNeeded();

        lblNombreD.setText(vjs.getNombre());
        lblPrecioD.setText(vjs.getPrecio()+"");
        lblConsolaD.setText(vjs.getConsola());
        lblContactoD.setText(vjs.getUsuarioAlta().getCorreo());
        idCompra.setText(vjs.getId()+"");
        lblDescripcionD.setText(vjs.getDescipcion());
        idb = vjs.getId();




        final String nom = vjs.getNombre();
        final String pric = vjs.getPrecio()+"";
        final String des = vjs.getDescipcion();

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
                SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                int idUsua = prefs.getInt("id", 0);

                VJDataSource vjds = new VJDataSource(getActivity());
                vjds.updateStatus(idUsua,idb);

                Utils.showToast(getActivity(), "Videojuego separado");


                Intent intent = new Intent(getActivity(), Main2Activity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0,intent,0);

                Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                builder.setSmallIcon(R.mipmap.logo);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                builder.setTicker("Juego separado! :D");
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logotipo));
                builder.setContentTitle(nom);
                builder.setContentText(pric);
                builder.setSubText(des);
                //builder.setVibrate(new long[]{100, 250, 100, 500});
                builder.setVibrate(new long[]{500, 550, 500, 700});
                builder.setLights(Color.GREEN, 1, 0);
                builder.setSound(defaultSound);

                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICACION_ID, builder.build());


            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemoryAlertDialog();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);



    }




    public void showMemoryAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.str_alert_memory_title);

        builder.setPositiveButton(R.string.str_alert_memory_btnInternal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = lblNombreD.getText().toString();
                VJDataSource vjs = new VJDataSource(getActivity());
                    vjs.deleteVJ(idb);
                    Utils.showToast(getActivity(), "Se borro el juego exitosamente " + title);

                changeFragment(new misVJS(), "fgMyvjs");
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



    private void changeFragment(Fragment newFragment, String tag) {

        FragmentManager fragmentManager = getFragmentManager();

        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);

        if (currentFragment != null && currentFragment.isVisible())
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, newFragment, tag).addToBackStack(null);
        ft.commit();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map)).commit();
        getFragmentManager().beginTransaction().remove(getMapFragment()).commit();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap =  getMapFragment().getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private MapFragment getMapFragment() {
        FragmentManager fm = null;

        // Log.d(TAG, "sdk: " + Build.VERSION.SDK_INT);
        //  Log.d(TAG, "release: " + Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Log.d(TAG, "using getFragmentManager");
            fm = getFragmentManager();
        } else {
            //   Log.d(TAG, "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }

        return (MapFragment) fm.findFragmentById(R.id.map);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        locationManager = (LocationManager)  getActivity().getSystemService(Context.LOCATION_SERVICE);

        //para asignarle la longitud y latitud

        //cameraupdatefactory hace los movimientos de la camara donde s eposiciona google maps
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(monterrey));
        //le agregamos un zoom para que se vea mas cerca

        //muestra el boton de obtener mi ubicacion en el google map fragmetn
        mMap.setMyLocationEnabled(true);

        LatLng monterrey=null;
        String sp[] = entregaLat.split(",");

        if(!sp[0].contains("(") && !sp[0].equals("")) {
            float lat = Float.parseFloat(sp[1]);
            float lon = Float.parseFloat(sp[0]);


            monterrey = new LatLng(lon, lat);
        }
        else
        {
            monterrey = getCurrentLocation();

            if(monterrey == null)
            {
                monterrey = new LatLng(25.65, -100.29);
            }
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

    public LatLng getCurrentLocation()
    {
        //4 pasos
        //precision alta: autoriza a las app para que pueda encontrar la ubicacion por wifi gps o datos
        // el ahorro de bateria: solo wifi o datos
        // el solo en dispositivos: puro gps
        //le debemos especificar a la app el modo a usar
        //nuestra criteria nos servira para buscar un proveedor que cumpla con el criterio dado
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //nos devuelve el mejor proveedor en este momento de acuero al criterio dado
        //los provedores son gps, wifi, redes moviles
        String provider = locationManager.getBestProvider(criteria, true);

        if(provider == null || !locationManager.isProviderEnabled(provider))
        {
            //  Toast.makeText(this, "provedor " + provider + " deshabilitado", Toast.LENGTH_SHORT);
        }
        else
        {
            //nos devuelve la ultima ubicacion conocida por el usuario
            Location currentLocation = locationManager.getLastKnownLocation(provider);
            if(currentLocation != null)
            {
                return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            }
        }

        return null;
    }


}
