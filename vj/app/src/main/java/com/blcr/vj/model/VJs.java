package com.blcr.vj.model;

import java.io.InputStream;
import com.google.gson.Gson;

/**
 * Created by ErickAlejandro on 02/11/2015.
 */
public class VJs {
    int id;
    String nombre;
    double precio;
    String descipcion;
    String entrega;
    String consola;
    String status;
    Usuarios usuarioAlta;
    Usuarios usuarioCompra;
    int idusua;
    //InputStream foto;
    int misvjs;
    private byte[] image;
    String imagenjson;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public VJs(String nombre, String consola, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.consola = consola;
    }

    public VJs(String nombre, double precio, String descipcion, String entrega, String consola, String status, int usuarioAlta, String imagenjson) {
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
        this.idusua = usuarioAlta;
        this.imagenjson = imagenjson;
    }

    public VJs(int id, String nombre, double precio, String descipcion, String entrega, String consola, String status, byte[] foto, int misvjs) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
        this.image = foto;
        this.misvjs = misvjs;
    }

    public VJs(String nombre, double precio, String descipcion, String entrega, String consola, String status, InputStream foto) {
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
       // this.foto = foto;
    }


    public VJs(String nombre, double precio, String descipcion, String entrega, String consola, String status, int usuarioAlta, byte[] image) {
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
        this.idusua = usuarioAlta;
        this.image = image;
    }

    public VJs(String nombre, double precio, String descipcion, String entrega, String consola, String status, byte[] foto, int idusua) {
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
        this.image = foto;
        this.idusua = idusua;
    }

    public VJs(int id, String nombre, double precio, String descipcion, String entrega, String consola, String status, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descipcion = descipcion;
        this.entrega = entrega;
        this.consola = consola;
        this.status = status;
        this.image = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuarios getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Usuarios usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Usuarios getUsuarioCompra() {
        return usuarioCompra;
    }

    public void setUsuarioCompra(Usuarios usuarioCompra) {
        this.usuarioCompra = usuarioCompra;
    }

   // public InputStream getFoto() {
   //     return foto;
   // }

   // public void setFoto(InputStream foto) {
   //     this.foto = foto;
    //}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMisvjs() {
        return misvjs;
    }

    public void setMisvjs(int misvjs) {
        this.misvjs = misvjs;
    }

    public int getIdusua() {
        return idusua;
    }

    public void setIdusua(int idusua) {
        this.idusua = idusua;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "vj{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descipcion='" + descipcion + '\'' +
                ", entrega='" + entrega + '\'' +
                ", consola='" + consola + '\'' +
                '}';
    }
}
