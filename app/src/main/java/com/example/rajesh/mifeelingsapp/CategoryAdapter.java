package com.example.rajesh.mifeelingsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
   private Context context;
   private List<CategoryDetails>  categoryDetails;
    public CategoryAdapter(Context context ,List<CategoryDetails> categoryDetails) {
        this.context = context;
        this.categoryDetails = categoryDetails;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.categorylist_item, null);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     final CategoryDetails categoryDetails1 = categoryDetails.get(position);
        Glide.with(context)
                .load(categoryDetails1.getImage())
                .into(holder.img);
        holder.total_folowrs.setText(categoryDetails1.getTotalFollowers());

        if (categoryDetails1.getUserfolow()==1)
        {
            holder.folwbtn.setText("Unfollow");
        }
        else{

                holder.folwbtn.setText("follow");

        }
        Log.e("follow", String.valueOf(categoryDetails1.getUserfolow()));
        holder.catname.setText(categoryDetails1.getCat_name());
        holder.folwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat_id = categoryDetails1.getId();
                String uid = categoryDetails1.getUser_id();
                Toast.makeText(context, "id"+categoryDetails1.getId(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "user id"+categoryDetails1.getUser_id(), Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String passChangeApi = Host_ip_Config.hostofficeIp+"/apis/manage-categories/usr_cate-follows/usr-cate-upt";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("_id",uid);
                    jsonBody.put(cat_id,categoryDetails1.getUserfolow());
                    Toast.makeText(context, cat_id+categoryDetails1.getUserfolow(), Toast.LENGTH_SHORT).show();
                    final String mRequestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, passChangeApi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Put Response is", response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Put Error is", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/jsonBodybody; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);



                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        private TextView total_folowrs;
        private ImageView img;
        private TextView catname;
        private Button folwbtn;


        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            total_folowrs = (TextView)itemView.findViewById(R.id.totalfolows);
            catname = (TextView)itemView.findViewById(R.id.catname);

            folwbtn = (Button)itemView.findViewById(R.id.folowbtn);


        }
    }
}
