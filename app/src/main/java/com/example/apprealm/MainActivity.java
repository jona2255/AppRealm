package com.example.apprealm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.apprealm.Controller.CancionesViewModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private CancionesViewModel cancionesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("canciones.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
        setContentView(R.layout.activity_main);
        cancionesViewModel = ViewModelProviders.of(this).get(CancionesViewModel.class);

    }

    @Override
    public void onBackPressed() {
        cancionesViewModel.isUserEditing = false;
        super.onBackPressed();
    }
}
