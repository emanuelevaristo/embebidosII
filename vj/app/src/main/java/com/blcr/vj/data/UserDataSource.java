package com.blcr.vj.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blcr.vj.model.Usuarios;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class UserDataSource {
    private final static String TABLE_NAMEUSUARIO = "USUARIO";
    private final static String TABLE_NAMEUSUARIOTEMPO = "USUARIOTEMPO";
    private MySQLite dbHelper;
    private SQLiteDatabase db;

    public UserDataSource(Context c) {
        dbHelper = new MySQLite(c);
    }

    public void addUsuarios(List<Usuarios> usua) {
        for (Usuarios usuario : usua) {
            addUser(usuario, false);
        }
    }

    public void addUser(Usuarios usua, boolean altacel) {
        open();
        ContentValues values = new ContentValues();
        values.put("NOMBREUSUARIO", usua.getNombreUsuario());
        values.put("APELLIDOS", usua.getApellidos());
        values.put("TELEGONO", usua.getTelefono());
        values.put("IMAGEN", usua.getImage());
        values.put("CORREO", usua.getCorreo());
        values.put("CONTRASENIA", usua.getContra());
        values.put("USERNAME", usua.getUsername());
        db.insert(TABLE_NAMEUSUARIO, null, values);


if(altacel) {

    String query = "SELECT last_insert_rowid() as IDUSUARIO from  " + TABLE_NAMEUSUARIO;
    Cursor cursor = db.rawQuery(query, null);
    cursor.moveToFirst();

    List<Usuarios> uss = new ArrayList<Usuarios>();
    if (cursor.getCount() > 0 ) {

        int id = cursor.getInt(cursor.getColumnIndex("IDUSUARIO"));


        ContentValues values1 = new ContentValues();
        values1.put("IDUSUARIOWEB", id);
        values1.put("CONTRASENIATEMP", usua.getContra());
        values1.put("USERNAMETEMP", usua.getUsername());
        db.insert(TABLE_NAMEUSUARIOTEMPO, null, values);


    }





}

        close();

    }

    public void updateUser(Usuarios usua) {
        open();
        ContentValues values = new ContentValues();
        values.put("NOMBREUSUARIO", usua.getNombreUsuario());
        values.put("APELLIDOS", usua.getApellidos());
        values.put("TELEGONO", usua.getTelefono());
        //values.put("IMAGEN", usua.getFoto());
        values.put("CORREO", usua.getCorreo());
        values.put("CONTRASENIA", usua.getContra());
        values.put("USERNAME", usua.getUsername());
        String where = "IDUSUARIO = " + usua.getIdUsuario();
        db.update(TABLE_NAMEUSUARIO, values, where, null);
        close();
    }

    public void deleteUser(Usuarios usua) {
        open();
        String where = "IDUSUARIO = " + usua.getIdUsuario();
        db.delete(TABLE_NAMEUSUARIO, where, null);
        close();
    }

    public List<Usuarios> getAllUsers() {
        open();
        String query = "SELECT * FROM " + TABLE_NAMEUSUARIO;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        List<Usuarios> usua = new ArrayList<Usuarios>();
        while(!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nombreusuario = cursor.getString(cursor.getColumnIndex("NOMBREUSUARIO"));
            String apellidos = cursor.getString(cursor.getColumnIndex("APELLIDOS"));
            String telefono = cursor.getString(cursor.getColumnIndex("TELEGONO"));
            // Blob foto = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
            String correo = cursor.getString(cursor.getColumnIndex("CORREO"));
            String contrasenia = cursor.getString(cursor.getColumnIndex("CONTRASENIA"));
            String username = cursor.getString(cursor.getColumnIndex("USERNAME"));

            Usuarios usuarios = new Usuarios(id, nombreusuario, apellidos, telefono, null, correo, contrasenia, username);
            usua.add(usuarios);
            cursor.moveToNext();
        }

        close();
        return usua;
    }

    public int login(String usuario, String contra)
    {
        open();
        String query = "SELECT IDUSUARIO FROM " + TABLE_NAMEUSUARIO + " WHERE USERNAME = '" + usuario + "' AND CONTRASENIA = '" + contra +"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0 ) {
            int id = cursor.getInt(cursor.getColumnIndex("IDUSUARIO"));

            close();

            return  id;
        }
        close();
        return 0;
    }

    public Usuarios getUsuario(int idUser) {
        open();
        String query = "SELECT * FROM " + TABLE_NAMEUSUARIO + " WHERE IDUSUARIO = " + idUser;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0 ) {
            int id = cursor.getInt(cursor.getColumnIndex("IDUSUARIO"));
            String nombreusuario = cursor.getString(cursor.getColumnIndex("NOMBREUSUARIO"));
            String apellidos = cursor.getString(cursor.getColumnIndex("APELLIDOS"));
            String telefono = cursor.getString(cursor.getColumnIndex("TELEGONO"));
            // Blob foto = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
            String correo = cursor.getString(cursor.getColumnIndex("CORREO"));
            String contrasenia = cursor.getString(cursor.getColumnIndex("CONTRASENIA"));
            String username = cursor.getString(cursor.getColumnIndex("USERNAME"));

            close();

            Usuarios usuarios = new Usuarios(id, nombreusuario, apellidos, telefono, null, correo, contrasenia, username);

            return usuarios;
        }
        close();
        return null;


       }

    public void resetVJSTable() {
        open();
        dbHelper.resetvjTable(db, 1);
        close();
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }
    private void close() {
        if (db.isOpen())
            db.close();;
    }

}
