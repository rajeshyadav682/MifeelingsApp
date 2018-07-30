package com.example.rajesh.mifeelingsapp.user_manage;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajesh.mifeelingsapp.Home_Page.UserData;
import com.example.rajesh.mifeelingsapp.Home_Page.WelcomeActivity;
import com.example.rajesh.mifeelingsapp.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import android.animation.ObjectAnimator;

public class SplashActivity extends AppCompatActivity {
    ObjectAnimator objectanimator;
    private TextView posttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         ImageView splash = (ImageView) findViewById(R.id.splashImage);
        splash.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.clock_animation));
        posttext = (TextView) findViewById(R.id.postfelling);
        posttext.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide));
        getSupportActionBar().hide();

        final UserData userData = new UserData(SplashActivity.this);
        ((MyApplication) this.getApplication()).setUserId(userData.getUserid());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (userData.getUserid() != "") {
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.putExtra("_id", userData.getUserid());

                    Log.e("userdata", userData.getUserid());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }


}
