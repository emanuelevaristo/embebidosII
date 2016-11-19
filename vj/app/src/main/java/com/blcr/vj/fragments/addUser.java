package com.blcr.vj.fragments;

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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.blcr.vj.R;
import com.blcr.vj.data.UserDataSource;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.Usuarios;
//import com.blcr.vj.networking.userNet;
import com.blcr.vj.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class addUser extends Fragment {
    EditText txtNombreUsuario;
    EditText txtApellidos;
    EditText txtTelefono;
    EditText txtCorreo;
    EditText txtContra;
    EditText txtUsername;
    Button btnGuardar;
    ImageButton fotoPerfil;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageLocaction = "";

    public addUser() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgg_adduser, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtNombreUsuario = (EditText) rootView.findViewById(R.id.txtNombreUsuario);
        txtApellidos = (EditText) rootView.findViewById(R.id.txtApellidos);
        txtTelefono = (EditText) rootView.findViewById(R.id.txtTelefono);
        txtCorreo = (EditText) rootView.findViewById(R.id.txtCorreo);
        txtContra = (EditText) rootView.findViewById(R.id.txtContra);
        txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardarUsuario);
        fotoPerfil = (ImageButton) rootView.findViewById(R.id.imgUsuario);

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callcam = new Intent();
                callcam.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                callcam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(callcam, ACTIVITY_START_CAMERA_APP);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombreUsuario.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String telefon = txtTelefono.getText().toString();
                String correo = txtCorreo.getText().toString();
                String contrs = txtContra.getText().toString();
                String username = txtUsername.getText().toString();

                byte[] logoImage = getLogoImage(mImageLocaction);
                boolean bandera = false;


              if("".equals(nombre))
              {
                  bandera = true;
                  Utils.showToast(getActivity(), "Ingresa tu(s) nombre(s)");
              }
                if("".equals(apellidos))
                {
                    bandera = true;
                    Utils.showToast(getActivity(), "Ingresa tus apellidos");
                }
                if("".equals(telefon))
                {
                    bandera = true;
                    Utils.showToast(getActivity(), "Ingresa tu telefono");
                }
                if("".equals(correo))
                {
                    bandera = true;
                    Utils.showToast(getActivity(), "Ingresa tu correo");
                }
                if("".equals(contrs))
                {
                    bandera = true;
                    Utils.showToast(getActivity(), "Ingresa tu contraseña");
                }

                if("".equals(username))
                {
                    bandera = true;
                    Utils.showToast(getActivity(), "Ingresa tu nombre de usuario");
                }

              //  if(logoImage == null)
              //  {
              //      bandera = true;
               //     Utils.showToast(getActivity(), "Ingresa tu foto de usuario");
               // }


                if(bandera==false) {
                    //new userNet(getActivity()).execute("get");

                    String telefono = telefon;

                    Usuarios usua = new Usuarios(nombre, apellidos, telefono, correo, contrs, username, logoImage);

                    UserDataSource usuas = new UserDataSource(getActivity());
                    usuas.addUser(usua, true);


                    Usuarios usua1 = new Usuarios(nombre, apellidos, telefono, correo, contrs, username, null);
                   // new userNet(getActivity()).execute("save", usua1.toJSON());

                    txtNombreUsuario.setText("");
                    txtApellidos.setText("");
                    txtTelefono.setText("");
                    txtCorreo.setText("");
                    txtContra.setText("");
                    txtUsername.setText("");
                    fotoPerfil.setImageResource(R.drawable.mariocharacter);

                    Utils.showToast(getActivity(), "Usuario guardado!!!");
                    changeFragment(new login(), "fgLogin");
                }
            }
        });

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


    private byte[] getLogoImage(String url){
        try {

            int largeImageViewWidth = fotoPerfil.getWidth();
            int largeImageViewHeight = fotoPerfil.getHeight();

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
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
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
        String imageFileName = "image_" + timeStamp + txtNombreUsuario.getText().toString() + txtApellidos.getText().toString();
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDirectory);
        mImageLocaction = image.getAbsolutePath();

        return image;
    }

    void serReducedImageSize()
    {
        int largeImageViewWidth = fotoPerfil.getWidth();
        int largeImageViewHeight = fotoPerfil.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageLocaction, bmOptions);
        int cameraImgaewidth = bmOptions.outHeight;
        int cameraImageHeigth = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImgaewidth/largeImageViewWidth, cameraImageHeigth/largeImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;

        bmOptions.inJustDecodeBounds = false;
        Bitmap photoReducida = BitmapFactory.decodeFile(mImageLocaction, bmOptions);
        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap rotated =  Bitmap.createBitmap(photoReducida, 0, 0, photoReducida.getWidth(), photoReducida.getHeight(), matrix, true);

        fotoPerfil.setImageBitmap(rotated);
    }

}
