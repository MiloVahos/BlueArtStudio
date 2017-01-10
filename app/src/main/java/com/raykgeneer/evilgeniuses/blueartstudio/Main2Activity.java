package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main2Activity extends AppCompatActivity {

    //Declaración de Componentes del Layout
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private NavigationView navigationView;
    //Declaración de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FacebookSdk.sdkInitialize(getApplicationContext());

        getSignatures(); //This method get the key hash and show it on the error debug

        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        actionbar = getSupportActionBar(); //Se obtiene el action bar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp); //Se activa el botón del menú auxiliar
        actionbar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.NavigationDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.NavigationView);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

    }

    @Override //Se infla el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(Main2Activity.this, SettingsAdminActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Esta función configura el navigation Drawer
    private void setupNavigationDrawerContent(NavigationView navigationView) {
        setFragment(0);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_news:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_tatuadores:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_designs:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_store:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_cita:
                                menuItem.setChecked(true);
                                setFragment(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_login:
                                menuItem.setChecked(true);
                                Intent intent = new Intent(Main2Activity.this, IniciarSesionActivity.class);
                                startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_help_and_feedback:
                                menuItem.setChecked(true);
                                Toast.makeText(Main2Activity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }


    //Esta función se encarga de administrar los fragments
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                NewsFragment novedades = new NewsFragment();
                fragmentTransaction.replace(R.id.MainFragment, novedades);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //TatuadoresFragment tatuadores = new TatuadoresFragment();
                //fragmentTransaction.replace(R.id.MainFragment, tatuadores);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //DesignsFragment designs = new DesignsFragment();
                //fragmentTransaction.replace(R.id.MainFragment, designs);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //StoreFragment store = new StoreFragment();
                //fragmentTransaction.replace(R.id.MainFragment, store);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //CitaFragment cita = new CitaFragment();
                //fragmentTransaction.replace(R.id.MainFragment, cita);
                fragmentTransaction.commit();
                break;

        }
    }

    public void getSignatures(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.raykgeneer.evilgeniuses.blueartstudioadmin", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
