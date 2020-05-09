package com.example.apprealm.View;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprealm.Controller.CancionesViewModel;
import com.example.apprealm.R;
import com.example.apprealm.model.Cancion;

import io.realm.RealmResults;

public class ListaCancionesFragment extends Fragment {

    private NavController navController;
    private CancionesViewModel cancionesViewModel;
    private CancionesAdapter cancionesAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;

    public ListaCancionesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_canciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        cancionesViewModel = ViewModelProviders.of(requireActivity()).get(CancionesViewModel.class);


        view.findViewById(R.id.fab_nuevaCancion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nuevaCancionFragment);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_listaCanciones);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        cancionesAdapter = new CancionesAdapter();

        cancionesAdapter.establecerListaCanciones(cancionesViewModel.obtenerCancionesDetalle());
        recyclerView.setAdapter(cancionesAdapter);

        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RealmResults<Cancion> canciones = newText.equals("") ? cancionesViewModel.obtenerCancionesDetalle() : cancionesViewModel.obtenerCancionesDetallePorNombre(newText);
                cancionesAdapter.establecerListaCanciones(canciones);
                recyclerView.setAdapter(cancionesAdapter);

                return true;
            }
        });


    }


    class CancionesAdapter extends RecyclerView.Adapter<CancionesAdapter.CancionViewHolder>{

        RealmResults<Cancion> cancionDetalleList;

        @NonNull
        @Override
        public CancionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CancionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cancion, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CancionViewHolder holder, int position) {
            final Cancion cancion = cancionDetalleList.get(position);
            Log.i("Logger", String.valueOf(cancion.getId()));
            holder.nombreTextView.setText(cancion.getNombre());
            holder.artsitaTextView.setText(cancion.getArtista());

            holder.eliminarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancionesViewModel.eliminarCancion(cancion.getId());
                    navController.navigate(R.id.listaCancionesFragment);

                }
            });

            holder.editarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancionesViewModel.editarCancion(cancion.getId());
                    navController.navigate(R.id.nuevaCancionFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cancionDetalleList != null ? cancionDetalleList.size() : 0;
        }

        void establecerListaCanciones(RealmResults<Cancion> list){
            cancionDetalleList = list;
            notifyDataSetChanged();
        }

        class CancionViewHolder extends RecyclerView.ViewHolder {
            TextView nombreTextView, artsitaTextView;
            ImageView editarImageView, eliminarImageView;

            CancionViewHolder(@NonNull View itemView) {
                super(itemView);
                nombreTextView = itemView.findViewById(R.id.textview_nombre);
                artsitaTextView = itemView.findViewById(R.id.textview_artista);
                editarImageView = itemView.findViewById(R.id.imageview_editar);
                eliminarImageView = itemView.findViewById(R.id.imageview_eliminar);
            }
        }
    }
}
