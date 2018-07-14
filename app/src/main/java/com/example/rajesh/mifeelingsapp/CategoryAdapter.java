package com.example.rajesh.mifeelingsapp;

import android.content.Context;
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

import com.bumptech.glide.Glide;

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
     CategoryDetails categoryDetails1 = categoryDetails.get(position);
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
