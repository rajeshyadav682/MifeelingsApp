package com.example.rajesh.mifeelingsapp.category;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rajesh.mifeelingsapp.Home_Page.BaseActivity;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.user_manage.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category extends BaseActivity {
private String user_id;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<CategoryDetails> categoryDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_category, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
        Toast.makeText(this, navigationView.getMenu().getItem(2).setChecked(true).toString(), Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_category, null, false);
        recyclerView = (RecyclerView)findViewById(R.id.activitycategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryDetailsList = new ArrayList<>();
       user_id =((MyApplication)this.getApplication()).getUserId();
        categoryDetails(user_id);

        }
        public void categoryDetails(final String user_id){
        final String categoryApi = Host_ip_Config.hostofficeIp+"/apis/manage-categories/cate_dt-usr-cate/"+user_id;
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



