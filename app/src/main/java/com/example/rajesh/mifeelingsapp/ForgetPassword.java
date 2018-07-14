package com.example.rajesh.mifeelingsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {
    private EditText f_id;
    private EditText f_email;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        f_id = (EditText) findViewById(R.id.fuserid);
        mButton = (Button) findViewById(R.id.mbtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstSeriveceCall();
            }
        });



        }
    public void firstSeriveceCall() {
        String user_id = f_id.getText().toString();
        if (user_id.equals("")) {
            f_id.setError("Enter User id");
        } else {
            RequestQueue queue = Volley.newRequestQueue(this);
            String checkuserUrl = "http://110.227.190.241:7070/apis/manage-user/users/psw-frgt/user_chk/" + user_id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, checkuserUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("first service Response", response.toString());
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                String uid = jsonObj.getString("_id");
                                String mobileNumber = jsonObj.getString("mob_no");
                          /*  String msg = jsonObj.getString("msg");
                            //        String msg_code = jsonObj.getString("msg_code");
                            //   Toast.makeText(ForgetPassword.this, msg, Toast.LENGTH_SHORT).show();
                            //  Toast.makeText(ForgetPassword.this, mobileNumber, Toast.LENGTH_SHORT).show();
                           *//* if (msg.equals("User Does Not Exist") || mobileNumber.equals(null) || response.equals("User Does Not Exist")) {
                                Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                                finish();
                            } else {*/

                                secondServiceCall(mobileNumber, uid);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("json exception", e.getMessage());
                                if (e.getMessage().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                                    Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                                }
                                if (e.getMessage().equals("No value for mob_no")) {
                                    Toast.makeText(ForgetPassword.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("First service error", error.toString());
                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.ConnectException: Network is unreachable")) {
                        Toast.makeText(getApplicationContext(), "please enable internet connection", Toast.LENGTH_SHORT).show();
                    }
                    // progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                }
            });

            queue.add(stringRequest);
        }
    }
       public void secondServiceCall(final String phnNo, final String userid)
        {
            String optApi="http://110.227.190.241:7070/apis/manage-user/users/psw-frgt/otp-req/"+phnNo;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.GET, optApi, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("second service response",response.toString());
                            try {
                            JSONObject jsonObj = new JSONObject(String.valueOf(response));
                                String otpNumber = jsonObj.getString("otp");
                                Log.e("otp is",otpNumber);
                                Intent intent = new Intent(getApplicationContext(),OtpSection.class);
                                intent.putExtra("userid",userid);
                                intent.putExtra("phoneNumber",phnNo);
                                intent.putExtra("otp",otpNumber);
                    //            intent.putExtra("_id",user_id);
                                startActivity(intent);


                                Toast.makeText(ForgetPassword.this, otpNumber, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("second service error",error.toString());
                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
        }

    }

