package com.blcr.vj.model;

import java.io.InputStream;
import com.google.gson.Gson;

/**
 * Created by ErickAlejandro on 03/11/2015.
 */
public class Usuarios {
    int idUsuario;
    String nombreUsuario;
    String apellidos;
    String telefono;
    InputStream foto;
    String correo;
    String contra;
    String username;
    int myusua;
    private byte[] image;

    public int getMyusua() {
        return myusua;
    }

    public void setMyusua(int myusua) {
        this.myusua = myusua;
    }

    public Usuarios(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(int idUsuario, String correo) {
        this.idUsuario = idUsuario;
        this.correo = correo;
    }

    public Usuarios(int idUsuario, String contra, String username) {
        this.idUsuario = idUsuario;
        this.contra = contra;
        this.username = username;
    }

    public Usuarios(String nombreUsuario, String apellidos, String telefono, String correo, String contra, String username, byte[] image) {
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.contra = contra;
        this.username = username;
        this.image = image;
    }

    public Usuarios(int idUsuario, String nombreUsuario, String apellidos, String telefono, String correo, String contra, String username) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.contra = contra;
        this.username = username;
    }

    public Usuarios(int idUsuario, String nombreUsuario, String apellidos, String telefono, InputStream foto, String correo, String contra, String username) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.foto = foto;
        this.correo = correo;
        this.contra = contra;
        this.username = username;
    }

    public Usuarios(String nombreUsuario, String apellidos, String telefono, InputStream foto, String correo, String contra, String username) {
        this.nombreUsuario = nombreUsuario;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.foto = foto;
        this.correo = correo;
        this.contra = contra;
        this.username = username;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getUsername() {
        return username;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
