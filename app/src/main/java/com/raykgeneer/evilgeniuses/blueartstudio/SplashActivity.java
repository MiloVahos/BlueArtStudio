package com.raykgeneer.evilgeniuses.blueartstudio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 4000; //Tiempo de duración del splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Se deshabilita la posibilidad de landscape

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //Quita la barra de notificaciones
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //Quita el Titulo
        setContentView(R.layout.activity_splash);

        //Se inicia el MainActivity una vez se acaba el tiempo
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);

        //Se le aplica una pequeña animación al logo
        ImageView Imagen = (ImageView) findViewById(R.id.ImageV);
        move(Imagen);

    }

    //Código de animación de rotación
    private void move(View view){

        AnimationSet animationSet = new AnimationSet(false);

        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(2);
        rotate.setDuration(3000);
        rotate.cancel();

        animationSet.addAnimation(rotate);

        view.startAnimation(animationSet);

    }
}


