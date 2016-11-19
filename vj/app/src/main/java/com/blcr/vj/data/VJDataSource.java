package com.blcr.vj.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blcr.vj.model.Usuarios;
import com.blcr.vj.model.VJs;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class VJDataSource {

    private final static String TABLE_NAMEVJ = "VJ";
    private final static String TABLE_NAMEUSUARIO = "USUARIO";
    private MySQLite dbHelper;
    private SQLiteDatabase db;

    public VJDataSource(Context c) {
        dbHelper = new MySQLite(c);
    }

    public void addVJs(List<VJs> vjs) {
        for (VJs juegos : vjs) {
            addVJ(juegos);
        }
    }

    public void addVJ(VJs vjs) {
        open();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", vjs.getNombre());
        values.put("PRECIO", vjs.getPrecio());
        values.put("DESCRIPCION", vjs.getDescipcion());
        values.put("ENTREGA", vjs.getEntrega());
        values.put("IMAGEN", vjs.getImage());
        values.put("STATUS", vjs.getStatus());
        values.put("IDUSUARIOVJ", vjs.getUsuarioAlta().getIdUsuario());
        //values.put("IDUSUARIOSEPARA", null);
        values.put("CONSOLA", vjs.getConsola());
        db.insert(TABLE_NAMEVJ, null, values);
        close();
    }

    public void updateVJ(VJs vjs) {
        open();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", vjs.getNombre());
        values.put("PRECIO", vjs.getPrecio());
        values.put("DESCRIPCION", vjs.getDescipcion());
        values.put("ENTREGA", vjs.getEntrega());
        //values.put("IMAGEN", vjs.getFoto());
        values.put("STATUS", vjs.getStatus());
        values.put("IDUSUARIOVJ", vjs.getUsuarioAlta().getIdUsuario());
        //values.put("IDUSUARIOSEPARA", null);
        values.put("CONSOLA", vjs.getConsola());

        String where = "ID = " + vjs.getId();
        db.update(TABLE_NAMEVJ, values, where, null);
        close();
    }

    public void updateStatus(int id, int idjuego) {
        open();
        ContentValues values = new ContentValues();
        values.put("STATUS", "Separado");
        values.put("IDUSUARIOSEPARA", id);

        String where = "ID = " + idjuego;
        db.update(TABLE_NAMEVJ, values, where, null);
        close();
    }

    public void deleteVJ(int vjs) {
        open();
        String where = "ID = " + vjs;
        db.delete(TABLE_NAMEVJ, where, null);
        close();
    }

    public List<VJs> getAllVJs() {
        open();
        String query = "SELECT * FROM " + TABLE_NAMEVJ + " WHERE STATUS LIKE '%activo%'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        List<VJs> vjs = new ArrayList<VJs>();
        while(!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
            double precio = cursor.getDouble(cursor.getColumnIndex("PRECIO"));
            String descripcion = cursor.getString(cursor.getColumnIndex("DESCRIPCION"));
            String entrega = cursor.getString(cursor.getColumnIndex("ENTREGA"));
            //byte[] foto = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
            byte[] foto = cursor.getBlob(5);
            String status = cursor.getString(cursor.getColumnIndex("STATUS"));
            int idusuariovj = cursor.getInt(cursor.getColumnIndex("IDUSUARIOVJ"));
            int idusuariosepara = cursor.getInt(cursor.getColumnIndex("IDUSUARIOSEPARA"));
            String consola = cursor.getString(cursor.getColumnIndex("CONSOLA"));
            VJs videoj = new VJs(id, nombre, precio, descripcion, entrega, consola, status, foto,0);
            Usuarios usua = new Usuarios(idusuariovj);
            videoj.setUsuarioAlta(usua);
            Usuarios usua2 = new Usuarios(idusuariosepara);
            videoj.setUsuarioCompra(usua2);
            vjs.add(videoj);
            cursor.moveToNext();
        }

        close();
        return vjs;
    }

    public void resetVJSTable() {
        open();
        dbHelper.resetvjTable(db, 1);
        close();
    }

    public List<VJs> getAllVJ(int idUser) {
        open();
        String query = "SELECT * FROM " + TABLE_NAMEVJ + " WHERE IDUSUARIOVJ = " + idUser;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        List<VJs> vjs = new ArrayList<VJs>();
        while(!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
            double precio = cursor.getDouble(cursor.getColumnIndex("PRECIO"));
            String descripcion = cursor.getString(cursor.getColumnIndex("DESCRIPCION"));
            String entrega = cursor.getString(cursor.getColumnIndex("ENTREGA"));
            byte[] foto = cursor.getBlob(5);
            String status = cursor.getString(cursor.getColumnIndex("STATUS"));
            int idusuariovj = cursor.getInt(cursor.getColumnIndex("IDUSUARIOVJ"));
            int idusuariosepara = cursor.getInt(cursor.getColumnIndex("IDUSUARIOSEPARA"));
            String consola = cursor.getString(cursor.getColumnIndex("CONSOLA"));
            VJs videoj = new VJs(id, nombre, precio, descripcion, entrega, consola, status, foto, 1);
            Usuarios usua = new Usuarios(idusuariovj);
            videoj.setUsuarioAlta(usua);
            Usuarios usua2 = new Usuarios(idusuariosepara);
            videoj.setUsuarioCompra(usua2);
            vjs.add(videoj);
            cursor.moveToNext();
        }

        close();
        return vjs;
    }

    public VJs getVJ(int idVj) {
        open();
        //String query = "SELECT ID, NOMBRE, PRECIO, DESCRIPCION, ENTREGA, STATUS, svj.IDUSUARIOVJ, svj.IDUSUARIOSEPARA, CONSOLA, usua.CORREO  FROM " + TABLE_NAMEVJ + " AS svj ";
        //query += " inner join " + TABLE_NAMEUSUARIO + " as usua on usua.IDUSUARIO = IDUSUARIOVJ  WHERE ID = " + idVj;

        String query = "SELECT ID, NOMBRE, PRECIO, DESCRIPCION, ENTREGA, IMAGEN, STATUS, IDUSUARIOVJ, IDUSUARIOSEPARA, CONSOLA FROM " + TABLE_NAMEVJ +  " WHERE ID = " + idVj;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0 ) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
            double precio = cursor.getDouble(cursor.getColumnIndex("PRECIO"));
            String descripcion = cursor.getString(cursor.getColumnIndex("DESCRIPCION"));
            String entrega = cursor.getString(cursor.getColumnIndex("ENTREGA"));
            byte[] foto = cursor.getBlob(5);
            String status = cursor.getString(cursor.getColumnIndex("STATUS"));
            int idusuariovj = cursor.getInt(cursor.getColumnIndex("IDUSUARIOVJ"));
            int idusuariosepara = cursor.getInt(cursor.getColumnIndex("IDUSUARIOSEPARA"));
            String consola = cursor.getString(cursor.getColumnIndex("CONSOLA"));
            //String correo = cursor.getString(cursor.getColumnIndex("CORREO"));

            close();

            VJs videoj = new VJs(id, nombre, precio, descripcion, entrega, consola, status, foto);
            Usuarios usua = new Usuarios(idusuariovj);//, correo);
            videoj.setUsuarioAlta(usua);
            Usuarios usua2 = new Usuarios(idusuariosepara);
            videoj.setUsuarioCompra(usua2);

            return videoj;
        }
        close();
        return null;
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }
    private void close() {
        if (db.isOpen())
            db.close();;
    }

}
