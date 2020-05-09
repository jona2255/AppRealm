package com.example.apprealm.Controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.apprealm.model.Cancion;

import io.realm.Realm;
import io.realm.RealmResults;

public class CancionesViewModel extends AndroidViewModel {

    private Realm realm;
    public boolean isUserEditing;
    public int userToEditId;

    public CancionesViewModel(@NonNull Application application) {
        super(application);
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<Cancion> obtenerCancionesDetalle(){
        return realm.where(Cancion.class).findAll();
    }

    public RealmResults<Cancion> obtenerCancionesDetallePorNombre(String criteria){
        return realm.where(Cancion.class).contains("nombre", criteria).or().contains("artista", criteria).findAll();
    }

    public Cancion obtenerCancionDetallePorId(int id){
        return realm.where(Cancion.class).equalTo("id", id).findAll().first();
    }

    public void insertarCancion(final Cancion cancion){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = obtenerCancionesDetalle().max("id");
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                Cancion cancionTmp = realm.createObject(Cancion.class, nextId);
                cancionTmp.setNombre(cancion.getNombre());
                cancionTmp.setArtista(cancion.getArtista());
                realm.insertOrUpdate(cancionTmp);
            }
        });
    }

    public void actualizarCancion( final int id, final Cancion cancion){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Cancion cancionEditar = obtenerCancionDetallePorId(id);
                cancionEditar.setNombre(cancion.getNombre());
                cancionEditar.setArtista(cancion.getArtista());

            }
        });
    }

    public void eliminarCancion(final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Cancion> result = realm.where(Cancion.class).equalTo("id",id).findAll();
                result.deleteAllFromRealm();

            }
        });
    }

    public void editarCancion(int userToEditId) {
        isUserEditing = true;
        this.userToEditId = userToEditId;
    }
}