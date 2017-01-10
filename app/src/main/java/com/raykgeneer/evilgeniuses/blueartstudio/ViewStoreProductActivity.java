package com.raykgeneer.evilgeniuses.blueartstudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class ViewStoreProductActivity extends AppCompatActivity {

    private TextView TProducto;
    private TextView TPrecio;
    private ImageView IVImage;

    private String Producto;
    private String Precio;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store_product);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Producto = extras.getString("PRO");
            Precio =  extras.getString("PRE");
            URL = extras.getString("URL");
            //The key argument here must match that used in the other activity
        }else{
            Toast.makeText(getApplicationContext(),"Something went wrong!!", Toast.LENGTH_SHORT).show();
            finish();
        }

        TProducto = (TextView) findViewById(R.id.TPro);
        TPrecio = (TextView) findViewById(R.id.TPre);
        IVImage = (ImageView) findViewById(R.id.ImageView);

        TPrecio.setText(Precio);
        TProducto.setText(Producto);
        Picasso.with(getApplicationContext()).load(URL).resize(600,1000).onlyScaleDown().into(IVImage);

    }
}
