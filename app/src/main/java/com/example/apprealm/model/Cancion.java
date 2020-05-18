package com.example.apprealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Cancion extends RealmObject {
    @PrimaryKey()
    int id;
    @Required
    String nombreCompleto;

    public Cancion(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Cancion(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
