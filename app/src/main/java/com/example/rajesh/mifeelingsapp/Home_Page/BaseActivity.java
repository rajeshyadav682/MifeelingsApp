package com.example.rajesh.mifeelingsapp.Home_Page;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.category.Category;
import com.example.rajesh.mifeelingsapp.user_manage.MainActivity;
import com.example.rajesh.mifeelingsapp.user_manage.MyApplication;
import com.example.rajesh.mifeelingsapp.user_manage.MyProfile;

import java.io.File;

public class BaseActivity extends AppCompatActivity {

        DrawerLayout drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle;
        Toolbar toolbar;
        private  String userId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_base);
            userId = ((MyApplication) this.getApplication()).getUserId();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
           ImageView dr_image = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.drawerImage);
            Glide.with(getApplicationContext()).load(Host_ip_Config.hostofficeIp+"/media/images/mif_users/"+userId+".png").into(dr_image);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {


                    switch (item.getItemId()) {

                        case R.id.homes:
                            Intent dash = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(dash);
                            overridePendingTransitionEnter();
                    //        drawerLayout.closeDrawers();
                            break;


                        case R.id.my_profile:
                            Intent anIntent = new Intent(getApplicationContext(), MyProfile.class);
                            startActivity(anIntent);
               //             drawerLayout.closeDrawers();

          //                  finish();
                            break;

                        case R.id.category:
                            Intent intent = new Intent(getApplicationContext(), Category.class);
                            overridePendingTransitionSlideup();
                            startActivity(intent);
                            //                finish();
              //              drawerLayout.closeDrawers();

                            break;
                        case R.id.logout:
                            new UserData(BaseActivity.this).removeUser();
                            Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(logoutIntent);
                            overridePendingTransitionExit();
                            MyApplication.getInstance().clearApplicationData();
                            clearCache();

                            finish();

            //                drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    return false;
                }
            });
drawerLayout.closeDrawers();
        }
//    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcome, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Refresh:
               onRestart();
                return true;
            case R.id.settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            actionBarDrawerToggle.syncState();
        }

        @Override
        public void finish() {
            super.finish();
            overridePendingTransitionExit();
        }

        @Override
        public void startActivity(Intent intent) {
            super.startActivity(intent);
           // overridePendingTransitionEnter();
            overridePendingTransitionExit();

        }

        /**
         * Overrides the pending Activity transition by performing the "Enter" animation.
         */
        protected void overridePendingTransitionEnter() {
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        /**
         * Overrides the pending Activity transition by performing the "Exit" animation.
         */
        protected void overridePendingTransitionExit() {
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    protected void overridePendingTransitionSlideup() {
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
    }

    public void clearCache() {
        Log.e("cache", "Clearing Cache.");
        File[] dir = this.getCacheDir().listFiles();
        if(dir != null){
            for (File f : dir){
                f.delete();
            }
        }
    }

    }

