/*
package com.blcr.vj.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.blcr.vj.MainActivity;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.Usuarios;
import com.blcr.vj.model.VJs;

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

public class vjNet extends AsyncTask<String, String, String> {
    */
/* Path donde se encuenta el webservice *//*

    //private static final String SERVER_PATH = "http://192.168.0.4:100/vj.php";
    private static final String SERVER_PATH = "http://blcrshop.x10host.com/vj.php";

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

    public vjNet(Context context) {
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
            sendVJ(params[1]);
        }else {
            // Obtenemos las notas del servidor a traves del webservice
            List<VJs> games = getVJS();
            if (games.size() > 0 ) {
                // Borramos las notas del dispositivo y almacenamos las nuevas del servidor (esta logica deberia de cambiar)
                VJDataSource vjds = new VJDataSource(context);
                vjds.resetVJSTable();
                vjds.addVJs(games);

                // Obtenemos el fragmento de donde mostramos las notas para poder actualizar el listview con las nuevas notas obtenidas
                // por el webservice
                MainActivity ma = (MainActivity) context;
                ma.updateMyGamesFromServer();
            }
        }

        return null;
    }

    public void sendVJ(String jsonVJS) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "save"));
        params.add(new BasicNameValuePair("jsonVJS", jsonVJS));

        String jsonResponse = getJSONResponseFromParams(params);
        Log.w("MensajeDelServidor", jsonResponse);
    }

    public List<VJs> getVJS() {
        List<VJs> vjs = new ArrayList<VJs>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "get"));

        // El objeto JSONArray nos servira para poder almacenar todas las notas que el servidor nos devuelva en un json sepradas
        JSONArray data = null;
        String jsonResponse = getJSONResponseFromParams(params);

        try{
            // Obtenemos las notas devueltas por el servidor en una string en json
            data = new JSONArray(jsonResponse);
            if (data == null || data.length() == 0 ) return vjs;

            // Por cada nota que este dentro del json la vamos recorriendo y guardando en nuestra lista de notas
            for (int i = 0; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                // vean como id, title y content tienen el mismo nombre que las columnas que nos regreso el webservice de la base de datos
                int id = row.getInt("id");
                String nombre = row.getString("nombre");
                double precio = row.getDouble("precio");
                String descripcion = row.getString("descripcion");
                String entrega = row.getString("entrega");
                String imagen = row.getString("imagen");
               // byte [] nono = Base64.decode(imagen, Base64.DEFAULT);
                String status = row.getString("status");
                int idusuariovj = row.getInt("idusuariovj");

                String nulo = row.getString("idusuariosepara");
                int idusuariosepara = 0;
                if(!"null".contains(nulo)) {
                    idusuariosepara = row.getInt("idusuariosepara");
                }


                String consola = row.getString("consola");

                VJs juegos = new VJs(id,nombre,precio,descripcion,entrega,consola,status,null);
                Usuarios usua = new Usuarios(idusuariovj);
                juegos.setUsuarioAlta(usua);
                Usuarios usua2 = new Usuarios(idusuariosepara);
                juegos.setUsuarioCompra(usua2);

                vjs.add(juegos);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return vjs;
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
