package com.blcr.vj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.Usuarios;
import com.blcr.vj.model.VJs;
//import com.blcr.vj.networking.vjNet;
import com.blcr.vj.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static  final  float ZOOM_MAP = 16.f;

    EditText txtNombre;
    EditText txtPrecio;
    EditText txtDescripcion;
    EditText txtEntrega;
    EditText txtConsola;
    Button btnGuardar;
    ImageButton fotoArticulo;
    String entregaLat="";

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageLocaction = "";

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
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        /*
        * supportmapfragment map = (supportmapfragment) getsupportf
        * map.getmapasync(this);
        * */


        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPrecio = (EditText) findViewById(R.id.txtPrecio);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        //txtEntrega = (EditText) findViewById(R.id.txtEntrega);
        txtConsola = (EditText) findViewById(R.id.txtConsola);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        fotoArticulo = (ImageButton) findViewById(R.id.fotoArticulo);


        fotoArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callcam = new Intent();
                callcam.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callcam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(callcam, ACTIVITY_START_CAMERA_APP);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                double precio = Double.parseDouble(txtPrecio.getText().toString());
                String descripcion = txtDescripcion.getText().toString();
                //String entrega = txtEntrega.getText().toString();
                String entrega = entregaLat;
                String consola = txtConsola.getText().toString();

               // byte[] logoImage = getLogoImage(mImageLocaction);

                VJs vjs = new VJs(nombre, precio, descripcion, entrega, consola, "activo", null);

                SharedPreferences prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                int idUsua = prefs.getInt("id", 0);

                Usuarios usua = new Usuarios(idUsua);
                vjs.setUsuarioAlta(usua);

                VJDataSource vjds = new VJDataSource(getApplicationContext());
                vjds.addVJ(vjs);

               // new vjNet(getApplicationContext()).execute("save", vjs.toJSON());

                txtNombre.setText("");
                txtPrecio.setText("");
                txtDescripcion.setText("");
                //txtEntrega.setText("");
                txtConsola.setText("");

                Utils.showToast(getApplicationContext(), "Video Juego guardado!!!");
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == Activity.RESULT_OK)
        {
            serReducedImageSize();
        }
    }

    File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String imageFileName = "image_" + timeStamp + txtNombre.getText().toString() + txtPrecio.getText().toString();
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDirectory);
        mImageLocaction = image.getAbsolutePath();

        return image;
    }

    void serReducedImageSize()
    {
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

        fotoArticulo.setImageBitmap(rotated);

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

        LatLng currentLocation = getCurrentLocation();

        if(currentLocation != null)
        {
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
            Toast.makeText(this, "provedor " + provider + " deshabilitado", Toast.LENGTH_SHORT);
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
            return b;
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

}