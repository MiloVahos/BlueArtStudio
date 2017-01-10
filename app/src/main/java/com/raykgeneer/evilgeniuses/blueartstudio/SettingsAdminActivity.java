package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsAdminActivity extends AppCompatActivity {

    private Button BAdminLogout;
    private Toolbar ToolbarAdminSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_admin);

        BAdminLogout = (Button) findViewById(R.id.BAdminLogout);
        ToolbarAdminSet = (Toolbar) findViewById(R.id.SettingsAdminToolbar);

        BAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut(); //LogOut from Firebase OAuth api
                Toast.makeText(getApplicationContext(),"See you later...",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingsAdminActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(ToolbarAdminSet);
        ToolbarAdminSet.setTitle("CONFIGURACIÓN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ToolbarAdminSet.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        ToolbarAdminSet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
