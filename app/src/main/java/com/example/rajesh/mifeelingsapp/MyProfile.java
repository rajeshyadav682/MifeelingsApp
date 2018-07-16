package com.example.rajesh.mifeelingsapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ExecutorDelivery;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    private NestedScrollView nestedScrollView;
    private TextView textInputEditTextUserid;
    private EditText textInputEditTextAge;
    private EditText textInputEditTextUserName;
    private EditText textInputEditTextPhone;
    private EditText textInputEditTextGender;
    private EditText textInputEditTextEmail;
    private TextView textInputEditTextPassword;
    private Button profileDone;
    private Button profileEdit;
    private Button passwordchangebtn;
    private Button emailchnage;
    private Button age_chnagebtn;
    private Button genderChange;
    private Button phoneChnager;
    private CircleImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().hide();
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        // passwordchangebtn  = (Button) findViewById(R.id.myprofilepass);


        final String user_id = getIntent().getExtras().getString("_id");
        userDetails(user_id);
        profileEdit = (Button) findViewById(R.id.profil_edit);
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

    public void userDetails(final String user_id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/users-profile/" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        Log.e("Response is: ", response.toString());
                        //     Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.e("my profile response", response);
                            String uid = jsonObj.getString("_id");
                            final String uname = jsonObj.getString("user_name");
                            String phone = jsonObj.getString("mob_no");
                            String gender = jsonObj.getString("gender");
                            String age = jsonObj.getString("age");
                            final String email_id = jsonObj.getString("email");
                            final String pass = jsonObj.getString("password");
                            String image = jsonObj.getString("image");
                            textInputEditTextUserid = (TextView) findViewById(R.id.getid);
                            textInputEditTextUserName = (EditText) findViewById(R.id.getname);
                            textInputEditTextPhone = (EditText) findViewById(R.id.getphone);
                            textInputEditTextGender = (EditText) findViewById(R.id.getgender);
                            textInputEditTextAge = (EditText) findViewById(R.id.getage);
                            textInputEditTextEmail = (EditText) findViewById(R.id.getemail);
                            textInputEditTextPassword = (TextView) findViewById(R.id.getpass);
                            textInputEditTextUserid.setText(uid);
                            textInputEditTextUserid.setVisibility(View.VISIBLE);
                            textInputEditTextUserName.setVisibility(View.VISIBLE);
                            textInputEditTextPhone.setVisibility(View.VISIBLE);
                            textInputEditTextGender.setVisibility(View.VISIBLE);
                            textInputEditTextAge.setVisibility(View.VISIBLE);
                            textInputEditTextEmail.setVisibility(View.VISIBLE);
                            textInputEditTextPassword.setVisibility(View.VISIBLE);
                            profileImage = (CircleImageView) findViewById(R.id.user_profile_photo);
                            Glide.with(getApplicationContext()).load(Host_ip_Config.hostofficeIp + image).into(profileImage);

                            textInputEditTextUserName.setText(uname);
                            textInputEditTextPhone.setText(phone);
                            textInputEditTextGender.setText(gender);
                            textInputEditTextAge.setText(age);
                            textInputEditTextEmail.setText(email_id);
                            textInputEditTextPassword.setText(pass);
                            textInputEditTextUserName.setEnabled(false);
                            textInputEditTextPhone.setEnabled(false);
                            textInputEditTextGender.setEnabled(false);
                            textInputEditTextAge.setEnabled(false);
                            textInputEditTextEmail.setEnabled(false);
                            profileEdit = (Button) findViewById(R.id.profil_edit);
                            profileDone = (Button) findViewById(R.id.profile_done);
                            profileEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textInputEditTextUserName.setEnabled(true);
                                    textInputEditTextPhone.setEnabled(true);
                                    textInputEditTextGender.setEnabled(true);
                                    textInputEditTextAge.setEnabled(true);
                                    textInputEditTextEmail.setEnabled(true);
                                    Toast.makeText(MyProfile.this, "edit", Toast.LENGTH_SHORT).show();


                                }
                            });
                            profileDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateDetails(user_id);
                                    Toast.makeText(MyProfile.this, "Profile Details Updated", Toast.LENGTH_SHORT).show();
                                 /*   Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                                    startActivity(intent);*/
                                }
                            });

                            passwordchangebtn = (Button) findViewById(R.id.myprofilepass);
                            passwordchangebtn.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onClick(View v) {
                                 passwordchangebtn.setBackground(getResources().getDrawable(R.drawable.btnborderchanger));

                                    String old_pass = pass;
                                    Intent intent = new Intent(getApplicationContext(),Changepassword.class);
                                    intent.putExtra("oldpass",pass);
                                    intent.putExtra("id",user_id);
                                    startActivity(intent);
                                    Log.e("username get",uname);
                                    Log.e("email get",email_id);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                            if (e.getMessage().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                                Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("That didn't work!", error.toString());
                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                    Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(stringRequest);
    }

    public void updateDetails(String id) {
        try {
            Toast.makeText(this, "update method", Toast.LENGTH_SHORT).show();
            String updateName = textInputEditTextUserName.getText().toString();
            String updatePhoneNo = textInputEditTextPhone.getText().toString();
            String updateGender = textInputEditTextGender.getText().toString();
            String updateEmail = textInputEditTextEmail.getText().toString();
            String updateAge = textInputEditTextAge.getText().toString();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_name", updateName);
            jsonBody.put("mob_no", updatePhoneNo);
            jsonBody.put("email", updateEmail);
            jsonBody.put("gender", updateGender);
            jsonBody.put("age", updateAge);

            String URL = Host_ip_Config.hostofficeIp + "/apis/manage-user/users/alter-users-profile/" + id;

            final String mRequestBody = jsonBody.toString();
            Log.e("json data", mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(" update Response is", response);
                    if (response.equals("200")) {
                        Toast.makeText(getApplicationContext(), "Details Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error is", error.toString());
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
}