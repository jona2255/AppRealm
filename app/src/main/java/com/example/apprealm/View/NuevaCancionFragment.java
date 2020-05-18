package com.example.apprealm.View;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.apprealm.Controller.CancionesViewModel;
import com.example.apprealm.R;
import com.example.apprealm.model.Cancion;


public class NuevaCancionFragment extends Fragment {

    private CancionesViewModel cancionesViewModel;
    private NavController navController;

    private EditText nombreCompletoEditText;
    private Button addCancion;
    private TextView cancionTextView;

    private Cancion cancionSelecionada;

    public NuevaCancionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_cancion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        cancionesViewModel = ViewModelProviders.of(requireActivity()).get(CancionesViewModel.class);


        nombreCompletoEditText = view.findViewById(R.id.edittext_nombre);
        addCancion = view.findViewById(R.id.button_crearCancion);
        cancionTextView = view.findViewById(R.id.textview_canciontitle);

        if (cancionesViewModel.isUserEditing) {
            cancionSelecionada = cancionesViewModel.obtenerCancionDetallePorId(cancionesViewModel.userToEditId);
            nombreCompletoEditText.setText(cancionSelecionada.getNombreCompleto());
            addCancion.setText("Editar Canción");
            cancionTextView.setText("Editar Canción");
        }
        else {
            addCancion.setText("Añadir Canción");
            cancionTextView.setText("Añadir Canción");
        }

        view.findViewById(R.id.button_crearCancion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreCompletoEditText.getText().toString().isEmpty()){
                    nombreCompletoEditText.setError("Introduzca valores en los campos");
                    return;
                }
                if (cancionesViewModel.isUserEditing) {
                    Cancion cancion = new Cancion(nombreCompletoEditText.getText().toString());
                    cancionesViewModel.actualizarCancion(cancionesViewModel.userToEditId, cancion);
                    cancionSelecionada = null;
                    cancionesViewModel.isUserEditing = false;
                    cancionesViewModel.userToEditId = 0;
                }
                else cancionesViewModel.insertarCancion(new Cancion(nombreCompletoEditText.getText().toString()));
                navController.popBackStack();
            }
        });
    }
}
