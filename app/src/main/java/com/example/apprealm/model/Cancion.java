package com.example.apprealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Cancion extends RealmObject {
    @PrimaryKey()
    int id;

    String nombre;
    String artista;

    public Cancion(){

    }

    public Cancion(String nombre, String artista) {
        this.nombre = nombre;
        this.artista = artista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
}
