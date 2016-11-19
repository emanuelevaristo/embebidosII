/*
package com.blcr.vj.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.blcr.vj.data.UserDataSource;
import com.blcr.vj.model.Usuarios;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
*/
/**
 * Created by ErickAlejandro on 08/11/2015.
 *//*

public class userNet extends AsyncTask<String, String, String> {
    */
/* Path donde se encuenta el webservice *//*

   // private static final String SERVER_PATH = "http://192.168.43.56:100/usuarios.php";
    //private static final String SERVER_PATH = "http://blcrshop.x10host.com/webservice.php";
    private static final String SERVER_PATH = "http://blcrshop.x10host.com/users.php";
    private static final int TIMEOUT = 3000;

    //10.1.122.241

    private Context context;


    */
/* Nos permite indicarle parametros a la conexion
        en este caso lo usamos para indicar el TIMEOUT*//*

    private HttpParams httpParams;

    */
/* Es el que se cominica con el webservice
        cuando se conecta*//*

    private HttpClient httpClient;

    */
/* Nos permite mandar nuestras variables por POST *//*

    private HttpPost httpPost;

    private ProgressDialog pDialog;

    public userNet(Context context) {
        this.context = context;


        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);

        httpClient = new DefaultHttpClient(httpParams);

        httpPost = new HttpPost(SERVER_PATH);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
    }

    @Override
    protected String doInBackground(String... params) {

        // Estamos enviando dos parametros uno 0 es la accion a realizar (guardar u obtener) y el otro en dado caso que sea guardar
        // seria la nota en json a guardar
        if (params[0] == "save") {
            sendUser(params[1]);
        }else {
            // Obtenemos las notas del servidor a traves del webservice
            List<Usuarios> usua = getusers();
            if (usua.size() > 0 ) {
                // Borramos las notas del dispositivo y almacenamos las nuevas del servidor (esta logica deberia de cambiar)
                UserDataSource usuads = new UserDataSource(context);
                usuads.resetVJSTable();
                usuads.addUsuarios(usua);
            }
        }

        return null;
    }

    public void sendUser(String jsonUsers) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "save"));
        params.add(new BasicNameValuePair("jsonUsers", jsonUsers));

        String jsonResponse = getJSONResponseFromParams(params);
        Log.w("MensajeDelServidor", jsonResponse);
    }

    public List<Usuarios> getusers() {
        List<Usuarios> usuas = new ArrayList<Usuarios>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "get"));

        // El objeto JSONArray nos servira para poder almacenar todas las notas que el servidor nos devuelva en un json sepradas
        JSONArray data = null;
        String jsonResponse = getJSONResponseFromParams(params);

        try{
            // Obtenemos las notas devueltas por el servidor en una string en json
            data = new JSONArray(jsonResponse);
            if (data == null || data.length() == 0 ) return usuas;

            // Por cada nota que este dentro del json la vamos recorriendo y guardando en nuestra lista de notas
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                // vean como id, title y content tienen el mismo nombre que las columnas que nos regreso el webservice de la base de datos
                int idusuario = row.getInt("idusuario");
                String nombreusuario = row.getString("nombreusuario");
                String apellidos = row.getString("apellidos");
                int telegono = row.getInt("telefono");
                //String imagen = row.getString("imagen");
                String correo = row.getString("correo");
                String contrasenia = row.getString("contrasenia");
                String username = row.getString("username");

                //usuas.add(new Usuarios(idusuario,nombreusuario,apellidos,telegono,null,correo,contrasenia,username));
                usuas.add(new Usuarios(idusuario,nombreusuario,apellidos,telegono,correo,contrasenia,username));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return usuas;
    }

    private String getJSONResponseFromParams(List<NameValuePair> params) {

        String jsonResponse = "";
        HttpResponse response = null;
        try {
            */
/* Asignamos los parametros que vamos a enviar por el metodo POST al PHP *//*

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            */
/* Mandamos nuestra consulta con los parametros POST al webservice *//*

            response = httpClient.execute(httpPost);

            */
/* Si el webservice nos devuelve una respuesta valida, la convertimos a String ya que viene como un objeto llamado
             * InputStream y ese objeto no nos sirve para nuestros propositos *//*

            jsonResponse = inputStreamToString(response.getEntity().getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    */
/* Convierte un objeto InputStream en una String lee linea por linea y adjunta la informacion a una String
     * Con este metodo obtendremos el JSON que nos regresa el webservice. *//*

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder response = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while((rLine = rd.readLine()) != null)
            {
                response.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
*/
