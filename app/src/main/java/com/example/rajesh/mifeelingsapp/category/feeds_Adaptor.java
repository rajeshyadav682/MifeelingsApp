package com.example.rajesh.mifeelingsapp.category;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.example.rajesh.mifeelingsapp.Comment_show;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.user_manage.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class feeds_Adaptor extends RecyclerView.Adapter<feeds_Adaptor.ViewHolder> {

    private PopupWindow popupWindow;
    private Context context;
    private List<CategoryEvents_Details> categoryEvents_details;
    public feeds_Adaptor(Context context, List<CategoryEvents_Details> categoryDetailsList) {
        this.context = context;
        this.categoryEvents_details=categoryDetailsList;



    }

    @NonNull
    @Override
    public feeds_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.events_details_items, null);
        return new feeds_Adaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final feeds_Adaptor.ViewHolder holder, final int position) {
         final CategoryEvents_Details events_details = categoryEvents_details.get(position);
         MyApplication.getInstance().setLikeCount( events_details.getLike());
         holder.feed.setText(events_details.getFeed());
         holder.uname.setText(events_details.getUid());


        Glide.with(context)
                .load(Host_ip_Config.hostofficeIp+"/media/images/mif_users/"+events_details.
                        getUid()+".png")
                .into(holder.userimgae);
        holder.like_count.setText(events_details.getLike());
        holder.date.setText(events_details.getFeed_date());

        if (events_details.getPost_type()==0) {


            holder.post_type.setText("Private");
        }
        else {
            holder.post_type.setText("Public");
        }
        if (events_details.getUser_like().equals("0")){
            holder.likebtn.setVisibility(View.INVISIBLE);
            holder.dislikebtn.setVisibility(View.VISIBLE);


        }
        else {
            holder.likebtn.setVisibility(View.VISIBLE);
            holder.dislikebtn.setVisibility(View.INVISIBLE);

        }
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.comment_popup, null);
                popupWindow  = new PopupWindow(view,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(holder.RelatvePopup, Gravity.NO_GRAVITY,180,180);

            }
        });

        holder.likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likebtn.setVisibility(View.INVISIBLE);
                holder.dislikebtn.setVisibility(View.VISIBLE);
                likepost(events_details.getUid(),events_details.getId(),1);


            }
        });
        holder.dislikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dislikebtn.setVisibility(View.INVISIBLE);
                holder.likebtn.setVisibility(View.VISIBLE);
                unlike_post(events_details.getUid(),events_details.getId(),0);


            }
        });

    }

    private void likepost( String uid,String post_id,int like_no) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String likePostAPi = "http://110.227.190.241:7070"+"/apis/manage-feeds/upt-usr-feeds/"+uid+"/"+post_id+"/"+like_no;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, likePostAPi, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("like Response is", response);
                if (response.equals("200")) {
                    Log.e(" like res", response);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("like Error is", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/jsonBodybody; charset=utf-8";
            }


        };

        requestQueue.add(stringRequest);


    }
    private void unlike_post( String uid,String post_id,int like_no) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String likePostAPi = "http://110.227.190.241:7070"+"/apis/manage-feeds/upt-usr-feeds/"+uid+"/"+post_id+"/"+like_no;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, likePostAPi, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("unlike Response is", response);
                if (response.equals("200")) {
                    Log.e(" unlike res", response);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("unlike Error is", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/jsonBodybody; charset=utf-8";
            }


        };

        requestQueue.add(stringRequest);


    }
   /* public void onShowPopup(View v){

                LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.events_details_items, null,false);

        ListView listView = (ListView)view.findViewById(R.id.commentsListView);

        // get device size
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        int pxWidth = display.widthPixels;
        float dpWidth = pxWidth / display.density;
        int pxHeight = display.heightPixels;
        float dpHeight = pxHeight / display.density;


        // fill the data to the list items
        setSimpleList(listView);


        // set height depends on the device size
        popupWindow = new PopupWindow(view, pxWidth,pxHeight, true );
        // set a background drawable with rounders corners
      *//*  popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.fb_popup_bg));
        popupWindow.setBackgroundDrawable(R.drawable.commnt_bg);
      popupWindow.setBackgroundResource(R.drawable.commnt_bg);*//*
        // make it focusable to show the keyboard to enter in `EditText`
        popupWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popupWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
    }


    void setSimpleList(ListView listView){

        ArrayList<String> contactsList = new ArrayList<String>();

        for (int index = 0; index < 10; index++) {
            contactsList.add("I am @ index " + index + " today " + Calendar.getInstance().getTime().toString());
        }

        listView.setAdapter(new ArrayAdapter<String>(context,
                R.layout.comment_list, android.R.id.text1,contactsList));
    }*/


    @Override
    public int getItemCount() {
        return categoryEvents_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
private RelativeLayout  RelatvePopup;
        private TextView uname;
        private CircleImageView userimgae;
        private ImageButton likebtn;
        private ImageButton dislikebtn;
        private TextView date;
        TextView like_count;
        private TextView feed;
        private TextView post_type;
        private TextView comment;
        public ViewHolder(View itemView) {
            super(itemView);
            userimgae=(CircleImageView) itemView.findViewById(R.id.userimage);
            likebtn = (ImageButton)itemView.findViewById(R.id.like_on);
            dislikebtn= (ImageButton)itemView.findViewById(R.id.like_off);
            like_count=(TextView)itemView.findViewById(R.id.like_count);
            feed = (TextView)itemView.findViewById(R.id.feed);
            date = (TextView)itemView.findViewById(R.id.feed_date);
            post_type=(TextView)itemView.findViewById(R.id.post_type);
            uname = (TextView)itemView.findViewById(R.id.uname);
            comment = (TextView)itemView.findViewById(R.id.cmnt);



        }
    }

}
