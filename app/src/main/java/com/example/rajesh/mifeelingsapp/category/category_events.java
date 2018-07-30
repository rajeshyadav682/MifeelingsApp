package com.example.rajesh.mifeelingsapp.category;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.rajesh.mifeelingsapp.Home_Page.BaseActivity;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.user_manage.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class category_events extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private String user_id;
    private String cat_id;
    private List<CategoryEvents_Details> categoryDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_category_events, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
        categoryDetailsList = new ArrayList<>();
        user_id = ((MyApplication) this.getApplication()).getUserId();
        cat_id = ((MyApplication) this.getApplication()).getCat_id();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.events_details_items, null, false);
        recyclerView = (RecyclerView) findViewById(R.id.events_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_category_events);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        dataLoad();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                dataLoad();
                Toast.makeText(category_events.this, "refresh", Toast.LENGTH_SHORT).show();


            }
        });


    }


    public void dataLoad(){


        String cat_wise_Api = "http://110.227.190.241:7070/apis/manage-feeds/cate-feeds/"+cat_id+"/"+user_id+"/1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, cat_wise_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(" event response is-----",response);
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("Log response",response);
                        Toast.makeText(category_events.this, "log", Toast.LENGTH_SHORT).show();

                        Log.e("test","before");
                        try {
                            Log.e("test","after");
                            JSONObject jObject = new JSONObject(response);
                            JSONObject jsonData = jObject.optJSONObject("feed_details");
                            JSONArray array = jsonData.getJSONArray("feed_dt");

                         //   JSONArray cmntArray = array.getJSONArray("");
                            Log.e("log response 2",array.toString());

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject categoryitems = array.getJSONObject(i);
                                Log.e("categories events", categoryitems.toString());
                                Log.e("feed", categoryitems.getString("feeds"));

                                categoryDetailsList.add(new CategoryEvents_Details(
                                        categoryitems.getString("_id"),
                                        categoryitems.getString("user_id"),
                                        categoryitems.getString("feeds"),
                                        categoryitems.getString("cate_name"),
                                        categoryitems.getString("cate_id"),
                                        categoryitems.getString("feed_dt"),
                                        categoryitems.getInt("post_type"),
                               //         categoryitems.getString("comment_dec"),
                                        categoryitems.getString("user_like"),
                                        categoryitems.getString("like")
                                ));
                            }
                            feeds_Adaptor adapter = new feeds_Adaptor(getApplicationContext(), categoryDetailsList);
                            Log.e("category lists",categoryDetailsList.toString());
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       swipeRefreshLayout.setRefreshing(false);
                        Log.e("error is",error.toString());

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);

    }
    }

