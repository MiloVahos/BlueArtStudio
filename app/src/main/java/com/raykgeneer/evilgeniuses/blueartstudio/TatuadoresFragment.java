//Nombre: TatuadoresFragment
//Descripción: Fragment que despliega una lista con los tatuadores del negocio que son a su vez, botones a otra actividad.
//Ingeniero: Juan Camilo Peña Vahos
//Fecha de Desarrollo: 19/09/2016 a 25/09/2016
//Última Actualización: 28/10/2016

package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class TatuadoresFragment extends Fragment {

    //ELEMENTOS GRÁFICOS
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tatuadores, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.TatuadoresRecyclerV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mRef = FirebaseDatabase.getInstance().getReference().child("Tatuadores");
        mRef.keepSynced(true);

        mAdapter = new FirebaseRecyclerAdapter<TatuadoresFragment.Artist, TatuadoresFragment.ArtistHolder>(
                TatuadoresFragment.Artist.class,
                R.layout.tatuadore_card_view,
                TatuadoresFragment.ArtistHolder.class,
                mRef
        ) {

            @Override
            protected void populateViewHolder(ArtistHolder viewHolder, Artist model, final int position) {
                viewHolder.setEstilos(model.getEstilos());
                viewHolder.setNombre(model.getNombre());
                viewHolder.setImage(getContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final Intent i = new Intent(view.getContext(), TatuadorProfileActivity.class);
                        DatabaseReference databaseRef = mAdapter.getRef(position);
                        databaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TatuadoresFragment.Artist artist = dataSnapshot.getValue(TatuadoresFragment.Artist.class);
                                String apodo = artist.getApodo();
                                i.putExtra("APODO", apodo);
                                startActivity(i);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(view.getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

        };
        recyclerView.setAdapter(mAdapter);


        return view;
    }

    //ViewHolder for firebaseUI pattern to populate recyclerview
    public static class ArtistHolder extends RecyclerView.ViewHolder{

        View mView;

        public ArtistHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setNombre(String titulo) {
            TextView Titulo = (TextView) mView.findViewById(R.id.TNombreTatuador);
            Titulo.setText(titulo);
        }

        public void setEstilos(String descripcion) {
            TextView Descripcion = (TextView) mView.findViewById(R.id.TEstilos);
            Descripcion.setText(descripcion);
        }

        public void setImage(Context ctx, String image){
            ImageView Imagen = (ImageView) mView.findViewById(R.id.ArtistImageV);
            Picasso.with(ctx).load(image).fit().into(Imagen);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class Artist {

        private String Nombre;
        private String Image;
        private String Estilos;
        private String Apodo;

        public Artist() {
        }

        public Artist(String Nombre, String Image, String Estilos, String Apodo) {
            this.Nombre = Nombre;
            this.Image = Image;
            this.Estilos = Estilos;
            this.Apodo = Apodo;
        }

        public String getNombre() {
            return Nombre;
        }

        public String getImage() {
            return Image;
        }

        public String getEstilos() {
            return Estilos;
        }

        public String getApodo(){
            return Apodo;
        }
    }
}
