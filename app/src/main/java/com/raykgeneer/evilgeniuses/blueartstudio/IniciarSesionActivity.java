package com.raykgeneer.evilgeniuses.blueartstudio;

//Ingeniero: Juan Camilo Peña Vahos
//Descripción: Actividad de Inicio de Sesión
//Ultima Actualización: 05/01/2017

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class IniciarSesionActivity extends AppCompatActivity {

    private Button Blogin;
    private Button BIniciarSesion;
    private CheckBox CBVisibility;
    private EditText ECorreo;
    private EditText EPass;

    private CallbackManager callbackManager;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private FirebaseAuth mAuth; //Autentificación con facebook y firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Declaración de variables en caso de usuario
    private String NombreUsuario; //Estos datos ya los tengo pero no los he usado aun,
    private Uri PhotoId;
    private String UserEmail;

    //Declaración de variables para autenticación con correo y contraseñal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Declaración de elementos
        Blogin = (Button) findViewById(R.id.BLoggin);
        CBVisibility = (CheckBox) findViewById(R.id.CBVisibility);
        ECorreo = (EditText) findViewById(R.id.ECorreo);
        EPass = (EditText) findViewById(R.id.EPass);
        BIniciarSesion = (Button) findViewById(R.id.BIniciarSesion);
        mAuth = FirebaseAuth.getInstance();

        //Poner visibles los edit text
        CBVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CBVisibility.isChecked()) {
                    ECorreo.setVisibility(View.VISIBLE);
                    EPass.setVisibility(View.VISIBLE);
                    Blogin.setVisibility(View.INVISIBLE);
                    BIniciarSesion.setVisibility(View.VISIBLE);
                }else{
                    ECorreo.setVisibility(View.INVISIBLE);
                    EPass.setVisibility(View.INVISIBLE);
                    Blogin.setVisibility(View.VISIBLE);
                    BIniciarSesion.setVisibility(View.INVISIBLE);
                }
            }

        });

        //Configuración cuando inicia sesión un adminsitrador o tatuador.
        BIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email;
                String Pass;
                Pass = EPass.getText().toString();
                Email = ECorreo.getText().toString();
                IniciarSesionCorreoPass(Email,Pass);
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(IniciarSesionActivity.this, Main2Activity.class));
                }
            }
        };
        //***************FIN DEL INICIO DE SESIÓN CON CORREO*******************************************


        FacebookSdk.sdkInitialize(getApplicationContext()); //Inicializa el SDK de facebook
        callbackManager = CallbackManager.Factory.create();

        //Manejo del botón de iniciar sesión en facebook
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Toast.makeText(getApplicationContext(), "Bienvenido a Blue Art Studio App", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(IniciarSesionActivity.this,
                        Arrays.asList("email","public_profile"));
            }
        });

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    NombreUsuario = user.getDisplayName();
                    PhotoId = user.getPhotoUrl();
                    UserEmail = user.getEmail();
                    Toast.makeText(IniciarSesionActivity.this,NombreUsuario,Toast.LENGTH_SHORT).show();
                } else {
                    //User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    //Toast.makeText(IniciarSesionActivity.this, "See you later!!!", Toast.LENGTH_SHORT).show();
                }

            }
        };*/

        //CONFIGURACIÓN DE LA PARTE VISUAL
        viewPager = (ViewPager) findViewById(R.id.SlidesViewPager);
        layouts = new int[]{R.layout.image_slide1,R.layout.image_slide2,
                R.layout.image_slide3, R.layout.image_slide4};
        changeStatusBarColor(); //Notification bar is now transparent
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
    }

    //OVERRIDED METHODS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {} //

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //METHODS CREATED
    private void IniciarSesionCorreoPass(final String Email, final String Pass){
        if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass)){
            Toast.makeText(IniciarSesionActivity.this,R.string.CamposVacios,Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(Email, Pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(IniciarSesionActivity.this, "Authentication failed...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    //***********************************************************************************************************

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //View pager adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter() {}
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }
        @Override
        public int getCount() {
            return layouts.length;
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(IniciarSesionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                        // ...
                    }
                });
    }
}
