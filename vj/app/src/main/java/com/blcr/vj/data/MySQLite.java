package com.blcr.vj.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class MySQLite extends SQLiteOpenHelper {

    final static int DATABASE_VERSION = 1;
    final static String DATABASE_NAME = "VideoJuegos";
    int borrar=0;

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableUsuario = "CREATE TABLE USUARIO(";
        tableUsuario += "IDUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT, ";
        tableUsuario += "NOMBREUSUARIO TEXT NOT NULL, ";
        tableUsuario += "APELLIDOS TEXT NOT NULL,";
        tableUsuario += "TELEGONO TEXT NOT NULL,";
        tableUsuario += "IMAGEN LONGBLOB NULL,";
        tableUsuario += "CORREO TEXT NOT NULL,";
        tableUsuario += "CONTRASENIA TEXT NOT NULL, USERNAME TEXT NOT NULL)";

        String tableVJ = "CREATE TABLE VJ(";
        tableVJ += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        tableVJ += "NOMBRE TEXT NOT NULL, ";
        tableVJ += "PRECIO REAL NOT NULL,";
        tableVJ += "DESCRIPCION TEXT NOT NULL,";
        tableVJ += "ENTREGA TEXT NOT NULL,";
        tableVJ += "IMAGEN LONGBLOB NULL,";
        tableVJ += "STATUS TEXT NOT NULL,";
        tableVJ += "IDUSUARIOVJ INTEGER NOT NULL,";
        tableVJ += "IDUSUARIOSEPARA INTEGER NULL,";
        tableVJ += "CONSOLA TEXT NOT NULL, ";
        tableVJ += "FOREIGN KEY(IDUSUARIOVJ) REFERENCES USUARIO(IDUSUARIO),";
        tableVJ += "FOREIGN KEY(IDUSUARIOSEPARA) REFERENCES USUARIO(IDUSUARIO))";




        String tableUsuarioTemp = "CREATE TABLE IF NOT EXISTS USUARIOTEMPO ( ";
        tableUsuarioTemp += "IDUSUARIOTEMP INTEGER PRIMARY KEY AUTOINCREMENT, ";
        tableUsuarioTemp += "IDUSUARIOWEB INTEGER NOT NULL, ";
        tableUsuarioTemp += "CONTRASENIATEMP TEXT NOT NULL, USERNAMETEMP TEXT NOT NULL)";


        db.execSQL(tableUsuarioTemp);




        db.execSQL(tableUsuario);
        db.execSQL(tableVJ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(borrar == 1) {
            db.execSQL("DROP TABLE IF EXISTS USUARIO");
        }
        db.execSQL("DROP TABLE IF EXISTS VJ");
        onCreate(db);
    }

    public void resetvjTable(SQLiteDatabase db, int dele) {
        borrar = dele;
        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION + 1);
    }

}
