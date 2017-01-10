package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TatuadorProfileActivity extends AppCompatActivity {

    private DatabaseReference mRef;

    private Toolbar toolbar;
    private TextView TNombreDesign;
    private TextView TDescripcionDesign;
    private TextView TEstilosDesign;
    private TextView TOrigenDesign;
    private CircleImageView CIVFotoDesign;
    private RecyclerView RVDesigns;
    private FirebaseRecyclerAdapter mAdapter;
    private StorageReference mStorageRef;

    //Variables
    private String Apodo;
    private String Descripcion;
    private String Nombre;
    private String Estilos;
    private String ImageURL;
    private String Origen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatuador_profile);

        TNombreDesign = (TextView) findViewById(R.id.TNombreProfile);
        TDescripcionDesign = (TextView) findViewById(R.id.TDescripcionProfile);
        TEstilosDesign = (TextView) findViewById(R.id.TEstilosProfile);
        TOrigenDesign = (TextView) findViewById(R.id.TOrigenProfile);
        CIVFotoDesign = (CircleImageView) findViewById(R.id.CIVProfileImage);
        RVDesigns = (RecyclerView) findViewById(R.id.RVProfile);

        //RecyclerView Configuration
        RVDesigns.setHasFixedSize(true);
        RVDesigns.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        //GETTING DE APODO
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Apodo = extras.getString("APODO");
        }else{
            Toast.makeText(getApplicationContext(),"Something went wrong!!", Toast.LENGTH_SHORT).show();
            finish();
        }

        //Storage carpet on firebase
        mStorageRef = FirebaseStorage.getInstance().getReference().child(Apodo);

        mRef = FirebaseDatabase.getInstance().getReference().child("Tatuadores").child(Apodo);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Modelo modelo = dataSnapshot.getValue(Modelo.class);
                Descripcion = modelo.getDescripcion();
                Nombre = modelo.getNombre();
                Estilos = modelo.getEstilos();
                ImageURL = modelo.getImage();
                Origen = modelo.getOrigen();
                AcoplarDatos();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TatuadorProfileActivity.this, "Something went wrong with the database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AcoplarDatos(){

        TNombreDesign.setText(Nombre);
        TDescripcionDesign.setText(Descripcion);
        TOrigenDesign.setText(Origen);
        TEstilosDesign.setText(Estilos);
        Picasso.with(this).load(ImageURL).into(CIVFotoDesign);

    }

    public static class Modelo {

        private String Descripcion;
        private String Nombre;
        private String Estilos;
        private String Image;
        private String Origen;

        public Modelo(){

        }

        public Modelo(String Descripcion, String Nombre, String Estilos, String Image, String Origen) {
            this.Descripcion = Descripcion;
            this.Nombre = Nombre;
            this.Estilos = Estilos;
            this.Image = Image;
            this.Origen = Origen;
        }

        public String getDescripcion() {
            return Descripcion;
        }

        public String getNombre() {
            return Nombre;
        }

        public String getEstilos() {
            return Estilos;
        }

        public String getImage() {
            return Image;
        }

        public String getOrigen() { return Origen; }
    }
}
