package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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


public class StoreFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;

    private String Producto;
    private String Precio;
    private String URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store, container, false);

        //Recyclever view configuration
        recyclerView = (RecyclerView) v.findViewById(R.id.StoreRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRef = FirebaseDatabase.getInstance().getReference().child("Store");
        mRef.keepSynced(true);

        mAdapter = new FirebaseRecyclerAdapter<StoreFragment.NewProduct, StoreFragment.StoreHolder>(StoreFragment.NewProduct.class,
                R.layout.cardrow_product, StoreFragment.StoreHolder.class, mRef) {

            @Override
            protected void populateViewHolder(final StoreFragment.StoreHolder viewHolder,
                                              StoreFragment.NewProduct model,
                                              final int position) {
                viewHolder.setProducto(model.getProducto());
                viewHolder.setPrecio(model.getPrecio());
                viewHolder.setImage(getContext(),model.getImageURL());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final Intent i = new Intent(view.getContext(), ViewStoreProductActivity.class);
                        final DatabaseReference databaseRef = mAdapter.getRef(position);
                        databaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                NewProduct newProduct = dataSnapshot.getValue(NewProduct.class);
                                Producto = newProduct.getProducto();
                                Precio = newProduct.getPrecio();
                                URL = newProduct.getImageURL();
                                i.putExtra("PRO", Producto);
                                i.putExtra("PRE", Precio);
                                i.putExtra("URL", URL);
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
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        return v;
    }

    //ViewHolder for firebaseUI pattern to populate recyclerview
    public static class StoreHolder extends RecyclerView.ViewHolder{

        View mView;

        public StoreHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setProducto(String producto) {
            TextView Producto = (TextView) mView.findViewById(R.id.TProducto);
            Producto.setText(producto);
        }

        public void setPrecio(String precio) {
            TextView Precio = (TextView) mView.findViewById(R.id.TPrecio);
            Precio.setText(precio);
        }

        public void setImage (Context ctx, String image){
            ImageView Imagen = (ImageView) mView.findViewById(R.id.IVProduct);
            Picasso.with(ctx).load(image).transform(new RoundedCornersTransform(ctx)).into(Imagen);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class NewProduct {

        private String Producto;
        private String ImageURL;
        private String Precio;

        public NewProduct() {
        }

        public NewProduct(String Producto, String ImageURL, String Precio) {
            this.Producto = Producto;
            this.ImageURL = ImageURL;
            this.Precio = Precio;
        }

        public String getProducto() {
            return Producto;
        }

        public String getImageURL() {
            return ImageURL;
        }

        public String getPrecio() {
            return Precio;
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        public SpacesItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
    }


}
