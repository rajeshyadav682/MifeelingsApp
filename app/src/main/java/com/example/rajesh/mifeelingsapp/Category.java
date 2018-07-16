package com.example.rajesh.mifeelingsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<CategoryDetails> categoryDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().getTitle();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_category, null, false);
        recyclerView = (RecyclerView)findViewById(R.id.activitycategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryDetailsList = new ArrayList<>();
        String user_id = getIntent().getExtras().getString("_id");
        categoryDetails(user_id);

        }
        public void categoryDetails(final String user_id){
        final String categoryApi =Host_ip_Config.hostofficeIp+"/apis/manage-categories/cate_dt-usr-cate/"+user_id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, categoryApi,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response is",response);
                            try {

                                JSONObject jObject = new JSONObject(response);

                                JSONArray array = jObject.getJSONArray("category_dt");
                                Log.e("array", String.valueOf(array.length()));

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject categoryitems = array.getJSONObject(i);
                                    Log.e("categories",categoryitems.toString());

                                    //adding the product to product list
                                    categoryDetailsList.add(new CategoryDetails(
                                            categoryitems.getString("cate_flw_cnt"),
                                            categoryitems.getString("cate_image"),
                                            categoryitems.getString("cate_name"),
                                            categoryitems.getInt("user_cate_flw_status"),
                                            categoryitems.getString("_id"),
                                            user_id




                                    ));
                                }

                                CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), categoryDetailsList);
                                Log.e("category lists",categoryDetailsList.toString());
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error is",error.toString());

                        }
                    });

            Volley.newRequestQueue(this).add(stringRequest);
        }

}



