package com.example.rajesh.mifeelingsapp.user_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rajesh.mifeelingsapp.Host_ip_Config;
import com.example.rajesh.mifeelingsapp.R;
import com.example.rajesh.mifeelingsapp.Home_Page.UserData;
import com.example.rajesh.mifeelingsapp.Home_Page.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private Button btnlogin;
    TextView createaccount;
    private TextView forgetp;
    private ProgressBar progressBar;
    Host_ip_Config host_ip_config;
    UserData userData;
    String network_Error="com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable";
    String network_error_show = "Please Enable Internet Connection";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initViews();
        initListeners();
    }



    private void initViews() {
        textInputEditTextEmail = (EditText) findViewById(R.id.inputId);
        textInputEditTextPassword = (EditText) findViewById(R.id.inputPass);
        createaccount =(TextView)findViewById(R.id.createaccountlink);
        btnlogin = (Button) findViewById(R.id.loginBtn);
        forgetp = (TextView)findViewById(R.id.forgetpass) ;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
    private void initListeners() {
        btnlogin.setOnClickListener(this);
        createaccount.setOnClickListener(this);
        forgetp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                apiCall();
                break;
            case R.id.createaccountlink:
                Intent intentRegister = new Intent(getApplicationContext(), User_Registration.class);
                startActivity(intentRegister);
                break;
            case R.id.forgetpass:
                Intent forgetpassIntent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(forgetpassIntent);
        }
    }


    public void apiCall() {

        final String user_id = textInputEditTextEmail.getText().toString();
        ((MyApplication) this.getApplication()).setUserId(user_id);
        String pass = textInputEditTextPassword.getText().toString();

        if (user_id.equals("")) {
            textInputEditTextEmail.setError("enter user id");

        } else if (pass.equals("")) {
            textInputEditTextPassword.setError("enter password");
        } else {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = host_ip_config.hostofficeIp+"/apis/manage-user/users/"+user_id+"/"+pass;
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Display the first 500 characters of the response string.
                            Log.e("Response is: ", response.toString());
                            //     Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                int test = jsonObj.getInt("msg_code");
                                progressBar.setVisibility(View.GONE);
                                if (test == 1) {
                                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                   userData = new UserData(getApplicationContext());
                                    userData.setUserid(user_id);
                                    Log.e("userid - ",user_id);
                                    Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                                    intent.putExtra("_id",user_id);
                                    Log.e("userid intent",userData.getUserid());
                                    startActivity(intent);
                                    overridePendingTransitionEnter();
                                }
                                if (test != 1) {
                                    Toast.makeText(MainActivity.this, jsonObj.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (e.getMessage().equals(network_Error)){
                                    Toast.makeText(MainActivity.this, network_error_show, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("That didn't work!", error.toString());
                    if (error.toString().equals(network_Error)){
                        Toast.makeText(MainActivity.this, network_error_show, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }
    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    }

