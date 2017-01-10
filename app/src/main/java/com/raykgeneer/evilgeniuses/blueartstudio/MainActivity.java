//Ingeniero: Juan Camilo Peña Vahos
//Fecha: 28/10/2016
//Descripción: Actividad principal de la aplicación.
//Proximamente:
//Implementar un sistema de hashtag para busqueda al interior de la aplicación

package com.raykgeneer.evilgeniuses.blueartstudio;

//Librerias
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declaración de Componentes del Layout
    //Declaración de variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] tabIcons = { //Iconos de los tab
            R.drawable.ic_bulletin_board_white_36dp,
            R.drawable.ic_skull_white_36dp,
            R.drawable.ic_hanger_white_36dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Manejo de Facebook
        if(isLoggedIn()==false){ //Inicia la actividad de logging cuando no hay usuario.
            Intent i = new Intent(this,IniciarSesionActivity.class);
            startActivity(i);
        }
        //Manejo del ViewPager
        //Declaraciones
        viewPager = (ViewPager) findViewById(R.id.MainViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);

        viewPager = (ViewPager) findViewById(R.id.MainViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //MANEJO DE LA TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

    }

    @Override //Se infla el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    //Esta función determinar si el usuario esta loggeado o no
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]).setText("News");//Bulleting
        tabLayout.getTabAt(1).setIcon(tabIcons[1]).setText("Artistas");//Skull
        tabLayout.getTabAt(2).setIcon(tabIcons[2]).setText("Store");//Information
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NovedadesFragment(), "NEWS");
        adapter.addFrag(new TatuadoresFragment(), "ARTISTAS");
        adapter.addFrag(new StoreFragment(), "STORE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
