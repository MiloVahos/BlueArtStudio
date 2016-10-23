//Nombre: TatuadoresFragment
//Descripción: Fragment que despliega una lista con los tatuadores del negocio que son a su vez, botones a otra actividad.
//Ingeniero: Juan Camilo Peña Vahos
//Fecha de Desarrollo: 19/09/2016 a 25/09/2016
//Última Actualización: 19/09/2016

package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TatuadoresFragment extends Fragment {

    private ListView TatuadoresListV;
    private ArrayList<String> mApodos = new ArrayList<>();
    private String TApodo;
    private String TNombre;
    private String TOrigen;
    private String TEstilos;
    private String TDescripcion;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Tatuadores");
    DatabaseReference myRef2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tatuadores, container, false);

        TatuadoresListV = (ListView) view.findViewById(R.id.TatuadoresListView);

        database = FirebaseDatabase.getInstance(); //Se instancia la base de datos
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, mApodos);

        TatuadoresListV.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                NuevoTatuador nuevoTatuador = dataSnapshot.getValue(NuevoTatuador.class);
                TApodo =  nuevoTatuador.Apodo;
                mApodos.add(TApodo);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        TatuadoresListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Inicia la nueva actividad con el apodo
                String item = (String)TatuadoresListV.getItemAtPosition(i);
                myRef2 = database.getReference("Tatuadores").child(item);
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NuevoTatuador nuevoTatuador2 = dataSnapshot.getValue(NuevoTatuador.class);
                        TApodo =  nuevoTatuador2.Apodo;
                        TNombre = nuevoTatuador2.Nombre;
                        TEstilos = nuevoTatuador2.Estilos;
                        TOrigen = nuevoTatuador2.Origen;
                        TDescripcion = nuevoTatuador2.Descripcion;
                        Intent intent = new Intent(getActivity(), DatosTatuadorActivity.class);
                        intent.putExtra("Apodo", TApodo);
                        intent.putExtra("Nombre", TNombre);
                        intent.putExtra("Estilos", TEstilos);
                        intent.putExtra("Origen", TOrigen);
                        intent.putExtra("Descripcion", TDescripcion);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        return view;
    }

}
