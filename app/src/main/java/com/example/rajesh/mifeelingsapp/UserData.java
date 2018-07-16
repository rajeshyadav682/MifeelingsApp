package com.example.rajesh.mifeelingsapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class UserData {
    Context context;
    public String userid;
    SharedPreferences sharedPreferences;

    public void removeUser() {
        sharedPreferences.edit().clear().commit();
    }

    public String getUserid() {
        userid = sharedPreferences.getString("userdata", "");
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
        sharedPreferences.edit().putString("userdata", userid).commit();
    }

    public UserData(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", context.MODE_PRIVATE);

    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    private String user_name;
    private String email_id;

}