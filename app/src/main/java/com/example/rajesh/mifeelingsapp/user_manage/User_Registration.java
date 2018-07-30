package com.example.rajesh.mifeelingsapp.user_manage;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
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
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class User_Registration extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = User_Registration.this;

    private NestedScrollView nestedScrollView;
    private TextView textInputEditTextUserid;
    private TextView textInputEditTextAge;
    private TextView textInputEditTextUserName;
    private TextView textInputEditTextPhone;
    private TextView textInputEditTextGender;
    private TextView textInputEditTextEmail;
    private TextView textInputEditTextPassword;
    private AppCompatButton ButtonRegister;
    private AppCompatTextView LoginLink;
    Host_ip_Config host_ip_config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);
        getSupportActionBar().hide();
        initViews();
        initListeners();

    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputEditTextUserid = (TextView) findViewById(R.id.singupId);
        textInputEditTextUserName = (TextView) findViewById(R.id.fullname);
        textInputEditTextPhone = (TextView) findViewById(R.id.phone);
        textInputEditTextGender = (TextView) findViewById(R.id.gender);
        textInputEditTextAge = (TextView) findViewById(R.id.age);
        textInputEditTextEmail = (TextView) findViewById(R.id.email);
        textInputEditTextPassword = (TextView) findViewById(R.id.password);

        ButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        LoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    private void initListeners() {
        ButtonRegister.setOnClickListener(this);
        LoginLink.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                regMethod();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }


    public void regMethod() {
        final String id = textInputEditTextUserid.getText().toString().trim();
        final String uName = textInputEditTextUserName.getText().toString().trim();
        final String phone = textInputEditTextPhone.getText().toString().trim();
        final String gender = textInputEditTextGender.getText().toString().trim();
        final String email = textInputEditTextEmail.getText().toString().trim();
        final String pass = textInputEditTextPassword.getText().toString().trim();
        final String age = textInputEditTextAge.getText().toString().trim();
      //  String MobilePattern = "[0-9]{10}";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (id.equals("")) {
            textInputEditTextUserid.setError("enter user id");
        } else if (uName.equals("")) {
            textInputEditTextUserName.setError("enter User name");

        } else if (phone.equals("")) {
            textInputEditTextPhone.setError("enter phone number");
        } else if (phone.length() < 10) {
            textInputEditTextPhone.setError("phone number should be 10 digits");


        } else if (gender.equals("")) {
            textInputEditTextGender.setError("enter gender");
        } else if (!gender.equals("male") || gender.equals("female") || gender.equals("others")) {
            textInputEditTextGender.setError("male/female/other are allowed");
        } else if (age.length()>2) {
            textInputEditTextAge.setError("only two digit allowed");

        } else if (email.equals("")) {
            textInputEditTextEmail.setError("enter email id");
        } else if (!email.matches(emailPattern)) {
            textInputEditTextEmail.setError("enter valid email id");
        } else if (pass.equals("")) {
            textInputEditTextPassword.setError("enter password");
        } else {
            try {
             RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

                    jsonBody.put("_id", id);
                    jsonBody.put("password", pass);
                    jsonBody.put("user_name", uName);
                    jsonBody.put("mob_no", phone);
                    jsonBody.put("email", email);
                    jsonBody.put("gender", gender);
                    jsonBody.put("age", age);

                        String URL = host_ip_config.hostofficeIp+"/apis/manage-user/users/create";

                        final String mRequestBody = jsonBody.toString();
                        Log.e("json data",mRequestBody);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Response is", response);
                                if (response.equals("200")){
                                    Toast.makeText(activity, "Signup successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
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
}