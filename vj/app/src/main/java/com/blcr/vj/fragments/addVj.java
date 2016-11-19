package com.blcr.vj.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blcr.vj.R;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.Usuarios;
import com.blcr.vj.model.VJs;
//import com.blcr.vj.networking.vjNet;
import com.blcr.vj.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhotoContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.util.ByteArrayBuffer;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class addVj extends Fragment {
    EditText txtNombre;
    EditText txtPrecio;
    EditText txtDescripcion;
    TextView txtEntrega;
    EditText txtConsola;
    Button btnGuardar;
    ImageButton fotoArticulo;
    String entregaLat="";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static  final  float ZOOM_MAP = 16.f;

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageLocaction = "";
    //para obtener nuestra ubicacion
    LocationManager locationManager;

    //para guardar las coordenadas del mapa y hacer una polylinea
    List<LatLng> markerPosition;

    public addVj() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgg_addvj, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtNombre = (EditText) rootView.findViewById(R.id.txtNombre);
        txtPrecio = (EditText) rootView.findViewById(R.id.txtPrecio);
        txtDescripcion = (EditText) rootView.findViewById(R.id.txtDescripcion);
        txtEntrega = (TextView) rootView.findViewById(R.id.txEntrega);
        txtConsola = (EditText) rootView.findViewById(R.id.txtConsola);
        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);
        fotoArticulo = (ImageButton) rootView.findViewById(R.id.fotoArticulo);

        SharedPreferences prefs = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int color =  prefs.getInt("colors", 0);

        txtNombre.setHintTextColor(Color.BLACK);
        txtPrecio.setHintTextColor(Color.BLACK);
        txtDescripcion.setHintTextColor(Color.BLACK);
        txtEntrega.setTextColor(Color.BLACK);
        txtConsola.setHintTextColor(Color.BLACK);
        btnGuardar.setTextColor(Color.BLACK);
        if (color == 1) {
            txtNombre.setHintTextColor(Color.WHITE);
            txtPrecio.setHintTextColor(Color.WHITE);
            txtDescripcion.setHintTextColor(Color.WHITE);
            txtEntrega.setTextColor(Color.WHITE);
            txtConsola.setHintTextColor(Color.WHITE);
            btnGuardar.setTextColor(Color.WHITE);
            btnGuardar.setBackgroundColor(0x6db1e2);
            txtNombre.setTextColor(Color.WHITE);
            txtPrecio.setTextColor(Color.WHITE);
            txtDescripcion.setTextColor(Color.WHITE);
            txtConsola.setTextColor(Color.WHITE);
        }else if (color == 2) {
            txtNombre.setHintTextColor(Color.CYAN);
            txtPrecio.setHintTextColor(Color.CYAN);
            txtDescripcion.setHintTextColor(Color.CYAN);
            txtEntrega.setTextColor(Color.CYAN);
            txtConsola.setHintTextColor(Color.CYAN);
            btnGuardar.setTextColor(Color.WHITE);
            btnGuardar.setBackgroundColor(Color.GREEN);
            txtNombre.setTextColor(Color.WHITE);
            txtPrecio.setTextColor(Color.WHITE);
            txtDescripcion.setTextColor(Color.WHITE);
            txtConsola.setTextColor(Color.WHITE);

        }else if (color==3)
        {
            txtNombre.setHintTextColor(Color.CYAN);
            txtPrecio.setHintTextColor(Color.CYAN);
            txtDescripcion.setHintTextColor(Color.CYAN);
            txtEntrega.setTextColor(Color.CYAN);
            txtConsola.setHintTextColor(Color.CYAN);
            btnGuardar.setTextColor(Color.CYAN);
            btnGuardar.setBackgroundColor(Color.YELLOW);
            txtNombre.setTextColor(Color.BLACK);
            txtPrecio.setTextColor(Color.BLACK);
            txtDescripcion.setTextColor(Color.BLACK);
            txtConsola.setTextColor(Color.BLACK);
        }
        else if(color==4)
        {
            txtNombre.setHintTextColor(Color.YELLOW);
            txtPrecio.setHintTextColor(Color.YELLOW);
            txtDescripcion.setHintTextColor(Color.YELLOW);
            txtEntrega.setTextColor(Color.YELLOW);
            txtConsola.setHintTextColor(Color.YELLOW);
            btnGuardar.setTextColor(Color.YELLOW);
            btnGuardar.setBackgroundColor(Color.BLACK);
            txtNombre.setTextColor(Color.WHITE);
            txtPrecio.setTextColor(Color.WHITE);
            txtDescripcion.setTextColor(Color.WHITE);
            txtConsola.setTextColor(Color.WHITE);
        }

        setUpMapIfNeeded();

        fotoArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent callcam = new Intent();
                    callcam.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Utils.showToast(getActivity(),"11 "+e.getMessage());
                    }
                    callcam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(callcam, ACTIVITY_START_CAMERA_APP);
                }
                catch (Exception ex){
                 //   Utils.showToast(getActivity(),"10 "+ex.getMessage());
                    Log.d("Error Chato", ex.getMessage());
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String preci = txtPrecio.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                //String entrega = txtEntrega.getText().toString();
                String entrega = entregaLat;
                String consola = txtConsola.getText().toString();
                boolean bandera = true;
                byte[] logoImage = null;
                if(mImageLocaction != "") {
                    logoImage = getLogoImage(mImageLocaction);
                }


                //byte[] logoImage = saveImage(mImageLocaction);

               //String encodedImage = Base64.encodeToString(logoImage, Base64.DEFAULT);

               // byte [] nono = Base64.decode(encodedImage,Base64.DEFAULT);

                if("".equals(nombre))
                {
                    bandera = false;
                    Utils.showToast(getActivity(), "Ingresa el nombre del juego");
                }

                if("".equals(preci))
                {
                    bandera = false;
                    Utils.showToast(getActivity(), "Ingresa el precio del juego");
                }

                if("".equals(descripcion))
                {
                    bandera = false;
                    Utils.showToast(getActivity(), "Ingresa la descripcion del juego");
                }

                if("".equals(consola))
                {
                    bandera = false;
                    Utils.showToast(getActivity(), "Ingresa la consola del juego");
                }

             //   if(logoImage == null)
               // {
                 //   bandera = false;
                   // Utils.showToast(getActivity(), "Ingresa la foto del juego");
                //}

                if("".equals(entrega))
                {
                   // bandera = false;
                   // Utils.showToast(getActivity(), "Ingresa el lugar de entrega del juego");
                    LatLng currentLocation = getCurrentLocation();

                    if(currentLocation != null)
                    {
                        entregaLat = currentLocation.toString();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM_MAP));
                    }
                    else {
                        LatLng monterrey = new LatLng(25.65, -100.29);
                        entregaLat = monterrey.toString();
                        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
                    }
                    entrega = entregaLat;
                }

                if(bandera) {

                    SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    int idUsua = prefs.getInt("id", 0);

                    double precio = Double.parseDouble(preci);
                    VJs vjs = null;
                    if (logoImage == null)
                    {
                        vjs = new VJs(nombre, precio, descripcion, entrega, consola, "activo", idUsua, "");
                    }
                    else {
                        vjs = new VJs(nombre, precio, descripcion, entrega, consola, "activo", idUsua, logoImage);
                    }
                    Usuarios usua = new Usuarios(idUsua);
                    vjs.setUsuarioAlta(usua);

                    VJDataSource vjds = new VJDataSource(getActivity());
                    vjds.addVJ(vjs);

                    VJs vjs1 = new VJs(nombre, precio, descripcion, entrega, consola, "activo", idUsua, "");

                    Usuarios usua1 = new Usuarios(idUsua);
                    vjs1.setUsuarioAlta(usua1);

                    //new vjNet(getActivity()).execute("save", vjs1.toJSON());

                    txtNombre.setText("");
                    txtPrecio.setText("");
                    txtDescripcion.setText("");
                    //txtEntrega.setText("");
                    txtConsola.setText("");
                    mMap.clear();
                    fotoArticulo.setImageResource(R.drawable.add);

                    Utils.showToast(getActivity(), "Video Juego guardado!!!");
                }
            }
        });

        return rootView;
    }



    public byte[] saveImage(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            byte[] image = new byte[fis.available()];
            fis.read(image);
            fis.close();
            return image;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Utils.showToast(getActivity(),"1 "+e.getMessage());
            Log.d("Error Chato", e.getMessage());
            return null;
        }
    }

    Boolean aaaaa = true;

    @Override
    public void onDestroyView() {
        MapFragment mf = getMapFragment();
        try {
            super.onDestroyView();

            if(getMapFragment() != null) {
                getFragmentManager().beginTransaction().remove(getMapFragment()).commit();
            }
            else
            {
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map2)).commit();
            }
        }
        catch (Exception ex){
            Utils.showToast(getActivity(),"1.- ");
            Log.d("Error Chato", ex.getMessage());
        }
    }


    public void getImage(View view)
    {
       // byte[] image = c.getBlob(0);
       // Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
       // fotoArticulo.setImageBitmap(bmp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == Activity.RESULT_OK) {
                serReducedImageSize();
            }
        }
        catch (Exception ex){
            Utils.showToast(getActivity(),"2 "+ex.getMessage());
            Log.d("Error Chato", ex.getMessage());
        }
    }

    File createImageFile() throws IOException
    {
        File image = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String imageFileName = "image_" + timeStamp + txtNombre.getText().toString() + txtPrecio.getText().toString();
            File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
           // if(storageDirectory.mkdirs()) {
                image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
                mImageLocaction = image.getAbsolutePath();
           // }
           // else
           // {
            //    Utils.showToast(getActivity(),"no existe");
            //}

        }
        catch (Exception ex){
            Utils.showToast(getActivity(),"3 "+ex.getMessage());
            Log.d("Error Chato", ex.getMessage());
        }
        return image;
    }

    void serReducedImageSize()
    {
        try {
            int largeImageViewWidth = fotoArticulo.getWidth();
            int largeImageViewHeight = fotoArticulo.getHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mImageLocaction, bmOptions);
            int cameraImgaewidth = bmOptions.outHeight;
            int cameraImageHeigth = bmOptions.outHeight;

            int scaleFactor = Math.min(cameraImgaewidth / largeImageViewWidth, cameraImageHeigth / largeImageViewHeight);
            bmOptions.inSampleSize = scaleFactor;

            bmOptions.inJustDecodeBounds = false;
            Bitmap photoReducida = BitmapFactory.decodeFile(mImageLocaction, bmOptions);

            Matrix matrix = new Matrix();

            matrix.postRotate(90);

            Bitmap rotated = Bitmap.createBitmap(photoReducida, 0, 0, photoReducida.getWidth(), photoReducida.getHeight(), matrix, true);

            fotoArticulo.setImageBitmap(rotated);
        }
        catch (Exception ex){
            Utils.showToast(getActivity(),"4 "+ex.getMessage());
            Log.d("Error Chato", ex.getMessage());
        }
    }



    private byte[] getLogoImage(String url){
        try {

            int largeImageViewWidth = fotoArticulo.getWidth();
            int largeImageViewHeight = fotoArticulo.getHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mImageLocaction, bmOptions);
            int cameraImgaewidth = bmOptions.outHeight;
            int cameraImageHeigth = bmOptions.outHeight;

            int scaleFactor = Math.min(cameraImgaewidth / largeImageViewWidth, cameraImageHeigth / largeImageViewHeight);
            bmOptions.inSampleSize = scaleFactor;

            bmOptions.inJustDecodeBounds = false;
            Bitmap photoReducida = BitmapFactory.decodeFile(mImageLocaction, bmOptions);

            Matrix matrix = new Matrix();

            matrix.postRotate(90);

            Bitmap rotated =  Bitmap.createBitmap(photoReducida, 0, 0, photoReducida.getWidth(), photoReducida.getHeight(), matrix, true);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            rotated.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();


           // String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


            return b;
        }
        catch (Exception ex){
          //  Utils.showToast(getActivity(),"5 "+ex.getMessage());
            Log.d("Error Chato", ex.getMessage());
        }
        return null;
    }



    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        try{
            if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
               // mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
                mMap = getMapFragment().getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    setUpMap();
                }
            }
        }
        catch (Exception ex){
            Utils.showToast(getActivity(),"41 "+ex.getMessage());
            Log.d("Error Chato", ex.getMessage());
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

        return (MapFragment) fm.findFragmentById(R.id.map2);
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

        LatLng currentLocation = getCurrentLocation();

        if(currentLocation != null)
        {
            entregaLat = currentLocation.toString();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM_MAP));
        }
        else {
            LatLng monterrey = new LatLng(25.65, -100.29);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monterrey, ZOOM_MAP));
            //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String title = "Lugar de entrega";
                addMarker(latLng, title);
                entregaLat = latLng.toString();
                entregaLat = entregaLat.replace("lat/lng: (", "");
                entregaLat = entregaLat.replace(")","");
            }
        });

        //inicializamos nuestro objeto que guardara las posiciones donde se han agregado marcadores
        markerPosition = new ArrayList<LatLng>();
    }

    public void addMarker(LatLng position, String title)
    {
        //para limpiar el mapa mMap.clear();

        MarkerOptions mo = new MarkerOptions();
        mo.position(position);
        mo.title(title);

        //para agregar polylineas
        //PolylineOptions lines = new PolylineOptions();
        //lines.width(0);
        //lines.color(Color.BLUE);
        //para agregar polylineas
        // if(markerPosition.size() > 0)
        // {
        //  lines.add(markerPosition.get(markerPosition.size()-1));
        // }
        //lines.add(position);

        //mMap.addPolyline(lines);
        //para agregar un marcador
        mMap.clear();
        mMap.addMarker(mo);

        markerPosition.add(position);
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
