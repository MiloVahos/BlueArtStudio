package com.raykgeneer.evilgeniuses.blueartstudio;

import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DatosTatuadorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerView;
    private CardView cardView;
    private ImageView IFotoTatuador;

    CardAdapter cardAdapter;
    List<TatuadoresCardView> DatosTatuadores;

    //Variables
    private String Apodo;
    private String Nombre;
    private String Origen;
    private String Estilos;
    private String Descripcion;

    //Firebase
    private FirebaseStorage mStorage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_tatuador);

        //Instancia de la base de datos
        mStorage = FirebaseStorage.getInstance();
        storageRef =  mStorage.getReference().child("FotosTatuadores");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.TatuadoresRecyclerV);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IFotoTatuador = (ImageView) findViewById(R.id.TatuadorPic);

        Bundle bundle = getIntent().getExtras();
        Apodo = bundle.getString("Apodo");
        Nombre = bundle.getString("Nombre");
        Origen = bundle.getString("Origen");
        Estilos = bundle.getString("Estilos");
        Descripcion = bundle.getString("Descripcion");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        collapsingToolbarLayout.setTitle(Apodo);

        DatosTatuadores = new ArrayList<>();
        DatosTatuadores.add(new TatuadoresCardView("Nombre:", Nombre));
        DatosTatuadores.add(new TatuadoresCardView("Apodo:", Apodo));
        DatosTatuadores.add(new TatuadoresCardView("Origen:", Origen));
        DatosTatuadores.add(new TatuadoresCardView("Estilos:", Estilos));
        DatosTatuadores.add(new TatuadoresCardView("Descripción:", Descripcion));


        cardAdapter = new CardAdapter(DatosTatuadores);
        //set adapter
        recyclerView.setAdapter(cardAdapter);


        storageRef.child(Apodo).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getBaseContext()).load(uri).fit().centerCrop().into(IFotoTatuador);
            }
        });



    }

}
