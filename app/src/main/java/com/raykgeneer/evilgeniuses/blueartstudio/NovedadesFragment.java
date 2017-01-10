package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class NovedadesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_novedades, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.NewsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        mRef = FirebaseDatabase.getInstance().getReference().child("News");
        mRef.keepSynced(true);

        mAdapter = new FirebaseRecyclerAdapter<NewPost, NewPostHolder>(NewPost.class,
               R.layout.cardrow_image, NewPostHolder.class, mRef) {

            @Override
            protected void populateViewHolder(NewPostHolder viewHolder, NewPost model, int position) {

                viewHolder.setTitulo(model.getTitulo());
                viewHolder.setDescripcion(model.getDescripcion());
                viewHolder.setImage(getContext(),model.getImageURL());
            }

        };
        recyclerView.setAdapter(mAdapter);

        return v;
    }


    //ViewHolder for firebaseUI pattern to populate recyclerview
    public static class NewPostHolder extends RecyclerView.ViewHolder{

        View mView;

        public NewPostHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitulo(String titulo) {
            TextView Titulo = (TextView) mView.findViewById(R.id.TTitle);
            Titulo.setText(titulo);
        }

        public void setDescripcion(String descripcion) {
            TextView Descripcion = (TextView) mView.findViewById(R.id.TDescription);
            Descripcion.setText(descripcion);
        }

        public void setImage (Context ctx, String image){
            ImageView Imagen = (ImageView) mView.findViewById(R.id.IVFoto);
            Picasso.with(ctx).load(image).fit().into(Imagen);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class NewPost {

        private String Titulo;
        private String ImageURL;
        private String Descripcion;
        private String Fecha;

        public NewPost() {
        }

        public NewPost(String Titulo, String ImageURL, String Descripcion, String Fecha) {
            this.Titulo = Titulo;
            this.ImageURL = ImageURL;
            this.Descripcion = Descripcion;
            this.Fecha = Fecha;
        }

        public String getTitulo() {
            return Titulo;
        }

        public String getImageURL() {
            return ImageURL;
        }

        public String getDescripcion() {
            return Descripcion;
        }

        public String getFecha() { return Fecha; }
    }


}
