//Ingeniero: Juan Camilo Peña Vahos
//Fecha: 02/11/2016
//Descripción: Actividad para configurar multiples aspectos de la app
//Proximamente:

package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsActivity extends AppCompatActivity {

    private Button BLogout;
    private LoginManager loginManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BLogout = (Button) findViewById(R.id.BFaceLogout);

        BLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginManager.getInstance().logOut(); //LogOut from facebook api
                FirebaseAuth.getInstance().signOut(); //LogOut from Firebase OAuth api
                Toast.makeText(getApplicationContext(),"Why you do this to me :(",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingsActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("CONFIGURACIÓN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
